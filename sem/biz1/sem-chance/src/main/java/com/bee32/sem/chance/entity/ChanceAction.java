package com.bee32.sem.chance.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.free.Strings;
import javax.persistence.*;

import org.hibernate.annotations.DefaultValue;

import com.bee32.icsf.principal.User;
import com.bee32.plover.ox1.color.Pink;
import com.bee32.sem.calendar.ICalendarEvent;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.process.base.ProcessEntity;

/**
 * 行动记录,日志
 *
 * 销售员每天记录的日志，计划。
 *
 * @author jack
 *
 */
@Entity
@Pink
@SequenceGenerator(name = "idgen", sequenceName = "chance_action_seq", allocationSize = 1)
public class ChanceAction
        extends ProcessEntity
        implements ICalendarEvent, Comparable<ChanceAction> {

    private static final long serialVersionUID = 1L;

    public static final int MORE_INFO_LENGTH = 10000;
    public static final int SPENDING_LENGTH = 1000;
    public static final int SUGGESTION_LENGTH = 200;

    boolean plan = false;
    List<Party> parties = new ArrayList<Party>();
    List<User> partners = new ArrayList<User>();

    User actor;
    ChanceActionStyle style = predefined(ChanceActionStyles.class).OTHER;

    String moreInfo;
    String spending = "";
    Chance chance;
    ChanceStage stage;

    String suggestion;
    Person suggester;
    boolean read;

    public ChanceAction() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        setBeginTime(cal.getTime());
        setEndTime(new Date());
    }

    @Override
    public void populate(Object source) {
        if (source instanceof ChanceAction)
            _populate((ChanceAction) source);
        else
            super.populate(source);
    }

    protected void _populate(ChanceAction o) {
        super._populate(o);
        plan = o.plan;
        parties = new ArrayList<Party>(o.parties);
        partners = new ArrayList<User>(o.partners);
        actor = o.actor;
        style = o.style;
        moreInfo = o.moreInfo;
        spending = o.spending;
        chance = o.chance;
        stage = o.stage;
    }

    /**
     * 工作日志类型
     *
     * 是日志还是计划
     *
     */
    @Column(nullable = false)
    public boolean isPlan() {
        return plan;
    }

    public void setPlan(boolean plan) {
        this.plan = plan;
    }

    /**
     * 对应客户
     *
     * 日志所对应的客户列表。
     *
     */
    @ManyToMany
    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        if (parties == null)
            parties = new ArrayList<Party>();
        this.parties = parties;
    }

    /**
     * 工作伙伴
     *
     * 在发生日志中所描述内容时一起参与的其他公司内部人员。
     *
     */
    @ManyToMany
    public List<User> getPartners() {
        return partners;
    }

    public void setPartners(List<User> partners) {
        this.partners = partners;
    }

    /**
     * 行动人
     *
     * 日志的具体行动人。
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        if (actor == null)
            throw new NullPointerException("can't set Null to ChanceAction.actor");
        this.actor = actor;
    }

    /**
     * 洽谈方式
     *
     * 机会发生内容时，和客户交流的所采取的方式。
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    public ChanceActionStyle getStyle() {
        return style;
    }

    public void setStyle(ChanceActionStyle style) {
        if (style == null)
            throw new NullPointerException("can't set null to ChanceAction.style");
        this.style = style;
    }

    /**
     * 详细描述
     *
     * 日志的详细描述。
     *
     * @return
     */
    @Column(length = MORE_INFO_LENGTH)
    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    /**
     * 费用明细
     *
     * 在发生日志中所描述内容时所产用的费用和描述。
     *
     */
    @Column(length = SPENDING_LENGTH, nullable = false)
    public String getSpending() {
        return spending;
    }

    public void setSpending(String spending) {
        if (spending == null)
            spending = "";
        this.spending = spending;
    }

    /**
     * 机会
     *
     * 日志对应的销售机会。
     */
    @ManyToOne
    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    /**
     * 对应机会的阶段
     *
     * 每一条日志，都可能便对应销售机会的阶段改变。即机会的阶段为时间最晚的日志的队段。
     *
     */
    @ManyToOne
    public ChanceStage getStage() {
        return stage;
    }

    public void setStage(ChanceStage stage) {
        this.stage = stage;
    }

    @Transient
    public void pushToStage(ChanceStage stage) {
        if (stage == null)
            throw new NullPointerException("stage");
        if (stage.getOrder() >= getStage().getOrder())
            this.stage = stage;
    }

    /**
     * 建议
     *
     * 批阅人所给出的建议。
     *
     * @return
     */
    @Column(length = SUGGESTION_LENGTH)
    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    /**
     * 批阅人
     *
     * 日志的批阅人。
     *
     * @return
     */
    @ManyToOne
    public Person getSuggester() {
        return suggester;
    }

    public void setSuggester(Person suggester) {
        this.suggester = suggester;
    }

    /**
     * 批阅标志
     *
     * 批阅人是否已经批阅
     *
     * @return
     */
    @Column(nullable = false)
    @DefaultValue("false")
    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    /**
     * 行动简略
     *
     * 从日志详细内容中自动取出一部份。
     *
     * 【工作日志】AAA...
     */
    @Transient
    @Override
    public String getSubject() {
        return Strings.ellipse(getDescription(), 25);
    }

    /**
     * 内容
     *
     * 日志的具体内容。
     */
    @Transient
    @Override
    public String getContent() {
        return getDescription();
    }

    @Transient
    @Override
    public List<String> getStyles() {
        return Collections.emptyList();
    }

    @Override
    public int compareTo(ChanceAction o) {
        Date time1 = getBeginTime();
        Date time2 = o.getBeginTime();
        int cmp = time1.compareTo(time2);
        if (cmp != 0)
            return cmp;
        int hash1 = System.identityHashCode(this);
        int hash2 = System.identityHashCode(o);
        cmp = hash1 - hash2;
        return cmp;
    }

}
