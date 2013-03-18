package user.war.web;

import com.bee32.sem.misc.SimpleEntityViewBean;

import user.war.dto.BuildMissionDto;
import user.war.entity.BuildMission;

public class BuildMissionBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public BuildMissionBean() {
        super(BuildMission.class, BuildMissionDto.class, 0);
    }

}
