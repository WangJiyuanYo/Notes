

# SpringBoot

## 注解

```java
//Configuration注入Bean后，通过这种方式获取Bean
public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        String[] beanNamesForType = applicationContext.getBeanNamesForType(Person.class);
        for (String s : beanNamesForType) {
            System.out.println(s);
        }
    }
```



### @ComponentScan

```java
public class IOCTest {
    @SuppressWarnings("source")
    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
```




```java
// 包含哪些
Filter[] includeFilters() default {};

//排除哪些
Filter[] excludeFilters() default {};
//按照注解或者类排除包下的扫描；Class或者Filter数组
userDefaultFilters = false; //关闭默认
```

根据注解排除Bean

```java

@Configuration
//@ComponentScan("icu.iseenu.studyspringboot") //指定要扫描的包

//根据注解排除Bean，排除Controller和Service
@ComponentScan(value = {"icu.iseenu.studyspringboot"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class, Service.class})})
public class MainConfig {

    //给容器中注入一个Bean，类型为返回值类型，ID默认为方法名
    @Bean("PersonTest")
    public Person Person01() {
        return new Person("LiSi", 25);
    }
}
```

自定义过滤

```java
public class MyFilter implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {

        //获取当前类注解的信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前正在扫描的类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //获取当前类资源（类的路径）
        Resource resource = metadataReader.getResource();

        String className = classMetadata.getClassName();
        System.out.println("--->" + className);
        if (className.contains("ser")) {
            return true;
        }
        return false;
    }
}
```

### @Scop/@Lazy  

默认为单例对象

```
    public void test02() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        //默认单实例
        Object bean = applicationContext.getBean("person");
        Object bean2 = applicationContext.getBean("person");
        System.out.println(bean2 == bean);
    }
}
// true
```

作用域：

|           | 执行结果 | 创建方式                                       | 调用方式                                                     |
| --------- | -------- | ---------------------------------------------- | ------------------------------------------------------------ |
| prototype | flase    | 每次调用，都会创建一个新的对象                 | IOC启动容器并不会调用方法创建对象放在容器中，每次获取的时候才会调用方法创建对象 |
| singleton | true     | IOC容器启动时调用方法创建一个对象放入IOC容器中 | 以后每次从IOC容器中获取对象(map.get)                         |
| request   |          | 同一次请求创建一个实例                         |                                                              |
| seesion   |          | 同一个session创建一个实例                      |                                                              |

### @Lazy

单例模式时，创建IOC后不会创建对象，在第一次调用后才会创建对象

### @Conditional:

**按照一定的条件进行判断，满足条件给容器注册Bean**

1. 当其在Bean方法上时，满足后注册Bean
2. 当期在Class上时，对此Class下所有东西都生效

@Conditional 源码：

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conditional {
    Class<? extends Condition>[] value();
}
```

Conditiony源码

```java
// 这是一个接口，需要对其进行实现
public interface Condition {
    boolean matches(ConditionContext var1, AnnotatedTypeMetadata var2);
}
```

Demo

注册不同的BeanName

```java
@Bean("Bill")
@Conditional({WindowsCondition.class})
public Person person01() {
    return new Person("Bill Gates", 62);
}

