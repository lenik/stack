package user.war.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import com.bee32.plover.ox1.color.Pink;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.builtin.IJudgeNumber;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;

@Entity
@Pink
@SequenceGenerator(name = "idgen", sequenceName = "build_mission_seq", allocationSize = 1)
public class BuildMission
        extends UIEntityAuto<Integer>
        implements IVerifiable<ISingleVerifierWithNumber>, IJudgeNumber {

    private static final long serialVersionUID = 1L;

    private long money;
    private String target;
    private SingleVerifierWithNumberSupport support = new SingleVerifierWithNumberSupport(this);

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

    @Embedded
    @Override
    public SingleVerifierWithNumberSupport getVerifyContext() {
        return support;
    }

    void setVerifyContext(SingleVerifierWithNumberSupport support) {
        support.bind(this);
        this.support = support;
    }

    @Transient
    @Override
    public String getNumberDescription() {
        return "建筑费用";
    }

    @Transient
    @Override
    public Number getJudgeNumber() {
        return getMoney();
    }

    @Override
    protected void formatEntryText(StringBuilder buf) {
        buf.append(getId() + ": " + target);
    }

}
