package com.bee32.plover.ox1.principal.web;

import java.util.ArrayList;
import java.util.List;

import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.GroupDto;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.RoleDto;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.people.dto.ContactDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonLogin;
import com.bee32.sem.people.util.PeopleCriteria;

@ForEntity(User.class)
public class UserAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    private String password;
    private String passwordConfirm;

    private RoleDto selectedRole;
    private GroupDto selectedGroup;

    private String personPattern;
    private List<PersonDto> persons;
    private PersonDto selectedPerson;

    public UserAdminBean() {
        super(User.class, UserDto.class, 0);
    }

    /**
     * 密码保存到数据库中为 hash 值，并无长度限制。但出于一般限制需要设置最大长度为 12 （相当于 96 位密钥强度） 。
     */
// @NotEmpty
// @Size(min = 5, max = 12)
// @Alphabet(tab = Alphabet.ALNUM, hint = Alphabet.ALNUM_HINT)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

// @NotEmpty
// @Size(min = 5, max = 12)
// @Alphabet(tab = Alphabet.ALNUM, hint = Alphabet.ALNUM_HINT)
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public RoleDto getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(RoleDto selectedRole) {
        this.selectedRole = selectedRole;
    }

    public GroupDto getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(GroupDto selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public List<RoleDto> getRoles() {
        UserDto user = getActiveObject();
        return user.getAssignedRoles();
    }

    public List<GroupDto> getGroups() {
        UserDto user = getActiveObject();
        return user.getAssignedGroups();
    }

    public PersonDto getPerson() {
        UserDto user = getActiveObject();
        PersonDto person = new PersonDto().create();
        if (user != null && user.getId() != null) {
            PersonLogin personLogin = serviceFor(PersonLogin.class).getUnique(//
                    new Equals("user.id", user.getId()));
            if (personLogin != null) {
                person = DTOs.marshal(PersonDto.class, personLogin.getPerson());
            }
        }
        return person;
    }

    public List<ContactDto> getContacts() {
        UserDto user = getActiveObject();
        List<ContactDto> contacts = new ArrayList<ContactDto>();
        if (user != null && user.getId() != null) {
            PersonLogin personLogin = serviceFor(PersonLogin.class).getUnique(//
                    new Equals("user.id", user.getId()));
            if (personLogin != null) {
                PersonDto person = DTOs.marshal(PersonDto.class, personLogin.getPerson());
                contacts = person.getContacts();
            }
        }
        return contacts;
    }

    public String getPersonPattern() {
        return personPattern;
    }

    public void setPersonPattern(String personPattern) {
        this.personPattern = personPattern;
    }

    public List<PersonDto> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonDto> persons) {
        this.persons = persons;
    }

    public PersonDto getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(PersonDto selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        if (currentView.equals(StandardViews.CREATE_FORM))
            for (UserDto user : uMap.<UserDto> dtos()) {
                Principal existing = serviceFor(Principal.class).getByName(user.getName());
                if (existing != null) {
                    uiLogger.error("保存失败: 用户已存在: " + user.getName());
                    return false;
                }
            }
        if (!password.equals(passwordConfirm)) {
            uiLogger.error("密码和密码确认不匹配");
            return false;
        }
        return true;
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        for (User user : uMap.<User> entitySet())
            try {
                serviceFor(UserPassword.class).findAndDelete(new Equals("user.id", user.getId()));
                UserPassword pass = new UserPassword(user, password);
                serviceFor(UserPassword.class).save(pass);
                password = "";
                passwordConfirm = "";
                uiLogger.info("更新密码成功。");
            } catch (Exception e) {
                uiLogger.error("更新密码失败。", e);
            }
    }

    @Override
    protected boolean preDelete(UnmarshalMap uMap)
            throws Exception {
        for (User user : uMap.<User> entitySet())
            serviceFor(UserPassword.class).findAndDelete(new Equals("user.id", user.getId()));
        return true;
    }

    public void doAddRole() {
        UserDto user = getActiveObject();
        user.addAssignedRole(selectedRole);
        save();
    }

    public void doRemoveRole() {
        UserDto user = getActiveObject();
        user = reload(user);
        user.removeAssignedRole(selectedRole);
        save();
    }

    public void doAddGroup() {
        UserDto user = getActiveObject();
        user.addAssignedGroup(selectedGroup);
        save();
    }

    public void doRemoveGroup() {
        UserDto user = getActiveObject();
        user.removeAssignedGroup(selectedGroup);
        save();
    }

    public void findPerson() {
        if (personPattern != null && !personPattern.isEmpty()) {

            List<Person> _persons = serviceFor(Person.class).list(//
                    // Restrictions.in("tags", new Object[] { PartyTagname.INTERNAL }), //
                    PeopleCriteria.internal(), //
                    PeopleCriteria.namedLike(personPattern));

            persons = DTOs.mrefList(PersonDto.class, _persons);
        }
    }

    public void choosePerson() {
        UserDto user = getActiveObject();
        if (user != null && user.getId() != null) {
            serviceFor(PersonLogin.class).findAndDelete(new Equals("user.id", user.getId()));
            PersonLogin personLogin = new PersonLogin();
            personLogin.setUser((User) user.unmarshal());
            personLogin.setPerson((Person) selectedPerson.unmarshal());

            serviceFor(PersonLogin.class).save(personLogin);
        }
    }

}
