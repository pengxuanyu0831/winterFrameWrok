package cn.winter.core.config;

import cn.winter.core.PropertyValue;
import cn.winter.core.PropertyValues;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2021/11/20 22:23
 */
public class BeanDefinition {
    private Class beanClass;

    private PropertyValues propertyValues;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues;
    }

    public Object getBean() {
        return beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
