package cn.winter.bean.factory;

/**
 * @author xuanyu peng
 * @description:
 *  * Interface to be implemented by beans that want to release resources
 *  * on destruction. A BeanFactory is supposed to invoke the destroy
 *  * method if it disposes a cached singleton. An application context
 *  * is supposed to dispose all of its singletons on close.
 * @date 2022/12/28 0:00
 */
public interface DisposableBean {

    void destroy() throws Exception;
}
