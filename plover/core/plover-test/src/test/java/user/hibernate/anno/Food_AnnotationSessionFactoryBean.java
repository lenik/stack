package user.hibernate.anno;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import user.hibernate.anno.ext.Banana;

public class Food_AnnotationSessionFactoryBean
        extends AnnotationSessionFactoryBean {

    @Inject
    DataSource dataSource;

    public Food_AnnotationSessionFactoryBean() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        this.setHibernateProperties(properties);

        this.setAnnotatedClasses(new Class<?>[] {
                //

                Friend.class, //
                Fruit.class, //
                Banana.class, //
        });
    }

    @Override
    public void afterPropertiesSet()
            throws Exception {

        this.setDataSource(dataSource);

        super.afterPropertiesSet();
    }

}
