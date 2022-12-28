package cn.winter.context.surpport;

import cn.winter.bean.BeansException;

import java.io.IOException;

/**
 * @author xuanyu peng
 * @description:
 * @date 2022/12/19 22:07
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{
    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    /**
     * 从 Xml 中加载BeanDefinition 并刷新上下文
     * @param configLocations
     * @throws BeansException
     */
    public ClassPathXmlApplicationContext(String configLocations) throws BeansException, IOException {
        this(new String[]{configLocations});
    }

    public ClassPathXmlApplicationContext(String[] strings) throws IOException {
        this.configLocations = strings;
        refresh();
    }

    @Override
    protected String[] getConfigLocation() {
        return new String[0];
    }
}
