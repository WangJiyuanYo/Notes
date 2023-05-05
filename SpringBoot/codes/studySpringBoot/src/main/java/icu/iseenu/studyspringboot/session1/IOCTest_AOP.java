package icu.iseenu.studyspringboot.session1;

import icu.iseenu.studyspringboot.session1.aop.MathCalculator;
import icu.iseenu.studyspringboot.session1.config.MainConfigAutowired;
import icu.iseenu.studyspringboot.session1.config.MainConfigOfAOP;
import icu.iseenu.studyspringboot.session1.entity.Boss;
import icu.iseenu.studyspringboot.session1.entity.Car;
import icu.iseenu.studyspringboot.session1.entity.Color;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class IOCTest_AOP {
//    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

    @SuppressWarnings("source")
    @Test
    public void aopTest() {
        //1.创建容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
        MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);
        mathCalculator.div(1, 0);

        //2.销毁容器
        applicationContext.close();
    }
}
