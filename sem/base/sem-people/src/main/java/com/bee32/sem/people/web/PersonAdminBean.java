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
import com.bee32.sem.people.Gender;
import com.bee32.sem.people.dto.AbstractPartyDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.dto.PersonRoleDto;
import com.bee32.sem.people.dto.PersonSidTypeDto;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.people.entity.PersonSidType;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.service.IPeopleService;
import com.bee32.sem.user.util.SessionLoginInfo;

@Component
@Scope("view")
public class PersonAdminBean
        extends AbstractPartyAdminBean {

    private static final long serialVersionUID = 1L;

    private int currTab;
	private boolean editable;

	private LazyDataModel<PersonDto> persons;
	private PersonDto selectedPerson;
	private PersonDto person;

	private PersonRoleDto selectedRole;

	@PostConstruct
	public void init() {
		persons = new LazyDataModel<PersonDto>() {

			private static final long serialVersionUID = 1L;

            @Override
            public List<PersonDto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
                IPeopleService peopleService = getBean(IPeopleService.class);
				return peopleService.listPersonByCurrentUser(first, pageSize);
            }
        };

		IPeopleService peopleService =getBean(IPeopleService.class);

		persons.setRowCount((int) peopleService.listPersonByCurrentUserCount());

		currTab = 0;
		editable = false;
	}

    @Override
    protected AbstractPartyDto<? extends Party> getParty() {
        return person;
    }

    @Override
    protected void setParty(AbstractPartyDto<? extends Party> party) {
        this.person = (PersonDto) party;
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

	public LazyDataModel<PersonDto> getPersons() {
		return persons;
	}

	public void setPersons(LazyDataModel<PersonDto> persons) {
		this.persons = persons;
	}

	public PersonDto getSelectedPerson() {
		return selectedPerson;
	}

	public void setSelectedPerson(PersonDto selectedPerson) {
		this.selectedPerson = selectedPerson;
	}

	public PersonDto getPerson() {
	    if(person == null)
	        _newPerson();
		return person;
	}

	public void setPerson(PersonDto person) {
		this.person = person;
	}

	public boolean isPersonSelected() {
		if(selectedPerson != null)
			return true;
		return false;
	}

    public List<SelectItem> getGenders() {
        List <SelectItem> genders = new ArrayList<SelectItem>();
        for(Gender g : Gender.values())
            genders.add(new SelectItem(g.getValue(), g.getDisplayName()));
        return genders;
    }


    public List<SelectItem> getSidTypes() {
        List<PersonSidType> sidTypes = getDataManager().loadAll(PersonSidType.class);
        List<PersonSidTypeDto> sidTypeDtos = DTOs.marshalList(PersonSidTypeDto.class, sidTypes);
        return UIHelper.selectItemsFromDict(sidTypeDtos);
    }

    public PersonRoleDto getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(PersonRoleDto selectedRole) {
        this.selectedRole = selectedRole;
    }

    public List<PersonRoleDto> getRoles() {
        List<PersonRoleDto> roles = new ArrayList<PersonRoleDto>();

        if(person != null && person.getId() != null) {
            if(person.getRoles() != null) {
                roles = new ArrayList<PersonRoleDto>(person.getRoles());
            }
        }

        return roles;
    }








    private void _newPerson() {
		person = new PersonDto();

		HttpSession session = ThreadHttpContext.requireSession();
        IUserPrincipal currUser = (IUserPrincipal) SessionLoginInfo.getCurrentUser(session);
		UserDto user = new UserDto().ref(currUser.getId());
		person.setOwner(user);
	}

	public void doNew() {
		_newPerson();

		currTab = 1;
		editable = true;
	}

	public void doModify() {
		FacesContext context = FacesContext.getCurrentInstance();

		if(selectedPerson == null) {
			context.addMessage(null, new FacesMessage("提示", "请选择需要修改的联系人!"));
            return;
		}

		person = selectedPerson;

		currTab = 1;
		editable = true;
	}

	public void doDelete() {
		FacesContext context = FacesContext.getCurrentInstance();

		if(selectedPerson == null) {
			context.addMessage(null, new FacesMessage("提示", "请选择需要删除的联系人!"));
            return;
		}

		try {
            IPeopleService peopleService = getBean(IPeopleService.class);

            getDataManager().delete(selectedPerson.unmarshal());

            persons.setRowCount((int) peopleService.listPersonByCurrentUserCount());

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("提示", "删除联系人失败;" + e.getMessage()));
		}
	}

	public void doSave() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            IPeopleService peopleService = getBean(IPeopleService.class);

            getDataManager().saveOrUpdate(person.unmarshal());

            persons.setRowCount((int) peopleService.listPersonByCurrentUserCount());

            currTab = 0;
            editable = false;
            context.addMessage(null, new FacesMessage("提示", "联系人保存成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "联系人保存失败" + e.getMessage()));
            e.printStackTrace();
        }
	}

	public void doCancel() {
	    currTab = 0;
	    editable = false;

	    _newPerson();
	}

	public void doDetail() {
	    if(selectedPerson == null) {
	        FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("提示", "请选择需要查看详细信息的联系人!"));
            return;
	    }

	    currTab = 1;
	    person = selectedPerson;
	}

    public void onRowSelect(SelectEvent event) {
    }

    public void onRowUnselect(UnselectEvent event) {
    }

    public void onRowSelectRole(SelectEvent event) {
    }

    public void onRowUnselectRole(UnselectEvent event) {
    }
}
