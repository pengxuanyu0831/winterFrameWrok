package cn.winter.bean.factory.config;

import cn.winter.bean.BeansException;
import cn.winter.bean.factory.ConfigurableListableBeanFactory;

/**
 * @program spring-core
 * @description: 在所有的BeanDefinition 加载完成后，实例化Bean对象之前，提供修改BeanDefinition的接口
 * @author: pengxuanyu
 * @create: 2022/08/27 23:00
 * @version: 1.0
 */
public interface BeanFactoryPostProcessor {
    void postProcessorBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException;
}
