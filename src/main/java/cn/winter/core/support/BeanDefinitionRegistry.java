package cn.winter.core.support;

import cn.winter.core.config.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registryBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
