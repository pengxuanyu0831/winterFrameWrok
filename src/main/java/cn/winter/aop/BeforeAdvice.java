package cn.winter.aop;

import org.aopalliance.aop.Advice;
/**
 * @author xuanyu peng
 * @description: 在 Spring 框架中，Advice 都是通过方法拦截器 MethodInterceptor 实现的。环
 * 绕 Advice 类似一个拦截器的链路，Before Advice、After advice 等
 * @date 2023/3/29 23:01
 */
public interface BeforeAdvice extends Advice{
}
