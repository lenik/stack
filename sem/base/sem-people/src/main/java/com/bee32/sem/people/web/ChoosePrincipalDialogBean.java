package com.bee32.sem.people.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.GroupDto;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalCriteria;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.RoleDto;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChoosePrincipalDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChoosePrincipalDialogBean.class);

    String header = "Please choose a principal..."; // NLS: 选择用户或组
    String stereo;

    public ChoosePrincipalDialogBean() {
        super(Principal.class, PrincipalDto.class, 0);
        // addSearchFragment("类型为", User.class);
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setStereo(String stereo) {
        if (stereo == null)
            stereo = "";
        switch (stereo) {
        case "":
        case "-":
            entityClass = Principal.class;
            dtoClass = PrincipalDto.class;
            break;
        case "U":
            entityClass = User.class;
            dtoClass = UserDto.class;
            break;
        case "G":
            entityClass = Group.class;
            dtoClass = GroupDto.class;
            break;
        case "R":
            entityClass = Role.class;
            dtoClass = RoleDto.class;
            break;
        default:
            throw new IllegalArgumentException("Bad stereo: " + stereo);
        }
    }

    @Override
    public void addNameOrLabelRestriction() {
        addSearchFragment("名称含有 " + searchPattern, PrincipalCriteria.namedLike(searchPattern));
        searchPattern = null;
    }

}
