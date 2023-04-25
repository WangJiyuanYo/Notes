package icu.iseenu.studyspringboot.session1;

import icu.iseenu.studyspringboot.session1.config.MainConfig;
import icu.iseenu.studyspringboot.session1.config.MainConfig2;
import icu.iseenu.studyspringboot.session1.config.MainConfigOfLifeCycle;
import icu.iseenu.studyspringboot.session1.entity.Blue;
import icu.iseenu.studyspringboot.session1.entity.Person;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

public class IOCTestLiftCycle {
//    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);

    @SuppressWarnings("source")
    @Test
    public void testImport() {
        //1.创建容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
        System.out.println("IOC容器创建完成...");

        //2.销毁容器
        applicationContext.close();
    }
}
