package icu.iseenu.studyspringboot.session1.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class LogAspects {

    //抽取公共的切入点表达式
    //1。本类引用
    //2。其他的切面类
    @Pointcut("execution(public int icu.iseenu.studyspringboot.session1.aop.MathCalculator.*(..))")
    public void pointCut() {
    }

    //@Before在目标方法之前切入；切入表达式（指定在哪个方法切入）
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("div运行。。。@Before：参数列表是{" + Arrays.asList(args) + "}");
    }

    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + " 除法结束。。。");

    }

    /**
     * JoinPoint一定出现在参数列表的第一位
     *
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        System.out.println(joinPoint.getSignature().getName() + "除法正常返回。。。运行结果是：{" + result + "}");
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        System.out.println(joinPoint.getSignature().getName() + "除法异常行。。。异常信息是：{" + exception + "}");
    }
}
