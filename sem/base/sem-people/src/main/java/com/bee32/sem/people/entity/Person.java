package com.bee32.sem.people.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.icsf.principal.User;
import com.bee32.sem.people.Gender;

@Entity
@DiscriminatorValue("PER")
public class Person
        extends Party {

    private static final long serialVersionUID = 1L;

    Gender sex = Gender.OTHER;

    String censusRegister;
    PersonSidType sidType = PersonSidType.IDENTITYCARD;

    Set<PersonRole> roles = new HashSet<PersonRole>();

    public Person() {
        super();
    }

    public Person(String name) {
        super(name);
    }

    @Column(name = "sex")
    char getSex_() {
        return sex.getValue();
    }

    void setSex_(char sex_) {
        sex = Gender.valueOf(sex_);
    }

    /**
     * 性别
     */
    @Transient
    public Gender getSex() {
        return sex;
    }

    /**
     * 性别
     */
    public void setSex(Gender sex) {
        this.sex = sex;
    }

    /**
     * 户籍
     */
    @Column(length = 15)
    public String getCensusRegister() {
        return censusRegister;
    }

    /**
     * 户籍
     */
    public void setCensusRegister(String censusRegister) {
        this.censusRegister = censusRegister;
    }

    /**
     * 身份证件类型 (SID = Social ID)
     */
    @ManyToOne
    public PersonSidType getSidType() {
        return sidType;
    }

    /**
     * 身份证件类型 (SID = Social ID)
     */
    public void setSidType(PersonSidType sidType) {
        this.sidType = sidType;
    }

    @OneToMany(mappedBy = "person")
    @Cascade({ CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public Set<PersonRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<PersonRole> roles) {
        if (roles == null)
            throw new NullPointerException("roles");
        this.roles = roles;
    }

    /**
     * 建立一个尽可能相似的 User 。
     *
     * 这可能包括: 名称，Email 等。
     * <p>
     * 注意：
     * <ul>
     * <li>实际的 User/Person 关联通过 {@link PersonLogin} 表示。
     * <li>Person.contact.email 是不需要验证的，而 User.email 是需要验证的。
     * </ul>
     *
     * @return Non-<code>null</code> User.
     */
    public User createUserLikeThis(String loginName) {
        String fullName = this.fullName;
        if (fullName == null)
            fullName = this.name;

        if (loginName == null)
            loginName = this.name;

        User user = new User(loginName);
        user.setFullName(fullName);
        List<Contact> contacts = getContacts();
        if (!contacts.isEmpty()) {
            Contact firstContact = contacts.get(0);
            user.setPreferredEmail(firstContact.email);
        }
        return user;
    }

}
