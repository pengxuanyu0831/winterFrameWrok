package cn.winter.bean.factory.config;

import cn.winter.bean.BeansException;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/3/29 23:26
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;
}
