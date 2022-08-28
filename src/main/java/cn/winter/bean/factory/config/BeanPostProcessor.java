package cn.winter.bean.factory.config;

import cn.winter.bean.BeansException;

/**
 * @program spring-core
 * @description: 在bean对象实例化之后修改bean对象，也可替换bean对象，与AOP相关
 * @author: pengxuanyu
 * @create: 2022/08/28 16:45
 * @version: 1.0
 */
public interface BeanPostProcessor {
    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在 Bean 对象执行初始化方法之后，执行此方法
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
