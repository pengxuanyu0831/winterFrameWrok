package cn.winter.bean.factory.support;


import cn.winter.bean.BeansException;
import cn.winter.bean.factory.config.BeanDefinition;

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
