package cn.winter.core.support;

import cn.winter.BeansException;
import cn.winter.core.BeanFactory;
import cn.winter.core.config.BeanDefinition;
import cn.winter.core.config.DefaultSingletonBeanRegistry;

import java.util.Objects;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2021/12/01 22:40
 */
public abstract class AbstracBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    @Override
    public Object getBean(String beanName) {
        return doGetBean(beanName, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    private Object doGetBean(String beanName, Object... args) {
        Object bean = getSingleton(beanName);
        if (null != bean) {
            return bean;
        } else {
            // 这里没有实现获取方法，而是定义调用过程，以及提供抽象方法
            BeanDefinition beanDefinition = getBeanDefinition(beanName);
            return createBean(beanName, beanDefinition, args);
        }
    }

    protected abstract BeanDefinition getBeanDefinition(String name);

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition,Object... args);
}
