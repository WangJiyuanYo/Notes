package icu.iseenu.studyspringboot.session1.config;

import icu.iseenu.studyspringboot.session1.entity.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Bean的生命周期：Bean的创建——>销毁
 * <p>
 * 容器管理Bean的生命周期
 * 可以自定义初始化和销毁方法 ；容器在Bean进行到当前生命周期的时候调用我们自定义的初始化和销毁方法
 *
 * 构造（对象创建）：
 *  单实例：IOC容器创建时创建对象
 *  多实例：调用对象时创建对象
 *
 * 初始化：对象创建并赋值好，调用初始化方法
 * 销毁：
 *      单实例：容器关闭时，销毁方法
 *      多实例：容器不会管理这个Bean；容器不会调用销毁方法
 *
 * 1. 指定初始化销毁方法
 * 通过@Bean指定init-method,destroy--method
 * 2. 让Bean实现InitializingBean接口进（定义初始化逻辑）,DisposableBean 定义销毁
 * 3. 使用JSR250
 *  - @PostConstructor:在Bean创建完成并且属性赋值完成后；来执行初始化
 *  - @PreDestroy:在容器销毁Bean之前，通知我们进行清理
 * 4. BeanPostProcessor【interface】：Bean后置处理器，在Bean初始化前后进行一些处理工作
 *  - postProcessBeforeInitialization
 *  - postProcessAfterInitialization
 */
@ComponentScan("icu.iseenu.studyspringboot.session1.entity")
@Configuration
public class MainConfigOfLifeCycle {
    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car() {
        return new Car();
    }
}
