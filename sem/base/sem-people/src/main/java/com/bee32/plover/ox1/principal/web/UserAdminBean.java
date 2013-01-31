package com.bee32.plover.ox1.principal.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.GroupDto;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalCriteria;
import com.bee32.icsf.principal.RoleDto;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.model.validation.core.Alphabet;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.people.dto.ContactDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonLogin;

@ForEntity(User.class)
public class UserAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    private String password;
    private String passwordConfirm;

    private RoleDto selectedRole;
    private GroupDto selectedGroup;
    private PersonDto selectedPerson;

    public UserAdminBean() {
        super(User.class, UserDto.class, 0);
    }

    @Size(min = 1, max = 12)
    @Alphabet(tab = Alphabet.ALNUM, hint = Alphabet.ALNUM_HINT)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null)
            throw new NullPointerException("password");
        this.password = password;
    }

    @NotEmpty
    @Size(min = 1, max = 12)
    @Alphabet(tab = Alphabet.ALNUM, hint = Alphabet.ALNUM_HINT)
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        if (passwordConfirm == null)
            throw new NullPointerException("passwordConfirm");
        this.passwordConfirm = passwordConfirm;
    }

    public void passwordValidator(FacesContext context, UIComponent toValidate, Object value) {
        if (!passwordConfirm.equals(password)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, //
                    "输入的密码不匹配！", null);
            throw new ValidatorException(message);
        }
    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        if (StandardViews.CREATE_FORM.equals(getCurrentView()))
            for (UserDto user : uMap.<UserDto> dtos()) {
                Principal existing = DATA(Principal.class).getByName(user.getName());
                if (existing != null) {
                    uiLogger.error("用户已存在: " + user.getName());
                    return false;
                }
            }
        return true;
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        for (User user : uMap.<User> entitySet())
            if (password != null) {
                // if (password.isEmpty()) ;
                try {
                    DATA(UserPassword.class).findAndDelete(new Equals("user.id", user.getId()));
                    UserPassword pass = new UserPassword(user, password);
                    DATA(UserPassword.class).save(pass);
                    password = "";
                    passwordConfirm = "";
                    uiLogger.info("更新密码成功。");
                } catch (Exception e) {
                    uiLogger.error("更新密码失败。", e);
                }
            }
    }

    // post-delete instead?
    @Override
    protected boolean preDelete(UnmarshalMap uMap)
            throws Exception {
        for (User user : uMap.<User> entitySet())
            DATA(UserPassword.class).findAndDelete(new Equals("user.id", user.getId()));
        return true;
    }

    public RoleDto getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(RoleDto selectedRole) {
        this.selectedRole = selectedRole;
    }

    public void addRole() {
        UserDto user = getOpenedObject();
        user.addAssignedRole(selectedRole);
    }

    public void removeRole() {
        if (selectedRole != null) {
            UserDto user = getOpenedObject();
            user.removeAssignedRole(selectedRole);
        }
    }

    public GroupDto getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(GroupDto selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public void addGroup() {
        UserDto user = getOpenedObject();
        user.addAssignedGroup(selectedGroup);
    }

    public void removeGroup() {
        if (selectedGroup != null) {
            UserDto user = getOpenedObject();
            user.removeAssignedGroup(selectedGroup);
        }
    }

    public PersonDto getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(PersonDto selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public void savePersonLogin() {
        UserDto user = getOpenedObject();
        if (user != null && user.getId() != null) {
            DATA(PersonLogin.class).findAndDelete(new Equals("user.id", user.getId()));
            PersonLogin personLogin = new PersonLogin();
            personLogin.setUser((User) user.unmarshal());
            personLogin.setPerson((Person) selectedPerson.unmarshal());
            DATA(PersonLogin.class).save(personLogin);
        }
    }

    public PersonDto getPerson() {
        UserDto user = getOpenedObject();
        if (user == null || user.getId() == null)
            return null;

        PersonLogin personLogin = DATA(PersonLogin.class).getUnique(//
                new Equals("user.id", user.getId()));

        PersonDto person;
        if (personLogin == null)
            person = new PersonDto().create();
        else
            person = DTOs.marshal(PersonDto.class, personLogin.getPerson());
        return person;
    }

    public List<ContactDto> getContacts() {
        UserDto user = getOpenedObject();
        List<ContactDto> contacts = new ArrayList<ContactDto>();
        if (user != null && user.getId() != null) {
            PersonLogin personLogin = DATA(PersonLogin.class).getUnique(//
                    new Equals("user.id", user.getId()));
            if (personLogin != null) {
                PersonDto person = DTOs.marshal(PersonDto.class, personLogin.getPerson());
                contacts = person.getContacts();
            }
        }
        return contacts;
    }

    @Override
    public void addNameOrLabelRestriction() {
        setSearchFragment("name", "名称含有 " + searchPattern, PrincipalCriteria.namedLike(searchPattern));
        searchPattern = null;
    }

}
