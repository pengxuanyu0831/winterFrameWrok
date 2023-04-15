package cn.winter.context.annotation;

import cn.hutool.core.util.ClassUtil;
import cn.winter.bean.factory.config.BeanDefinition;
import cn.winter.stereotype.Component;
import cn.winter.util.ClassUtils;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author xuanyu peng
 * @description:
 * Spring 官方实现请查看
 * package org.springframework.context.annotation;
 * 这里就不用这么复杂的办法，就引入hutool
 * @date 2023/4/15 16:27
 */
public class ClassPathScanningCandidateComponentProvider {
    /**
     * 根据路径扫描所有打了@Component 注解的Bean对象
     * @param basePackage
     * @return
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }


}
