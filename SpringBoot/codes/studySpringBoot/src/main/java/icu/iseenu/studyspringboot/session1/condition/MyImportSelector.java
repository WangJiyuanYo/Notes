package icu.iseenu.studyspringboot.session1.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

//自定义返回逻辑，
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
