package cn.winter.bean.factory;

import cn.winter.bean.BeansException;
import cn.winter.bean.factory.config.BeanDefinition;
import cn.winter.bean.factory.config.BeanPostProcessor;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2022/08/28 16:18
 * @version: 1.0
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory,HierarchicalBeanFactory,ConfigurableBeanFactory{

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
