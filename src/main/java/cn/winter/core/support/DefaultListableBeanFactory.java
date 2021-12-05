package cn.winter.core.support;

import cn.winter.core.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2021/12/05 09:30
 */
public class DefaultListableBeanFactory extends AbstracAutowireCapableBeanFactory implements BeanDefintionRegistry {
    Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    protected BeanDefinition getBeanDefinition(String name) {
        return beanDefinitionMap.get(name);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }

    @Override
    public void registryBeanDefintion(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }
}
