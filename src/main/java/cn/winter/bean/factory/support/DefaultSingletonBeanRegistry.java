package cn.winter.bean.factory.support;

import cn.winter.bean.BeansException;
import cn.winter.bean.factory.DisposableBean;
import cn.winter.bean.factory.ObjectFactory;
import cn.winter.bean.factory.config.SingletonBeanRegistry;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program spring-core
 * @description: 单例注册bean
 * @author: pengxuanyu
 * @create: 2021/12/01 22:35
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    protected static final Object NULL_OBJECT = new Object();

    private Map<String, Object> singletonBeanRegistry = new HashMap<>();

    // 这个接口的方法最终会被 #AbstractApplicationContext 的 close() 方法通过getBeanFactory().destroySingletons()调用
    private final Map<String, DisposableBean> disposableBeanMap = new HashMap<>();

    // 用于存放已经创建好的单例对象 一级缓存
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    // 用于存放提前暴露的单例对象 二级缓存
    protected final Map<String, Object> earlySingletonObjects = new HashMap<>();

    // 三级缓存，存放代理对象
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<String, ObjectFactory<?>>();

    /**
     * spring的实现方式
     * DefaultSingletonBeanRegistry#getSingleton(String beanName, boolean allowEarlyReference)
     * @param beanName
     * @return
     */
    @Override
    public Object getSingleton(String beanName) {
        // 从一级缓存中获取
        Object o = singletonObjects.get(beanName);
        if (null == o) {
            Object secondObject = earlySingletonObjects.get(beanName);
            // 判断二级缓存中是否有对象，这个对象就是代理对象，因为只有代理对象才会放到三级缓存中
            if (null == secondObject) {
                Object singletonFactory = singletonFactories.get(beanName);
                if (null != singletonFactory) {
                    o = singletonFactory;
                    // 把三级缓存中的代理对象中的真实对象获取出来，放入二级缓存中
                    earlySingletonObjects.put(beanName, o);
                    singletonFactories.remove(beanName);
                }
            }
        }

        return o;
    }

    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonBeanRegistry.put(beanName, singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeanMap.put(beanName, bean);
    }


    // 只能给继承了这个类的方法调用
    protected void addBeanRegistry(String beanName, Object bean) {
        singletonBeanRegistry.put(beanName, bean);
    }

    public void destroySingletons() {
        Set<String> keySet = this.disposableBeanMap.keySet();
        Object[] disposableBeanNames = keySet.toArray();

        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeanMap.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
