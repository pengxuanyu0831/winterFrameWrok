package cn.winter.bean.factory.support;

import cn.winter.bean.BeansException;
import cn.winter.bean.factory.BeanFactory;
import cn.winter.bean.factory.ConfigurableBeanFactory;
import cn.winter.bean.factory.FactoryBean;
import cn.winter.bean.factory.config.BeanDefinition;
import cn.winter.bean.factory.config.BeanPostProcessor;
import cn.winter.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2021/12/01 22:40
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {
// 原来继承的 DefaultSingletonBeanRegistry，修改
// 为继承 FactoryBeanRegistrySupport。因为需要扩展出创建 FactoryBean 对象的能
// 力，所以这就想一个链条服务上，截出一个段来处理额外的服务，并把链条再链接上
    /** BeanPostProcessors to apply in createBean */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

    /** ClassLoader to resolve bean class names with, if necessary */
    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    @Override
    public Object getBean(String beanName) {
        return doGetBean(beanName, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    private <T> T doGetBean(String beanName, Object... args) {
        Object bean = getSingleton(beanName);
        if (null != bean) {
            // 如果是FactoryBean，则需要调用FactoryBean#getObject
            return (T)getObjectForBeanInstance(bean, beanName);
        }
        // 这里没有实现获取方法，而是定义调用过程，以及提供抽象方法
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return (T) this.createBean(beanName, beanDefinition, args);
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        Object object = getCacheObjectForFactoryBean(beanName);
        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }
        return object;
    }

    protected abstract BeanDefinition getBeanDefinition(String name);

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition,Object... args);

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getClassLoader() {
        return this.beanClassLoader;
    }
}
