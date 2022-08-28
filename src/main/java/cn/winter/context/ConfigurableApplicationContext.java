package cn.winter.context;

import cn.winter.bean.BeansException;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2022/08/28 16:58
 * @version: 1.0
 */
public interface ConfigurableApplicationContext extends ApplicationContext {
    /**
     * 刷新容器 『核心方法』
     * @throws BeansException
     */
    void refresh() throws BeansException;
}
