package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.sem.people.Gender;
import com.bee32.sem.people.dto.ContactCategoryDto;
import com.bee32.sem.people.dto.OrgTypeDto;
import com.bee32.sem.people.dto.PartySidTypeDto;
import com.bee32.sem.people.dto.PartyTagnameDto;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.PartySidType;
import com.bee32.sem.people.entity.PartyTagname;
import com.bee32.sem.sandbox.UIHelper;

public class PeopleDictsBean
        extends DataViewBean {

    private static final long serialVersionUID = 1L;

    List<SelectItem> contactCategorySelectItems;
    List<SelectItem> partyTagnameSelectItems;
    List<SelectItem> orgTypeSelectItems;

    public synchronized List<SelectItem> getContactCategorySelectItems() {
        if (contactCategorySelectItems == null) {
            List<ContactCategoryDto> list = mrefList(ContactCategory.class, ContactCategoryDto.class, 0);
            contactCategorySelectItems = UIHelper.selectItemsFromDict(list);
        }
        return contactCategorySelectItems;
    }

    public synchronized List<SelectItem> getPartyTagnameSelectItems() {
        if (contactCategorySelectItems == null) {
            List<PartyTagnameDto> list = mrefList(PartyTagname.class, PartyTagnameDto.class, 0);
            partyTagnameSelectItems = UIHelper.selectItemsFromDict(list);
        }
        return partyTagnameSelectItems;
    }

    public synchronized List<SelectItem> getOrgTypeSelectItems() {
        if (orgTypeSelectItems == null) {
            List<OrgTypeDto> list = mrefList(OrgType.class, OrgTypeDto.class, 0);
            orgTypeSelectItems = UIHelper.selectItemsFromDict(list);
        }
        return orgTypeSelectItems;
    }

    public List<SelectItem> getGenders() {
        List<SelectItem> genders = new ArrayList<SelectItem>();
        for (Gender g : Gender.values())
            genders.add(new SelectItem(g.getValue(), g.getDisplayName()));
        return genders;
    }

    public List<SelectItem> getSidTypes() {
        List<PartySidType> sidTypes = ctx.data.access(PartySidType.class).list();
        List<PartySidTypeDto> sidTypeDtos = DTOs.marshalList(PartySidTypeDto.class, sidTypes);
        return UIHelper.selectItemsFromDict(sidTypeDtos);
    }

}
