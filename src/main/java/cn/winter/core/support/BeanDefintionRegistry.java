package cn.winter.core.support;

import cn.winter.core.config.BeanDefinition;

public interface BeanDefintionRegistry {
    void registryBeanDefintion(String beanName, BeanDefinition beanDefinition);
}
