package icu.iseenu.studyspringboot.session1;

import icu.iseenu.studyspringboot.session1.config.MainConfigOfProfile;
import icu.iseenu.studyspringboot.session1.entity.Yellow;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class IOCTestProfile {

    //1.使用命令行参数；在虚拟机参数位置：-Dspring.profiles.active=test
    //2.代码激活某种环境
    @Test
    public void test() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //1.创建一个ApplicationContext
//        2.设置需要激活的环境
        applicationContext.getEnvironment().setActiveProfiles("dev");
        //3.注册主配置类；
        applicationContext.register(MainConfigOfProfile.class);
        //4.启动刷新
        applicationContext.refresh();
        String[] beanNamesForType = applicationContext.getBeanNamesForType(DataSource.class);
        for (String s : beanNamesForType) {
            System.out.println(s);
        }

        Yellow yellow = applicationContext.getBean(Yellow.class);
        System.out.println(yellow);

        applicationContext.close();
    }
}
