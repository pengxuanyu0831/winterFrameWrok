package cn.winter.bean.factory.support;

import cn.winter.bean.BeansException;
import cn.winter.bean.factory.DisposableBean;
import cn.winter.bean.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * @author xuanyu peng
 * @description:
 * @date 2022/12/28 23:03
 */
public class DisposableBeanAdapter implements DisposableBean {
    private final Object bean;

    private final String beanName;

    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();

        }

        // 防止二次销毁
        if (destroyMethodName != null && !destroyMethodName.equals("") && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
            Method method = bean.getClass().getMethod(destroyMethodName);
            if (null == destroyMethodName) {
                throw new BeansException("Could not find a destroy method name " + destroyMethodName + "on bean with name " + beanName);
            }
            method.invoke(bean);
        }
    }
}
