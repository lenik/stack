package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.free.UnexpectedException;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.people.dto.AbstractPartyDto;
import com.bee32.sem.people.dto.ContactCategoryDto;
import com.bee32.sem.people.dto.ContactDto;
import com.bee32.sem.people.dto.PartyTagDto;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyTag;
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
        List<ContactCategory> contactCategories = getDataManager().loadAll(ContactCategory.class);
        List<ContactCategoryDto> contactCategoryDtos = DTOs.marshalList(ContactCategoryDto.class, contactCategories);
        return UIHelper.selectItemsFromDict(contactCategoryDtos);
    }

    public List<SelectItem> getTags() {
        List<PartyTag> partyTags = getDataManager().loadAll(PartyTag.class);
        List<PartyTagDto> partyTagDtos = DTOs.marshalList(PartyTagDto.class, partyTags);
        return UIHelper.selectItemsFromDict(partyTagDtos);
    }

    public List<ContactDto> getContacts() {
        List<ContactDto> contacts = new ArrayList<ContactDto>();

        AbstractPartyDto<? extends Party> party = getParty();

        if (party != null && party.getId() != null) {
            if (party.getContacts() != null) {
                contacts = party.getContacts();
            }
        }

        return contacts;
    }

    void _newContact() {
        contact = new ContactDto();
        contact.setParty(getParty());
    }

    public void doNewContact() {
        if (getParty() == null || getParty().getId() == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("提示", "请先选择主人!"));
        }
        _newContact();
    }

    public void doModifyContact() {
        contact = selectedContact;
    }

    public void doDeleteContact() {
        FacesContext context = FacesContext.getCurrentInstance();

        AbstractPartyDto<? extends Party> party = getParty();

        if (selectedContact == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择需要删除的联系方式!"));
            return;
        }

        try {
            party.getContacts().remove(selectedContact);
            Party _party = party.unmarshal();
            getDataManager().saveOrUpdate(_party);

            party = reload(party);
            setParty(party);
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "删除联系方式失败;" + e.getMessage()));
        }
    }

    public void doSaveContact() {
        FacesContext context = FacesContext.getCurrentInstance();

        AbstractPartyDto<? extends Party> party = getParty();

        if (party == null || party.getId() == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择所操作的联系方式对应的客户/供应商!"));
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
            getDataManager().saveOrUpdate(_party);

            party = reload(party);
            setParty(party);

            context.addMessage(null, new FacesMessage("提示", "联系方式保存成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "联系方式保存失败" + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void onRowSelectContact(SelectEvent event) {
        contact = selectedContact;
    }

    public void onRowUnselectContact(UnselectEvent event) {
        _newContact();
    }

    public void addTags() {
        FacesContext context = FacesContext.getCurrentInstance();

        AbstractPartyDto<? extends Party> party = getParty();

        if (party == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择所操作的联系方式对应的客户/供应商!"));
            return;
        }

        if (party.getTags() == null) {
            List<PartyTagDto> tags = new ArrayList<PartyTagDto>();
            party.setTags(tags);
        }
        for (String tagId : selectedTagsToAdd) {
            PartyTag tag = loadEntity(PartyTag.class, tagId);
            PartyTagDto t = DTOs.mref(PartyTagDto.class, tag);

            if (!party.getTags().contains(t))
                party.getTags().add(t);
        }
    }

    public void deleteTag() {
        FacesContext context = FacesContext.getCurrentInstance();

        AbstractPartyDto<? extends Party> party = getParty();

        if (party == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择所操作的联系方式对应的客户/供应商!"));
            return;
        }

        for (PartyTagDto t : party.getTags()) {
            if (t.getId().equals(selectedTagId)) {
                party.getTags().remove(t);
                return;
            }
        }
    }

}
