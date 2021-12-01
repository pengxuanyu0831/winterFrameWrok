package cn.winter.core.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @program spring-core
 * @description: 单例注册bean
 * @author: pengxuanyu
 * @create: 2021/12/01 22:35
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{
    private Map<String, Object> singletonBeanRegistry = new HashMap<>();
    @Override
    public Object getSingleton(String beanName) {
        return singletonBeanRegistry.get(beanName);
    }


    // 只能给继承了这个类的方法调用
    protected void addBeanRegistry(String beanName, Object bean) {
        singletonBeanRegistry.put(beanName, bean);
    }
}
