package cn.winter.core.support;

import cn.winter.core.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2021/12/05 09:22
 */
public abstract class AbstracAutowireCapableBeanFactory extends  AbstracBeanFactory{
    private InstantiationStrategy instantiationStrategy = new CglibInstantiationStrategy();
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition,Object... args) {
        Object bean = null;
        try{
            // bean 对象的实例化
            bean = createBeanInstance(beanDefinition, beanName, args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        // 实例化完成， 放到单例bean 的对象缓存中
        addBeanRegistry(beanName,bean);
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition,
                                        String beanName,
                                        Object... args) throws NoSuchMethodException {
        Constructor constructor = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] constructors = beanClass.getDeclaredConstructors();

        for (Constructor c : constructors) {
            if (null != args && c.getParameterTypes().length == args.length) {
                constructor = c;
                break;
            }
        }
        return instantiationStrategy.instantiate(beanDefinition, beanName, constructor, args);
    }
}