@Bean("linux")
@Conditional({LinuxCondition.class})
public Person person02() {
    return new Person("Linus", 62);
}
```

继承Condition的实现

```java
public class LinuxCondition implements Condition {
    /**
     * @param conditionContext      判断条件能使用的上下文环境
     * @param annotatedTypeMetadata 注释信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //获取IOC使用的BeanFactory
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        //获取Bean的类加载器
        ClassLoader classLoader = conditionContext.getClassLoader();
        //获取运行环境
        Environment environment = conditionContext.getEnvironment();
        //获取Bean定义的注册类
        BeanDefinitionRegistry registry = conditionContext.getRegistry();

        //可以判断Bean中的注册情况
        boolean person = registry.containsBeanDefinition("person");
        
        String property = environment.getProperty("os.name");
        if (property.contains("linux"))
            return true;

        return false;
    }
}
```

```java
public class WindowsCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();
        String property = environment.getProperty("os.name");
        if (property.contains("Windows 10"))
            return true;

        return false;
    }
}
```

测试结果

```java
@SuppressWarnings("source")
@Test
public void test03() {
    ConfigurableEnvironment environment = applicationContext.getEnvironment();
    //Windows 10
    String osName = environment.getProperty("os.name");
    System.out.println(osName);

    String[] beanNamesForType = applicationContext.getBeanNamesForType(Person.class);
    for (String s : beanNamesForType) {
        System.out.println(s);
    }

    Map<String, Person> beansOfType = applicationContext.getBeansOfType(Person.class);
    System.out.println(beansOfType);
}

//-------------------------------
Windows 10
person
Bill
执行了初始化......
{person=Person(name=张三, age=25), Bill=Person(name=Bill Gates, age=62)}

```

## Bean的注册方式

1. 包扫描+组件标注注解（@Controller/@Service/@Repositry/@Compontent）//局限自己写的类
2. @Bean,可注入第三方包
3. @Import；快速给容器中导入组件
   1. @Import(Class.class);容器中会自动注册这个组件,ID默认为全限定类名
   2. ImportSelector:返回需要导入的组件的全类名数组;(见下)
   3. ImportBeanDefinitionRegistrar:
4. 使用Spring提供的FactoryBean（工厂Bean）
   			1. 默认获取到的是工厂Bean调用getObject创建对象
      			1. 要获取工厂Bean本身，我们需要给id前面加一个&

// 需要仔细看的Bean的源码



Spring对BeanPostProcess的使用

### @Import

 默认为全限定类名

```java
@Configuration
@Import(Color.class)
public class MainConfig2 {...}

//------------------------------
icu.iseenu.studyspringboot.session1.entity.Color
```

### ImportSelector 接口

```java
//先Import自己的Class
@Import({Color.class, Red.class, MyImportSelector.class})
```

重写接口实现

```java
public class MyImportSelector implements ImportSelector {
    /**
     * @param annotationMetadata：当前标注@Import注解类的所有注解信息
     * @return 需要导入容器中的全类名组件
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"icu.iseenu.studyspringboot.session1.entity.Blue",
        "icu.iseenu.studyspringboot.session1.entity.Yellow"};
//        return new String[0];
    }
}
```

### ImportBeanDefinitionRegistrar

```java
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     * @param annotationMetadata     当前类的注册信息
     * @param beanDefinitionRegistry beanDefinition注册类，把所有需要添加到容器的Bean；
     *                               调用BeanDefinitionRegistry.registerBeanDefinition，手工注册进来
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        boolean red = beanDefinitionRegistry.containsBeanDefinition("icu.iseenu.studyspringboot.session1.entity.Red");
        boolean blue = beanDefinitionRegistry.containsBeanDefinition("icu.iseenu.studyspringboot.session1.entity.Blue");
        if (red && blue) {
            //执行Bean的定义信息
            RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
            //指定Bean名
            beanDefinitionRegistry.registerBeanDefinition("rainBow", beanDefinition);
        }
    }
}
```



## Bean的生命周期

Bean的生命周期：Bean的创建——>销毁

容器管理Bean的生命周期
 可以自定义初始化和销毁方法 ；容器在Bean进行到当前生命周期的时候调用我们自定义的初始化和销毁方法

 构造（对象创建）：
  单实例：IOC容器创建时创建对象
  多实例：调用对象时创建对象

 初始化：对象创建并赋值好，调用初始化方法
 销毁：
      单实例：容器关闭时，销毁方法
      多实例：容器不会管理这个Bean；容器不会调用销毁方法
     

### 四种方法

 1. 指定初始化销毁方法
     通过@Bean指定init-method,destroy--method

 2. 让Bean实现InitializingBean接口进（定义初始化逻辑）,DisposableBean 定义销毁

 3. 使用JSR250

     @PostConstructor:在Bean创建完成并且属性赋值完成后；来执行初始化

     @PreDestroy:在容器销毁Bean之前，通知我们进行清理

 4.   BeanPostProcessor【interface】：Bean后置处理器，在Bean初始化前后进行一些处理工作

     ​	postProcessBeforeInitialization

     ​	postProcessAfterInitialization

#### @Bean


```java
//创建个Bean对象
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
```


```java
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
 */
@Configuration
public class MainConfigOfLifeCycle {
    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car() {
        return new Car();
    }
}
```

```java
@SuppressWarnings("source")
@Test
public void testImport() {
    //1.创建容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
    System.out.println("IOC容器创建完成...");

    //2.销毁容器
    applicationContext.close();
}
```



#### @InitializingBean

```java
//2.让Bean实现InitializingBean接口进（定义初始化逻辑）,DisposableBean 定义销毁
@Component
public class Cat implements InitializingBean, DisposableBean {
    public Cat(){
        System.out.println("Cat constructor...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Cat Destroy...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Cat...afterPropertiesSet...");
    }
}
```



```java
@ComponentScan("icu.iseenu.studyspringboot.session1.entity")
@Configuration
public class MainConfigOfLifeCycle {
    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car() {
        return new Car();
    }
}
```

#### JSR250

```java
3. 使用JSR250
*  - @PostConstructor:在Bean创建完成并且属性赋值完成后；来执行初始化
*  - @PreDestroy:在容器销毁Bean之前，通知我们进行清理
@Component
public class Dog {
    public Dog() {
        System.out.println("Dog Constructor...");
    }

