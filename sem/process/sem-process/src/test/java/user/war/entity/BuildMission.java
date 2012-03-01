package user.war.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.Pink;
import com.bee32.sem.process.base.ProcessEntity;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;

@Entity
@Pink
@SequenceGenerator(name = "idgen", sequenceName = "build_mission_seq", allocationSize = 1)
public class BuildMission
        extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    private long money;
    private String target;

    public BuildMission() {
        setVerifyContext(new SingleVerifierWithNumberSupport());
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    protected void formatEntryText(StringBuilder buf) {
        buf.append(getId() + ": " + target);
    }

    @Transient
    @Override
    public String getNumberDescription() {
        return "建筑费用";
    }

    @Override
    protected Number computeJudgeNumber()
            throws Exception {
        return getMoney();
    }

}
