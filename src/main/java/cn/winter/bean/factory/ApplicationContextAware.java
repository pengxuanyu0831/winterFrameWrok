package cn.winter.bean.factory;

import cn.winter.bean.BeansException;
import cn.winter.context.ApplicationContext;

/**
 * @author xuanyu peng
 * @description: 实现此接口，spring可以感知到所属的Application Context
 * @date 2023/1/7 21:07
 */
public interface ApplicationContextAware extends Aware{
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
