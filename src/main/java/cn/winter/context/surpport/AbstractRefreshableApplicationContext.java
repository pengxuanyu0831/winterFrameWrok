package cn.winter.context.surpport;

import cn.winter.bean.BeansException;
import cn.winter.bean.factory.ConfigurableListableBeanFactory;
import cn.winter.bean.factory.support.DefaultListableBeanFactory;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2022/08/28 21:41
 * @version: 1.0
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{
    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);
}
