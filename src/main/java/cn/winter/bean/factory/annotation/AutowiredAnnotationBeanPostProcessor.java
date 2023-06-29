package cn.winter.bean.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.winter.bean.BeansException;
import cn.winter.bean.PropertyValues;
import cn.winter.bean.factory.BeanFactory;
import cn.winter.bean.factory.BeanFactoryAware;
import cn.winter.bean.factory.ConfigurableBeanFactory;
import cn.winter.bean.factory.ConfigurableListableBeanFactory;
import cn.winter.bean.factory.config.InstantiationAwareBeanPostProcessor;
import cn.winter.util.ClassUtils;

import java.lang.reflect.Field;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/4/24 22:36
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private ConfigurableListableBeanFactory beanFactory;




    @Override
    public void setBeanFactory(BeanFactory factory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException, InstantiationException, IllegalAccessException {

        Class<?> clazz = bean.getClass();
        // 需要判断是否是cglib代理类
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;

        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {
            // 1 处理@Value
            Value value = f.getAnnotation(Value.class);
            if (null != value) {
                String valueStr = value.value();
                valueStr = beanFactory.resolveEmbeddedValue(valueStr);
                BeanUtil.setFieldValue(bean, f.getName(), valueStr);
            }

            // 2 处理@Autowired
            Autowired autowired  = f.getAnnotation(Autowired.class);
            if (null != autowired) {
                Class<?> type = f.getType();
                String dependentBeanName = null;

                Qualifier qualifier = f.getAnnotation(Qualifier.class);
                Object dependentBean = null;

                if (null != qualifier) {
                    dependentBeanName = qualifier.value();
                    dependentBean = beanFactory.getBean(dependentBeanName, type);
                } else {
                    dependentBean = beanFactory.getBean(type);
                }
                BeanUtil.setFieldValue(bean, f.getName(), dependentBean);
            }
        }
        return pvs;
    }
}
