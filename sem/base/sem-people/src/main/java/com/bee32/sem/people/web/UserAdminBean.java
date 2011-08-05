package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.bee32.icsf.login.UserPassword;
import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dto.GroupDto;
import com.bee32.icsf.principal.dto.RoleDto;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.people.dto.ContactDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonLogin;
import com.bee32.sem.people.util.PeopleCriteria;

@Component
@Scope("view")
public class UserAdminBean
        extends PrincipalAdminBean {

    private static final long serialVersionUID = 1L;

    private UserDto user;

    private String password;
    private String passwordConfirm;

    private RoleDto selectedRole;
    private TreeNode selectedRoleNode;

    private GroupDto selectedGroup;
    private TreeNode selectedGroupNode;

    private String personPattern;
    private List<PersonDto> persons;
    private PersonDto selectedPerson;

    public UserDto getUser() {
        if (user == null) {
            _newUser();
        }
        return user;
    }

    public void setUser(UserDto user) {
        this.user = reload(user);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public TreeNode getSelectedRoleNode() {
        return selectedRoleNode;
    }

    public void setSelectedRoleNode(TreeNode selectedRoleNode) {
        this.selectedRoleNode = selectedRoleNode;
    }

    public GroupDto getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(GroupDto selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public TreeNode getSelectedGroupNode() {
        return selectedGroupNode;
    }

    public void setSelectedGroupNode(TreeNode selectedGroupNode) {
        this.selectedGroupNode = selectedGroupNode;
    }

    public List<RoleDto> getRoles() {
        return user.getAssignedRoles();
    }

    public List<GroupDto> getGroups() {
        return user.getAssignedGroups();
    }

    public PersonDto getPerson() {
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

    @PostConstruct
    public void init() {
        loadRoleTree();
        loadGroupTree();
    }

    private void _newUser() {
        user = new UserDto().create();
    }

    public void doNewUser() {
        editNewStatus = true;
        _newUser();
    }

    public void doModifyUser() {
        editNewStatus = false;
    }

    public void doSave() {
        if (editNewStatus) {
            // 新增
            Principal existing = serviceFor(Principal.class).getByName(user.getName());
            if (existing != null) {
                uiLogger.error("保存失败:用户已存在。");
                return;
            }
        }

        if (!password.equals(passwordConfirm)) {
            uiLogger.error("密码和密码确认不匹配");
            return;
        }

        User u = user.unmarshal(this);

        try {
            serviceFor(User.class).saveOrUpdate(u);

            serviceFor(UserPassword.class).deleteAll(new Equals("user.id", user.getId()));
            UserPassword pass = new UserPassword(u, password);
            serviceFor(UserPassword.class).save(pass);
            password = "";
            passwordConfirm = "";

            uiLogger.info("保存成功。");
        } catch (Exception e) {
            uiLogger.error("保存失败.错误消息:" + e.getMessage());
        }
    }

    public void doDelete() {
        try {
            serviceFor(UserPassword.class).deleteAll(new Equals("user.id", user.getId()));

            serviceFor(User.class).delete(user.unmarshal());
            uiLogger.info("删除成功!");
        } catch (DataIntegrityViolationException e) {
            uiLogger.error("删除失败,违反约束归则,可能你需要删除的用户在其它地方被使用到!");
        }
    }

    public void doAddRole() {
        user = reload(user);
        user.addAssignedRole((RoleDto) selectedRoleNode.getData());
        serviceFor(User.class).saveOrUpdate(user.unmarshal());
    }

    public void doRemoveRole() {
        user = reload(user);
        user.removeAssignedRole(selectedRole);
        serviceFor(User.class).saveOrUpdate(user.unmarshal());
    }

    public void doAddGroup() {
        GroupDto group = (GroupDto) selectedGroupNode.getData();
        group = reload(group);
        user = reload(user);
        group.addMemberUser(user);
        serviceFor(Group.class).saveOrUpdate(group.unmarshal());
        user.addAssignedGroup((GroupDto) selectedGroupNode.getData());
    }

    public void doRemoveGroup() {
        selectedGroup = reload(selectedGroup);
        selectedGroup.removeMemberUser(user);
        serviceFor(Group.class).saveOrUpdate(selectedGroup.unmarshal());
        user.removeAssignedGroup(selectedGroup);
    }

    public void findPerson() {
        if (personPattern != null && !personPattern.isEmpty()) {

            List<Person> _persons = serviceFor(Person.class).list(//
                    // Restrictions.in("tags", new Object[] { PartyTagname.INTERNAL }), //
                    PeopleCriteria.internal(), //
                    PeopleCriteria.namedLike(personPattern));

            persons = DTOs.marshalList(PersonDto.class, _persons, true);
        }
    }

    public void choosePerson() {
        if (user != null && user.getId() != null) {
            serviceFor(PersonLogin.class).deleteAll(new Equals("user.id", user.getId()));
            PersonLogin personLogin = new PersonLogin();
            personLogin.setUser(user.unmarshal());
            personLogin.setPerson(selectedPerson.unmarshal());

            serviceFor(PersonLogin.class).save(personLogin);
        }
    }

}
