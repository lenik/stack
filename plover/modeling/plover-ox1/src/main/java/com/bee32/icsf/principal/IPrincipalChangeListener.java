package com.bee32.icsf.principal;

/**
 *
 */
public interface IPrincipalChangeListener {

    int PRINCIPAL_NAME = 0x1;
    int PRINCIPAL_INFO = 0x2;
    int PRINCIPAL_IM_SET = 0x10;
    int PRINCIPAL_INV_SET = 0x20;
    int PRINCIPAL_CREATE = 0x100;
    int PRINCIPAL_MODIFIED = 0x200;
    int PRINCIPAL_DELETED = 0x800;

    void principalChanged(PrincipalChangeEvent event);

}
