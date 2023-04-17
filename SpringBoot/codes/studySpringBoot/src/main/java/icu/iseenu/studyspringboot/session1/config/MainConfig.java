package icu.iseenu.studyspringboot.session1.config;

import icu.iseenu.studyspringboot.session1.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "icu.iseenu.studyspringboot")
public class MainConfig {

    public Person Person01() {
        return new Person("LiSi", 25);
    }

}
