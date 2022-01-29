package cn.winter.core;

import cn.winter.BeansException;
import cn.winter.core.config.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2021/11/20 22:24
 */
public interface BeanFactory {
    public Object getBean(String name) throws BeansException;

    public Object getBean(String name, Object... args) throws BeansException;

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition);
}
