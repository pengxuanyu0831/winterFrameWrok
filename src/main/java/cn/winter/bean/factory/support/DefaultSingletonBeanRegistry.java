package cn.winter.bean.factory.support;

import cn.winter.bean.BeansException;
import cn.winter.bean.factory.DisposableBean;
import cn.winter.bean.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    @Override
    public Object getSingleton(String beanName) {
        return singletonBeanRegistry.get(beanName);
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
