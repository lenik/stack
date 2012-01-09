package user.war;

import user.war.entity.AttackMission;
import user.war.entity.BuildMission;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.process.SEMProcessUnit;

@ImportUnit(SEMProcessUnit.class)
public class WarUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(AttackMission.class);
        add(BuildMission.class);
    }

}
