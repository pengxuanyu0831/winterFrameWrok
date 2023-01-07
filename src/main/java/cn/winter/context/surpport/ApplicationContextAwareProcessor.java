package cn.winter.context.surpport;

import cn.winter.bean.BeansException;
import cn.winter.bean.factory.ApplicationContextAware;
import cn.winter.bean.factory.config.BeanPostProcessor;
import cn.winter.context.ApplicationContext;

/**
 * @author xuanyu peng
 * @description:
 * 由于 ApplicationContext 的获取并不能直接在创建 Bean 时候就可以拿到，所以
 * 需要在 refresh 操作时，把 ApplicationContext 写入到一个包装的
 * BeanPostProcessor 中去，再由 AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization方法调用。
 * @date 2023/1/7 21:09
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
