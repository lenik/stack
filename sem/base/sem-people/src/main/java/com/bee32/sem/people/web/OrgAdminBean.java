package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;

import com.bee32.icsf.login.SessionLoginInfo;
import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.people.dto.AbstractPartyDto;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.OrgTypeDto;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.dto.PersonRoleDto;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.Person;
import com.bee32.sem.people.entity.PersonRole;
import com.bee32.sem.people.util.PeopleCriteria;
import com.bee32.sem.sandbox.EntityDataModelOptions;
import com.bee32.sem.sandbox.UIHelper;

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

    private TreeNode orgUnitRootNode;
    private TreeNode selectedOrgUnitNode;

    private OrgUnitDto orgUnit;
    private TreeNode selectedParentOrgUnitNode;

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
            OrgUnitDto selectedOrgUnit = (OrgUnitDto) selectedOrgUnitNode.getData();
            if(selectedOrgUnit != null) {
                //点选部门，出现公司的所选部门中的人员
                List<PersonRole> personRoles = serviceFor(PersonRole.class).list(Restrictions.eq("orgUnit.id", selectedOrgUnit.getId()));
                roles = DTOs.marshalList(PersonRoleDto.class, personRoles);
            } else {
                //没有点选部门，出现公司所有的人员
                if (org.getRoles() != null) {
                    roles = new ArrayList<PersonRoleDto>(org.getRoles());
                }
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

    public TreeNode getOrgUnitRoot() {
        if (org != null && org.getId() != null) {
            orgUnitRootNode = new DefaultTreeNode(org, null);

            List<OrgUnit> topOrgUnits = serviceFor(OrgUnit.class).list(
                    Restrictions.and(Restrictions.eq("org.id", org.getId()),
                            Restrictions.isNull("parent")));
            List<OrgUnitDto> topOrgUnitDtos = DTOs.marshalList(OrgUnitDto.class, topOrgUnits);
            for (OrgUnitDto orgUnitDto : topOrgUnitDtos) {
                loadOrgUnitTree(orgUnitDto, orgUnitRootNode);
            }

        }

        return orgUnitRootNode;
    }

    private void loadOrgUnitTree(OrgUnitDto orgUnitDto, TreeNode parentNode) {
        TreeNode orgUnitNode = new DefaultTreeNode(orgUnitDto, parentNode);

        List<OrgUnitDto> subOrgUnits = orgUnitDto.getChildren();
        for (OrgUnitDto subOrgUnit : subOrgUnits) {
            loadOrgUnitTree(subOrgUnit, orgUnitNode);
        }
    }

    public TreeNode getSelectedOrgUnitNode() {
        return selectedOrgUnitNode;
    }

    public void setSelectedOrgUnitNode(TreeNode selectedOrgUnitNode) {
        this.selectedOrgUnitNode = selectedOrgUnitNode;
    }

    public OrgUnitDto getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnitDto orgUnit) {
        this.orgUnit = orgUnit;
    }

    public boolean isOrgUnitSelected() {
        if (selectedOrgUnitNode != null && selectedOrgUnitNode.getData() != null)
            return true;
        return false;
    }

    public TreeNode getSelectedParentOrgUnitNode() {
        return selectedParentOrgUnitNode;
    }

    public void setSelectedParentOrgUnitNode(TreeNode selectedParentOrgUnitNode) {
        this.selectedParentOrgUnitNode = selectedParentOrgUnitNode;
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
        if (selectedOrg == null) {
            uiLogger.error("提示:请选择需要修改的客户/供应商!");
            return;
        }

        org = selectedOrg;

        setActiveTab(TAB_FORM);
        editable = true;
    }

    public void doDelete() {
        if (selectedOrg == null) {
            uiLogger.error("提示:请选择需要删除的客户/供应商!");
            return;
        }

        try {
            Org org = serviceFor(Org.class).load(selectedOrg.getId());
            org.getContacts().clear();
            serviceFor(Org.class).save(org);
            serviceFor(Org.class).delete(org);
            refreshOrgCount();

        } catch (Exception e) {
            uiLogger.error("提示:删除客户/供应商失败;" + e.getMessage());
            return;
        }

        uiLogger.info("删除客户/供应商成功。");
    }

    public void doSave() {
        try {
            serviceFor(Org.class).saveOrUpdate(org.unmarshal());
            refreshOrgCount();

            setActiveTab(TAB_INDEX);
            editable = false;
            uiLogger.info("提示:客户/供应商保存成功");
        } catch (Exception e) {
            uiLogger.error("提示:客户/供应商保存失败" + e.getMessage());
        }
    }

    public void doCancel() {
        setActiveTab(TAB_INDEX);
        editable = false;

        _newOrg();
    }

    public void doDetail() {
        if (selectedOrg == null) {
            uiLogger.error("提示:请选择需要查看详细信息的客户/供应商!");
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
        if (selectedRole == null) {
            uiLogger.error("提示:请选择需要去除关联的相关人员!");
            return;
        }

        try {
            org.getRoles().remove(selectedRole);
            serviceFor(Org.class).saveOrUpdate(org.unmarshal());

        } catch (Exception e) {
            uiLogger.error("提示:去除相关人员关联失败;" + e.getMessage());
        }
    }

    public void doSaveRole() {
        if (org == null || org.getId() == null) {
            uiLogger.error("提示:请选择所操作的相关人员对应的客户/供应商!");
            return;
        }

        try {
            org.getRoles().add(role);
            serviceFor(Org.class).saveOrUpdate(org.unmarshal());

            uiLogger.info("提示:相关人员设置关联成功");
        } catch (Exception e) {
            uiLogger.info("提示:相关人员设置关联失败" + e.getMessage());
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

    public void doNewOrgUnit() {
        orgUnit = new OrgUnitDto().create();
    }

    public void doDeleteOrgUnit() {
        OrgUnitDto selectedOrgUnit = (OrgUnitDto) selectedOrgUnitNode.getData();
        serviceFor(OrgUnit.class).delete(selectedOrgUnit.unmarshal());

        uiLogger.info("删除部门成功");
    }

    public void doSaveOrgUnit() {
        if (org == null || org.getId() == null) {
            uiLogger.error("提示:请选择所操作的部门对应的客户/供应商!");
            return;
        }


        orgUnit.setOrg(org);
        serviceFor(OrgUnit.class).save(orgUnit.unmarshal());

        uiLogger.info("保存部门成功");
    }

    public void doSelectParentOrgUnit() {
        orgUnit.setParent((OrgUnitDto) selectedParentOrgUnitNode.getData());
    }
}
