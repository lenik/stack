package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.model.LazyDataModel;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntities;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.people.Gender;
import com.bee32.sem.people.dto.ContactDto;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PartyTagnameDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.PartyTagname;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonRole;
import com.bee32.sem.people.util.ContactHolder;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.UIHelper;

@ForEntities({
    //
    @ForEntity(OrgUnit.class), //
    @ForEntity(Org.class) })
public class OrgPersonAdminBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private static final int CONTACT_INITSIZE = 5;

    private LazyDataModel<OrgDto> orgs;
    private OrgDto selectedOrg;

    String orgName;
    List<String> selectedTags;
    String orgAddress;
    String personName;
    char sex;
    String personRole;
    String personMemo;

    List<ContactHolder> contactHolders = new ArrayList<ContactHolder>();

    public OrgPersonAdminBean() {
        EntityDataModelOptions<Org, OrgDto> options = new EntityDataModelOptions<Org, OrgDto>(//
                Org.class, OrgDto.class, PartyDto.CONTACTS | PartyDto.ROLES, //
                Order.desc("id"));
        orgs = UIHelper.buildLazyDataModel(options);

        refreshOrgCount();

        for (int i = 0; i < CONTACT_INITSIZE; i++) {
            contactHolders.add(new ContactHolder());
        }
    }

    void refreshOrgCount() {
        int count = serviceFor(Org.class).count();
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

    public String getPersonRole() {
        return personRole;
    }

    public void setPersonRole(String personRole) {
        this.personRole = personRole;
    }

    public String getPersonMemo() {
        return personMemo;
    }

    public void setPersonMemo(String personMemo) {
        this.personMemo = personMemo;
    }

    public List<ContactHolder> getContactHolders() {
        return contactHolders;
    }

    public void setContactHolders(List<ContactHolder> contactHolders) {
        this.contactHolders = contactHolders;
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
        // selectedTags = null;
        orgAddress = "";
        personName = "";
        // sex;
        personMemo = null;

        for (int i = 0; i < CONTACT_INITSIZE; i++) {
            contactHolders.get(i).setTel("");
            contactHolders.get(i).setCellphone("");
            contactHolders.get(i).setOrg(false);
            contactHolders.get(i).setPerson(false);
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

        // 检测是否有输入联系方式
        boolean haveContact = false;
        for (int i = 0; i < CONTACT_INITSIZE; i++) {
            if (contactHolders.get(i).isOrg()) {
                haveContact = true;
                break;
            }

            if (contactHolders.get(i).isPerson()) {
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

        for (int i = 0; i < CONTACT_INITSIZE; i++) {

            if (contactHolders.get(i).isOrg()) {
                ContactDto contact = new ContactDto().create();

                if (contactHolders.get(i).getTel() != null && contactHolders.get(i).getTel().trim().length() > 0) {
                    contact.setTel(contactHolders.get(i).getTel());
                }

                if (contactHolders.get(i).getCellphone() != null
                        && contactHolders.get(i).getCellphone().trim().length() > 0) {
                    contact.setMobile(contactHolders.get(i).getCellphone());
                }

                if (orgAddress != null && orgAddress.length() > 0) {
                    contact.setAddress(orgAddress);
                }
                contact.setParty(org);

                org.getContacts().add(contact);

            }

            if (contactHolders.get(i).isPerson()) {
                ContactDto contact = new ContactDto().create();

                if (contactHolders.get(i).getTel() != null && contactHolders.get(i).getTel().trim().length() > 0) {
                    contact.setTel(contactHolders.get(i).getTel());
                }

                if (contactHolders.get(i).getCellphone() != null
                        && contactHolders.get(i).getCellphone().trim().length() > 0) {
                    contact.setMobile(contactHolders.get(i).getCellphone());
                }

                if (orgAddress != null && orgAddress.length() > 0) {
                    contact.setAddress(orgAddress);
                }
                contact.setParty(person);

                person.getContacts().add(contact);
            }
        }

        try {
            Person _person = (Person) person.unmarshal();
            serviceFor(Person.class).saveOrUpdate(_person);

            Org _org = (Org) org.unmarshal();
            serviceFor(Org.class).saveOrUpdate(_org);

            PersonRole _role = new PersonRole();
            _role.setOrg(_org);
            _role.setPerson(_person);
            _role.setRole(personRole);

            serviceFor(PersonRole.class).saveOrUpdate(_role);
            serviceFor(Org.class).evict(_org);

            refreshOrgCount();

            uiLogger.info("保存成功.");
        } catch (Exception e) {
            uiLogger.error("保存失败!", e);
        }
    }

}
