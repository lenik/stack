package user.war.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.Pink;
import com.bee32.sem.process.base.ProcessEntity;
import com.bee32.sem.process.state.util.StateInt;

/**
 * 攻击作业
 *
 * <p lang="en">
 * Attack Mission
 */
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

    /*************************************************************************
     * Section: State Management
     *************************************************************************/

    /**
     * 请求中
     *
     * <p lang="en">
     * Request State
     *
     * An attack mission is under requesting.
     */
    @StateInt
    public static final int STATE_REQUEST = _STATE_0;

    /**
     * 已启动
     *
     * <p lang="en">
     * Start State
     *
     * An attack mission has been approved and started.
     */
    @StateInt
    public static final int STATE_START = 1;

    /**
     * 已完成
     *
     * <p lang="en">
     * Done State
     *
     * The mission is completed.
     */
    @StateInt
    public static final int STATE_DONE = _STATE_NORMAL_END;

    /**
     * 已取消
     *
     * <p lang="en">
     * Canceled State
     *
     * The mission is canceled.
     */
    @StateInt
    public static final int STATE_CANCELED = _STATE_ABNORMAL_END;

}
