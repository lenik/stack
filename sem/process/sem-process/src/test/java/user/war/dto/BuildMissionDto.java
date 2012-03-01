package user.war.dto;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import user.war.entity.BuildMission;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.process.base.ProcessEntityDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;

public class BuildMissionDto
        extends ProcessEntityDto<BuildMission>
        implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    private long money;
    private String target;

    @Override
    protected void _marshal(BuildMission source) {
        money = source.getMoney();
        target = source.getTarget();
    }

    @Override
    protected void _unmarshalTo(BuildMission target) {
        target.setMoney(money);
        target.setTarget(this.target);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException, TypeConvertException {
        money = map.getLong("money");
        target = map.getString("target");
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

}
