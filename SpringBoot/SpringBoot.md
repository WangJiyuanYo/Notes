

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

## @Scop/@Lazy  

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
