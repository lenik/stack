package com.bee32.sem.makebiz.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.make.entity.MakeStepModel;

/**
 * 工艺点
 *
 * 产品的每一个生产步骤所对应的信息都记录在本类和本类的items中
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_step_instance_seq", allocationSize = 1)
public class MakeStep
        extends MomentInterval
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    MakeProcess parent;

    // Behavior as <model.part, model.processOrder>.
    MakeStepModel model;

    boolean done;

    List<MakeStepItem> items = new ArrayList<MakeStepItem>();

    @Override
    public void populate(Object source) {
        if (source instanceof MakeStep)
            _populate((MakeStep) source);
        else
            super.populate(source);
    }

    protected void _populate(MakeStep o) {
        super._populate(o);
        parent = o.parent;
        model = o.model;
        done = o.done;

        items = CopyUtils.copyList(o.items);
    }

    /**
     * 工艺流转单
     *
     * 工艺流转单主控类。
     *
     * @return
     */
    @NaturalId
    @ManyToOne(optional = false)
    public MakeProcess getParent() {
        return parent;
    }

    public void setParent(MakeProcess parent) {
        this.parent = parent;
    }

    /**
     * 工艺步骤
     *
     * 对应的标准工艺步骤。
     *
     * @return
     */
    @NaturalId
    @ManyToOne(optional = false)
    public MakeStepModel getModel() {
        return model;
    }

    public void setModel(MakeStepModel model) {
        this.model = model;
    }


    /**
     * 是否完成
     *
     * 本步骤是否完成。
     *
     * @return
     */
    @Column(nullable = false)
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * 工艺点数据名细列表
     *
     */
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<MakeStepItem> getItems() {
        return items;
    }

    public void setItems(List<MakeStepItem> items) {
        if(items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public synchronized void addItem(MakeStepItem item) {
        if (item == null)
            throw new NullPointerException("item");

        items.add(item);
    }

    public synchronized void removeItem(MakeStepItem item) {
        if (item == null)
            throw new NullPointerException("item");

        int index = items.indexOf(item);
        if (index == -1)
            return /* false */;

        items.remove(index);
        item.detach();

    }

}
