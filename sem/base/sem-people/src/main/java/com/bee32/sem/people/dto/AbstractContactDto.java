package com.bee32.sem.people.dto;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.orm.ext.xp.EntityExtDto;
import com.bee32.sem.people.entity.Contact;
import com.bee32.sem.people.entity.ContactXP;

public class AbstractContactDto<E extends Contact>
        extends EntityExtDto<E, Long, ContactXP> {

    private static final long serialVersionUID = 1L;

    public AbstractContactDto() {
        super();
    }

    public AbstractContactDto(E source) {
        super(source);
    }

    public AbstractContactDto(int selection) {
        super(selection);
    }

    public AbstractContactDto(int selection, E source) {
        super(selection, source);
    }

    ContactCategoryDto category;
    PartyDto party;
    String address;
    String postCode;
    String tel;
    String fax;
    String email;
    String website;


    @Override
    protected void _marshal(E source) {
        category = new ContactCategoryDto(source.getCategory());
        party = new PartyDto(source.getParty());
        address = source.getAddress();
        postCode= source.getPostCode();
        tel = source.getTel();
        fax = source.getFax();
        email = source.getEmail();
        website = source.getWebsite();

    }

    @Override
    protected void _unmarshalTo(E target) {
        merge(target, "category", category);
        merge(target, "party", party);
        target.setAddress(address);
        target.setPostCode(postCode);
        target.setTel(tel);
        target.setFax(fax);
        target.setEmail(email);
        target.setWebsite(website);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public ContactCategoryDto getCategory() {
        return category;
    }

    public void setCategory(ContactCategoryDto category) {
        this.category = category;
    }

    public PartyDto getParty() {
        return party;
    }

    public void setParty(PartyDto party) {
        this.party = party;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


}
