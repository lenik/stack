package com.bee32.sem.chance.dto;

import java.util.Date;
import java.util.List;

import javax.free.ParseException;
import javax.free.Strings;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.util.DateToRange;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;

public class ChanceActionDto
        extends EntityDto<ChanceAction, Long> {

    private static final long serialVersionUID = 1L;

    public static final int PARTIES = 1;

    private String date;
    private String timeRange;
    private String actionType;
    private String party;
    private String contentShort;

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
        super();
    }

    public ChanceActionDto(int selection) {
        super(selection);
    }

    public void addParty(PartyDto partyDto) {
        if (partyDto == null)
            throw new NullPointerException("partyDto");
        if (!parties.contains(partyDto))
            parties.add(partyDto);
    }

    public void deleteParty(PartyDto partyDto) {
        if (parties.contains(partyDto))
            parties.remove(partyDto);
    }

    @Override
    protected void _marshal(ChanceAction source) {

        this.date = source.getBeginTime() == null ? "" : DateToRange.fullFormat.format(source.getBeginTime())
                .substring(0, 10);

        if (source.getBeginTime() != null && source.getEndTime() != null)
            this.timeRange = DateToRange.fullFormat.format(source.getBeginTime()).substring(10, 16) + " 到 "
                    + DateToRange.fullFormat.format(source.getEndTime()).substring(10, 16);
        else
            this.timeRange = "";

        this.actionType = source.isPlan() == true ? "计划" : "日志";

        String temp = null;
        for (Party party : source.getParties()) {
            if (temp == null)
                temp = party.getName();
            else
                temp += "," + party.getName();
        }
        this.party = temp;
        this.contentShort = Strings.ellipse(source.getContent(), 16);

        this.plan = source.isPlan();

        if (selection.contains(PARTIES))
            this.parties = marshalList(PartyDto.class, source.getParties(), true);

        this.actor = mref(UserDto.class, source.getActor());
        this.style = mref(ChanceActionStyleDto.class, source.getStyle());

        this.beginTime = source.getBeginTime();
        this.endTime = source.getEndTime();

        this.content = source.getContent();
        this.spending = source.getSpending();

        this.chance = mref(ChanceDto.class, 0, source.getChance());
        this.stage = mref(ChanceStageDto.class, source.getStage());
    }

    @Override
    protected void _unmarshalTo(ChanceAction target) {
        target.setPlan(plan);
        mergeList(target, "parties", parties);
        merge(target, "actor", actor);
        merge(target, "style", style);
        target.setBeginTime(beginTime);
        target.setEndTime(endTime);
        target.setContent(content);
        target.setSpending(spending);
        if (chance != null)
            merge(target, "chance", chance);
        if (stage != null)
            merge(target, "stage", stage);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        plan = map.getString("plan") == "plan" ? true : false;
        actor = new UserDto().ref(map.getString("actorId"));
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getContentShort() {
        return contentShort;
    }

    public void setContentShort(String contentShort) {
        this.contentShort = contentShort;
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
