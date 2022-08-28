package cn.winter.bean.factory.support;

import cn.winter.bean.BeansException;
import cn.winter.bean.factory.BeanFactory;
import cn.winter.bean.factory.config.BeanDefinition;

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
            return this.createBean(beanName, beanDefinition, args);
        }
    }

    protected abstract BeanDefinition getBeanDefinition(String name);

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition,Object... args);
}
