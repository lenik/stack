package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.icsf.principal.dto.UserDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.sem.people.dto.AbstractPartyDto;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.OrgTypeDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.dto.PersonRoleDto;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.service.IPeopleService;
import com.bee32.sem.user.util.SessionLoginInfo;

@Component
@Scope("view")
public class OrgAdminBean
        extends AbstractPartyAdminBean {

    private static final long serialVersionUID = 1L;

    private int currTab;
	private boolean editable;

	private LazyDataModel<OrgDto> orgs;
	private OrgDto selectedOrg;
	private OrgDto org;

    private PersonRoleDto selectedRole;
    private PersonRoleDto role;

    private String personPartten;
    private List<PersonDto> persons;
    private PersonDto selectedPerson;

	@PostConstruct
	public void init() {
		orgs = new LazyDataModel<OrgDto>() {

            private static final long serialVersionUID = 1L;

            @Override
            public List<OrgDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
                IPeopleService peopleService = getBean(IPeopleService.class);
                return peopleService.listOrgByCurrentUser(first, pageSize);
            }
        };

		IPeopleService peopleService = getBean(IPeopleService.class);

		orgs.setRowCount((int) peopleService.listOrgByCurrentUserCount());

		currTab = 0;
		editable = false;
	}

	@Override
	protected AbstractPartyDto<? extends Party> getParty() {
	    return org;
	}

	@Override
	protected void setParty(AbstractPartyDto<? extends Party> party) {
	    this.org = (OrgDto) party;
	}

	public int getCurrTab() {
		return currTab;
	}

	public void setCurrTab(int currTab) {
		this.currTab = currTab;
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
	    if(org == null)
	        _newOrg();
		return org;
	}

	public void setOrg(OrgDto org) {
		this.org = org;
	}

	public boolean isOrgSelected() {
		if(selectedOrg != null)
			return true;
		return false;
	}

    public List<SelectItem> getOrgTypes() {
        List<OrgType> orgTypes = getDataManager().loadAll(OrgType.class);
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

        if(org != null && org.getId() != null) {
            if(org.getRoles() != null) {
                roles = new ArrayList<PersonRoleDto>(org.getRoles());
            }
        }

        return roles;
    }

    public boolean isRoleSelected() {
        if(selectedRole != null)
            return true;
        return false;
    }

    public String getPersonPartten() {
        return personPartten;
    }

    public void setPersonPartten(String personPartten) {
        this.personPartten = personPartten;
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
		org = new OrgDto();

		HttpSession session = ThreadHttpContext.requireSession();
        IUserPrincipal currUser = SessionLoginInfo.getCurrentUser(session);
		UserDto user = new UserDto().ref(currUser.getId());
		org.setOwner(user);
	}

	public void doNew() {
		_newOrg();

		currTab = 1;
		editable = true;
	}

	public void doModify() {
		FacesContext context = FacesContext.getCurrentInstance();

		if(selectedOrg == null) {
			context.addMessage(null, new FacesMessage("提示", "请选择需要修改的客户/供应商!"));
            return;
		}

		org = selectedOrg;

		currTab = 1;
		editable = true;
	}

	public void doDelete() {
		FacesContext context = FacesContext.getCurrentInstance();

		if(selectedOrg == null) {
			context.addMessage(null, new FacesMessage("提示", "请选择需要删除的客户/供应商!"));
            return;
		}

		try {
            IPeopleService peopleService = getBean(IPeopleService.class);

            getDataManager().delete(selectedOrg.unmarshal());

            orgs.setRowCount((int) peopleService.listOrgByCurrentUserCount());

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("提示", "删除客户/供应商失败;" + e.getMessage()));
		}
	}

	public void doSave() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
			IPeopleService peopleService = getBean(IPeopleService.class);

			getDataManager().saveOrUpdate(org.unmarshal());

            orgs.setRowCount((int) peopleService.listOrgByCurrentUserCount());

            currTab = 0;
            editable = false;
            context.addMessage(null, new FacesMessage("提示", "客户/供应商保存成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "客户/供应商保存失败" + e.getMessage()));
            e.printStackTrace();
        }
	}

	public void doCancel() {
	    currTab = 0;
	    editable = false;

	    _newOrg();
	}

	public void doDetail() {
	    if(selectedOrg == null) {
	        FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("提示", "请选择需要查看详细信息的客户/供应商!"));
            return;
	    }

	    currTab = 1;
	    org = selectedOrg;
	}


    public void onRowSelect(SelectEvent event) {
    }

    public void onRowUnselect(UnselectEvent event) {
    }

    private void _newRole() {
        role = new PersonRoleDto();

        PersonDto person = new PersonDto();
        role.setPerson(person);

        role.setOrg(org);
    }

	public void doNewRole() {
	    if(org == null || org.getId() == null) {
	        FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("提示", "请选择需要新增联系方式的客户/供应商!"));
	    }
	    _newRole();
	}

    public void doModifyRole() {
        role = selectedRole;
    }

    public void doDeleteRole() {
        FacesContext context = FacesContext.getCurrentInstance();

        if(selectedRole == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择需要去除关联的相关人员!"));
            return;
        }

        try {
            org.getRoles().remove(selectedRole);
            getDataManager().saveOrUpdate(org.unmarshal());

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "去除相关人员关联失败;" + e.getMessage()));
        }
    }

    public void doSaveRole() {
        FacesContext context = FacesContext.getCurrentInstance();

        if(org == null || org.getId() == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择所操作的相关人员对应的客户/供应商!"));
            return;
        }

        try {
            org.getRoles().add(role);
            getDataManager().saveOrUpdate(org.unmarshal());


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
        if(personPartten != null && !personPartten.isEmpty()) {

            IPeopleService peopleService = getBean(IPeopleService.class);
            persons = peopleService.listPersonByCurrentUser(personPartten);

        }
    }

    public void choosePerson() {
        role.setPerson(selectedPerson);
    }

}
