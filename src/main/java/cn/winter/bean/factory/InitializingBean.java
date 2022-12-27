package cn.winter.bean.factory;

/**
 * @author xuanyu peng
 * @description:
 *  * Interface to be implemented by beans that need to react once all their
 *  * properties have been set by a BeanFactory: for example, to perform custom
 *  * initialization, or merely to check that all mandatory properties have been set.
 *  *
 *  * 实现此接口的 Bean 对象，会在 BeanFactory 设置属性后作出相应的处理，如：执行自定义初始化，或者仅仅检查是否设置了所有强制属性。
 * @date 2022/12/27 23:49
 */
public interface InitializingBean {
    /**
     * Bean 处理属性填充 后 调用
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;
}
