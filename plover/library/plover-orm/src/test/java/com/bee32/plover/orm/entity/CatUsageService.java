package com.bee32.plover.orm.entity;

import java.util.ArrayList;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.dao.CatDao;

@Service
@Transactional
public class CatUsageService {

    @Inject
    CatDao dao;

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

//        dao.evict(kate);
//        dao.evict(kitty);
//        dao.evict(lily);
//        dao.evict(lucy);
    }

    @Transactional
    public void tcList() {
        for (Cat cat : dao.list()) {
            System.out.println("Cat new: " + cat);
        }
    }

}
