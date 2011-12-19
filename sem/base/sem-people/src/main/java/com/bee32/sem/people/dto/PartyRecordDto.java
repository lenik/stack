package com.bee32.sem.people.dto;

import java.util.Date;

import javax.free.ParseException;
import javax.validation.constraints.NotNull;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.color.MomentIntervalDto;
import com.bee32.sem.hr.entity.PersonRecord;

public class PartyRecordDto
        extends MomentIntervalDto<PersonRecord> {

    private static final long serialVersionUID = 1L;

    PartyRecordCategoryDto category;
    Date date;
    PartyDto party;
    String text;

    public PartyRecordDto() {
        super();
    }

    @Override
    protected void _marshal(PersonRecord source) {
        party = marshal(PartyDto.class, source.getParty());
        category = marshal(PartyRecordCategoryDto.class, source.getCategory());
        text = source.getText();
    }

    @Override
    protected void _unmarshalTo(PersonRecord target) {
        target.setText(text);
        merge(target, "category", category);
        merge(target, "party", party);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        String categoryId = map.getString("category.id");
        category = new PartyRecordCategoryDto().ref(categoryId);

        int partyId = map.getInt("party.id");
        party = new PartyDto(0).ref(partyId);

        text = map.getString("text");
    }

    @NotNull
    public PartyRecordCategoryDto getCategory() {
        return category;
    }

    public void setCategory(PartyRecordCategoryDto category) {
        this.category = category;
    }

    @NotNull
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @NotNull
    public PartyDto getParty() {
        return party;
    }

    public void setParty(PartyDto party) {
        this.party = party;
    }

    @NLength(min = 5, max = PersonRecord.TEXT_LENGTH)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
