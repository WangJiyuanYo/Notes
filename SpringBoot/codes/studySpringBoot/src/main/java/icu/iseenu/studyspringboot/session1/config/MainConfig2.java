package icu.iseenu.studyspringboot.session1.config;


import icu.iseenu.studyspringboot.session1.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
public class MainConfig2 {

    //给容器中注入一个Bean，类型为返回值类型，ID默认为方法名
    @Bean("person")
//    @Scope("prototype")
    @Lazy
    public Person Person() {
        System.out.println("执行了初始化......");
        return new Person("张三", 25);
    }

}
