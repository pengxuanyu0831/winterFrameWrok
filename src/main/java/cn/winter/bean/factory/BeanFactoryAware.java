package cn.winter.bean.factory;

import cn.winter.bean.BeansException;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/1/7 21:05
 */
public interface BeanFactoryAware extends Aware{
    void setBeanFactory(BeanFactory factory) throws BeansException;
}
