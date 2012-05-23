package com.bee32.plover.ox1.principal.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.hibernate.validator.constraints.NotEmpty;

import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.GroupDto;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalCriteria;
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

    /**
     * 密码保存到数据库中为 hash 值，并无长度限制。但出于一般限制需要设置最大长度为 12 （相当于 96 位密钥强度） 。
     */
    @NotEmpty
    // @Size(min = 5, max = 12)
    // @Alphabet(tab = Alphabet.ALNUM, hint = Alphabet.ALNUM_HINT)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty
    // @Size(min = 5, max = 12)
    // @Alphabet(tab = Alphabet.ALNUM, hint = Alphabet.ALNUM_HINT)
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void passwordValidator(FacesContext context, UIComponent toValidate, Object value) {
        UIInput passwordField = (UIInput) context.getViewRoot().findComponent("editDialog:form:password");
        if (passwordField == null)
            throw new IllegalArgumentException(String.format("Unable to find component."));
        String password = (String) passwordField.getValue();
        String confirmPassword = (String) value;
        if (!confirmPassword.equals(password)) {
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
                Principal existing = ctx.data.access(Principal.class).getByName(user.getName());
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
                try {
                    ctx.data.access(UserPassword.class).findAndDelete(new Equals("user.id", user.getId()));
                    UserPassword pass = new UserPassword(user, password);
                    ctx.data.access(UserPassword.class).save(pass);
                    password = "";
                    passwordConfirm = "";
                    uiLogger.info("更新密码成功。");
                } catch (Exception e) {
                    uiLogger.error("更新密码失败。", e);
                }

                //虽然p:inputText:passPlain和passPlainConfirm没有绑定bean内的值，
                //但是里面输入的值在page范围内还是会自动保存,
                //所以这里做清除
                FacesContext context = FacesContext.getCurrentInstance();
                UIInput passPlain = (UIInput) context.getViewRoot().findComponent("editDialog:form:passPlain");
                passPlain.setValue(null);
                UIInput passPlainConfirm = (UIInput) context.getViewRoot().findComponent("editDialog:form:passPlainConfirm");
                passPlainConfirm.setValue(null);
            }
    }

    // post-delete instead?
    @Override
    protected boolean preDelete(UnmarshalMap uMap)
            throws Exception {
        for (User user : uMap.<User> entitySet())
            ctx.data.access(UserPassword.class).findAndDelete(new Equals("user.id", user.getId()));
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
            ctx.data.access(PersonLogin.class).findAndDelete(new Equals("user.id", user.getId()));
            PersonLogin personLogin = new PersonLogin();
            personLogin.setUser((User) user.unmarshal());
            personLogin.setPerson((Person) selectedPerson.unmarshal());
            ctx.data.access(PersonLogin.class).save(personLogin);
        }
    }

    public PersonDto getPerson() {
        UserDto user = getOpenedObject();
        if (user == null || user.getId() == null)
            return null;

        PersonLogin personLogin = ctx.data.access(PersonLogin.class).getUnique(//
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
            PersonLogin personLogin = ctx.data.access(PersonLogin.class).getUnique(//
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
