package com.bee32.sem.people.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.free.UnexpectedException;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.dto.ContactDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PartyTagnameDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PartyTagname;
import com.bee32.sem.people.util.PeopleCriteria;

@ForEntity(Party.class)
public abstract class AbstractPartyAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    ContactDto selectedContact;
    ContactDto contact;

    List<String> selectedTagsToAdd;
    String selectedTagId;

    public <E extends Entity<K>, D extends EntityDto<? super E, K>, K extends Serializable> //
    AbstractPartyAdminBean(Class<E> entityClass, Class<D> dtoClass, int fmask, ICriteriaElement... criteriaElements) {
        super(entityClass, dtoClass, fmask, criteriaElements);
    }

    protected final PartyDto getParty() {
        return getOpenedObject();
    }

    protected final void setParty(PartyDto party) {
        setOpenedObject(party);
    }

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

    public SelectableList<ContactDto> getContacts() {
        List<ContactDto> contacts = new ArrayList<ContactDto>();

        PartyDto party = getParty();

        if (party != null && party.getId() != null) {
            contacts = party.getContacts();
        }

        return SelectableList.decorate(contacts);
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
        contact = reload(selectedContact);
    }

    public void doDeleteContact() {
        PartyDto party = getParty();

        if (selectedContact == null) {
            uiLogger.error("提示:请选择需要删除的联系方式!");
            return;
        }

        try {
            party.getContacts().remove(selectedContact);
            Party _party = party.unmarshal();
            ctx.data.access(Party.class).saveOrUpdate(_party);

            party = reload(party);
            setParty(party);
        } catch (Exception e) {
            uiLogger.error("提示:删除联系方式失败;" + e.getMessage(), e);
        }
    }

    public void doSaveContact() {
        PartyDto party = getParty();

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
            ctx.data.access(Party.class).saveOrUpdate(_party);

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
        PartyDto party = getParty();

        if (party == null) {
            uiLogger.error("提示:请选择所操作的联系方式对应的客户/供应商!");
            return;
        }

        if (party.getTags() == null) {
            List<PartyTagnameDto> tags = new ArrayList<PartyTagnameDto>();
            party.setTags(tags);
        }
        for (String tagId : selectedTagsToAdd) {
            PartyTagname tag = ctx.data.getOrFail(PartyTagname.class, tagId);
            PartyTagnameDto t = DTOs.mref(PartyTagnameDto.class, tag);

            if (!party.getTags().contains(t))
                party.getTags().add(t);
        }
    }

    public void deleteTag() {
        PartyDto party = getParty();

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

    @Override
    public void addNameOrLabelRestriction() {
        addSearchFragment("名称含有 " + searchPattern, //
                // UIEntity doesn't have name: CommonCriteria.namedLike(pattern), //
                PeopleCriteria.namedLike(searchPattern, true));
        searchPattern = null;
    }

}
