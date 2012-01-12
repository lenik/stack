package user.war.web;

import user.war.dto.AttackMissionDto;
import user.war.entity.AttackMission;

import com.bee32.sem.misc.SimpleEntityViewBean;

public class AttackMissionBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public AttackMissionBean() {
        super(AttackMission.class, AttackMissionDto.class, 0);
    }

}
