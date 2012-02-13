package user.war.dto;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import user.war.entity.AttackMission;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.process.base.ProcessEntityDto;

public class AttackMissionDto
        extends ProcessEntityDto<AttackMission> {

    private static final long serialVersionUID = 1L;

    private String target;

    @Override
    protected void _marshal(AttackMission source) {
        target = source.getTarget();
    }

    @Override
    protected void _unmarshalTo(AttackMission target) {
        target.setTarget(this.target);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException, TypeConvertException {
        target = map.getString("target");
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
