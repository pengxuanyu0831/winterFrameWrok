package cn.winter.bean.factory;

import cn.winter.bean.BeansException;
import cn.winter.bean.PropertyValue;
import cn.winter.bean.PropertyValues;
import cn.winter.bean.factory.config.BeanDefinition;
import cn.winter.bean.factory.config.BeanFactoryPostProcessor;
import cn.winter.core.io.DefaultResourceLoader;
import cn.winter.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * @author xuanyu peng
 * @description: 处理占位符配置
 * @date 2023/4/6 21:56
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;


    @Override
    public void postProcessorBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());

            String[] names = configurableListableBeanFactory.getBeanDefinitionNames();
            for (String n : names) {
                BeanDefinition bd = configurableListableBeanFactory.getBeanDefinition(n);
                PropertyValues propertyValues = bd.getPropertyValues();
                for (PropertyValue p : propertyValues.getPropertyValues()) {
                    Object v = p.getValue();
                    if (!(v instanceof String)) {
                        String varStr = (String) v;
                        StringBuilder sb = new StringBuilder(varStr);
                        int prefix = varStr.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                        int suffix = varStr.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                        if (prefix != -1 && suffix != -1 && prefix < suffix) {
                            String propKey = varStr.substring(prefix + 2, suffix);
                            String propValue = properties.getProperty(propKey);
                            sb.replace(prefix, suffix + 1, propValue);
                            propertyValues.addPropertyValues(new PropertyValue(p.getName(), sb.toString()));
                        }
                    }
                }

            }
        } catch (IOException e) {
            throw new BeansException("Could not load properties!", e);
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
