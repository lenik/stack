package com.bee32.sem.chance.dto;

import java.util.Date;
import java.util.List;

import javax.free.ParseException;

import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.PinkEntityDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.entity.ChanceActionStyle;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.people.entity.Party;

public class ChanceActionDto
        extends PinkEntityDto<ChanceAction, Long> {

    private static final long serialVersionUID = 1L;

    public static final int PARTIES = 1;

    private boolean plan;
    private List<Party> parties;

    private User actor;
    private ChanceActionStyle style;

    private Date beginTime;
    private Date endTime;
    private String content;
    private String spending;
    private Chance chance;
    private ChanceStage stage;

    public ChanceActionDto() {
        super(PARTIES);
    }

    public ChanceActionDto(ChanceAction source) {
        super(PARTIES, source);
    }

    public ChanceActionDto(int selection, ChanceAction source) {
        super(selection, source);
    }

    public ChanceActionDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(ChanceAction source) {
    }

    @Override
    protected void _unmarshalTo(ChanceAction target) {
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public boolean isPlan() {
        return plan;
    }

    public void setPlan(boolean plan) {
        this.plan = plan;
    }

    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        this.parties = parties;
    }

    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }

    public ChanceActionStyle getStyle() {
        return style;
    }

    public void setStyle(ChanceActionStyle style) {
        this.style = style;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpending() {
        return spending;
    }

    public void setSpending(String spending) {
        this.spending = spending;
    }

    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    public ChanceStage getStage() {
        return stage;
    }

    public void setStage(ChanceStage stage) {
        this.stage = stage;
    }

}
