package cn.winter.context.event;

import cn.winter.bean.factory.BeanFactory;
import cn.winter.context.ApplicationEvent;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/3/12 20:45
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent applicationEvent) {
        for (final ApplicationListener listener : getApplicationListeners(applicationEvent)) {
            listener.onApplicationEvent(applicationEvent);
        }
    }
}
