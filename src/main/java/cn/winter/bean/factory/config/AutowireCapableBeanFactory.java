package cn.winter.bean.factory.config;

import cn.winter.bean.BeansException;
import cn.winter.bean.factory.BeanFactory;

/**
 * @program spring-core
 * @description: 是在BeanFactory的基础上实现对已存在实例的管理。可以使用这个接口集成其他框架，捆绑并填充并不由Spring管理生命周期并已存在的实例
 * @author: pengxuanyu
 * @create: 2022/08/28 16:24
 * @version: 1.0
 */
/**
 * Extension of the {@link BeanFactory}
 * interface to be implemented by bean factories that are capable of
 * autowiring, provided that they want to expose this functionality for
 * existing bean instances.
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;

}
