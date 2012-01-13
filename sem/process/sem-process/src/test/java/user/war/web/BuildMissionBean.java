package user.war.web;

import user.war.dto.BuildMissionDto;
import user.war.entity.BuildMission;

import com.bee32.sem.misc.SimpleEntityViewBean;

public class BuildMissionBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public BuildMissionBean() {
        super(BuildMission.class, BuildMissionDto.class, 0);
    }

}
