package cn.winter.core;

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
    public Object getBean(String name);

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition);
}
