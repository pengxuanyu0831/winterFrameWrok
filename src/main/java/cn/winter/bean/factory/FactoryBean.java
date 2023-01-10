package cn.winter.bean.factory;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/1/10 23:06
 */
public interface FactoryBean<T> {
    T getObject() throws Exception;

    Class<?> getObjectType();

    boolean isSingleton();
}
