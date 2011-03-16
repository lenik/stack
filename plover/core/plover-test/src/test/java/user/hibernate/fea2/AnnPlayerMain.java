package user.hibernate.fea2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import user.hibernate.fea2.ext.Banana;

import com.bee32.plover.inject.cref.ContextRefs;
import com.bee32.plover.inject.qualifier.Variant;
import com.bee32.plover.orm.context.TxContext;
import com.bee32.plover.orm.dao.HibernateTemplate;

@Transactional
public class AnnPlayerMain {

    @Inject
    @Variant("AnnSFB")
    SessionFactory sessionFactory;

    public static void main(String[] args)
            throws Exception {
        mainByContext(args);
    }

    public static void mainByContext(String[] args)
            throws Exception {

        ApplicationContext applicationContext = new LocalContext(//
                new TxContext(ContextRefs.SCAN_TEST)).getApplicationContext();

        // applicationContext.getBean(AnnPlayerMain.class).runAop();

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            applicationContext.getBean(AnnPlayer.class).run();
            System.out.println("Press enter to try again");
            stdin.readLine();
        }
    }

    Fruit apple;
    Serializable appleId;

    Banana banana;
    Serializable bananaId;

    public void runAop()
            throws Exception {
        addFruits();
        reloadApple();
        addFriends();
        reloadTom();
    }

    @Transactional
    void addFruits() {
        HibernateTemplate template = new HibernateTemplate(sessionFactory);

        apple = new Fruit();
        apple.setName("Apple");
        apple.setPrice(10);
        appleId = template.save(apple);

        banana = new Banana();
        banana.setPrice(30);
        banana.setLength(12);
        bananaId = template.save(banana);

        System.out.println("Apple: " + apple);
    }

    @Transactional(readOnly = true)
    void reloadApple() {
        HibernateTemplate template = new HibernateTemplate(sessionFactory);

        final Fruit reloadApple = template.load(Fruit.class, appleId);

        System.out.println("Reload: " + reloadApple);
    }

    Friend tom;
    Serializable tomId;

    @Transactional
    void addFriends() {
        HibernateTemplate template = new HibernateTemplate(sessionFactory);

        Friend tom = new Friend("tom");
// tom.addFruit(apple);
// tom.addFruit(banana);
        tom.setFav(apple);
        tomId = template.save(tom);

        System.out.println("Saved tom: " + tom);
    }

    @Transactional
    void reloadTom() {
        HibernateTemplate template = new HibernateTemplate(sessionFactory);

        Friend tom = template.load(Friend.class, tomId);
        System.out.println("Reload tom: " + tom);
    }

}
