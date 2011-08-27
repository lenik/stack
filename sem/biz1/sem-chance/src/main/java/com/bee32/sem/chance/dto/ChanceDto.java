package com.bee32.sem.chance.dto;

import java.util.List;

import javax.free.ParseException;
import javax.free.Strings;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceParty;
import com.bee32.sem.chance.util.DateToRange;

public class ChanceDto
        extends UIEntityDto<Chance, Long> {

    private static final long serialVersionUID = 1L;

    public static final int PARTIES = 1;
    public static final int ACTIONS = 2;

    String serial;

    ChanceCategoryDto category;
    String subject;
    String content;
    String date;

    String party;
    ChanceSourceDto source;

    List<ChancePartyDto> parties;
    List<ChanceActionDto> actions;

    ChanceStageDto stage;

    public ChanceDto() {
        super();
    }

    public ChanceDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(Chance source) {
        this.serial = source.getSerial();

        String partyString = null;
        for (ChanceParty party : source.getParties()) {
            if (partyString == null)
                partyString = party.getParty().getName();
            else
                partyString += "," + party.getParty().getName();
        }
        this.party = partyString;

        this.date = DateToRange.fullFormat.format(source.getCreatedDate()).substring(0, 16);
        this.category = mref(ChanceCategoryDto.class, source.getCategory());
        this.source = mref(ChanceSourceDto.class, source.getSource());
        this.subject = source.getSubject();
        this.content = source.getContent();

        if (selection.contains(PARTIES))
            this.parties = marshalList(ChancePartyDto.class, source.getParties());

        if (selection.contains(ACTIONS))
            this.actions = marshalList(ChanceActionDto.class, source.getActions(), true);

        this.stage = mref(ChanceStageDto.class, source.getStage());
    }

    @Override
    protected void _unmarshalTo(Chance target) {
        target.setSerial(serial);

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

    public void addParty(ChancePartyDto chanceParty) {
        if (chanceParty == null)
            throw new NullPointerException("chanceParty");
        if (!parties.contains(chanceParty))
            this.parties.add(chanceParty);
    }

    public void removeParty(ChancePartyDto chanceParty) {
        if (parties.contains(chanceParty))
            parties.remove(chanceParty);
    }

    public void addAction(ChanceActionDto action) {
        if (action == null)
            throw new NullPointerException("action");
        actions.add(action);
        refreshStage();
    }

    public void deleteAction(ChanceActionDto action) {
        if (actions.contains(action))
            this.actions.remove(action);
        refreshStage();
    }

    void refreshStage() {
        int cachedOrder = getStage().getOrder();

        int maxOrder = 0;
        ChanceStageDto maxStage = null;
        for (ChanceActionDto action : getActions()) {
            int order = action.getStage().getOrder();
            if (order > maxOrder) {
                maxOrder = order;
                maxStage = action.getStage();
            }
        }

        if (maxStage != null)
            if (maxOrder > cachedOrder)
                this.stage = maxStage;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShortContent() {
        return Strings.ellipse(content, 16);
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
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ChancePartyDto> getParties() {
        return parties;
    }

    public void setParties(List<ChancePartyDto> parties) {
        if (parties == null)
            throw new NullPointerException("parties");
        this.parties = parties;
    }

    public List<ChanceActionDto> getActions() {
        return actions;
    }

    public void setActions(List<ChanceActionDto> actions) {
        if (actions == null)
            throw new NullPointerException("actions");
        this.actions = actions;
    }

    public ChanceStageDto getStage() {
        return stage;
    }

    public void setStage(ChanceStageDto stage) {
        this.stage = stage;
    }

}
