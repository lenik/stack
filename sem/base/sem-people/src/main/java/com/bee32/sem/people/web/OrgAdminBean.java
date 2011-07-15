package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hibernate.criterion.Order;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.people.dto.AbstractPartyDto;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.OrgTypeDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.dto.PersonRoleDto;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.UIHelper;

@Component
@Scope("view")
public class OrgAdminBean
        extends AbstractPartyAdminBean {

    private static final long serialVersionUID = 1L;

    private boolean editable;

    private LazyDataModel<OrgDto> orgs;
    private OrgDto selectedOrg;
    private OrgDto org;

    private PersonRoleDto selectedRole;
    private PersonRoleDto role;

    private String personPattern;
    private List<PersonDto> persons;
    private PersonDto selectedPerson;

    @PostConstruct
    public void init() {

        EntityDataModelOptions<Org, OrgDto> options = new EntityDataModelOptions<Org, OrgDto>(Org.class, OrgDto.class,
                -1, Order.desc("id"), PeopleCriteria.ownedByCurrentUser());
        orgs = UIHelper.buildLazyDataModel(options);

        refreshOrgCount();

        setActiveTab(TAB_INDEX);
        editable = false;
    }

    void refreshOrgCount() {
        int count = serviceFor(Org.class).count(PeopleCriteria.ownedByCurrentUser());
        orgs.setRowCount(count);
    }

    @Override
    protected AbstractPartyDto<? extends Party> getParty() {
        return org;
    }

    @Override
    protected void setParty(AbstractPartyDto<? extends Party> party) {
        this.org = (OrgDto) party;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public LazyDataModel<OrgDto> getOrgs() {
        return orgs;
    }

    public void setOrgs(LazyDataModel<OrgDto> orgs) {
        this.orgs = orgs;
    }

    public OrgDto getSelectedOrg() {
        return selectedOrg;
    }

    public void setSelectedOrg(OrgDto selectedOrg) {
        this.selectedOrg = selectedOrg;
    }

    public OrgDto getOrg() {
        if (org == null)
            _newOrg();
        return org;
    }

    public void setOrg(OrgDto org) {
        this.org = org;
    }

    public boolean isOrgSelected() {
        if (selectedOrg != null)
            return true;
        return false;
    }

    public List<SelectItem> getOrgTypes() {
        List<OrgType> orgTypes = serviceFor(OrgType.class).list();
        List<OrgTypeDto> orgTypeDtos = DTOs.marshalList(OrgTypeDto.class, orgTypes);
        return UIHelper.selectItemsFromDict(orgTypeDtos);
    }

    public PersonRoleDto getRole() {
        return role;
    }

    public void setRole(PersonRoleDto role) {
        this.role = role;
    }

    public PersonRoleDto getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(PersonRoleDto selectedRole) {
        this.selectedRole = selectedRole;
    }

    public List<PersonRoleDto> getRoles() {
        List<PersonRoleDto> roles = new ArrayList<PersonRoleDto>();

        if (org != null && org.getId() != null) {
            if (org.getRoles() != null) {
                roles = new ArrayList<PersonRoleDto>(org.getRoles());
            }
        }

        return roles;
    }

    public boolean isRoleSelected() {
        if (selectedRole != null)
            return true;
        return false;
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

    private void _newOrg() {
        org = new OrgDto().create();

        IUserPrincipal currentUser = SessionLoginInfo.getCurrentUser();
        UserDto user = new UserDto().ref(currentUser.getId());
        org.setOwner(user);
    }

    public void doNew() {
        _newOrg();

        setActiveTab(TAB_FORM);
        editable = true;
    }

    public void doModify() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (selectedOrg == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择需要修改的客户/供应商!"));
            return;
        }

        org = selectedOrg;

        setActiveTab(TAB_FORM);
        editable = true;
    }

    public void doDelete() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (selectedOrg == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择需要删除的客户/供应商!"));
            return;
        }

        try {
		Org org = serviceFor(Org.class).load(selectedOrg.getId());
		org.getContacts().clear();
		serviceFor(Org.class).save(org);
            serviceFor(Org.class).delete(org);
            refreshOrgCount();

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "删除客户/供应商失败;" + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void doSave() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            serviceFor(Org.class).saveOrUpdate(org.unmarshal());
            refreshOrgCount();

            setActiveTab(TAB_INDEX);
            editable = false;
            context.addMessage(null, new FacesMessage("提示", "客户/供应商保存成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "客户/供应商保存失败" + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void doCancel() {
        setActiveTab(TAB_INDEX);
        editable = false;

        _newOrg();
    }

    public void doDetail() {
        if (selectedOrg == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("提示", "请选择需要查看详细信息的客户/供应商!"));
            return;
        }

        setActiveTab(TAB_FORM);
        org = selectedOrg;
    }

    public void onRowSelect(SelectEvent event) {
    }

    public void onRowUnselect(UnselectEvent event) {
    }

    private void _newRole() {
        role = new PersonRoleDto().create();
        role.setOrg(org);
    }

    public void doNewRole() {
        if (org == null || org.getId() == null) {
            uiLogger.error("提示:请选择需要新增联系方式的客户/供应商!");
            return;
        }
        _newRole();
    }

    public void doModifyRole() {
        role = selectedRole;
    }

    public void doDeleteRole() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (selectedRole == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择需要去除关联的相关人员!"));
            return;
        }

        try {
            org.getRoles().remove(selectedRole);
            serviceFor(Org.class).saveOrUpdate(org.unmarshal());

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "去除相关人员关联失败;" + e.getMessage()));
        }
    }

    public void doSaveRole() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (org == null || org.getId() == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择所操作的相关人员对应的客户/供应商!"));
            return;
        }

        try {
            org.getRoles().add(role);
            serviceFor(Org.class).saveOrUpdate(org.unmarshal());

            context.addMessage(null, new FacesMessage("提示", "相关人员设置关联成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "相关人员设置关联失败" + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void onRowSelectRole(SelectEvent event) {
        role = selectedRole;
    }

    public void onRowUnselectRole(UnselectEvent event) {
        _newRole();
    }

    public void findPerson() {
        if (personPattern != null && !personPattern.isEmpty()) {

            List<Person> _persons = serviceFor(Person.class).list(//
                    PeopleCriteria.ownedByCurrentUser(), //
                    PeopleCriteria.nameLike(personPattern));
            persons = DTOs.marshalList(PersonDto.class, _persons);
        }
    }

    public void choosePerson() {
        role.setPerson(selectedPerson);
    }

}
