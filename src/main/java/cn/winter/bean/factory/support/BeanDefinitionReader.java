package cn.winter.bean.factory.support;


import cn.winter.BeansException;
import cn.winter.core.io.Resource;
import cn.winter.core.io.ResourceLoader;
import cn.winter.core.support.BeanDefinitionRegistry;

public interface BeanDefinitionReader {
    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

}
