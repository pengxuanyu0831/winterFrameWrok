package cn.winter.bean.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.winter.bean.BeansException;
import cn.winter.bean.factory.support.AbstractBeanDefinitionReader;
import cn.winter.bean.PropertyValue;
import cn.winter.bean.factory.config.BeanDefinition;
import cn.winter.bean.factory.config.BeanReference;
import cn.winter.core.io.Resource;
import cn.winter.core.io.ResourceLoader;
import cn.winter.bean.factory.support.BeanDefinitionRegistry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 * @program spring-core
 * @description:
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
        } catch (ClassNotFoundException | IOException e) {
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

    // 这里是用hutool 工具类的，实际的spring框架是怎么用的？？？？
    protected void doLoadBeanDefinition(InputStream inputStream) throws ClassNotFoundException {
        Document document = XmlUtil.readXML(inputStream);
        Element element = document.getDocumentElement();
        NodeList nodes = element.getChildNodes();

        for (int i = 0; i < nodes.getLength(); i++) {
            if (!(nodes.item(i) instanceof Element)) {
                continue;
            }
            //
            if (!"bean".equals(nodes.item(i).getNodeName())) {
                continue;
            }

            // XML配置的属性
            Element el = (Element) nodes.item(i);
            String id = el.getAttribute("id");
            String name = el.getAttribute("name");
            String className = el.getAttribute("class");

            Class<?> aClass = Class.forName(className);
            // beanName 的优先级，id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(aClass.getSimpleName());
            }

            // 定义beanDefinition
            BeanDefinition beanDefinition = new BeanDefinition(aClass);
            // 填充属性
            for (int j = 0; j < el.getChildNodes().getLength(); j++) {
                if (!(el.getChildNodes().item(j) instanceof Element)) {
                    continue;
                }
                if (!"property".equals(el.getChildNodes().item(j).getNodeName())) {
                    continue;
                }
                Element property = (Element) el.getChildNodes().item(j);
                String attributeName = property.getAttribute("name");
                String attributeValue = property.getAttribute("value");
                String attributeRef = property.getAttribute("ref");

                Object obj = StrUtil.isNotEmpty(attributeRef) ? new BeanReference(attributeRef) : attributeValue;
                PropertyValue propertyValue = new PropertyValue(attributeName, obj);
                beanDefinition.getPropertyValues().addPropertyValues(propertyValue);

                // 不允许重复注册
                if (getRegistry().containsBeanDefinition(beanName)) {
                    throw new BeansException("Duplication bean name [" + beanName + "] is not allowed");
                }
                // 注册beanDefinition
                getRegistry().registryBeanDefinition(beanName, beanDefinition);
            }
        }
    }
}
