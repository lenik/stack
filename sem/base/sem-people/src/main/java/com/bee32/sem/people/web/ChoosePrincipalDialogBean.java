package com.bee32.sem.people.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.*;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChoosePrincipalDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChoosePrincipalDialogBean.class);

    String stereo;

    public ChoosePrincipalDialogBean() {
        super(Principal.class, PrincipalDto.class, 0);
        // addSearchFragment("类型为", User.class);
    }

    public void setStereo(String stereo) {
        if (stereo == null)
            stereo = "";
        switch (stereo) {
        case "":
        case "-":
            setEntityType(Principal.class);
            setEntityDtoType(PrincipalDto.class);
            break;
        case "U":
            setEntityType(User.class);
            setEntityDtoType(UserDto.class);
            break;
        case "G":
            setEntityType(Group.class);
            setEntityDtoType(GroupDto.class);
            break;
        case "R":
            setEntityType(Role.class);
            setEntityDtoType(RoleDto.class);
            break;
        default:
            throw new IllegalArgumentException("Bad stereo: " + stereo);
        }
    }

    @Override
    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "名称含有 " + searchPattern, PrincipalCriteria.namedLike(searchPattern));
        searchPattern = null;
    }

}
