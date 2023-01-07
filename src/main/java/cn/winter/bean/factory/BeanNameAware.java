package cn.winter.bean.factory;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/1/7 21:07
 */
public interface BeanNameAware extends Aware{
    void setBeanName(String beanName);
}
