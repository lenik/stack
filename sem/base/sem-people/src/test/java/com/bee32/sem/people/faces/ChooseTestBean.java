package com.bee32.sem.people.faces;

import java.util.ArrayList;
import java.util.List;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.orm.util.DataViewBean;

public class ChooseTestBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    List<PrincipalDto> principals = new ArrayList<PrincipalDto>();

    public List<PrincipalDto> getPrincipals() {
        return principals;
    }

    public void setSelection(Object selection) {
        System.out.println("Set selection: " + selection);
    }

}