    //对象创建并赋值之后调用
    @PostConstruct
    public void init() {
        System.out.println("Dog @PostConstruct...");

    }

    @PreDestroy
    public void destroy() {
        System.out.println("Dog @PreDestroy...");

    }
}
```

#### BeanPostProcessor


```java
 /* 4. BeanPostProcessor【interface】：Bean后置处理器，在Bean初始化前后进行一些处理工作
 *  - postProcessBeforeInitialization
 *  - postProcessAfterInitialization
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization..." + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization..." + beanName);
        return bean;
    }
}
```



### @Value

```java
public class Person {
    /**
     * @Value("") 1. 基本数值
     * 2. SPEL；#{}
     * 3. ${};取出配置文件中的值
     */
    @Value("ZhangSan")
    private String name;

    @Value("#{20-2}")
    private Integer age;

    @Value("${person.nickName}")
    private String nickName;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person(){}
}
```

```java
@Configuration
//读取外部配置文件中的K/V保存到运行的环境变量中
@PropertySource(value = {"classpath:/person.properties"})
public class MainConfigOfPropertyValues {


    @Bean
    public Person person(){
        return new Person();
    }
}
```

### @Autowired

自动装配：Spring依赖DI，完成对IOC容器中各个组件的依赖关系赋值

1. Autowired:自动注入

   1).默认按照类型自动装配->applicationContext.getBean(BookService.class);

   2).如果找到多个相同类型的组件，再将属性的名称作为组件的ID去容器中查找

​                 applicationContext.getBean(bookDAO)

   3).@Qualifier；

​      使用@Qualifier执行需要装配组件的ID，而不是使用属性名

   4).自动装配一定要将属性赋值好，没有就会报错 required = false 若有则装配。没有就跳过

   5).@Primary：

​      让Spring自动装配的时候，默认使用首选的Bean

​      也可以继续使用@Qualifier指定需要装配的Bean的名字

2. Spring还支持使用@Resource(JSR250)和@Inject(JSR330)[Java规范]

   @Resource：

​     可以和@Autowired一样实现自动装配；默认按照组件名称进行装配的

​     没有支持@Primary功能没有支持

   @Inject:

​      需要导入javax.inject的包，和 Autowired的功能一样没有 required = false

3. @Autowired:构造器，参数，方法，属性；都是从容器中获取
   1. [标注在方法上];@Bean+方法参数；参数从容器中获取；默认不写Autowired
   2. 标在构造器上；如果只有一个有参构造器，这个有参构造器的Autowired可以省略，参数位子的组件还可以自动从容器中获取
   3. 放在参数位置
4. 自定义组件，想要使用Spring底层的一些组件（ApplicationContext，BeanFactory）实现xxxAware接口；在创建对象时，会调用接口规定的方法注入到组件中；把Spring底层的一些组件注入到自定义的Bean中；xxxAware：功能使用xxxProcessor：ApplicationContextAware => ApplicationContextAwareProcessor；

```java
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
```

```java
@Service
public class BookService {

//    @Autowired(required = false)
//    @Qualifier("bookDAO")
//    @Resource(name = "bookDAO2")
    @Inject
    private BookDAO bookDAO;

