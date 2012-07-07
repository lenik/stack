package com.bee32.sem.chance.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.color.Green;
import com.bee32.sem.process.base.ProcessEntity;

/**
 * 销售机会
 */
@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "chance_seq", allocationSize = 1)
public class Chance
        extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    public static final int SUBJECT_LENGTH = 100;
    public static final int CONTENT_LENGTH = 500;
    public static final int ADDRESS_LENGTH = 200;

    ChanceCategory category = null;
    ChanceSourceType source = predefined(ChanceSourceTypes.class).OTHER;
    String subject = "";
    String content = "";
    Date anticipationBegin;
    Date anticipationEnd;

    List<ChanceParty> parties = new ArrayList<ChanceParty>();
    List<ChanceCompetitor> competitories = new ArrayList<ChanceCompetitor>();
    List<WantedProduct> products = new ArrayList<WantedProduct>();
    List<ChanceAction> actions = new ArrayList<ChanceAction>();

    ChanceStage stage = predefined(ChanceStages.class).INIT;
    ProcurementMethod procurementMethod = predefined(ProcurementMethods.class).OTHER;
    PurchaseRegulation purchaseRegulation = predefined(PurchaseRegulations.class).OTHER;

    String address;

    @Override
    public void populate(Object source) {
        if (source instanceof Chance)
            _populate((Chance) source);
        else
            super.populate(source);
    }

    protected void _populate(Chance o) {
        super._populate(o);
        category = o.category;
        source = o.source;
        subject = o.subject;
        content = o.content;
        anticipationBegin = o.anticipationBegin;
        anticipationEnd = o.anticipationEnd;
        parties = CopyUtils.copyList(o.parties);
        competitories = CopyUtils.copyList(o.competitories);
        products = CopyUtils.copyList(o.products);
        actions = new ArrayList<ChanceAction>(o.actions);
        stage = o.stage;
        procurementMethod = o.procurementMethod;
        purchaseRegulation = o.purchaseRegulation;
        address = o.address;
    }

    /**
     * 类型
     */
    @ManyToOne
    public ChanceCategory getCategory() {
        return category;
    }

    public void setCategory(ChanceCategory category) {
        this.category = category;
    }

    /**
     * 机会主题
     */
    @Column(length = SUBJECT_LENGTH, nullable = false)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = subject;
    }

    /**
     * 来源
     */
    @ManyToOne
    public ChanceSourceType getSource() {
        return source;
    }

    public void setSource(ChanceSourceType source) {
        if (source == null)
            throw new NullPointerException("source");
        this.source = source;
    }

    /**
     * 机会内容
     */
    @Column(length = CONTENT_LENGTH, nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null)
            throw new NullPointerException("content");
        this.content = content;
    }

    public Date getAnticipationBegin() {
        return anticipationBegin;
    }

    public void setAnticipationBegin(Date anticipationBegin) {
        this.anticipationBegin = anticipationBegin;
    }

    public Date getAnticipationEnd() {
        return anticipationEnd;
    }

    public void setAnticipationEnd(Date anticipationEnd) {
        this.anticipationEnd = anticipationEnd;
    }

    @OneToMany(mappedBy = "chance", orphanRemoval = true)
    @Cascade({ CascadeType.ALL })
    public List<ChanceParty> getParties() {
        return parties;
    }

    public void setParties(List<ChanceParty> parties) {
        if (parties == null)
            throw new NullPointerException("parties");
        this.parties = parties;
    }

    @OneToMany(mappedBy = "chance", orphanRemoval = true)
    @Cascade({ CascadeType.ALL })
    public List<ChanceCompetitor> getCompetitories() {
        return competitories;
    }

    public void setCompetitories(List<ChanceCompetitor> competitories) {
        if (competitories == null)
            throw new NullPointerException("competitories");
        this.competitories = competitories;
    }

    @OneToMany(mappedBy = "chance", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<WantedProduct> getProducts() {
        return products;
    }

    public void setProducts(List<WantedProduct> products) {
        if (products == null)
            throw new NullPointerException("products");
        this.products = products;
    }

    @OneToMany(mappedBy = "chance")
    @OrderBy("beginTime")
    public List<ChanceAction> getActions() {
        return actions;
    }

    public void setActions(List<ChanceAction> actions) {
        if (actions == null)
            throw new NullPointerException("actions");
        this.actions = actions;
    }

    /**
     * @return 最近一次行动，如果还没有任何行动返回 <code>null</code>.
     */
    @Transient
    public ChanceAction getLatestAction() {
        ChanceAction ca = null;
        if (getActions() != null) {
            ca = getActions().get(0);
            for (ChanceAction item : getActions()) {
                if (ca.getBeginTime().before(item.getBeginTime()))
                    ca = item;
            }
            // int lastIndex = getActions().size() - 1;
            // ca = getActions().get(lastIndex);
        }
        return ca;
    }

    public void addAction(ChanceAction action) {
        if (action == null)
            throw new NullPointerException("action");
        actions.add(action);
        action.setChance(this);
        refreshStage();
    }

    public void removeAction(ChanceAction action) {
        actions.remove(action);
        action.setChance(null);
        refreshStage();
    }

    void refreshStage() {
        ChanceStage maxStage = predefined(ChanceStages.class).INIT;
        for (ChanceAction action : actions) {
            ChanceStage stage = action.getStage();
            if (stage != null)
                if (stage.getOrder() >= maxStage.getOrder())
                    maxStage = stage;
        }
        this.stage = maxStage;
    }

    /**
     * 获取机会阶段/进度（冗余）。
     *
     * @return 机会最后一次更新的进度，如果尚无更新，应返回一个非空的初始进度。
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    @Redundant
    public ChanceStage getStage() {
        return stage;
    }

    /**
     * 机会进度只能通过日志项来改变，本对象中的进度为冗余。
     */
    public void setStage(ChanceStage stage) {
        this.stage = stage;
    }

    @ManyToOne
    public ProcurementMethod getProcurementMethod() {
        if (procurementMethod == null)
            procurementMethod = predefined(ProcurementMethods.class).OTHER;
        return procurementMethod;
    }

    public void setProcurementMethod(ProcurementMethod procurementMethod) {
        this.procurementMethod = procurementMethod;
    }

    @ManyToOne
    public PurchaseRegulation getPurchaseRegulation() {
        if (purchaseRegulation == null)
            purchaseRegulation = predefined(PurchaseRegulations.class).OTHER;
        return purchaseRegulation;
    }

    public void setPurchaseRegulation(PurchaseRegulation purchaseRegulation) {
        this.purchaseRegulation = purchaseRegulation;
    }

    /**
     * 机会地址: 项目型机会一般这个地址和客户公司地址是不同的
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
