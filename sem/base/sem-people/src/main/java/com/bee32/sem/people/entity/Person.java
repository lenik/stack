package com.bee32.sem.people.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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

    public static final int THEME_LENGTH = 100;
    public static final int CENSUS_REGISTER_LENGTH = 15;

    Gender sex = Gender.OTHER;
    Locale locale;
    TimeZone timeZone;
    String theme;

    String censusRegister;

    Set<PersonRole> roles = new HashSet<PersonRole>();

    public Person() {
        this(null);
    }

    public Person(String label) {
        super(label);
        sidType = predefined(PartySidTypes.class).IDENTITYCARD;
    }

    @Override
    public void retarget(Object o) {
        super.retarget(o);
        _retarget((Person) o);
    }

    private void _retarget(Person o) {
        _retargetMerge(roles, o.roles);
    }

    @Column(name = "sex")
    char get_sex() {
        return sex.getValue();
    }

    void set_sex(char _sex) {
        sex = Gender.valueOf(_sex);
    }

    @Transient
    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    /**
     * 户籍
     */
    @Column(length = CENSUS_REGISTER_LENGTH)
    public String getCensusRegister() {
        return censusRegister;
    }

    public void setCensusRegister(String censusRegister) {
        this.censusRegister = censusRegister;
    }

    @OneToMany(mappedBy = "person", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
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
