package com.bee32.plover.orm.feaCat;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.bee32.plover.orm.dao.CatDao;

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

        Cat kate = new Cat("kate", "yellow");
        Cat kitty = new Cat("kitty", "pink");
        Cat lily = new Cat("lily", "white");
        Cat lucy = new Cat("lucy", "blue");

// kitty.setParent(kate);
// lily.setParent(kate);

        lily.setLeader(lucy);

        Set<Cat> children = new HashSet<Cat>();
        children.add(kitty);
        children.add(lily);
        children.add(lucy);
        kate.setChildren(children);

        System.err.println("Delete all");
        dao.deleteAll();

        System.err.println("Create new cats");

        dao.save(kate);
//        dao.save(kitty);
//        dao.save(lucy);
//        dao.save(lily);

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
            System.out.println("Cat new: " + cat);
        }

        logger.warn("End of catDao.list()");

    }

}
