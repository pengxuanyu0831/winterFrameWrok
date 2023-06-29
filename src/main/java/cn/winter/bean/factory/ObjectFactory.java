package cn.winter.bean.factory;

import cn.winter.bean.BeansException;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/6/29 22:24
 */
public interface ObjectFactory<T> {
    T getObject() throws BeansException;

}
