package icu.iseenu.studyspringboot.session1.config;

import icu.iseenu.studyspringboot.session1.MyFilter;
import icu.iseenu.studyspringboot.session1.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Configuration
//@ComponentScan("icu.iseenu.studyspringboot") //指定要扫描的包
// excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class, Service.class})}
@ComponentScan(value = {"icu.iseenu.studyspringboot"},
        includeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyFilter.class})},
        useDefaultFilters = false
)
public class MainConfig {

    //给容器中注入一个Bean，类型为返回值类型，ID默认为方法名
    @Bean("PersonTest")
    public Person Person01() {
        return new Person("LiSi", 25);
    }

}
