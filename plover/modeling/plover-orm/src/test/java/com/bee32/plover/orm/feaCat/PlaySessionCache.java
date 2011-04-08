package com.bee32.plover.orm.feaCat;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.dao.CatDao;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.test.FeaturePlayer;

@Using(Animals.class)
@Import(WiredDaoTestCase.class)
@Scope("prototype")
@Lazy
@Service
public class PlaySessionCache
        extends FeaturePlayer<PlaySessionCache> {

    static Logger logger = LoggerFactory.getLogger(PlaySessionCache.class);

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
        new PlaySessionCache().mainLoop();
    }

    @Override
    protected void main(PlaySessionCache player)
            throws Exception {
        player.tcPrepare();
        player.tcList();
    }

}
