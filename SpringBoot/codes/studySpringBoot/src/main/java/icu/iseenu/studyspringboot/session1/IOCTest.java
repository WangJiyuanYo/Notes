package icu.iseenu.studyspringboot.session1;

import icu.iseenu.studyspringboot.session1.config.MainConfig;
import icu.iseenu.studyspringboot.session1.config.MainConfig2;
import icu.iseenu.studyspringboot.session1.entity.Blue;
import icu.iseenu.studyspringboot.session1.entity.ColorFactoryBean;
import icu.iseenu.studyspringboot.session1.entity.Person;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.yaml.snakeyaml.external.com.google.gdata.util.common.base.PercentEscaper;

import java.util.Map;

public class IOCTest {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

    @SuppressWarnings("source")
    @Test
    public void testImport(){
        printBeans(applicationContext);
        Blue bean = applicationContext.getBean(Blue.class);
        System.out.println(bean);

        Object factoryBean = applicationContext.getBean("colorFactoryBean");
        Object factoryBean2 = applicationContext.getBean("colorFactoryBean");
        System.out.println(factoryBean.getClass());
        System.out.println(factoryBean == factoryBean2);

        Object factoryBean3 = applicationContext.getBean("&colorFactoryBean");
        System.out.println(factoryBean3.getClass());

    }

    private void printBeans(AnnotationConfigApplicationContext applicationContext){
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String definitionName : definitionNames) {
            System.out.println(definitionName);
        }
    }

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

    @SuppressWarnings("source")
    @Test
    public void test03() {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        //Windows 10
        String osName = environment.getProperty("os.name");
        System.out.println(osName);

        String[] beanNamesForType = applicationContext.getBeanNamesForType(Person.class);
        for (String s : beanNamesForType) {
            System.out.println(s);
        }

        Map<String, Person> beansOfType = applicationContext.getBeansOfType(Person.class);
        System.out.println(beansOfType);
    }
}
