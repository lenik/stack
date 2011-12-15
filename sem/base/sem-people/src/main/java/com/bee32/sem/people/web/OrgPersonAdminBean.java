package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.model.LazyDataModel;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.misc.EntityCriteria;
import com.bee32.sem.people.Gender;
import com.bee32.sem.people.dto.ContactDto;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PartyTagnameDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.dto.PersonRoleDto;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.PartyTagname;
import com.bee32.sem.people.entity.Person;
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

    Boolean[] orgContacts = new Boolean[5];
    Boolean[] personContacts = new Boolean[5];




    public OrgPersonAdminBean() {
        EntityDataModelOptions<Org, OrgDto> options = new EntityDataModelOptions<Org, OrgDto>(//
                Org.class, OrgDto.class, PartyDto.CONTACTS | PartyDto.ROLES, //
                Order.desc("id"), EntityCriteria.ownedByCurrentUser());
        orgs = UIHelper.buildLazyDataModel(options);

        refreshOrgCount();

        for (int i=0; i<5; i++) {
            orgContacts[i] = new Boolean(false);
            personContacts[i] = new Boolean(false);
        }
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

    public Boolean[] getOrgContacts() {
        return orgContacts;
    }

    public void setOrgContacts(Boolean[] orgContacts) {
        this.orgContacts = orgContacts;
    }

    public Boolean[] getPersonContacts() {
        return personContacts;
    }

    public void setPersonContacts(Boolean[] personContacts) {
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


    public void add() {
        orgName = "";
        //selectedTags = null;
        orgAddress = "";
        personName = "";
        //sex;
        personMemo = null;

        for (int i=0; i<5; i++) {
            tels[i] = "";
            cellphones[i] = "";

            orgContacts[i] = false;
            personContacts[i] = false;
        }
    }

    @Transactional
    public void save() {
        if (orgName == null || orgName.trim().length() <= 0) {
            uiLogger.error("没有输入公司名称!");
            return;
        }

        if (selectedTags == null || selectedTags.size() <= 0) {
            uiLogger.error("没有为公司选择标签!");
            return;
        }

        if (personName == null || personName.trim().length() <= 0) {
            uiLogger.error("没有相关人员姓名!");
            return;
        }

        //检测是否有输入联系方式
        boolean haveContact = false;
        for(int i=0; i<5; i++) {
            if (orgContacts[i]) {
                haveContact = true;
                break;
            }

            if (personContacts[i]) {
                haveContact = true;
                break;
            }
        }
        if (!haveContact) {
            uiLogger.error("联系方式没有输入!");
            return;
        }

        OrgDto org = new OrgDto().create();
        PersonDto person = new PersonDto().create();

        org.setName(orgName);
        person.setName(personName);
        for (String tagId : selectedTags) {
            PartyTagname tag = loadEntity(PartyTagname.class, tagId);
            PartyTagnameDto t = DTOs.mref(PartyTagnameDto.class, tag);

            if (!org.getTags().contains(t)) {
                org.getTags().add(t);
            }

            if (!person.getTags().contains(t)) {
                person.getTags().add(t);
            }
        }
        person.setSex(sex);
        person.setMemo(personMemo);

        for (int i=0; i<5; i++) {
            if (orgContacts[i] || personContacts[i]) {
                ContactDto contact = new ContactDto().create();

                contact.setTel(tels[i]);
                contact.setMobile(cellphones[i]);

                contact.setAddress(orgAddress);


                if (orgContacts[i]) {
                    org.getContacts().add(contact);
                }

                if (personContacts[i]) {
                    person.getContacts().add(contact);
                }
            }
        }

        PersonRoleDto role = new PersonRoleDto().create();
        role.setOrg(org);
        role.setPerson(person);

        org.getRoles().add(role);
        person.getRoles().add(role);

        Org _org = (Org)org.unmarshal();
        serviceFor(Org.class).saveOrUpdate(_org);
        Person _person = (Person)person.unmarshal();
        serviceFor(Person.class).saveOrUpdate(_person);
    }

}
