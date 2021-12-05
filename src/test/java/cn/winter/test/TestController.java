package cn.winter.test;

import cn.winter.core.BeanFactory;
import cn.winter.core.config.BeanDefinition;
import cn.winter.core.support.DefaultListableBeanFactory;
import cn.winter.test.bean.UserService;
import org.junit.Test;

/**
 * @program spring-core
 * @description: 重剑无锋，大巧不工
 * @author: pengxuanyu
 * @create: 2021/11/20 22:28
 */
public class TestController {

    @Test
    public void testBeanFactory() {
        // 初始化bean工厂
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 注册beanDefinition
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        // 注册bean
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
        UserService singletonUserServer = (UserService) beanFactory.getBean("userService");
        singletonUserServer.queryUserInfo();
    }

}
