package com.bee32.sem.chance.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.builtin.ISingleVerifier;
import com.bee32.sem.process.verify.builtin.SingleVerifierSupport;

/**
 * 销售机会
 */
@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "chance_seq", allocationSize = 1)
public class Chance
        extends UIEntityAuto<Long>
        implements IVerifiable<ISingleVerifier> {

    private static final long serialVersionUID = 1L;

    public static final int SERIAL_LENGTH = 30;
    public static final int SUBJECT_LENGTH = 100;
    public static final int CONTENT_LENGTH = 500;
    public static final int ADDRESS_LENGTH = 200;

    String serial;

    ChanceCategory category = ChanceCategory.NORMAL;
    ChanceSourceType source = ChanceSourceType.OTHER;
    String subject = "";
    String content = "";

    List<ChanceParty> parties = new ArrayList<ChanceParty>();
    List<ChanceQuotation> quotations = new ArrayList<ChanceQuotation>();
    List<ChanceAction> actions = new ArrayList<ChanceAction>();

    ChanceStage stage = ChanceStage.INIT;

    String address;

    public Chance() {
        setVerifyContext(new SingleVerifierSupport());
    }

    @NaturalId
    @Column(length = SERIAL_LENGTH)
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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
    @Cascade(CascadeType.ALL)
    public List<ChanceQuotation> getQuotations() {
        return quotations;
    }

    public void setQuotations(List<ChanceQuotation> quotations) {
        if (quotations == null)
            throw new NullPointerException("quotations");
        this.quotations = quotations;
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
        refreshStage();
    }

    public void removeAction(ChanceAction action) {
        actions.remove(action);
        refreshStage();
    }

    void refreshStage() {
        ChanceStage maxStage = null;
        for (ChanceAction action : actions) {
            if (maxStage == null)
                maxStage = action.getStage();
            else {
                ChanceStage stage = action.getStage();
                if (stage != null)
                    if (stage.getOrder() >= maxStage.getOrder())
                        maxStage = stage;
            }
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

    /**
     * 机会地址: 项目型机会一般这个地址和客户公司地址是不同的
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    protected Serializable naturalId() {
        return serial;
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (serial == null)
            return null;
        return new Equals(prefix + "serial", serial);
    }

    SingleVerifierSupport verifyContext;

    @Embedded
    @Override
    public SingleVerifierSupport getVerifyContext() {
        return verifyContext;
    }

    public void setVerifyContext(SingleVerifierSupport verifyContext) {
        this.verifyContext = verifyContext;
    }
}
