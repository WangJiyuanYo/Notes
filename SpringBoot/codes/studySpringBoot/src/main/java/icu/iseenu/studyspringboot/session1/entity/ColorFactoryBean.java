package icu.iseenu.studyspringboot.session1.entity;

import org.springframework.beans.factory.FactoryBean;

//创建一个Spring定义的FactoryBean
public class ColorFactoryBean implements FactoryBean<Color> {

    //会返回一个Color对象，这个对象会添加到容器中
    @Override
    public Color getObject() throws Exception {
        System.out.println("Create Color Factory Bean");
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    //Ture : 单例，在容器中保存一份
    @Override
    public boolean isSingleton() {
        return true;
    }
}
