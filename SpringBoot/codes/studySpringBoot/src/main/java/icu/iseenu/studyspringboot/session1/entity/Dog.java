package icu.iseenu.studyspringboot.session1.entity;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Dog {
    public Dog() {
        System.out.println("Dog Constructor...");
    }

    //对象创建并赋值之后调用
    @PostConstruct
    public void init() {
        System.out.println("Dog @PostConstruct...");

    }

    @PreDestroy
    public void destroy() {
        System.out.println("Dog @PreDestroy...");

    }
}
