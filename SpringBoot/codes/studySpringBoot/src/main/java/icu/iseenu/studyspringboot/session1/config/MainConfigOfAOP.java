package icu.iseenu.studyspringboot.session1.config;

import icu.iseenu.studyspringboot.session1.aop.LogAspects;
import icu.iseenu.studyspringboot.session1.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP： 动态代理
 * 程序运行期间动态的将某段代码切入到指定方法，指定位置进行运行的编程方式；
 * <p>
 * 1.导入AOP模块 Spring-AOP spring-aspects
 * 2.定义一个业务逻辑类（MathCalculator）；在业务逻辑运行时将日志进行打印，（方法之前，方法运行，方法结束，方法异常）
 * 3.定义一个日志切面类（LogAspects）；切面类里面的方法需要动态感知 MathCalculator.div运行到哪里
 * 通知方法：
 * 前置通知(@Before)：logStart：在目标运行之前运行
 * 后置通知(@After)：logEnd：在目标运行之后运行（无论方法正常结束还是异常结束都执行）
 * 返回通知(@AfterReturning)：logReturn：在目标方法正常返回之后运行
 * 异常通知(@AfterThrowing)：logException：在运行出现异常以后执行
 * 环绕通知(@Around)：动态代理，手动推进目标方法运行(joinPoint.proceed())
 * 4.给切面类的目标方法标注何时何地运行
 * 5.将切面类和业务逻辑类（目标方法所在类）都加入到容器中；
 * 6.必须告诉Spring哪个类是切面类（给切面类上加一个注解 @Aspect）
 * [7].给配置类加 @EnableAspectJAutoProxy 【开启基于注解的AOP模式】
 *  --在Spring中有很多EnableXXX
 *
 *     三步：
 *     1.将业务逻辑组件和切面类都加入容器中；告诉Spring哪个是切面类
 *     2.在切面类上的每一个通知方法上标注注解通知，告诉Spring何时何地运行（切入点表达式）
 *     3.开启基于注解的AOP模式 @EnableAspectJAutoProxy
 *
 *   流程：
 *      1）、传入配置类，创建IOC容器
 *      2）、注册配置类、调用refresh()刷新容器
 *      3）、registerBeanPostProcessor(beanFactory);注册bean的后置处理器来方便拦截bean的创建
 *          （1）、先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor
 *          （2）、给容器中加别的BeanPostProcessor
 *          （3)、优先注册了PriorityOrdered接口的BeanPostProcessor
 *          （4）、再给容器中注册实现了Ordered接口的BeanPostProcessor
 *          （5）、注册没实现优先级接口的BeanPostProcessor
 *          （6）、注册BeanPostProcessor实际上就是创建BeanPostProcessor对象，保存在容器中
 *                创建internalAutoProxyCreator的BeanPostProcessor
 *                （1）、创建Bean实例
 *                （2）、populateBean：给Bean的各种属性赋值
 *                （3）、initializeBean：初始化Bean
 *                      1）、invokeAwareMethods();处理Aware接口的方法回调
 *                      2）、applyBeanPostProcessorBeforeInitialization()；应用后置处理器的postProcessBeforeInitialization()
 *                      3）、invokeInitMethods()；执行自定义的初始化方法
 *                      4）、applyBeanPostProcessorAfterInitialization；执行后置处理器的postProcessAfterInitialization()
 *                （4）、BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)创建成功 --》aspectJAdvisorsBuilder
 */
@Configuration
@EnableAspectJAutoProxy
public class MainConfigOfAOP {

    @Bean
    public MathCalculator calculator() {
        return new MathCalculator();
    }

    @Bean
    public LogAspects logAspects() {
        return new LogAspects();
    }

}
