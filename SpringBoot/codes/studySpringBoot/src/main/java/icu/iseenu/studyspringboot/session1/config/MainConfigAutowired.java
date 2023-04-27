package icu.iseenu.studyspringboot.session1.config;

import icu.iseenu.studyspringboot.session1.dao.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 自动装配：Spring依赖DI，完成对IOC容器中各个组件的依赖关系赋值
 * 1. Autowired:自动注入
 *      1). 默认按照类型自动装配->applicationContext.getBean(BookService.class);
 *      2）.如果找到多个相同类型的组件，再将属性的名称作为组件的ID去容器中查找
 *                                  applicationContext.getBean(bookDAO)
 *      3).@Qualifier；使用@Qualifier执行需要装配组件的ID，而不是使用属性名
 *      4）.自动装配一定要将属性赋值好，没有就会报错 required = false 若有则装配。没有就跳过
 *      5）.@Primary：让Spring自动装配的时候，默认使用首选的Bean
 *              也可以继续使用@Qualifier指定需要装配的Bean的名字
 *
 * 2.Spring还支持使用@Resource(JSR250)和@Inject(JSR330)[Java规范]
 * --      @Resource：
 *          可以和@Autowired一样实现自动装配；默认按照组件名称进行装配的
 *          没有支持@Primary功能没有支持
 * --      @Inject:
 *              需要导入javax.inject的包，和 Autowired的功能一样没有 required = false
 *
 */
@Configuration
@ComponentScan({"icu.iseenu.studyspringboot.session1.controller",
        "icu.iseenu.studyspringboot.session1.service",
        "icu.iseenu.studyspringboot.session1.dao"})
public class MainConfigAutowired {

    @Bean("bookDAO2")
    @Primary
    public BookDAO bookDAO() {
        BookDAO bookDAO = new BookDAO();
        bookDAO.setLabel("2");
        return bookDAO;
    }
}
