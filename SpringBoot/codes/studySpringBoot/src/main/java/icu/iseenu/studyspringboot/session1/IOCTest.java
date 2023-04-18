package icu.iseenu.studyspringboot.session1;

import icu.iseenu.studyspringboot.session1.config.MainConfig;
import icu.iseenu.studyspringboot.session1.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest {
    @SuppressWarnings("source")
    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }

    @SuppressWarnings("source")
    @Test
    public void test02() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        System.out.println("ioc创建完成");
        Object bean = applicationContext.getBean("person");
//        Object bean2 = applicationContext.getBean("person");
//        System.out.println(bean2 == bean);
    }
}
