package cn.winter.bean.factory;

import cn.winter.bean.factory.config.BeanPostProcessor;
import cn.winter.bean.factory.config.SingletonBeanRegistry;

/**
 * Configuration interface to be implemented by most bean factories. Provides
 * facilities to configure a bean factory, in addition to the bean factory
 * client methods in the {@link cn.winter.bean.factory}
 * interface.
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2022/08/28 16:43
 * @version: 1.0
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
