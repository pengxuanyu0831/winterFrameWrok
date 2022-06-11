package cn.winter.core.support;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.bean.BeanUtil;
import cn.winter.core.PropertyValue;
import cn.winter.core.PropertyValues;
import cn.winter.core.config.BeanDefinition;
import cn.winter.core.config.BeanReference;

import java.lang.reflect.Constructor;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2021/12/05 09:22
 */
public abstract class AbstractAutowireCapableBeanFactory extends  AbstracBeanFactory{
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
}
