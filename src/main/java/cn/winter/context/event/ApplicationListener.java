package cn.winter.context.event;

import cn.winter.context.ApplicationEvent;

import java.util.EventListener;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/3/12 20:27
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    void onApplicationEvent(E event);
}
