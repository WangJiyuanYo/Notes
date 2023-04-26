package icu.iseenu.studyspringboot.session1.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(toBuilder = true)
@Data
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
