package com.bee32.sem.people.web;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PersonRoleDto;
import com.bee32.sem.people.entity.Org;

@ForEntity(Org.class)
public class OrgAdminBean
        extends AbstractPartyAdminBean {

    private static final long serialVersionUID = 1L;

    public OrgAdminBean() {
        super(Org.class, OrgDto.class, PartyDto.CONTACTS, //
                Order.desc("id"));
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<PersonRoleDto> rolesMBean = ListMBean.fromEL(this, //
            "openedObject.roles", PersonRoleDto.class);
    final ListMBean<OrgUnitDto> orgUnitsMBean = ListMBean.fromEL(this, //
            "openedObject.orgUnits", OrgUnitDto.class);

    public ListMBean<PersonRoleDto> getRolesMBean() {
        return rolesMBean;
    }

    public ListMBean<OrgUnitDto> getOrgUnitsMBean() {
        return orgUnitsMBean;
    }

}
