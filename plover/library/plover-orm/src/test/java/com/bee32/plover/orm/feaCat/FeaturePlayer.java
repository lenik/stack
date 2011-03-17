package com.bee32.plover.orm.feaCat;

import java.util.ArrayList;

import javax.inject.Inject;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.bee32.plover.orm.dao.CatDao;

@Service
// @Transactional
public class FeaturePlayer {

    @Inject
    SessionFactory sessionFactory;

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

        kitty.setParent(kate);
        lily.setParent(kate);

        lily.setLeader(lucy);

        ArrayList<Cat> children = new ArrayList<Cat>();
        children.add(kitty);
        children.add(lily);
        kate.setChildren(children);

        dao.deleteAll();
        dao.save(kate);
        dao.save(kitty);
        dao.save(lucy);
        dao.save(lily);
        dao.flush();

        dao.evict(kate);
        dao.evict(kitty);
        dao.evict(lily);
        dao.evict(lucy);
    }

    @Inject
    TransactionTemplate ttmp;

    @Transactional
    public void tcList() {
        ttmp.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                for (Cat cat : dao.list()) {
                    System.out.println("Cat new: " + cat);
                }
            }
        });
    }

}
