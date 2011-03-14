package user.hibernate.anno;

import java.io.Serializable;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.inject.qualifier.Variant;
import com.bee32.plover.orm.context.TxLegacyContext;
import com.bee32.plover.orm.dao.HibernateTemplate;

//@Component
@Transactional
public class AnnHibernatePlayer {

    @Inject
    @Variant("AnnSFB")
    SessionFactory sessionFactory;

    public static void main(String[] args)
            throws Exception {
        mainByContext(args);
    }

    public static void mainByContext(String[] args)
            throws Exception {
        new TxLegacyContext().getBean(AnnHibernatePlayer.class).runAop();
    }

    public void runAop()
            throws Exception {
        test1();
        test2();
    }

    Fruit apple;
    Serializable appleId;

    @Transactional
    void test1() {
        apple = new Fruit();

        // apple.setName("Apple");
        apple.setPrice(10);

        HibernateTemplate template = new HibernateTemplate(sessionFactory);
        appleId = template.save(apple);
        System.out.println("Apple id: " + appleId + ", " + apple.getId() + "    -- " + appleId.getClass());
    }

    @Transactional(readOnly = true)
    void test2() {
        HibernateTemplate template = new HibernateTemplate(sessionFactory);

        final Fruit reload = template.load(Fruit.class, appleId);

        System.out.println("Reload: " + reload);
    }

}
