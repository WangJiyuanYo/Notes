package icu.iseenu.studyspringboot.session1.config;

import icu.iseenu.studyspringboot.session1.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//读取外部配置文件中的K/V保存到运行的环境变量中
@PropertySource(value = {"classpath:/person.properties"})
public class MainConfigOfPropertyValues {


    @Bean
    public Person person(){
        return new Person();
    }
}
