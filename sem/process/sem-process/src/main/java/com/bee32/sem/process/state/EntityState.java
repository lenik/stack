package com.bee32.sem.process.state;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DefaultValue;

import com.bee32.plover.orm.entity.EntityAuto;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.ox1.color.IUserFriendly;
import com.bee32.plover.ox1.color.UIEntity;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "entity_state_seq", allocationSize = 1)
public class EntityState
        extends EntityAuto<Integer>
        implements ITypeAbbrAware, IUserFriendly {

    private static final long serialVersionUID = 1L;

    public static final int LABEL_LENGTH = UIEntity.LABEL_LENGTH;
    public static final int DESCRIPTION_LENGTH = UIEntity.DESCRIPTION_LENGTH;

    String label = "";
    String description = "";

    Class<?> entityClass;
    int state;
    List<EntityStateManager> managers = new ArrayList<EntityStateManager>();

    @Column(length = LABEL_LENGTH, nullable = false)
    @DefaultValue("''")
    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        if (label == null)
            throw new NullPointerException("label");
        this.label = label;
    }

    @Column(length = DESCRIPTION_LENGTH, nullable = false)
    @DefaultValue("''")
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        if (description == null)
            throw new NullPointerException("description");
        this.description = description;
    }

    @Column(name = "entity", length = ABBR_LEN, nullable = false)
    public String getEntityAbbr() {
        return ABBR.abbr(entityClass);
    }

    public void setEntityAbbr(String entityAbbr) {
        if (entityAbbr == null)
            throw new NullPointerException("entityAbbr");
        try {
            entityClass = ABBR.expand(entityAbbr);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    @Transient
    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        if (entityClass == null)
            throw new NullPointerException("entityClass");
        this.entityClass = entityClass;
    }

    @Column(nullable = false)
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @OneToMany(mappedBy = "entityState", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<EntityStateManager> getManagers() {
        return managers;
    }

    public void setManagers(List<EntityStateManager> managers) {
        if (managers == null)
            throw new NullPointerException("managers");
        this.managers = managers;
    }

}
