package com.bee32.plover.orm.feaCat;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityFlags;
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

        System.out.println("Clean up");
        // asFor(Tiger.class).deleteAll();

        Tiger lucy = new Tiger("Lucy 8", "black");
        lucy.setBirthday(new Date());
        lucy.setAddr(new CaveAddr("ZJ", "11 X Rd."));

        System.out.println("Save lucy");
        ctx.data.access(Tiger.class).save(lucy);

        EntityFlags flags = EntityAccessor.getFlags(lucy);
        flags.setLocked(true);
        System.out.println("Lock lucy");
        ctx.data.access(Tiger.class).saveOrUpdate(lucy);

        flags = EntityAccessor.getFlags(lucy);
        boolean locked = flags.isLocked();
        // flags.setUnlockReq(true);
        System.out.println("Unlock lucy");
        ctx.data.access(Tiger.class).saveOrUpdate(lucy);

        flags = EntityAccessor.getFlags(lucy);
        locked = flags.isLocked();

        System.out.println("Delete specific");
        ctx.data.access(Tiger.class).delete(lucy);

        System.out.println("---------------- FILL END ----------------");
    }

    @Transactional(readOnly = true)
    public void doList() {
        System.out.println("---------------- LIST BEGIN ----------------");
        System.out.println("List tigers");
        List<Tiger> tigers = ctx.data.access(Tiger.class).list();

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
