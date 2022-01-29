package cn.winter.core.support;

import cn.winter.BeansException;
import cn.winter.core.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * @program spring-core
 * @description: Cglib实例化
 * @author: pengxuanyu
 * @create: 2022/01/29 17:38
 */
public class CglibInstantiationStrategy implements InstantiationStrategy{
    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor constructor, Object[] args) throws BeansException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        if (null == constructor) {
            return enhancer.create();
        } else {
            return enhancer.create(constructor.getParameterTypes(), args);

        }
    }
}
