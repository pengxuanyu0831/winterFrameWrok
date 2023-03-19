package cn.winter.aop.framework;

/**
 * @author xuanyu peng
 * @description: 定义一个标准接口，用于获取代理类。因为具体实现代理的方式可以有 JDK 方
 * 式，也可以是 Cglib 方式，所以定义接口会更加方便管理实现类。
 * @date 2023/3/19 22:04
 */
public interface AopProxy {
    Object getProxy();
}
