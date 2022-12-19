package cn.winter.context.surpport;

import cn.winter.bean.factory.support.DefaultListableBeanFactory;
import cn.winter.bean.factory.xml.XmlBeanDefinitionReader;

import java.io.IOException;

/**
 * @author xuanyu peng
 * @description:
 * @date 2022/12/19 21:52
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws IOException {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configsLocations = getConfigLocation();
        if (null != configsLocations) {
            reader.loadBeanDefinitions(configsLocations);
        }
    }

    protected abstract String[] getConfigLocation();
}
