package user.war.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.Pink;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.builtin.ISingleVerifier;
import com.bee32.sem.process.verify.builtin.SingleVerifierSupport;

@Entity
@Pink
@SequenceGenerator(name = "idgen", sequenceName = "attack_mission_seq", allocationSize = 1)
public class AttackMission
        extends UIEntityAuto<Integer>
        implements IVerifiable<ISingleVerifier> {

    private static final long serialVersionUID = 1L;

    private String target;
    private SingleVerifierSupport singleVerifierSupport = new SingleVerifierSupport();

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Embedded
    @Override
    public SingleVerifierSupport getVerifyContext() {
        return singleVerifierSupport;
    }

    void setVerifyContext(SingleVerifierSupport singleVerifierSupport) {
        this.singleVerifierSupport = singleVerifierSupport;
    }

    @Override
    protected void formatEntryText(StringBuilder buf) {
        buf.append(getId() + ": " + target);
    }

}
