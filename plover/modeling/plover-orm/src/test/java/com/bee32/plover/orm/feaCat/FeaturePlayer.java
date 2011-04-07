package com.bee32.plover.orm.feaCat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.spring.ApplicationContextBuilder;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.dao.CatDao;

@Import(FeatureConfig.class)
@Scope("prototype")
@Service
public class FeaturePlayer {

    static Logger logger = LoggerFactory.getLogger(FeaturePlayer.class);

    @Inject
    SessionFactory sessionFactory;

    @Inject
    TransactionTemplate txt;

    @Inject
    CatDao dao;

    public void run() {
        tcPrepare();
        tcList();
    }

    @Transactional
    public void tcPrepare() {
        dao.deleteAll();

        Cat kate = new Cat("kate", "yellow");
        Cat kitty = new Cat("kitty", "pink");
        Cat lily = new Cat("lily", "white");
        Cat lucy = new Cat("lucy", "blue");

        kitty.setParent(kate);
        lily.setParent(kate);

        Set<Cat> children = new HashSet<Cat>();
        children.add(kitty);
        children.add(lily);
        kate.setChildren(children);

        lily.setLeader(lucy);

        System.err.println("Create new cats");

        dao.save(kate);
        dao.save(lucy);

        Tiger tiger = new Tiger("smith", "black");
        Tiger son = new Tiger("son", "orange");
        son.setParent(tiger);

        dao.save(tiger);
        dao.save(son);

        System.err.println("Flush");
        dao.flush();

        System.err.println("Evict the instances");
        dao.evict(kate);
        dao.evict(kitty);
        dao.evict(lily);
        dao.evict(lucy);

        System.err.println("End of prepare()");
    }

    @Transactional(readOnly = true)
    public void tcList() {

        logger.warn("List using catDao.list()");

        for (Cat cat : dao.list()) {
            System.out.println("Listed:" + cat);
        }

        logger.warn("End of catDao.list()");

    }

    public static void main(String[] args)
            throws Exception {

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {

            try {
                FeaturePlayer player = new FeaturePlayer();

                ApplicationContext context = ApplicationContextBuilder.buildSelfHostedContext(player.getClass());
                Object b1 = context.getBean("mybook");
                System.out.println(b1);
                b1 = context.getBean("featureSFB");
                b1 = context.getBean(CustomizedSessionFactoryBean.class);
                b1 = context.getBean(SessionFactory.class);
                AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();

                beanFactory.autowireBean(player);

                // ApplicationContextBuilder.selfWire(player);

                System.err.println("Got player: " + player);

                player.tcPrepare();
                player.tcList();

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Press enter to try again");
            stdin.readLine();
        }
    }

}
