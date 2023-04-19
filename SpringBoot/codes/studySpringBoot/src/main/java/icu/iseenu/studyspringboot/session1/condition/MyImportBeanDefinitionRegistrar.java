package icu.iseenu.studyspringboot.session1.condition;

import icu.iseenu.studyspringboot.session1.entity.RainBow;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

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
