package cn.winter.aop.framework;

import cn.winter.aop.AdviceSupport;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK-based {@link AopProxy} implementation for the Spring AOP framework,
 * based on JDK {@link Proxy dynamic proxies}.
 * <p>
 * JDK 动态代理
 * <p>
 * @deprecated 基于 JDK 实现的代理类，需要实现接口 AopProxy、InvocationHandler，这样就可
 * 以把代理对象 getProxy 和反射调用方法 invoke 分开处理了
 * 作者：DerekYRC https://github.com/DerekYRC/mini-spring
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdviceSupport advised;

    public JdkDynamicAopProxy(AdviceSupport advised) {
        this.advised = advised;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), advised.getTargetSource().getTargetClass(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (advised.getMethodMatcher().matcher(method, advised.getTargetSource().getTarget().getClass())) {
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(), method, args));
        }
        return method.invoke(advised.getTargetSource().getTarget(), args);
    }

}
