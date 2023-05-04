package icu.iseenu.studyspringboot.session1.config;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import icu.iseenu.studyspringboot.session1.entity.Yellow;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Profile
 * Spring为我们提供可以根据当前环境，动态的激活和切换一系列组件的功能；
 * 开发环境，测试环境，生产环境
 * 数据源（/A/B/C）
 * <p>
 * 1.加了环境标识的Bean，只有这个环境被激活时才能注册到容器中；默认Default
 * 2.写在配置类上，只有指定环境的时候，整个配置类里面所有的配置才开始生效
 * 3.没有标注环境就表示的Bean，在任何环境下都是加载的
 */

@Component
//@Profile("test")
@PropertySource("classpath:/dbconfig.properties")
public class MainConfigOfProfile implements EmbeddedValueResolverAware {

    @Value("${db.password}")
    private String dbPassword;

    private String driver;

    private StringValueResolver stringValueResolver;

    @Bean
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
