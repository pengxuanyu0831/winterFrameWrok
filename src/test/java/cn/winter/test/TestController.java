package cn.winter.test;

import cn.winter.core.BeanFactory;
import cn.winter.core.PropertyValue;
import cn.winter.core.PropertyValues;
import cn.winter.core.config.BeanDefinition;
import cn.winter.core.config.BeanReference;
import cn.winter.core.support.DefaultListableBeanFactory;
import cn.winter.test.bean.UserDao;
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

        // 注册bean
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));
        // 注册属性
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValues(new PropertyValue("id","00001"));
        propertyValues.addPropertyValues(new PropertyValue("userDao",new BeanReference("userDao")));
        // 给UserService 注入UserDao
        // 注册beanDefinition
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class,propertyValues);
        beanFactory.registryBeanDefinition("userService", beanDefinition);

        // 获取bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
/*        UserService singletonUserServer = (UserService) beanFactory.getBean("userService");
        singletonUserServer.queryUserInfo();*/
    }

}
