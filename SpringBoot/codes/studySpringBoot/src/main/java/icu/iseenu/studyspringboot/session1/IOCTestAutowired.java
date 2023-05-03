package icu.iseenu.studyspringboot.session1;

import icu.iseenu.studyspringboot.session1.config.MainConfigAutowired;
import icu.iseenu.studyspringboot.session1.entity.Boss;
import icu.iseenu.studyspringboot.session1.entity.Car;
import icu.iseenu.studyspringboot.session1.entity.Color;
import icu.iseenu.studyspringboot.session1.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class IOCTestAutowired {
//    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

    @SuppressWarnings("source")
    @Test
    public void testImport() {
        //1.创建容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigAutowired.class);
//        BookService bookService = applicationContext.getBean(BookService.class);
//        System.out.println(bookService);

        Boss boss = applicationContext.getBean(Boss.class);
        System.out.println(boss);

        Car car = applicationContext.getBean(Car.class);
        System.out.println(car);

        Color color = applicationContext.getBean(Color.class);
        System.out.println(color);

        //2.销毁容器
        applicationContext.close();
    }
}
