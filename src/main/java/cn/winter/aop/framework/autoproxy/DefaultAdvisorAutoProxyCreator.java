package cn.winter.aop.framework.autoproxy;


import cn.winter.aop.*;
import cn.winter.aop.aspectj.AspectJExpressionPointcutAdvisor;
import cn.winter.aop.framework.ProxyFactory;
import cn.winter.bean.BeansException;
import cn.winter.bean.PropertyValues;
import cn.winter.bean.factory.BeanFactory;
import cn.winter.bean.factory.BeanFactoryAware;
import cn.winter.bean.factory.config.InstantiationAwareBeanPostProcessor;
import cn.winter.bean.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;

/**
 * BeanPostProcessor implementation that creates AOP proxies based on all candidate
 * Advisors in the current BeanFactory. This class is completely generic; it contains
 * no special code to handle any particular aspects, such as pooling aspects.
 * <p>
 *
 *
 *
 *
 *
 * 作者：DerekYRC https://github.com/DerekYRC/mini-spring
 * 融入 Bean 生命周期的自动代理创建者
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {


        return null;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    /**
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 在 Bean 对象实例化完成后，设置属性操作之后执行此方法,这里的 bean 是已经实例化完成的
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (isInfrastructureClass(bean.getClass())) return null;

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfTypes(AspectJExpressionPointcutAdvisor.class).values();

        // 获取了 advisors 以后就可以遍历相应的 AspectJExpressionPointcutAdvisor 填充对
        //应的属性信息，包括：目标对象、拦截方法、匹配器，之后返回代理对象即可
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            if (!classFilter.matches(bean.getClass())) continue;

            AdviceSupport advisedSupport = new AdviceSupport();

            TargetSource targetSource = null;
            try {
                targetSource = new TargetSource(bean.getClass().getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);

            return new ProxyFactory(advisedSupport).getProxy();
        }
        return bean;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }
}
