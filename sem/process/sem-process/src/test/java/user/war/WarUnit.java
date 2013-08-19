package user.war;

import user.war.entity.AttackMission;
import user.war.entity.BuildMission;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.file.SEMFileUnit;
import com.bee32.sem.people.SEMPeopleUnit;
import com.bee32.sem.process.SEMProcessUnit;

/**
 * 作战计划（测试）单元
 *
 * <p lang="en">
 * War (For Test) Unit
 */
@ImportUnit({ SEMProcessUnit.class, //
        /** 因为 People 无法单独运行，故这里引入 people-unit。 */
        SEMPeopleUnit.class, //
        /** 因为 File 无法单独运行，故这里引入 file-unit。 */
        SEMFileUnit.class, //
})
public class WarUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(AttackMission.class);
        add(BuildMission.class);
    }

}
