package icu.iseenu.studyspringboot.session1.config;


import icu.iseenu.studyspringboot.session1.condition.LinuxCondition;
import icu.iseenu.studyspringboot.session1.condition.MyImportBeanDefinitionRegistrar;
import icu.iseenu.studyspringboot.session1.condition.MyImportSelector;
import icu.iseenu.studyspringboot.session1.condition.WindowsCondition;
import icu.iseenu.studyspringboot.session1.entity.Color;
import icu.iseenu.studyspringboot.session1.entity.ColorFactoryBean;
import icu.iseenu.studyspringboot.session1.entity.Person;
import icu.iseenu.studyspringboot.session1.entity.Red;
import org.springframework.context.annotation.*;

@Configuration
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {

    //给容器中注入一个Bean，类型为返回值类型，ID默认为方法名
    @Bean("person")
//    @Scope("prototype")
    @Lazy
    public Person Person() {
        System.out.println("执行了初始化......");
        return new Person("张三", 25);
    }

    @Bean("Bill")
    @Conditional({WindowsCondition.class})
    public Person person01() {
        return new Person("Bill Gates", 62);
    }

    @Bean("linux")
    @Conditional({LinuxCondition.class})
    public Person person02() {
        return new Person("Linus", 62);
    }


    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }

}
