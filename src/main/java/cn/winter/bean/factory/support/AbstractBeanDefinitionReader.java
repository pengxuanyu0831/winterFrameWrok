package cn.winter.bean.factory.support;

import cn.winter.core.io.DefaultResourceLoader;
import cn.winter.core.io.ResourceLoader;

/**
 * @program spring-core
 * @description: 此抽象类将接口的get方法实现了，并提供了构造函数，让外部的调用方，可以直接将Bean的定义注入类传进来
 * @author: pengxuanyu
 * @create: 2022/08/08 22:46
 * @version: 1.0
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
