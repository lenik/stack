package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.free.UnexpectedException;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.people.dto.AbstractPartyDto;
import com.bee32.sem.people.dto.ContactCategoryDto;
import com.bee32.sem.people.dto.ContactDto;
import com.bee32.sem.people.dto.PartyTagnameDto;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyTagname;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.sandbox.MultiTabEntityViewBean;
import com.bee32.sem.sandbox.UIHelper;

public abstract class AbstractPartyAdminBean
        extends MultiTabEntityViewBean {

    private static final long serialVersionUID = 1L;

    protected static final int TAB_INDEX = 0;
    protected static final int TAB_FORM = 1;

    ContactDto selectedContact;
    ContactDto contact;

    List<String> selectedTagsToAdd;
    String selectedTagId;

    protected abstract AbstractPartyDto<? extends Party> getParty();

    protected abstract void setParty(AbstractPartyDto<? extends Party> party);

    public boolean isContactSelected() {
        if (selectedContact != null)
            return true;
        return false;
    }

    public ContactDto getSelectedContact() {
        return selectedContact;
    }

    public void setSelectedContact(ContactDto selectedContact) {
        this.selectedContact = selectedContact;
    }

    public ContactDto getContact() {
        if (contact == null)
            _newContact();
        return contact;
    }

    public void setContact(ContactDto contact) {
        this.contact = contact;
    }

    public List<String> getSelectedTagsToAdd() {
        return selectedTagsToAdd;
    }

    public void setSelectedTagsToAdd(List<String> selectedTagsToAdd) {
        this.selectedTagsToAdd = selectedTagsToAdd;
    }

    public String getSelectedTagId() {
        return selectedTagId;
    }

    public void setSelectedTagId(String selectedTagId) {
        this.selectedTagId = selectedTagId;
    }

    public List<SelectItem> getContactCategories() {
        List<ContactCategory> contactCategories = serviceFor(ContactCategory.class).list();
        List<ContactCategoryDto> contactCategoryDtos = DTOs.marshalList(ContactCategoryDto.class, contactCategories);
        return UIHelper.selectItemsFromDict(contactCategoryDtos);
    }

    public List<SelectItem> getTags() {
        List<PartyTagname> partyTags = serviceFor(PartyTagname.class).list();
        List<PartyTagnameDto> partyTagDtos = DTOs.marshalList(PartyTagnameDto.class, partyTags);
        return UIHelper.selectItemsFromDict(partyTagDtos);
    }

    public List<SelectItem> getOuterTags() {
        List<PartyTagname> partyTags = serviceFor(PartyTagname.class).list(PeopleCriteria.outerPartyTagList("id"));
        List<PartyTagnameDto> partyTagDtos = DTOs.marshalList(PartyTagnameDto.class, partyTags);
        return UIHelper.selectItemsFromDict(partyTagDtos);
    }

    public List<ContactDto> getContacts() {
        List<ContactDto> contacts = new ArrayList<ContactDto>();

        AbstractPartyDto<? extends Party> party = getParty();

        if (party != null && party.getId() != null) {
            contacts = party.getContacts();
        }

        return contacts;
    }

    void _newContact() {
        contact = new ContactDto().create();
        contact.setParty(getParty());
    }

    public void doNewContact() {
        if (getParty() == null || getParty().getId() == null) {
            uiLogger.error("提示:请先选择主人!");
        }
        _newContact();
    }

    public void doModifyContact() {
        contact = selectedContact;
    }

    public void doDeleteContact() {
        AbstractPartyDto<? extends Party> party = getParty();

        if (selectedContact == null) {
            uiLogger.error("提示:请选择需要删除的联系方式!");
            return;
        }

        try {
            party.getContacts().remove(selectedContact);
            Party _party = party.unmarshal();
            serviceFor(Party.class).saveOrUpdate(_party);

            party = reload(party);
            setParty(party);
        } catch (Exception e) {
            uiLogger.error("提示:删除联系方式失败;" + e.getMessage(), e);
        }
    }

    public void doSaveContact() {
        AbstractPartyDto<? extends Party> party = getParty();

        if (party == null || party.getId() == null) {
            uiLogger.error("提示:请选择所操作的联系方式对应的客户/供应商!");
            return;
        }

        try {
            List<ContactDto> contacts = party.getContacts();
            if (contact.getId() == null) {
                contacts.add(contact);
            } else {
                int newContactId = contact.getId();
                boolean matched = false;
                for (int i = 0; i < contacts.size(); i++) {
                    Integer oldId = contacts.get(i).getId();
                    if (oldId != null && oldId == newContactId) {
                        contacts.set(i, contact);
                        matched = true;
                        break;
                    }
                }
                if (!matched)
                    throw new UnexpectedException("No matched contact");
            }

            Party _party = party.unmarshal();
            serviceFor(Party.class).saveOrUpdate(_party);

            party = reload(party);
            setParty(party);

            uiLogger.info("提示:联系方式保存成功");
        } catch (Exception e) {
            uiLogger.error("提示:联系方式保存失败" + e.getMessage(), e);
        }
    }

    public void onRowSelectContact(SelectEvent event) {
        contact = selectedContact;
    }

    public void onRowUnselectContact(UnselectEvent event) {
        _newContact();
    }

    public void addTags() {
        AbstractPartyDto<? extends Party> party = getParty();

        if (party == null) {
            uiLogger.error("提示:请选择所操作的联系方式对应的客户/供应商!");
            return;
        }

        if (party.getTags() == null) {
            List<PartyTagnameDto> tags = new ArrayList<PartyTagnameDto>();
            party.setTags(tags);
        }
        for (String tagId : selectedTagsToAdd) {
            PartyTagname tag = loadEntity(PartyTagname.class, tagId);
            PartyTagnameDto t = DTOs.mref(PartyTagnameDto.class, tag);

            if (!party.getTags().contains(t))
                party.getTags().add(t);
        }
    }

    public void deleteTag() {
        AbstractPartyDto<? extends Party> party = getParty();

        if (party == null) {
            uiLogger.error("提示:请选择所操作的联系方式对应的客户/供应商!");
            return;
        }

        for (PartyTagnameDto t : party.getTags()) {
            if (t.getId().equals(selectedTagId)) {
                party.getTags().remove(t);
                return;
            }
        }
    }

}
