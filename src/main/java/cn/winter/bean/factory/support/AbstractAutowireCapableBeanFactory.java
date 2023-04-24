package cn.winter.bean.factory.support;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.bean.BeanUtil;
import cn.winter.bean.BeansException;
import cn.winter.bean.PropertyValue;
import cn.winter.bean.PropertyValues;
import cn.winter.bean.factory.*;
import cn.winter.bean.factory.config.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @program spring-core
 * @description: 单例模式和原型模式的区别就在于是否存放到内存中，如果是原型模式那么就不会存放到内存中，每次获取都重新创建对象，另外非 Singleton 类型的 Bean 不需要执行销毁方法
 * @author: pengxuanyu
 * @create: 2021/12/05 09:22
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    // 实例化的实现有两种 这里用的CGlib的实现方式
    private InstantiationStrategy instantiationStrategy = new CglibInstantiationStrategy();
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition,Object... args) {
        Object bean = null;
        try{
            // 判断是否返回代理对象
            bean = resolveBeforeInstantiation(beanName, beanDefinition);
            if (null != bean) {
                return bean;
            }
            // bean 对象的实例化
            bean = this.createBeanInstance(beanDefinition, beanName, args);
            // 在设置 Bean 属性之前，允许 BeanPostProcessor 修改属性值
            applyBeanPostProcessorsBeforeApplyingPropertyValues(beanName, bean, beanDefinition);
            // 填充对象属性，在实例化之后
            this.applyPropertyValues(beanName,bean,beanDefinition);
            // 执行bean 的前置 和后置方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (NoSuchMethodException e) {
            throw new BeansException("Instantiation of Bean Failed", e);
        }

        // 注册实现了 DisposableBean 接口的Bean 对象
        // 创建Bean 对象的实例的时候，先保存销毁的方法，方便后续调用
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        if (beanDefinition.isSingleton()) {
            // 实例化完成， 放到单例bean 的对象缓存中
            // 如果是单例bean 执行加入单例对象缓存中
            registerSingleton(beanName,bean);
        }
        return bean;
    }


    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 非单例bean对象，不执行销毁方法
        if (!beanDefinition.isSingleton()) {
            return;
        }
        if (bean instanceof DisposableBean || beanDefinition.getDestroyMethodName() != null) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (null != bean) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (null != result){
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 在设置 Bean 属性之前，允许 BeanPostProcessor 修改属性值
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor){
                PropertyValues pvs = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues(beanDefinition.getPropertyValues(), bean, beanName);
                if (null != pvs) {
                    for (PropertyValue propertyValue : pvs.getPropertyValues()) {
                        beanDefinition.getPropertyValues().addPropertyValues(propertyValue);
                    }
                }
            }
        }
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition,
                                        String beanName,
                                        Object... args) throws NoSuchMethodException {
        Constructor constructor = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] constructors = beanClass.getDeclaredConstructors();

        for (Constructor c : constructors) {
            if (null != args && c.getParameterTypes().length == args.length) {
                constructor = c;
                break;
            }
        }
        return instantiationStrategy.instantiate(beanDefinition, beanName, constructor, args);
    }

    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try{
            PropertyValues values = beanDefinition.getPropertyValues();
            for (PropertyValue v : values.getPropertyValues()) {
                String name = v.getName();
                Object value = v.getValue();
                if (value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                BeanUtil.setProperty(bean, name, value);
            }
        } catch (Exception e) {
            throw new BeanException("Error setting property value which bean name is [" + beanName + "]");
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }


    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {

        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }


        // 先执行 Before 处理
        Object o = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        try {
            // 执行 Bean 对象的初始化方法
            invokeInitMethods(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean 【" + beanName + "】 fail", e);
        }

        // 再执行 after 处理
        o = applyBeanPostProcessorsAfterInitialization(o, beanName);
        return o;
    }


    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethodName();
        // 加入判断是为了防止二次销毁
        if (initMethodName != null && !initMethodName.equals("") && !(bean instanceof InitializingBean)) {
            Method method = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (method == null) {
                throw new BeansException("Could not find an init method named 【" + initMethodName + "】 on bean which bean name is " + beanName);
            }
            method.invoke(bean);
        }
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor postProcessor : getBeanPostProcessors()) {
            Object current = postProcessor.postProcessBeforeInitialization(result, beanName);
            if (Objects.isNull(current)) {
                return result;
            }
            result = current;
        }
        return result;
    }


    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor postProcessor : getBeanPostProcessors()) {
            Object current = postProcessor.postProcessAfterInitialization(result, beanName);
            if (Objects.isNull(current)) {
                return result;
            }
            result = current;
        }
        return result;
    }
}
