package user.hibernate.fea2;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import user.hibernate.fea2.ext.Banana;

public class LocalSessionFactory
        extends AnnotationSessionFactoryBean {

    @Inject
    DataSource dataSource;

    public LocalSessionFactory() {
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
