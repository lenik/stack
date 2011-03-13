package user.hibernate.anno;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.stereotype.Component;

import com.bee32.plover.inject.LegacyContext;
import com.bee32.plover.orm.dao.HibernateTemplate;

@Component
public class AnnHibernateTest {

    @Inject
    DataSource dataSource;

    SessionFactory sessionFactory;

    HibernateTemplate template;

    public static void main(String[] args)
            throws BeansException, Exception {
        new LegacyContext().getBean(AnnHibernateTest.class).run();
    }

    public void run()
            throws Exception {
        AnnotationSessionFactoryBean asfb = new AnnotationSessionFactoryBean();

        asfb.setDataSource(dataSource);

        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        asfb.setHibernateProperties(properties);

        asfb.setAnnotatedClasses(new Class<?>[] { Fruit.class });

        asfb.afterPropertiesSet();

        sessionFactory = asfb.getObject();
        template = new HibernateTemplate(sessionFactory);

        test1();
    }

    void test1() {
        Fruit apple = new Fruit();
        // apple.setName("Apple");
        apple.setRank(100);
        apple.setPrice(10);

        Serializable id = template.save(apple);
        System.out.println("Apple id: " + id + ", " + apple.getId());

        final Fruit reload = template.load(Fruit.class, id);

        template.executeWithNativeSession(new HibernateCallback<Long>() {
            @Override
            public Long doInHibernate(org.hibernate.Session session)
                    throws HibernateException, SQLException {

                System.out.println("Update to reload");
                session.update(reload);

                System.out.println("Reload: " + reload);
                return null;
            }
        });
    }

}
