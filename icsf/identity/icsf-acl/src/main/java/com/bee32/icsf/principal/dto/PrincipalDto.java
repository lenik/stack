package com.bee32.icsf.principal.dto;

import com.bee32.icsf.principal.Principal;

public class PrincipalDto
        extends AbstractPrincipalDto<Principal> {

    private static final long serialVersionUID = 1L;

    public PrincipalDto() {
        super();
    }

    public PrincipalDto(int selection) {
        super(selection);
    }

}
