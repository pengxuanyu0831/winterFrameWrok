package cn.winter.bean.factory.support;


import cn.winter.BeansException;
import cn.winter.core.io.Resource;
import cn.winter.core.io.ResourceLoader;

import java.io.IOException;

public interface BeanDefinitionReader {
    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException, IOException;

    void loadBeanDefinitions(Resource... resources) throws BeansException, IOException;

    void loadBeanDefinitions(String location) throws BeansException, IOException;

}
