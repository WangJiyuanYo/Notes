package icu.iseenu.studyspringboot.session1;

import icu.iseenu.studyspringboot.session1.config.MainConfigOfLifeCycle;
import icu.iseenu.studyspringboot.session1.config.MainConfigOfPropertyValues;
import icu.iseenu.studyspringboot.session1.entity.Person;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class IOCTest_PropertyValue {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);

    @SuppressWarnings("source")
    @Test
    public void testImport() {
        //1.创建容器
        System.out.println("IOC容器创建完成...");
        printBeans(applicationContext);
        System.out.println("-----------------------------------");
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);
        //2.销毁容器

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("person.nickName");
        System.out.println(property);
        applicationContext.close();
    }

    private void printBeans(AnnotationConfigApplicationContext applicationContext) {
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String definitionName : definitionNames) {
            System.out.println(definitionName);
        }
    }
}
