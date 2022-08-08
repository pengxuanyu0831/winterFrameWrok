package cn.winter.bean.factory.xml;

import cn.hutool.core.util.XmlUtil;
import cn.winter.BeansException;
import cn.winter.bean.factory.support.AbstractBeanDefinitionReader;
import cn.winter.core.io.Resource;
import cn.winter.core.io.ResourceLoader;
import cn.winter.core.support.BeanDefinitionRegistry;
import org.w3c.dom.Document;

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
    }
}
