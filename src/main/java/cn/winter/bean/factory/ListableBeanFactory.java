package cn.winter.bean.factory;

import cn.winter.bean.BeansException;

import java.util.Map;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2022/08/28 16:19
 * @version: 1.0
 */
public interface ListableBeanFactory extends BeanFactory {
    /**
     * Extension of the {@link BeanFactory} interface to be implemented by bean factories
     *  * that can enumerate all their bean instances, rather than attempting bean lookup
     *  * by name one by one as requested by clients. BeanFactory implementations that
     *  * preload all their bean definitions (such as XML-based factories) may implement
     *  * this interface.
     * 根据bean类型，返回bean实例
     * @param type
     * @param <T>
     * @return
     * @throws BeansException
     */
    <T> Map<String, T> getBeansOfTypes(Class<T> type) throws BeansException;


    /**
     * 返回所有所有注册表中的bean实例名
     * @return
     */
    String[] getBeanDefinitionNames();
}
