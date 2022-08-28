package cn.winter.bean.factory.config;

/**
 * @program spring-core
 * @description:
 * @author: pengxuanyu
 * @create: 2022/06/11 21:55
 */
public class BeanReference {
    String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
