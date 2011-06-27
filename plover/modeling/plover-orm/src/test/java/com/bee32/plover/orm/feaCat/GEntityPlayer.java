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
@Using(Animals.class)
@Import(WiredDaoTestCase.class)
public class GEntityPlayer
        extends FeaturePlayer<GEntityPlayer> {

    static Logger logger = LoggerFactory.getLogger(SessionCachePlayer.class);

    @Inject
    CommonDataManager dataManager;

    <E extends Entity<K>, K extends Serializable> //
    IEntityAccessService<E, K> asFor(Class<? extends E> entityType) {
        IEntityAccessService<E, K> service = dataManager.access(entityType);
        return service;
    }

    @Transactional
    public void doFill() {
        Cat tom = new Cat("Tom", "black");
        asFor(Cat.class).save(tom);

        CatFavTag catFavTag = new CatFavTag();
        catFavTag.setWho(tom);
        catFavTag.setTag("fish");
        asFor(CatFavTag.class).save(catFavTag);
    }

    @Transactional(readOnly = true)
    public void doList() {
        List<CatFavTag> tags = dataManager.access(CatFavTag.class).list();
        for (CatFavTag tag : tags)
            System.out.println("Tag: " + tag);
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
