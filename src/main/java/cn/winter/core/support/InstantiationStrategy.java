package cn.winter.core.support;


import cn.winter.BeansException;
import cn.winter.core.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 实例化策略接口
 */
public interface InstantiationStrategy {
    Object instantiate(BeanDefinition beanDefinition,
                       String beanName,
                       Constructor constructor,
                       Object[] args) throws BeansException;

}
