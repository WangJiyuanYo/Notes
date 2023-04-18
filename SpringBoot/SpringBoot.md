

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
