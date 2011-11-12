package com.bee32.sem.people.dto;

import javax.free.ParseException;
import javax.validation.constraints.NotNull;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.model.validation.core.NLength;
import com.bee32.plover.ox1.xp.EntityExtDto;
import com.bee32.sem.people.entity.Contact;
import com.bee32.sem.people.entity.ContactXP;

public class ContactDto
        extends EntityExtDto<Contact, Integer, ContactXP> {

    private static final long serialVersionUID = 1L;

    PartyDto party;
    ContactCategoryDto category;
    String address;
    String postCode;
    String tel;
    String mobile;
    String fax;
    String email;
    String website;
    String qq;

    boolean marshalled;

    public ContactDto() {
        super();
    }

    public ContactDto(int selection) {
        super(selection);
    }

    @Override
    protected void _marshal(Contact source) {
        party = mref(PartyDto.class, source.getParty());
        category = mref(ContactCategoryDto.class, source.getCategory());
        address = source.getAddress();
        postCode = source.getPostCode();
        tel = source.getTel();
        mobile = source.getMobile();
        fax = source.getFax();
        email = source.getEmail();
        website = source.getWebsite();
        qq = source.getQq();
        marshalled = true;
    }

    @Override
    protected void _unmarshalTo(Contact target) {
        merge(target, "party", party);
        if (marshalled && !category.getMarshalType().isReference())
            throw new IllegalStateException("marshalled but category mt changed!");
        merge(target, "category", category);
        target.setAddress(address);
        target.setPostCode(postCode);
        target.setTel(tel);
        target.setMobile(mobile);
        target.setFax(fax);
        target.setEmail(email);
        target.setWebsite(website);
        target.setQq(qq);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public PartyDto getParty() {
        return party;
    }

    public void setParty(PartyDto party) {
        this.party = party;
    }

    @NotNull
    public ContactCategoryDto getCategory() {
        return category;
    }

    public void setCategory(ContactCategoryDto category) {
        this.category = category;
    }

    @NLength(max = Contact.ADDRESS_LENGTH)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NLength(max = Contact.POSTCODE_LENGTH)
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @NLength(max = Contact.TEL_MOBILE_FAX_LENGTH)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @NLength(max = Contact.TEL_MOBILE_FAX_LENGTH)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @NLength(max = Contact.TEL_MOBILE_FAX_LENGTH)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @NLength(max = Contact.EMAIL_LENGTH)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NLength(max = Contact.WEBSITE_LENGTH)
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @NLength(max = Contact.QQ_LENGTH)
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    // @Override
    // protected Integer naturalHashCode() {
    // int hash = 0;
    //
    // hash += party.hashCode();
    //
    // if (category != null)
    // hash += category.hashCode();
    //
    // return hash;
    // }
    //
    // @Override
    // protected Boolean naturalEquals(EntityDto<Contact, Integer> other) {
    // ContactDto o = (ContactDto) other;
    //
    // if (!party.equals(o.party))
    // return false;
    //
    // if (!Nullables.equals(category, o.category))
    // return false;
    //
    // return super.idEquals(other);
    // }

}
