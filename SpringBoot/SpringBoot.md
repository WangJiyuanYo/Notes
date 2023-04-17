# SpringBoot

## 注解

### @ComponentScan

```java
// 包含哪些
Filter[] includeFilters() default {};

//排除哪些
Filter[] excludeFilters() default {};
//按照注解或者类排除包下的扫描；Class或者Filter数组
userDefaultFilters = false; //关闭默认
```

