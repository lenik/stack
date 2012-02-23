package com.bee32.icsf.access.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.DataModel;

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

    String header = "Please choose an entity type..."; // NLS: 选择资源…

    /**
     * NOT USED !
     */
    @SuppressWarnings("unchecked")
    public ChooseEntityTypeDialogBean() {
        super(Entity.class, EntityDto.class, 0);
    }

    // Properties

    static List<EntityTypeDescriptor> descriptors;
    static {
        descriptors = new ArrayList<EntityTypeDescriptor>();
        PersistenceUnit unit = CustomizedSessionFactoryBean.getForceUnit();
        for (ClassCatalog catalog : unit.getDependencies()) {
            for (Class<?> entityType : catalog.getLocalClasses()) {
                EntityTypeDescriptor descriptor = new EntityTypeDescriptor(catalog, entityType);
                descriptors.add(descriptor);
            }
        }
    }

    @Override
    public DataModel<?> getDataModel() {
        return SelectableList.decorate(descriptors);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
