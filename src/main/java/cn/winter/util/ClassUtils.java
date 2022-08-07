package cn.winter.util;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2022/08/07 22:51
 */
public class ClassUtils {
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;
        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Throwable defaultClassLoader) {

        }
        if (classLoader == null) {
            classLoader = ClassUtils.getDefaultClassLoader();
        }
        return classLoader;
    }
}
