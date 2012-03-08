package user.war.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.Pink;
import com.bee32.sem.process.base.ProcessEntity;

@Entity
@Pink
@SequenceGenerator(name = "idgen", sequenceName = "attack_mission_seq", allocationSize = 1)
public class AttackMission
        extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    private String target;

    @Override
    public void populate(Object source) {
        if (source instanceof AttackMission)
            _populate((AttackMission) source);
        else
            super.populate(source);
    }

    protected void _populate(AttackMission o) {
        super._populate(o);
        target = o.target;
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

}
