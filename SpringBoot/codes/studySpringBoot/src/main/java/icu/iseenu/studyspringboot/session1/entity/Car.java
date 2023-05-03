package icu.iseenu.studyspringboot.session1.entity;

import org.springframework.stereotype.Component;

@Component
public class Car {
    public Car(){
        System.out.println("car constructor...");
    }

    public void init(){
        System.out.println("Car ... init ");
    }

    public void destroy(){
        System.out.println("Car ... destroy ");
    }
}
