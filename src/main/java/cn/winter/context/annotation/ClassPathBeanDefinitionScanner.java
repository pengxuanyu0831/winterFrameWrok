package cn.winter.context.annotation;

import cn.hutool.core.util.StrUtil;
import cn.winter.bean.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import cn.winter.bean.factory.config.BeanDefinition;
import cn.winter.bean.factory.support.BeanDefinitionRegistry;
import cn.winter.stereotype.Component;

import java.util.Set;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/4/15 16:58
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private BeanDefinitionRegistry beanDefinitionRegistry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void doScan(String... basePackages) {
        for (String b : basePackages) {
            Set<BeanDefinition> components = findCandidateComponents(b);

            for (BeanDefinition bd : components) {
                String s = resolveBeanScope(bd);
                if (!s.equals("") && s != null) {
                    bd.setScope(s);
                }
                beanDefinitionRegistry.registerBeanDefinition(determineBeanName(bd), bd);
            }
        }
        // 注册处理注解的 BeanPostProcessor（@Autowired、@Value）
        beanDefinitionRegistry.registerBeanDefinition("cn.winter.context.annotation.internalAutowiredAnnotationProcessor", new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    private String resolveBeanScope(BeanDefinition bd) {
        Class<?> beanClass = bd.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (null != scope) {
            return scope.value();
        }
        return "";

    }

    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (value != null) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}
