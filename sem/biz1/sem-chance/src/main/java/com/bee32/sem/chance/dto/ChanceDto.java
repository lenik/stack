package com.bee32.sem.chance.dto;

import java.util.Date;
import java.util.List;

import javax.free.ParseException;

import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.color.UIEntityDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceParty;

public class ChanceDto
        extends UIEntityDto<Chance, Long> {

    private static final long serialVersionUID = 1L;

    public static final int PARTIES = 1;
    public static final int ACTIONS = 2;

    private String party;
    private UserDto owner;
    private ChanceCategoryDto category;
    private ChanceSourceDto source;
    private String subject;
    private String content;

    private Date createDate;

    private List<ChancePartyDto> parties;
    private List<ChanceActionDto> actions;

    ChanceStageDto stage;

    public ChanceDto() {
        super(PARTIES + ACTIONS);
    }

    public ChanceDto(Chance source) {
        super(PARTIES + ACTIONS, source);
    }

    public ChanceDto(int selection, Chance source) {
        super(selection, source);
    }

    public ChanceDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(Chance source) {
        String partyString = null;
        for (ChanceParty party : source.getParties()) {
            if (partyString == null)
                partyString = party.getParty().getName();
            else
                partyString += "," + party.getParty().getName();
        }
        this.party = partyString;
        this.owner = new UserDto(source.getOwner());
        this.category = new ChanceCategoryDto(source.getCategory());
        this.source = new ChanceSourceDto(source.getSource());
        this.subject = source.getSubject();
        this.content = source.getContent();

        if (selection.contains(PARTIES))
            this.parties = marshalList(ChancePartyDto.class, source.getParties());
        if (selection.contains(ACTIONS))
            this.actions = marshalList(ChanceActionDto.class, source.getActions());
        this.stage = new ChanceStageDto(source.getStage());
    }

    @Override
    protected void _unmarshalTo(Chance target) {
        merge(target, "owner", owner);
        merge(target, "category", category);
        merge(target, "source", source);
        target.setSubject(subject);
        target.setContent(content);

        if (selection.contains(PARTIES))
            mergeList(target, "parties", parties);
        if (selection.contains(ACTIONS))
            mergeList(target, "actions", actions);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public ChanceCategoryDto getCategory() {
        return category;
    }

    public void setCategory(ChanceCategoryDto category) {
        this.category = category;
    }

    public ChanceSourceDto getSource() {
        return source;
    }

    public void setSource(ChanceSourceDto source) {
        this.source = source;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<ChancePartyDto> getParties() {
        return parties;
    }

    public void setParties(List<ChancePartyDto> parties) {
        this.parties = parties;
    }

    public List<ChanceActionDto> getActions() {
        return actions;
    }

    public void setActions(List<ChanceActionDto> actions) {
        this.actions = actions;
    }

    public ChanceStageDto getStage() {
        return stage;
    }

    public void setStage(ChanceStageDto stage) {
        this.stage = stage;
    }

}
