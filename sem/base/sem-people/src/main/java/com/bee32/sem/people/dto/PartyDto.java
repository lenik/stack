package com.bee32.sem.people.dto;

import com.bee32.plover.arch.util.MarshalType;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.people.entity.Party;

public class PartyDto
        extends AbstractPartyDto<Party> {

    private static final long serialVersionUID = 1L;

//    public PartyDto() {
//        super();
//    }
//
//    public PartyDto(Party source) {
//        super(source);
//    }

    public PartyDto(int selection) {
        super(selection);
    }

    public PartyDto(int selection, Party source) {
        super(selection, source);
    }

    /*
     * TODO Fix typearg for DTO.ref(E).
     */
    @SuppressWarnings("unchecked")
    public <D extends EntityDto<? extends Party, Integer>> D ref2(Party entity) {
        EntityDto<Party, Integer> $ = super.ref(entity);
        return (D) $;
    }

}
