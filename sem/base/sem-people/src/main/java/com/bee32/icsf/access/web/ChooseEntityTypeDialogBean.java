package com.bee32.icsf.access.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.ClassCatalog;
import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.config.CustomizedSessionFactoryBean;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseEntityTypeDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseEntityTypeDialogBean.class);

    /**
     * NOT USED !
     */
    @SuppressWarnings("unchecked")
    public ChooseEntityTypeDialogBean() {
        super(Entity.class, EntityDto.class, 0);
    }

    // Properties

    public SelectableList<?> getList() {
        List<EntityTypeDescriptor> descriptors;
        descriptors = new ArrayList<EntityTypeDescriptor>();
        PersistenceUnit unit = CustomizedSessionFactoryBean.getForceUnit();
        for (ClassCatalog catalog : unit.getAllDependencies()) {
            for (Class<?> entityType : catalog.getLocalClasses()) {
                EntityTypeDescriptor descriptor = new EntityTypeDescriptor(catalog, entityType);
                descriptors.add(descriptor);
            }
        }
        return SelectableList.decorate(descriptors);
    }

}
