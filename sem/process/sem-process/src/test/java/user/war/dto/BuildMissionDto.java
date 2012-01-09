package user.war.dto;

import javax.free.ParseException;
import javax.free.TypeConvertException;

import user.war.entity.BuildMission;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierWithNumberSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;

public class BuildMissionDto
        extends UIEntityDto<BuildMission, Integer>
        implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    private long money;
    private String target;
    private SingleVerifierWithNumberSupportDto svn;

    @Override
    protected void _marshal(BuildMission source) {
        money = source.getMoney();
        target = source.getTarget();
        svn = marshal(SingleVerifierWithNumberSupportDto.class, source.getVerifyContext());
    }

    @Override
    protected void _unmarshalTo(BuildMission target) {
        target.setMoney(money);
        target.setTarget(this.target);
        merge(target, "verifyContext", svn);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException, TypeConvertException {
        money = map.getLong("money");
        target = map.getString("target");
        svn.parse(map);
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
    public SingleVerifierWithNumberSupportDto getVerifyContext() {
        return svn;
    }

    public void setVerifyContext(SingleVerifierWithNumberSupportDto verifyContext) {
        this.svn = verifyContext;
    }

}
