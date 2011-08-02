package com.bee32.plover.orm.feaCat;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Alias;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.test.FeaturePlayer;

@Service
@Scope("prototype")
@Lazy
@Using(AnimalUnit.class)
@Import(WiredDaoTestCase.class)
public class GEntityPlayer
        extends FeaturePlayer<GEntityPlayer> {

    static Logger logger = LoggerFactory.getLogger(GEntityPlayer.class);

    @Inject
    CommonDataManager dataManager;

    <E extends Entity<? extends K>, K extends Serializable> //
    IEntityAccessService<E, K> asFor(Class<E> entityType) {
        IEntityAccessService<E, K> service = dataManager.asFor(entityType);
        return service;
    }

    @Transactional
    public void doFill() {
        Tiger lucy = new Tiger("Lucy", "black");
        Tiger child = new Tiger("Child", "pink");

        child.setParent(lucy);

        asFor(Tiger.class).save(lucy);
        asFor(Tiger.class).save(child);

    }

    @Transactional(readOnly = true)
    public void doList() {
        List<Tiger> tigers = asFor(Tiger.class).list(//
                new Alias("parent", "parent"), //
                new Equals("parent.name", "Lucy"));

        for (Tiger t : tigers)
            System.out.println("Tiger: " + t);
    }

    public static void main(String[] args)
            throws Exception {
        new GEntityPlayer().mainLoop();
    }

    @Override
    protected void main(GEntityPlayer player)
            throws Exception {
        player.doFill();
        player.doList();
    }

}
