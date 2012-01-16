package com.bee32.sem.chance.dto;

import java.util.List;

import javax.free.ParseException;
import javax.free.Strings;

import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.chance.util.DateToRange;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;

public class ChanceActionDto
        extends MomentIntervalDto<ChanceAction>
        implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    public static final int PARTIES = 1;
    public static final int PARTNERS = 2;

    private boolean plan;
    private List<PartyDto> parties;
    private List<UserDto> partners;

    private UserDto actor;
    private ChanceActionStyleDto style;

    private String moreInfo;
    private String spending;
    private ChanceDto chance;
    private ChanceStageDto stage;

    SingleVerifierSupportDto singleVerifierSupport;

    public ChanceActionDto() {
        super();
    }

    public ChanceActionDto(int selection) {
        super(selection);
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

        if (source.getChance() == null)
            this.chance = new ChanceDto().ref();
        else
            this.chance = mref(ChanceDto.class, source.getChance());

        if (source.getStage() == null)
            this.stage = new ChanceStageDto().ref();
        else
            this.stage = mref(ChanceStageDto.class, source.getStage());

        singleVerifierSupport = marshal(SingleVerifierSupportDto.class, source.getVerifyContext());


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

        merge(target, "verifyContext", singleVerifierSupport);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        plan = map.getString("plan") == "plan" ? true : false;
        actor = new UserDto().ref(map.getInt("actorId"));
    }

    public String getDate() {
        if (beginTime == null)
            return "";
        else
            return DateToRange.fullFormat.format(beginTime).substring(0, 10);
    }

    public String getTimeRange() {
        if (beginTime != null && endTime != null)
            return DateToRange.fullFormat.format(beginTime).substring(10, 16) + " 到 "
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

    public String getActionType() {
        return plan ? "计划" : "日志";
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

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getSubject() {
        if (moreInfo == null)
            return "(无标题)";
        return Strings.ellipse(getMoreInfo(), 16);
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

    public void pushStage(ChanceStageDto stageDto) {
        if (stageDto != null) {
            if (stageDto.getOrder() > stage.getOrder())
                this.stage = stageDto;
        }
    }

    @Override
    public SingleVerifierSupportDto getVerifyContext() {
        return singleVerifierSupport;
    }

    public void setVerifyContext(SingleVerifierSupportDto singleVerifierSupport) {
        this.singleVerifierSupport = singleVerifierSupport;
    }

}
