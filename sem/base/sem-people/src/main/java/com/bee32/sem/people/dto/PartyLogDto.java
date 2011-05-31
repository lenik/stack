package com.bee32.sem.people.dto;

import java.util.Date;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.people.entity.PartyLog;

public class PartyLogDto
        extends EntityDto<PartyLog, Long> {

    private static final long serialVersionUID = 1L;

    PartyLogCategoryDto category;
    Date date;
    PartyDto party;
    String description;

    public PartyLogDto() {
        super();
    }

    public PartyLogDto(PartyLog source) {
        super(source);
    }

    @Override
    protected void _marshal(PartyLog source) {
        party = new PartyDto(source.getParty());
        date = source.getDate();
        category = new PartyLogCategoryDto(source.getCategory());
        description = source.getDescription();
    }

    @Override
    protected void _unmarshalTo(PartyLog target) {
        target.setDate(date);
        target.setDescription(description);
        merge(target, "category", category);
        merge(target, "party", party);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
        String categoryId = map.getString("category.id");
        category = new PartyLogCategoryDto().ref(categoryId);

        date = map.getDate("date");

        int partyId = map.getInt("party.id");
        party = new PartyDto().ref(partyId);

        description = map.getString("description");
    }

}
