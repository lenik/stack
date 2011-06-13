package com.bee32.sem.people.dto;

import com.bee32.sem.people.entity.PersonContact;

public class PersonContactDto
        extends AbstractContactDto<PersonContact> {

    private static final long serialVersionUID = 1L;

    public static final int PERSON = 1;

    PersonDto person;

    String qq;
    String homeTel;
    String mobileTel;
    String workTel;


    public PersonContactDto() {
        super();
    }

    public PersonContactDto(int selection, PersonContact source) {
        super(selection, source);
    }

    public PersonContactDto(int selection) {
        super(selection);
    }

    public PersonContactDto(PersonContact source) {
        super(source);
    }

    @Override
    protected void _marshal(PersonContact source) {
        super._marshal(source);

        if (selection.contains(PERSON))
            person = new PersonDto(source.getPerson());

        qq = source.getQq();
        homeTel = source.getHomeTel();
        mobileTel = source.getMobileTel();
        workTel = source.getWorkTel();
    }



    @Override
    protected void _unmarshalTo(PersonContact target) {
        super._unmarshalTo(target);

        target.setQq(qq);
        target.setHomeTel(homeTel);
        target.setMobileTel(mobileTel);
        target.setWorkTel(workTel);
    }

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getHomeTel() {
        return homeTel;
    }

    public void setHomeTel(String homeTel) {
        this.homeTel = homeTel;
    }

    public String getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    public String getWorkTel() {
        return workTel;
    }

    public void setWorkTel(String workTel) {
        this.workTel = workTel;
    }
}
