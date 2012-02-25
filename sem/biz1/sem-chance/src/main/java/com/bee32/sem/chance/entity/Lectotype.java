package com.bee32.sem.chance.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.process.base.ProcessEntity;

/**
 * 选型
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "lectotype_seq", allocationSize = 1)
public class Lectotype extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    Chance chance;

    List<LectotypeItem> items;

    /**
     * 对应机会
     */
    @OneToOne(optional = false)
    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    @OneToMany(mappedBy = "parent")
    @Cascade(CascadeType.ALL)
    public List<LectotypeItem> getItems() {
        return items;
    }

    public void setItems(List<LectotypeItem> items) {
        this.items = items;
    }


}
