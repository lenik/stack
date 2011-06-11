package com.bee32.sem.people.dto;

import java.util.Date;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.people.entity.PartyRecord;

public class PartyRecordDto
        extends EntityDto<PartyRecord, Long> {

    private static final long serialVersionUID = 1L;

    PartyRecordCategoryDto category;
    Date date;
    PartyDto party;
    String description;

    public PartyRecordDto() {
        super();
    }

    public PartyRecordDto(PartyRecord source) {
        super(source);
    }

    @Override
    protected void _marshal(PartyRecord source) {
        party = new PartyDto(source.getParty());
        date = source.getDate();
        category = new PartyRecordCategoryDto(source.getCategory());
        description = source.getDescription();
    }

    @Override
    protected void _unmarshalTo(PartyRecord target) {
        target.setDate(date);
        target.setDescription(description);
        merge(target, "category", category);
        merge(target, "party", party);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        String categoryId = map.getString("category.id");
        category = new PartyRecordCategoryDto().ref(categoryId);

        date = map.getDate("date");

        int partyId = map.getInt("party.id");
        party = new PartyDto().ref(partyId);

        description = map.getString("description");
    }

}
