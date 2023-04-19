package icu.iseenu.studyspringboot.session1.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinuxCondition implements Condition {
    /**
     * @param conditionContext      判断条件能使用的上下文环境
     * @param annotatedTypeMetadata 注释信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //获取IOC使用的BeanFactory
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        //获取Bean的类加载器
        ClassLoader classLoader = conditionContext.getClassLoader();
        //获取运行环境
        Environment environment = conditionContext.getEnvironment();
        //获取Bean定义的注册类
        BeanDefinitionRegistry registry = conditionContext.getRegistry();

        //可以判断Bean中的注册情况
        boolean person = registry.containsBeanDefinition("personG");
        return person;

//        String property = environment.getProperty("os.name");
//        if (property.contains("linux"))
//            return true;

//        return false;
    }
}
