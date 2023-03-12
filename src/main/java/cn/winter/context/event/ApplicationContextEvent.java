package cn.winter.context.event;

import cn.winter.context.ApplicationContext;
import cn.winter.context.ApplicationEvent;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/3/12 20:19
 */
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
