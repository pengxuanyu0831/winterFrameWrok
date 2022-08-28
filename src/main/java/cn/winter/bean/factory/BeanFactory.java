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
    public Object getBean(String name) throws BeansException;

    public Object getBean(String name, Object... args) throws BeansException;

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition);
}
