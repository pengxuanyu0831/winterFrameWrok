package cn.winter.core.support;

import cn.winter.core.config.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registryBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 判断是否包含指定名称的BeanDefinition
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);
}
