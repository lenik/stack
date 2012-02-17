package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.annotation.ForEntities;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.Gender;
import com.bee32.sem.people.dto.ContactDto;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.PartyDto;
import com.bee32.sem.people.dto.PartyTagnameDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.dto.PersonRoleDto;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.PartyTagname;
import com.bee32.sem.people.util.ContactHolder;
import com.bee32.sem.sandbox.UIHelper;

@ForEntities({
//
        @ForEntity(OrgUnit.class), //
        @ForEntity(Org.class) })
public class OrgPersonAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    private static final int CONTACT_INITSIZE = 5;

    String orgName;
    List<String> selectedTags;
    String orgAddress;
    String personName;
    char sex;
    String personRole;
    String personMemo;

    List<ContactHolder> contactHolders = new ArrayList<ContactHolder>();

    public OrgPersonAdminBean() {
        super(Org.class, OrgDto.class, PartyDto.CONTACTS | PartyDto.ROLES, //
                Order.desc("id"));

        for (int i = 0; i < CONTACT_INITSIZE; i++) {
            contactHolders.add(new ContactHolder());
        }
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
        List<PartyTagname> partyTags = ctx.data.access(PartyTagname.class).list();
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

        for (ContactHolder contactHolder : contactHolders) {
            contactHolder.setTel("");
            contactHolder.setCellphone("");
            contactHolder.setOrg(false);
            contactHolder.setPerson(false);
        }
    }

    @Override
    protected boolean postValidate(List<?> dtos) {
        if (selectedTags == null || selectedTags.size() <= 0) {
            uiLogger.error("没有为公司选择标签!");
            return false;
        }

        if (personName == null || personName.trim().length() <= 0) {
            uiLogger.error("没有相关人员姓名!");
            return false;
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
            return false;
        }

        OrgDto org = new OrgDto().create();
        PersonDto person = new PersonDto().create();

        PersonRoleDto role = new PersonRoleDto().create();
        role.setOrg(org);
        role.setPerson(person);
        role.setRole(personRole);

        org.setName(orgName);
        person.setName(personName);
        for (String tagId : selectedTags) {
            PartyTagname tag = ctx.data.getOrFail(PartyTagname.class, tagId);
            PartyTagnameDto t = DTOs.mref(PartyTagnameDto.class, tag);

            if (!org.getTags().contains(t))
                org.getTags().add(t);

            if (!person.getTags().contains(t))
                person.getTags().add(t);
        }
        person.setSex(sex);
        person.setMemo(personMemo);

        for (ContactHolder contactHolder : contactHolders) {
            if (contactHolder.isOrg()) {
                ContactDto contact = new ContactDto().create();

                if (contactHolder.getTel() != null && contactHolder.getTel().trim().length() > 0)
                    contact.setTel(contactHolder.getTel());

                if (contactHolder.getCellphone() != null && contactHolder.getCellphone().trim().length() > 0)
                    contact.setMobile(contactHolder.getCellphone());

                if (orgAddress != null && orgAddress.length() > 0)
                    contact.setAddress(orgAddress);

                contact.setParty(org);
                org.getContacts().add(contact);
            }

            if (contactHolder.isPerson()) {
                ContactDto contact = new ContactDto().create();

                if (!StringUtils.isEmpty(contactHolder.getTel()))
                    contact.setTel(contactHolder.getTel());

                if (!StringUtils.isEmpty(contactHolder.getCellphone()))
                    contact.setMobile(contactHolder.getCellphone());

                if (orgAddress != null && orgAddress.length() > 0)
                    contact.setAddress(orgAddress);
                contact.setParty(person);

                person.getContacts().add(contact);
            }
        }
        return true;
    }

}
