package com.bee32.plover.orm.feaCat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
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

    @Transactional(readOnly = false)
    public void doFill() {
        Tiger lucy = new Tiger("Lucy", "black");
        lucy.setBirthday(new Date());

        ICriteriaElement selector = lucy.getSelector();

        int count = asFor(Tiger.class).deleteAll(selector);
        System.out.println("Deleted: " + count);
        asFor(Tiger.class).save(lucy);
        asFor(Tiger.class).evict(lucy);

        Tiger reload = asFor(Tiger.class).getFirst(selector);
        Date birthday = reload.getBirthday();
        System.out.println(birthday);

        long time = birthday.getTime();
        Date d2 = new Date(time);
        System.out.println(d2);
    }

    @Transactional(readOnly = true)
    public void doList() {
        List<Tiger> tigers = asFor(Tiger.class).list();

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
