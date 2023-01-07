package cn.winter.bean.factory;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/1/7 21:06
 */
public interface BeanClassLoaderAware extends Aware{
    void setBeanClassLoader(ClassLoader classLoader);
}
