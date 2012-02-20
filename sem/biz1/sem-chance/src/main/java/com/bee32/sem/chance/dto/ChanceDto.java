package com.bee32.sem.chance.dto;

import java.util.ArrayList;
import java.util.List;

import javax.free.ParseException;
import javax.free.Strings;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.util.TextUtil;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.chance.util.DateToRange;
import com.bee32.sem.process.base.ProcessEntityDto;

public class ChanceDto
        extends ProcessEntityDto<Chance> {

    private static final long serialVersionUID = 1L;

    public static final int PARTIES = 1;
    public static final int QUOTATIONS = 2;
    public static final int ACTIONS = 4;

    String serial;

    ChanceCategoryDto category;
    String subject;
    String content;
    String date;

    ChanceSourceTypeDto source;

    List<ChancePartyDto> parties;
    List<ChanceQuotationDto> quotations;
    List<ChanceActionDto> actions;

    ChanceStageDto stage;

    String address;

    public ChanceDto() {
        super();
    }

    public ChanceDto(int fmask) {
        super(fmask);
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
        else
            parties = new ArrayList<ChancePartyDto>();

        if (selection.contains(QUOTATIONS))
            quotations = marshalList(ChanceQuotationDto.class, source.getQuotations());
        else
            quotations = new ArrayList<ChanceQuotationDto>();

        if (selection.contains(ACTIONS))
            actions = mrefList(ChanceActionDto.class, source.getActions());
        else
            actions = new ArrayList<ChanceActionDto>();

        stage = mref(ChanceStageDto.class, source.getStage());
        address = source.getAddress();
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
        if (selection.contains(QUOTATIONS))
            mergeList(target, "quotations", quotations);
        if (selection.contains(ACTIONS))
            mergeList(target, "actions", actions);

        target.setAddress(address);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    @NLength(max = Chance.SERIAL_LENGTH)
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = TextUtil.normalizeSpace(serial);
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

    @NLength(min = 1, max = Chance.SUBJECT_LENGTH)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = TextUtil.normalizeSpace(subject);
    }

    @NLength(min = 1, max = Chance.CONTENT_LENGTH)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = TextUtil.normalizeSpace(content);
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

    public List<ChanceQuotationDto> getQuotations() {
        return quotations;
    }

    public void setQuotations(List<ChanceQuotationDto> quotations) {
        this.quotations = quotations;
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

    public boolean removeAction(ChanceActionDto action) {
        if (!actions.remove(action))
            return false;
        refreshStage();
        return true;
    }

    public ChanceStageDto getStage() {
        return stage;
    }

    public void setStage(ChanceStageDto stage) {
        this.stage = stage;
    }

    @NLength(max = Chance.ADDRESS_LENGTH)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = TextUtil.normalizeSpace(address);
    }

    void refreshStage() {
        int cachedOrder = getStage().getOrder();

        int maxOrder = 0;
        ChanceStageDto maxStage = new ChanceStageDto().ref(ChanceStage.INIT);
        for (ChanceActionDto action : getActions()) {
            int order = action.getStage().getOrder();
            if (order > maxOrder) {
                maxOrder = order;
                maxStage = action.getStage();
            }
        }

        if (maxOrder > cachedOrder)
            this.stage = maxStage;
    }

}
