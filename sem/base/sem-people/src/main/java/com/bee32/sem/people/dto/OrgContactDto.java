package com.bee32.sem.people.dto;

import com.bee32.sem.people.entity.OrgContact;

public class OrgContactDto
        extends AbstractContactDto<OrgContact> {

    private static final long serialVersionUID = 1L;

    OrgDto org;

    private OrgContactDto() {
        super();
    }

    private OrgContactDto(int selection, OrgContact source) {
        super(selection, source);
    }

    private OrgContactDto(int selection) {
        super(selection);
    }

    private OrgContactDto(OrgContact source) {
        super(source);
    }

    @Override
    protected void _marshal(OrgContact source) {
        super._marshal(source);

        org = new OrgDto().ref(source.getOrg());
    }



    @Override
    protected void _unmarshalTo(OrgContact target) {
        super._unmarshalTo(target);

        // orgContact.org is transient, so don't unmarshal it.
    }

    public OrgDto getOrg() {
        return org;
    }

    public void setOrg(OrgDto org) {
        this.org = org;
    }

}
