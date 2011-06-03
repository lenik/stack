package com.bee32.sem.chance.dto;

import java.util.Date;
import java.util.List;

import javax.free.ParseException;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.PinkEntityDto;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.people.dto.PartyDto;

public class ChanceActionDto
        extends PinkEntityDto<ChanceAction, Long> {

    private static final long serialVersionUID = 1L;

    public static final int PARTIES = 1;

    private boolean plan;
    private List<PartyDto> parties;

    private UserDto actor;
    private ChanceActionStyleDto style;

    private Date beginTime;
    private Date endTime;
    private String content;
    private String spending;
    private ChanceDto chance;
    private ChanceStageDto stage;

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
        this.plan = source.isPlan();
        if (selection.contains(PARTIES))
            this.parties = marshalList(PartyDto.class, source.getParties());
        this.actor = new UserDto(source.getActor());
        this.actor = new UserDto(source.getActor());
        this.style = new ChanceActionStyleDto(source.getStyle());
        this.beginTime = source.getBeginTime();
        this.endTime = source.getEndTime();
        this.content = source.getContent();
        this.spending = source.getSpending();
        this.chance = new ChanceDto(0, source.getChance());
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

    public List<PartyDto> getParties() {
        return parties;
    }

    public void setParties(List<PartyDto> parties) {
        this.parties = parties;
    }

    public UserDto getActor() {
        return actor;
    }

    public void setActor(UserDto actor) {
        this.actor = actor;
    }

    public ChanceActionStyleDto getStyle() {
        return style;
    }

    public void setStyle(ChanceActionStyleDto style) {
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

    public ChanceDto getChance() {
        return chance;
    }

    public void setChance(ChanceDto chance) {
        this.chance = chance;
    }

    public ChanceStageDto getStage() {
        return stage;
    }

    public void setStage(ChanceStageDto stage) {
        this.stage = stage;
    }

}
