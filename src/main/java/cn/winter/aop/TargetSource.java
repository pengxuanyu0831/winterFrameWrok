package cn.winter.aop;

import cn.winter.util.ClassUtils;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/3/19 21:59
 */
public class TargetSource {
    private final Object target;

    public TargetSource(Object obj) {
        this.target = obj;
    }


    /**
     * 用于获取Target对象的接口 用于判断是否是JDK动态代理，如果是JDK动态代理，那么获取的就是接口，如果是CGLIB动态代理，那么获取的就是类
     * @return
     */
    public Class<?>[] getTargetClass() {
        Class<?> classes = this.target.getClass();
        classes = ClassUtils.isCglibProxyClass(classes) ? this.target.getClass().getSuperclass() : classes;
        return classes.getInterfaces();
    }


    public Object getTarget() {
        return this.target;
    }
}
