package cn.winter.context;

import java.util.EventObject;

/**
 * @author xuanyu peng
 * @description: 以继承 java.util.EventObject 定义出具备事件功能的抽象类 ApplicationEvent，后续所有事件的类都需要继承这个类
 * @date 2023/3/12 20:17
 */
public abstract class ApplicationEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
