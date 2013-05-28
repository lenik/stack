package com.bee32.sem.chance.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DefaultValue;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.color.Green;
import com.bee32.sem.file.entity.UserFile;
import com.bee32.sem.process.base.ProcessEntity;
import com.bee32.sem.process.state.util.StateInt;

/**
 * 销售机会
 *
 * 销售员在跑销售时发掘的潜在的销售可能。
 *
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
    public static final int APPROVE_MESSAGE_LENGTH = 200;

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
    List<UserFile> attachments = new ArrayList<UserFile>();

    ChanceStage stage = predefined(ChanceStages.class).INIT;
    ProcurementMethod procurementMethod = predefined(ProcurementMethods.class).OTHER;
    PurchaseRegulation purchaseRegulation = predefined(PurchaseRegulations.class).OTHER;

    String address = "";

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
        attachments = new ArrayList<UserFile>(o.attachments);

        stage = o.stage;
        procurementMethod = o.procurementMethod;
        purchaseRegulation = o.purchaseRegulation;
        address = o.address;

        approveUser = o.approveUser;
        approved = o.approved;
        approveMessage = o.approveMessage;
    }

    /**
     * 类型
     *
     * 机会类型。
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
     *
     * 机会内容的概括。
     *
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
     * 机会来源
     *
     * 例如，机会来源于客户介绍，互联网搜索等。
     *
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
     *
     * 机会的详细描述。
     *
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

    /**
     * 预期定货时间起
     *
     * 达成销售，签定合同的可能开始时间。
     *
     * @return
     */
    public Date getAnticipationBegin() {
        return anticipationBegin;
    }

    public void setAnticipationBegin(Date anticipationBegin) {
        this.anticipationBegin = anticipationBegin;
    }

    /**
     * 预期定货时间止
     *
     * 达成销售，签定合同的可能结束时间。
     *
     * @return
     */
    public Date getAnticipationEnd() {
        return anticipationEnd;
    }

    public void setAnticipationEnd(Date anticipationEnd) {
        this.anticipationEnd = anticipationEnd;
    }

    /**
     * 对应客户
     *
     * 销售机会对应的所有客户。
     *
     * @return
     */
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

    /**
     * 竞争对手
     *
     * 机会存在的竞争对手列表。
     *
     * @return
     */
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

    /**
     * 选型产品
     *
     * 为客户选型产品列表。
     *
     * @return
     */
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

    /**
     * 日志
     *
     * 销售机会对应的销售员日志。
     *
     * @return
     */
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
     * 附件
     *
     * 附加的相关文件列表。
     */
    @OneToMany
    @JoinTable(name = "ChanceAttachment",//
    /*            */joinColumns = @JoinColumn(name = "chance"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "user_file"))
    public List<UserFile> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<UserFile> attachments) {
        if (attachments == null)
            throw new NullPointerException("attachments");
        this.attachments = attachments;
    }

    /**
     * 最后一次行动
     *
     * 机会对应的最后一次销售员的活动日志。
     *
     * @return
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
     * 机会阶段
     *
     * 获取机会阶段/进度（冗余）。机会最后一次更新的进度，如果尚无更新，应返回一个非空的初始进度。
     *
     * @return
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
     * 供货方式
     *
     * 产品供货方式。
     *
     * @return
     */
    @ManyToOne
    public ProcurementMethod getProcurementMethod() {
        if (procurementMethod == null)
            procurementMethod = predefined(ProcurementMethods.class).OTHER;
        return procurementMethod;
    }

    public void setProcurementMethod(ProcurementMethod procurementMethod) {
        this.procurementMethod = procurementMethod;
    }

    /**
     * 采购原则
     *
     * 客户采购产品的原则。
     *
     * @return
     */
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
     * 机会地址
     *
     * 项目型机会一般这个地址和客户公司地址是不同的。
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /*************************************************************************
     * Section: State Management
     *************************************************************************/

    /**
     * 初始状态
     *
     * 机会刚刚被建立，尚未通过初核。
     */
    /**
     * 已审核状态
     *
     * 机会已通过审核，表示机会中的信息正确。
     */
    /**
     * 审核未通过状态
     */
    @StateInt
    public static final int STATE_INIT = _STATE_0;

    @StateInt
    public static final int STATE_APPROVED = _STATE_NORMAL_END;

    @StateInt
    public static final int STATE_REJECTED = _STATE_ABNORMAL_END;

    User approveUser;
    boolean approved;
    String approveMessage = "";

    /**
     * 审核人
     *
     * 执行审核的用户。
     */
    @ManyToOne
    public User getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(User approveUser) {
        this.approveUser = approveUser;
    }

    /**
     * 审核状态
     *
     * 表示审核是否被通过。
     */
    @Column(nullable = false)
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    /**
     * 审核消息
     *
     * 审核人提供的附加消息。
     */
    @Column(nullable = false, length = APPROVE_MESSAGE_LENGTH)
    @DefaultValue("''")
    public String getApproveMessage() {
        return approveMessage;
    }

    public void setApproveMessage(String approveMessage) {
        if (approveMessage == null)
            throw new NullPointerException("approveMessage");
        this.approveMessage = approveMessage;
    }

    @Override
    protected Integer stateCode() {
        if (approveUser == null)
            return STATE_INIT;

        if (approved)
            return STATE_APPROVED;
        else
            return STATE_REJECTED;
    }

}
