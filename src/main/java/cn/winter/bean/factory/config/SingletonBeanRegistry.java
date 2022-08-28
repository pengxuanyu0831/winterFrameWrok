package cn.winter.bean.factory.config;

/**
 * @program spring-core
 * @description: 定义一个获取单例bean的接口
 * @author: pengxuanyu
 * @create: 2021/12/01 22:32
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);
}
