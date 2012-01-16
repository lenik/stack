package com.bee32.sem.chance.dto;

import java.util.List;

import javax.free.ParseException;
import javax.free.Strings;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.util.DateToRange;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierSupportDto;
import com.bee32.sem.process.verify.dto.IVerifiableDto;

public class ChanceDto
        extends UIEntityDto<Chance, Long>
        implements IVerifiableDto {

    private static final long serialVersionUID = 1L;

    public static final int PARTIES = 1;
    public static final int ACTIONS = 2;

    String serial;

    ChanceCategoryDto category;
    String subject;
    String content;
    String date;

    ChanceSourceTypeDto source;

    List<ChancePartyDto> parties;
    List<ChanceActionDto> actions;

    ChanceStageDto stage;

    String address;

    // View-Related

    ChancePartyDto selectedParty;
    ChanceActionDto selectedAction;

    SingleVerifierSupportDto singleVerifierSupport;

    public ChanceDto() {
        super();
    }

    public ChanceDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(Chance source) {
        serial = source.getSerial();

        date = DateToRange.fullFormat.format(source.getCreatedDate()).substring(0, 16);
        category = mref(ChanceCategoryDto.class, source.getCategory());
        this.source = mref(ChanceSourceTypeDto.class, source.getSource());
        subject = source.getSubject();
        content = source.getContent();

        if (selection.contains(PARTIES))
            parties = marshalList(ChancePartyDto.class, source.getParties());

        if (selection.contains(ACTIONS))
            actions = mrefList(ChanceActionDto.class, source.getActions());

        stage = mref(ChanceStageDto.class, source.getStage());
        address = source.getAddress();

        singleVerifierSupport = marshal(SingleVerifierSupportDto.class, source.getVerifyContext());
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

        target.setAddress(address);

        merge(target, "verifyContext", singleVerifierSupport);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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

    public ChanceSourceTypeDto getSource() {
        return source;
    }

    public void setSource(ChanceSourceTypeDto source) {
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

    public void addParty(ChancePartyDto chanceParty) {
        if (chanceParty == null)
            throw new NullPointerException("chanceParty");
        if (!parties.contains(chanceParty))
            parties.add(chanceParty);
    }

    public void removeParty(ChancePartyDto chanceParty) {
        if (parties.contains(chanceParty))
            parties.remove(chanceParty);
    }

    public String getPartiesHint() {
        if (parties == null)
            return "(n/a)";
        StringBuilder sb = null;
        for (ChancePartyDto chparty : parties) {
            if (sb == null)
                sb = new StringBuilder();
            else
                sb.append(", ");
            sb.append(chparty.getParty().getDisplayName());
        }
        return sb.toString();
    }

    public List<ChanceActionDto> getActions() {
        return actions;
    }

    public void setActions(List<ChanceActionDto> actions) {
        if (actions == null)
            throw new NullPointerException("actions");
        this.actions = actions;
    }

    public void addAction(ChanceActionDto action) {
        if (action == null)
            throw new NullPointerException("action");
        actions.add(action);
        refreshStage();
    }

    public void deleteAction(ChanceActionDto action) {
        if (actions.contains(action))
            actions.remove(action);
        refreshStage();
    }

    public ChanceStageDto getStage() {
        return stage;
    }

    public void setStage(ChanceStageDto stage) {
        this.stage = stage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ChancePartyDto getSelectedParty() {
        return selectedParty;
    }

    public void setSelectedParty(ChancePartyDto selectedParty) {
        this.selectedParty = selectedParty;
    }

    public ChanceActionDto getSelectedAction() {
        return selectedAction;
    }

    public void setSelectedAction(ChanceActionDto selectedAction) {
        this.selectedAction = selectedAction;
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

    @Override
    public SingleVerifierSupportDto getVerifyContext() {
        return singleVerifierSupport;
    }

    public void setVerifyContext(SingleVerifierSupportDto singleVerifierSupport) {
        this.singleVerifierSupport = singleVerifierSupport;
    }

}
