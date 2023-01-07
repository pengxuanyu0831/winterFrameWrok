package cn.winter.bean.factory.support;

import cn.winter.bean.BeansException;
import cn.winter.bean.factory.BeanFactory;
import cn.winter.bean.factory.ConfigurableBeanFactory;
import cn.winter.bean.factory.config.BeanDefinition;
import cn.winter.bean.factory.config.BeanPostProcessor;
import cn.winter.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2021/12/01 22:40
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
    /** BeanPostProcessors to apply in createBean */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    /** ClassLoader to resolve bean class names with, if necessary */
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

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

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getClassLoader() {
        return this.beanClassLoader;
    }
}
