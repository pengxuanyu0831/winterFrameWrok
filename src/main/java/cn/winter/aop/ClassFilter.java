package cn.winter.aop;

/**
 * @author xuanyu peng
 * @description:
 * @date 2023/3/19 21:39
 */
public interface ClassFilter {
    // 切点是否需要应用到这个指定的clazz上
    boolean matches(Class<?> clazz);
}
