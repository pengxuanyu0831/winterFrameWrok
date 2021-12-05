package cn.winter.core.support;

import cn.winter.core.config.BeanDefinition;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2021/12/05 09:22
 */
public abstract class AbstracAutowireCapableBeanFactory extends  AbstracBeanFactory{
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try{
            // bean 对象的实例化
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // 实例化完成， 放到单例bean 的对象缓存中
        addBeanRegistry(beanName,bean);
        return bean;
    }
}
