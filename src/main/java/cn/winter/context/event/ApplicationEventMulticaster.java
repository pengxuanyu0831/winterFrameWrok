package cn.winter.context.event;

import cn.winter.context.ApplicationEvent;

/**
 * @author xuanyu peng
 * @description: 事件广播器接口
 * @date 2023/3/12 20:25
 */
public interface ApplicationEventMulticaster {
    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播事件的方法
     * @param applicationEvent
     */
    void multicastEvent(ApplicationEvent applicationEvent);
}
