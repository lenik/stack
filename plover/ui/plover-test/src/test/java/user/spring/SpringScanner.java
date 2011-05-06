package user.spring;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import com.bee32.plover.orm.entity.IEntity;
import com.bee32.plover.test.WiredTestCase;

@ContextConfiguration("context1.xml")
// @TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
// @Transactional
@Ignore
public class SpringScanner
        extends WiredTestCase {

    @Autowired
    ApplicationContext context;

    @Test
    public void getBeanNames() {
        for (String n : context.getBeanDefinitionNames())
            System.out.println("Bean def name: " + n);
    }

    @Test
    public void getConfigClasses() {
        Map<String, Object> beans = context.getBeansWithAnnotation(Configuration.class);
        for (Entry<String, Object> ent : beans.entrySet()) {
            System.out.printf("%s -> %s\n", ent.getKey(), ent.getValue());
        }
    }

    @Test
    public void getEntities() {
        Map<String, ?> all = context.getBeansOfType(IEntity.class);
        for (Entry<String, ?> ent : all.entrySet()) {
            System.out.printf("%s -> %s\n", ent.getKey(), ent.getValue());
        }
    }

}
