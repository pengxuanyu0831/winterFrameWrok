package cn.winter.bean.factory;

import cn.winter.bean.BeansException;
import cn.winter.bean.factory.config.BeanDefinition;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2021/11/20 22:24
 */
public interface BeanFactory {
    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;

    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    <T> T getBean(Class<T> type) throws InstantiationException, IllegalAccessException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

}
