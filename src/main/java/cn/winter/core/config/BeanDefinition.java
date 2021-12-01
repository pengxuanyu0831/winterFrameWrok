package cn.winter.core.config;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2021/11/20 22:23
 */
public class BeanDefinition {
    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Object getBean() {
        return beanClass;
    }
}
