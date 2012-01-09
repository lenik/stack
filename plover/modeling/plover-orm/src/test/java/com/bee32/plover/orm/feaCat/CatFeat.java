package com.bee32.plover.orm.feaCat;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoFeat;
import com.bee32.plover.site.scope.SiteNaming;
import com.bee32.plover.test.ICoordinator;

@Using(AnimalUnit.class)
public class CatFeat
        extends WiredDaoFeat<CatFeat> {

    static Logger logger = LoggerFactory.getLogger(CatFeat.class);

    @Transactional(readOnly = false)
    public void doFill() {
        System.out.println("---------------- FILL BEGIN ----------------");
        Tiger lucy = new Tiger("Lucy", "black");
        lucy.setBirthday(new Date());
        lucy.setAddr(new CaveAddr("ZJ", "11 X Rd."));

        ICriteriaElement selector = lucy.getSelector();

        System.out.println("Pre-clean");
        int count = asFor(Tiger.class).findAndDelete(selector);
        System.out.println("    Deleted: " + count);

        System.out.println("Save lucy");
        asFor(Tiger.class).save(lucy);

        System.out.println("Evict lucy");
        asFor(Tiger.class).evict(lucy);

        System.out.println("Get first");
        Tiger reload = asFor(Tiger.class).getFirst(selector);
        Date birthday = reload.getBirthday();
        System.out.println(birthday);

        long time = birthday.getTime();
        Date d2 = new Date(time);
        System.out.println(d2);
        System.out.println("---------------- FILL END ----------------");
    }

    @Transactional(readOnly = true)
    public void doList() {
        System.out.println("---------------- LIST BEGIN ----------------");
        System.out.println("List tigers");
        List<Tiger> tigers = asFor(Tiger.class).list();

        for (Tiger t : tigers)
            System.out.println("Tiger: " + t);
        System.out.println("---------------- LIST END ----------------");
    }

    public static void main(String[] args)
            throws Exception {

        SiteNaming.setDefaultSiteName("cat");

        new CatFeat().mainLoop(new ICoordinator<CatFeat>() {
            @Override
            public void main(CatFeat feat)
                    throws Exception {
                System.out.println(">>>> doFill() ");
                feat.doFill();
                System.out.println(">>>> doList() ");
                feat.doList();
                System.out.println(">>>> end() ");
            }
        });
    }

}
