package cn.winter.aop.framework;

import cn.winter.aop.AdviceSupport;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/3/29 23:19
 */
public class ProxyFactory {
    private AdviceSupport adviceSupport;

    public ProxyFactory(AdviceSupport adviceSupport) {
        this.adviceSupport = adviceSupport;
    }

    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    private AopProxy createAopProxy() {
        if (adviceSupport.isProxyTargetClass()) {
            return new Cglib2AopProxy(adviceSupport);
        }
        return new JdkDynamicAopProxy(adviceSupport);
    }
}

