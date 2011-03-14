package user.hibernate.anno;

import java.io.Serializable;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import user.hibernate.anno.ext.Banana;

import com.bee32.plover.inject.qualifier.Variant;
import com.bee32.plover.orm.dao.HibernateTemplate;

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
        new AnnoContext().getBean(AnnHibernatePlayer.class).runAop();
    }

    public void runAop()
            throws Exception {
        addFruits();
        reloadApple();

        addFriends();
        reloadTom();
    }

    Fruit apple;
    Serializable appleId;

    Banana banana;
    Serializable bananaId;

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
//        tom.addFruit(apple);
//        tom.addFruit(banana);
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
