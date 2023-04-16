package cn.winter.bean.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.winter.bean.BeansException;
import cn.winter.bean.factory.support.AbstractBeanDefinitionReader;
import cn.winter.bean.PropertyValue;
import cn.winter.bean.factory.config.BeanDefinition;
import cn.winter.bean.factory.config.BeanReference;
import cn.winter.context.annotation.ClassPathBeanDefinitionScanner;
import cn.winter.core.io.Resource;
import cn.winter.core.io.ResourceLoader;
import cn.winter.bean.factory.support.BeanDefinitionRegistry;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @program spring-core
 * @description: 对XML文件的解析
 * @author: pengxuanyu
 * @create: 2022/08/08 22:52
 * @version: 1.0
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException, IOException {
        try {
            try (InputStream inputStream = resource.getInputStream()) {
                doLoadBeanDefinition(inputStream);
            }
        } catch (ClassNotFoundException | IOException | DocumentException e) {
            throw new BeansException("IOException parsing XML doc from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException, IOException {
        for (Resource r : resources) {
            loadBeanDefinitions(r);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException, IOException {
        ResourceLoader resourceLoader = super.getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException, IOException {
        for (String s : locations) {
            loadBeanDefinitions(s);
        }
    }

    // 这里是用hutool 工具类的，实际的spring框架是怎么用的？？？？
    // 查看 package org.springframework.beans.factory.xml  ---spring-beans5.2.9
    protected void doLoadBeanDefinition(InputStream inputStream) throws ClassNotFoundException, DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();

        // 解析 context:component-scan 标签，扫描包中的类并提取相关信息，用于组装 BeanDefinition
        Element element = root.element("component-scan");
        if (null != element) {
            String basePackage = element.attributeValue("base-package");
            if (StrUtil.isEmpty(basePackage)) {
                throw new BeansException("The value of the base package attribute can not be empty or null!");
            }
            scanPackage(basePackage);
        }


        List<Element> nodes = root.elements();
        for (Element el : nodes) {

            String id = el.attributeValue("id");
            String name = el.attributeValue("name");
            String className = el.attributeValue("class");
            String initMethod = el.attributeValue("init-method");
            String destroyMethod = el.attributeValue("destroy-method");
            String scope = el.attributeValue("scope");


            Class<?> aClass = Class.forName(className);
            // beanName 的优先级，id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(aClass.getSimpleName());
            }

            // 定义beanDefinition
            BeanDefinition beanDefinition = new BeanDefinition(aClass);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethod);

            // step9 给bean对象添加scope属性的填充
            if (scope != null && !scope.equals("")) {
                beanDefinition.setScope(scope);
            }
            // 填充属性
            List<Element> property = el.elements("property");
            for (Element p : property) {

                String attributeName = p.attributeValue("name");
                String attributeValue = p.attributeValue("value");
                String attributeRef = p.attributeValue("ref");

                Object obj = StrUtil.isNotEmpty(attributeRef) ? new BeanReference(attributeRef) : attributeValue;
                PropertyValue propertyValue = new PropertyValue(attributeName, obj);
                beanDefinition.getPropertyValues().addPropertyValues(propertyValue);

                // 不允许重复注册
                if (getRegistry().containsBeanDefinition(beanName)) {
                    throw new BeansException("Duplication bean name [" + beanName + "] is not allowed");
                }
                // 注册beanDefinition
                getRegistry().registerBeanDefinition(beanName, beanDefinition);
            }
        }
    }

    private void scanPackage(String scanPath) {
        String[] basePackages = StrUtil.splitToArray(scanPath, ',');
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(basePackages);
    }
}
