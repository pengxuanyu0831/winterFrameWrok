package cn.winter.context.surpport;

import cn.winter.bean.BeansException;
import cn.winter.bean.factory.BeanFactory;
import cn.winter.bean.factory.ConfigurableListableBeanFactory;
import cn.winter.bean.factory.config.BeanDefinition;
import cn.winter.bean.factory.config.BeanFactoryPostProcessor;
import cn.winter.bean.factory.config.BeanPostProcessor;
import cn.winter.context.ApplicationEvent;
import cn.winter.context.ConfigurableApplicationContext;
import cn.winter.context.event.*;
import cn.winter.core.io.DefaultResourceLoader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program spring-core
 * @description: 继承 DefaultResourceLoader 是为了处理spring.xml文件中配置的资源的加载
 * @author: pengxuanyu
 * @create: 2022/08/28 17:00
 * @version: 1.0
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    private final static Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    // todo 此时是一级缓存
    @Override
    public <T> T getBean(Class<T> beanClass) throws BeansException, InstantiationException, IllegalAccessException {
        String lowerCase = beanClass.getSimpleName().toLowerCase(Locale.ROOT);
        if(singletonObjects.containsKey(lowerCase)){
            return (T) singletonObjects.get(lowerCase);
        }

        T t = beanClass.newInstance();
        singletonObjects.put(lowerCase, t);
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            Class<?> type = f.getType();
            String s = type.getSimpleName().toLowerCase(Locale.ROOT);
            f.set(t, singletonObjects.containsKey(s) ? singletonObjects.get(s) : getBean(type));
            f.setAccessible(false);
        }

        return t;
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
        return getBeanFactory().getBeanDefinitionNames();
    }

    /**
     * 刷新容器 『核心方法』
     *
     * @throws BeansException
     */
    @Override
    public void refresh() throws BeansException, IOException {
        // 1 创建BeanFactory ，并加载BeanDefinition
        refreshBeanFactory();

        // 2 获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3 添加ApplicationContextAwareProcessor 让继承字ApplicationContextAware 的Bean 对象都能感知到所属的ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 4 在bean加载完成后，实例化bean之前执行
        invokeBeanFactoryPostProcessors(beanFactory);

        // 5 BeanPostProcessor需提前于其他Bean对象实例化之前执行注册操作
        registerBeanPostProcessor(beanFactory);

        // 6 初始化事件发布者
        initApplicationEventMulticaster();

        // 7 注册事件发布者
        registerListeners();

        // 8 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();

        // 9 发布容器刷新完成事件
        finishRefresh();

    }

    protected abstract void refreshBeanFactory() throws BeansException, IOException;

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

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfTypes(type);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        // 执行销毁单例bean的方法
        getBeanFactory().destroySingletons();
    }
}
