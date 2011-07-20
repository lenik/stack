package com.bee32.sem.chance.dto;

import java.util.List;

import javax.free.ParseException;
import javax.free.Strings;

import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.MomentIntervalDto;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.util.DateToRange;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.entity.Party;

public class ChanceActionDto
        extends MomentIntervalDto<ChanceAction> {

    private static final long serialVersionUID = 1L;

    public static final int PARTIES = 1;
    public static final int PARTNERS = 2;

    private String date;
    private String timeRange;
    private String actionType;
    private String party;
    private String partner;

    private boolean plan;
    private List<PartyDto> parties;
    private List<UserDto> partners;

    private UserDto actor;
    private ChanceActionStyleDto style;

    private String moreInfo;
    private String spending;
    private ChanceDto chance;
    private ChanceStageDto stage;

    public ChanceActionDto() {
        super();
    }

// public ChanceActionDto(int selection) {
// super(selection);
// }

    public void addParty(PartyDto... partyDtos) {
        if (partyDtos == null)
            throw new NullPointerException("partyDtos");
        for (PartyDto partyDto : partyDtos)
            if (!parties.contains(partyDto))
                parties.add(partyDto);
    }

    public void deleteParty(PartyDto partyDto) {
        if (parties.contains(partyDto))
            parties.remove(partyDto);
    }

    public void addPartners(UserDto... userDtos) {
        if (userDtos == null)
            throw new NullPointerException("partners");
        for (UserDto userDto : userDtos)
            if (!partners.contains(userDto))
                partners.add(userDto);
    }

    public void deletePartner(UserDto partner) {
        if (partners.contains(partner))
            partners.remove(partner);
    }

    public void pushStage(ChanceStageDto stageDto) {
        if (stageDto != null) {
            if (stageDto.getOrder() > stage.getOrder())
                this.stage = stageDto;
        }
    }

    static int index = 1;

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

        String tempParty = null;
        for (Party party : source.getParties()) {
            if (tempParty == null)
                tempParty = party.getDisplayName();
            else
                tempParty += "," + party.getDisplayName();
        }
        this.party = tempParty;

        String tempPartner = null;
        for (User user : source.getPartners()) {
            if (tempPartner == null)
                tempPartner = user.getDisplayName();
            else
                tempPartner += "," + user.getDisplayName();
        }
        this.partner = tempPartner;

        this.plan = source.isPlan();

        if (selection.contains(PARTIES))
            this.parties = marshalList(PartyDto.class, source.getParties(), true);

        if (selection.contains(PARTNERS))
            this.partners = marshalList(UserDto.class, source.getPartners());

        this.actor = mref(UserDto.class, source.getActor());
        this.style = mref(ChanceActionStyleDto.class, source.getStyle());

        this.moreInfo = source.getMoreInfo();
        this.spending = source.getSpending();

        if (source.getChance() == null)
            this.chance = new ChanceDto().ref();
        else
            this.chance = mref(ChanceDto.class, source.getChance());

        if (source.getStage() == null)
            this.stage = new ChanceStageDto().ref();
        else
            this.stage = mref(ChanceStageDto.class, source.getStage());
    }

    @Override
    protected void _unmarshalTo(ChanceAction target) {
        target.setPlan(plan);
        mergeList(target, "parties", parties);
        mergeList(target, "partners", partners);
        merge(target, "actor", actor);
        merge(target, "style", style);
        target.setMoreInfo(moreInfo);
        target.setSpending(spending);

        merge(target, "chance", chance);
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

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
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
        if (parties == null)
            throw new NullPointerException("parties");
        this.parties = parties;
    }

    public List<UserDto> getPartners() {
        return partners;
    }

    public void setPartners(List<UserDto> partners) {
        this.partners = partners;
    }

    public UserDto getActor() {
        return actor;
    }

    public void setActor(UserDto actor) {
        if (actor == null)
            throw new NullPointerException("actor");
        this.actor = actor;
    }

    public ChanceActionStyleDto getStyle() {
        return style;
    }

    public void setStyle(ChanceActionStyleDto style) {
        this.style = style;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getSubject() {
        return Strings.ellipse(getDescription(), 16);
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
// if (chance == null)
// throw new NullPointerException("chance");
        this.chance = chance;
    }

    public ChanceStageDto getStage() {
        return stage;
    }

    public void setStage(ChanceStageDto stage) {
// if (stage == null)
// throw new NullPointerException("stage");
        this.stage = stage;
    }

}
