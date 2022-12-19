package cn.winter.context.surpport;

import cn.winter.bean.BeansException;
import cn.winter.bean.factory.BeanFactory;
import cn.winter.bean.factory.ConfigurableListableBeanFactory;
import cn.winter.bean.factory.config.BeanDefinition;
import cn.winter.bean.factory.config.BeanFactoryPostProcessor;
import cn.winter.bean.factory.config.BeanPostProcessor;
import cn.winter.context.ConfigurableApplicationContext;
import cn.winter.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * @program spring-core
 * @description: 继承 DefaultResourceLoader 是为了处理spring.xml文件中配置的资源的加载
 * @author: pengxuanyu
 * @create: 2022/08/28 17:00
 * @version: 1.0
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    @Override
    public Object getBean(String name) throws BeansException {
        return null;
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return null;
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {

    }

    /**
     * Extension of the {@link BeanFactory} interface to be implemented by bean factories
     * * that can enumerate all their bean instances, rather than attempting bean lookup
     * * by name one by one as requested by clients. BeanFactory implementations that
     * * preload all their bean definitions (such as XML-based factories) may implement
     * * this interface.
     * 根据bean类型，返回bean实例
     *
     * @param type
     * @return
     * @throws BeansException
     */
    @Override
    public <T> Map<String, T> getBeansOfTypes(Class<T> type) throws BeansException {
        return null;
    }

    /**
     * 返回所有所有注册表中的bean实例名
     *
     * @return
     */
    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    /**
     * 刷新容器 『核心方法』
     *
     * @throws BeansException
     */
    @Override
    public void refresh() throws BeansException {
        // 1 创建BeanFactory ，并加载BeanDefinition
        refreshBeanFactory();

        // 2 获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3 在bean加载完成后，实例化bean之前执行
        invokeBeanFactoryPostProcessors(beanFactory);

        // 4 BeanPostProcessor需提前于其他Bean对象实例化之前执行注册操作
        registerBeanPostProcessor(beanFactory);

        // 5 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();

    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> types = beanFactory.getBeansOfTypes(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor p : types.values()) {
            p.postProcessorBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        // 返回BeanPostProcessor类型的实例
        Map<String, BeanPostProcessor> types = beanFactory.getBeansOfTypes(BeanPostProcessor.class);
        for (BeanPostProcessor p : types.values()) {
            beanFactory.addBeanPostProcessor(p);
        }

    }


}
