package com.bee32.sem.process.state.web;

import com.bee32.icsf.access.web.EntityTypeDescriptor;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.process.state.EntityStateDef;
import com.bee32.sem.process.state.EntityStateDefDto;
import com.bee32.sem.process.state.EntityStateOpDto;

public class EntityStateDefBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public EntityStateDefBean() {
        super(EntityStateDef.class, EntityStateDefDto.class, 0);
    }

    public void setDescriptorToApply(EntityTypeDescriptor descriptor) {
        EntityStateDefDto stateDef = getOpenedObject();
        stateDef.setEntityClass(descriptor.getEntityType());
    }

    /*************************************************************************
     * Section: Search
     *************************************************************************/

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<EntityStateOpDto> opsMBean = ListMBean.fromEL(this,//
            "openedObject.ops", EntityStateOpDto.class);

    public ListMBean<EntityStateOpDto> getOpsMBean() {
        return opsMBean;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/
    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        EntityStateDefDto stateDef = getOpenedObject();
        stateDef.getStateInt();
        return true;
    }

}
