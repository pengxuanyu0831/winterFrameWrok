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
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {
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
    public void registryBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    /**
     * 判断是否包含指定名称的BeanDefinition
     *
     * @param beanName
     * @return
     */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }
}
