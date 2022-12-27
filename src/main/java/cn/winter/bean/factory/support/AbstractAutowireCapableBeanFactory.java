package cn.winter.bean.factory.support;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.bean.BeanUtil;
import cn.winter.bean.BeansException;
import cn.winter.bean.PropertyValue;
import cn.winter.bean.PropertyValues;
import cn.winter.bean.factory.config.AutowireCapableBeanFactory;
import cn.winter.bean.factory.config.BeanDefinition;
import cn.winter.bean.factory.config.BeanPostProcessor;
import cn.winter.bean.factory.config.BeanReference;
import cn.winter.bean.factory.xml.InitializingBean;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2021/12/05 09:22
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstracBeanFactory implements AutowireCapableBeanFactory {
    // 实例化的实现有两种 这里用的CGlib的实现方式
    private InstantiationStrategy instantiationStrategy = new CglibInstantiationStrategy();
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition,Object... args) {
        Object bean = null;
        try{
            // bean 对象的实例化
            bean = this.createBeanInstance(beanDefinition, beanName, args);
            // 填充对象属性，在实例化之后
            this.applyPropertyValues(beanName,bean,beanDefinition);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        // 实例化完成， 放到单例bean 的对象缓存中
        addBeanRegistry(beanName,bean);
        return bean;
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