    public void print() {
        System.out.println(bookDAO);
    }

    @Override
    public String toString() {
        return "BookService{" +
                "bookDAO=" + bookDAO +
                '}';
    }
}
```

### @Profile

指定组件在哪个环境的情况下才能被注册到容器中，不指定，任何环境下都能注册这个组件

1. 加了环境标识的Bean，只有这个环境被激活时才能注册到容器中；默认Default
2. *写在配置类上，只有指定环境的时候，整个配置类里面所有的配置才开始生效*
3. *没有标注环境就表示的**Bean**，在任何环境下都是加载的*



```java

    public Yellow yellow() {
        return new Yellow();
    }

    @Bean("devDatasource")
    @Profile("dev")
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword(dbPassword);
        dataSource.setJdbcUrl("jdbc:mysql://3306/test");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Bean("testDatasource")
    @Profile("test")
    public DataSource dataSource01(@Value("db.password") String pwd) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword(pwd);
        dataSource.setJdbcUrl("jdbc:mysql://3306/test");
        dataSource.setDriverClass(driver);
        return dataSource;
    }

    @Bean("prodDatasource")
    @Profile("prod")
    public DataSource dataSource02() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setJdbcUrl("jdbc:mysql://3306/test");
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        String dbDriver = stringValueResolver.resolveStringValue("${db.driver}");
        this.driver = dbDriver;
    }
}
```



```java
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
```

## AOP

 AOP： 动态代理

 程序运行期间动态的将某段代码切入到指定方法，指定位置进行运行的编程方式；

 <p>

1. 导入AOP模块 Spring-AOP spring-aspects

2. 定义一个业务逻辑类（MathCalculator）；在业务逻辑运行时将日志进行打印，（方法之前，方法运行，方法结束，方法异常）

3. 定义一个日志切面类（LogAspects）；切面类里面的方法需要动态感知 MathCalculator.div运行到哪里

 	通知方法：

 	前置通知(@Before)：logStart：在目标运行之前运行

​	 后置通知(@After)：logEnd：在目标运行之后运行（无论方法正常结束还是异常结束都执行）

​	 返回通知(@AfterReturning)：logReturn：在目标方法正常返回之后运行

​	 异常通知(@AfterThrowing)：logException：在运行出现异常以后执行

 	环绕通知(@Around)：动态代理，手动推进目标方法运行(joinPoint.proceed())

4. 给切面类的目标方法标注何时何地运行

5. 将切面类和业务逻辑类（目标方法所在类）都加入到容器中；

6. 必须告诉Spring哪个类是切面类（给切面类上加一个注解 @Aspect）

 [7].给配置类加 @EnableAspectJAutoProxy 【开启基于注解的AOP模式】

 --在Spring中有很多EnableXXX

**三步：**

1. 将业务逻辑组件和切面类都加入容器中；告诉Spring哪个是切面类

   2.在切面类上的每一个通知方法上标注注解通知，告诉Spring何时何地运行（切入点表达式）

   3.开启基于注解的AOP模式 @EnableAspectJAutoProxy



```java
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
```

```java
public class MathCalculator {
    public int div(int i, int j) {
        System.out.println("MathCalculator...div...");
        return i / j;
    }
}
```



```java
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
```

```java
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
```

### AOP原理

【看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么】

@EnableAspectJAutoProxy

1. 是什么：

​		@Import({AspectJAutoProxyRegistrar.class})；给容器中导入AspectJAutoProxyRegistrar

2. AnnotationAwareAspectJAutoProxyCreator;

   ->AspectJAwareAdvisorAutoProxyCreator

   ​	->AbstractAdvisorAutoProxyCreator

   ​		->AbstractAutoProxyCreator

   ​				implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware

   ​				关注后置处理器（在Bean初始化完成前后做的事情）、自动装配BeanFactory、
