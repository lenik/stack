package com.bee32.sem.chance.dto;

import java.util.Date;
import java.util.List;

import javax.free.ParseException;
import javax.free.Strings;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.util.DateToRange;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.process.base.ProcessEntityDto;

public class ChanceActionDto
        extends ProcessEntityDto<ChanceAction>
        implements IEnclosedObject<ChanceDto> {

    private static final long serialVersionUID = 1L;

    public static final int PARTIES = 1;
    public static final int PARTNERS = 2;

    boolean plan;
    List<PartyDto> parties;
    List<UserDto> partners;

    UserDto actor;
    ChanceActionStyleDto style;

    String moreInfo;
    String spending;
    ChanceDto chance;
    ChanceStageDto stage;
    ChanceStageDto stage0;

    String suggestion;
    PersonDto suggester = new PersonDto();
    boolean read;

    public ChanceActionDto() {
        super();
    }

    public ChanceActionDto(int fmask) {
        super(fmask);
    }

    @Override
    protected void _marshal(ChanceAction source) {
        this.plan = source.isPlan();

        if (selection.contains(PARTIES))
            this.parties = mrefList(PartyDto.class, 0, source.getParties());

        if (selection.contains(PARTNERS))
            this.partners = mrefList(UserDto.class, 0, source.getPartners());

        this.actor = mref(UserDto.class, source.getActor());
        this.style = mref(ChanceActionStyleDto.class, source.getStyle());

        this.moreInfo = source.getMoreInfo();
        this.spending = source.getSpending();

        this.chance = mref(ChanceDto.class, source.getChance());
        this.stage = mref(ChanceStageDto.class, source.getStage());
        this.stage0 = stage;

        this.suggestion = source.getSuggestion();
        this.suggester = mref(PersonDto.class, source.getSuggester());
        this.read = source.isRead();
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
        merge(target, "stage", stage.joinByOrder(stage0));
        target.setSuggestion(suggestion);
        merge(target, "suggester", suggester);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        plan = map.getString("plan") == "plan" ? true : false;
        actor = new UserDto().ref(map.getInt("actorId"));
    }

    @Override
    public ChanceDto getEnclosingObject() {
        return getChance();
    }

    @Override
    public void setEnclosingObject(ChanceDto enclosingObject) {
        setChance(enclosingObject);
    }

    public String getDate() {
        if (beginTime == null)
            return "";
        else
            return DateToRange.fullFormat.format(beginTime).substring(0, 10);
    }

    @NotNull
    @Override
    public Date getBeginTime() {
        return super.getBeginTime();
    }

    @NotNull
    @Override
    public Date getEndTime() {
        return super.getEndTime();
    }

    public String getTimeRange() {
        if (beginTime != null && endTime != null)
            return DateToRange.fullFormat.format(beginTime).substring(10, 16) + "-"
                    + DateToRange.fullFormat.format(endTime).substring(10, 16);
        else
            return "";
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

    public void addParty(PartyDto party) {
        if (party == null)
            throw new NullPointerException("party");
        if (!parties.contains(party))
            parties.add(party);
    }

    public void deleteParty(PartyDto partyDto) {
        parties.remove(partyDto);
    }

    public String getPartiesHint() {
        if (parties == null)
            return "(n/a)";
        StringBuilder sb = null;
        for (PartyDto party : parties) {
            if (sb == null)
                sb = new StringBuilder();
            else
                sb.append(", ");
            sb.append(party.getDisplayName());
        }
        return sb.toString();
    }

    public List<UserDto> getPartners() {
        return partners;
    }

    public void setPartners(List<UserDto> partners) {
        this.partners = partners;
    }

    public void addPartner(UserDto partner) {
        if (partner == null)
            throw new NullPointerException("partner");
        if (!partners.contains(partner))
            partners.add(partner);
    }

    public void deletePartner(UserDto partner) {
        if (partners.contains(partner))
            partners.remove(partner);
    }

    public String getPartnersHint() {
        if (partners == null)
            return "(n/a)";
        StringBuilder sb = null;
        for (UserDto partner : partners) {
            if (sb == null)
                sb = new StringBuilder();
            else
                sb.append(", ");
            sb.append(partner.getDisplayName());
        }
        return sb.toString();
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

    public String getSubject() {
        if (moreInfo == null)
            return "(无标题)";
        return Strings.ellipse(getMoreInfo(), 16);
    }

    /**
     * 更多信息
     */
    @Length(max = ChanceAction.MORE_INFO_LENGTH)
    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    /**
     * 花费情况
     */
    @Length(max = ChanceAction.SPENDING_LENGTH)
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

    public void pushStage(ChanceStageDto stageDto) {
        if (stageDto != null) {
            if (stageDto.getOrder() > stage.getOrder())
                this.stage = stageDto;
        }
    }

    @NLength(max = ChanceAction.SUGGESTION_LENGTH)
    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = TextUtil.normalizeSpace(suggestion);
    }

    public PersonDto getSuggester() {
        return suggester;
    }

    public void setSuggester(PersonDto suggester) {
        this.suggester = suggester;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getStyleClass() {
        if (!DTOs.isNull(suggester) && !read)
            return "f-red f-bold";
        else
            return "";
    }

}
