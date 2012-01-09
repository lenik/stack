package user.war;

import user.war.entity.AttackMission;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Process, SEMOids.process.Process, 1 })
public class WarModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/2/1/1";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(AttackMission.class, "attack");
    }

}
