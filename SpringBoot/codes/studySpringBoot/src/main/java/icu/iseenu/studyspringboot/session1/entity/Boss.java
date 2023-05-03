package icu.iseenu.studyspringboot.session1.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//默认加在ioc容器中的组件，容器启动会调用无参构造创建对象。再进行赋值等操作
@Component
public class Boss {
    //    @Autowired
    private Car car;

    public Car getCar() {
        return car;
    }

    //    @Autowired
    //有参构造器要用的组件也是从IOC容器中获取的
    public Boss(@Autowired Car car) {
        this.car = car;
        System.out.println("调用了有参构造器。。。。");
    }

    // @Autowired //标注方法，Spring容器创建对象时，就会调用方法完成赋值；
    //方法使用的参数，自定义类型的值从IOC容器中获取
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}
