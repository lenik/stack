package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.model.LazyDataModel;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.misc.EntityCriteria;
import com.bee32.sem.people.Gender;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PartyTagnameDto;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.PartyTagname;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.UIHelper;

public class OrgPersonAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private LazyDataModel<OrgDto> orgs;
    private OrgDto selectedOrg;

    String orgName;
    List<String> selectedTags;
    String orgAddress;
    String personName;
    char sex;
    String personMemo;

    String[] tels = new String[5];
    String[] cellphones = new String[5];

    boolean[] orgContacts = new boolean[5];
    boolean[] personContacts = new boolean[5];




    public OrgPersonAdminBean() {
        EntityDataModelOptions<Org, OrgDto> options = new EntityDataModelOptions<Org, OrgDto>(//
                Org.class, OrgDto.class, PartyDto.CONTACTS | PartyDto.ROLES, //
                Order.desc("id"), EntityCriteria.ownedByCurrentUser());
        orgs = UIHelper.buildLazyDataModel(options);

        refreshOrgCount();
    }

    void refreshOrgCount() {
        int count = serviceFor(Org.class).count(EntityCriteria.ownedByCurrentUser());
        orgs.setRowCount(count);
    }

    public LazyDataModel<OrgDto> getOrgs() {
        return orgs;
    }

    public OrgDto getSelectedOrg() {
        return selectedOrg;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public List<String> getSelectedTags() {
        return selectedTags;
    }

    public void setSelectedTags(List<String> selectedTags) {
        this.selectedTags = selectedTags;
    }

    public void setSelectedOrg(OrgDto selectedOrg) {
        this.selectedOrg = selectedOrg;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getPersonMemo() {
        return personMemo;
    }

    public void setPersonMemo(String personMemo) {
        this.personMemo = personMemo;
    }

    public String[] getTels() {
        return tels;
    }

    public void setTels(String[] tels) {
        this.tels = tels;
    }

    public String[] getCellphones() {
        return cellphones;
    }

    public void setCellphones(String[] cellphones) {
        this.cellphones = cellphones;
    }

    public boolean[] getOrgContacts() {
        return orgContacts;
    }

    public void setOrgContacts(boolean[] orgContacts) {
        this.orgContacts = orgContacts;
    }

    public boolean[] getPersonContacts() {
        return personContacts;
    }

    public void setPersonContacts(boolean[] personContacts) {
        this.personContacts = personContacts;
    }

    public List<SelectItem> getTags() {
        List<PartyTagname> partyTags = serviceFor(PartyTagname.class).list();
        List<PartyTagnameDto> partyTagDtos = DTOs.marshalList(PartyTagnameDto.class, partyTags);
        return UIHelper.selectItemsFromDict(partyTagDtos);
    }

    public List<SelectItem> getGenders() {
        List<SelectItem> genders = new ArrayList<SelectItem>();
        for (Gender g : Gender.values())
            genders.add(new SelectItem(g.getValue(), g.getDisplayName()));
        return genders;
    }


}
