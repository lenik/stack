package com.bee32.sem.people.web;

import java.io.Serializable;
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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.jsf.FacesContextUtils;

import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.sem.people.Gender;
import com.bee32.sem.people.dto.ContactCategoryDto;
import com.bee32.sem.people.dto.ContactDto;
import com.bee32.sem.people.dto.PersonDto;
import com.bee32.sem.people.dto.PersonSidTypeDto;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.PersonSidType;
import com.bee32.sem.service.IPeopleService;
import com.bee32.sem.user.util.SessionLoginInfo;

@Component
@Scope("view")
public class PersonAdminBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int currTab;
	private boolean editable;

	private LazyDataModel<PersonDto> persons;
	private PersonDto selectedPerson;
	private PersonDto person;

	private ContactDto selectedContact;
	private ContactDto contact;

	@PostConstruct
	public void init() {
		persons = new LazyDataModel<PersonDto>() {

			@Override
			public List<PersonDto> load(int first, int pageSize,
					String sortField, boolean sortOrder,
					Map<String, String> filters) {
				IPeopleService peopleService = (IPeopleService) FacesContextUtils
						.getWebApplicationContext(
								FacesContext.getCurrentInstance()).getBean(
								"peopleService");

				return peopleService.listPersonByCurrentUser(first, pageSize);
			}

		};

		IPeopleService peopleService = (IPeopleService) FacesContextUtils
				.getWebApplicationContext(FacesContext.getCurrentInstance())
				.getBean("peopleService");

		persons.setRowCount((int) peopleService.listPersonByCurrentUserCount());

		currTab = 0;
		editable = false;
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
	        newPerson();
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
        List genders = new ArrayList();
        for(Gender g : Gender.values()) {
            genders.add(new SelectItem(g.ordinal(), g.toString()));
        }
        return genders;
    }


    public List<SelectItem> getSidTypes() {
        CommonDataManager commonDataManager = (CommonDataManager) FacesContextUtils
                .getWebApplicationContext(FacesContext.getCurrentInstance()).getBean(
                        "commonDataManager");

        List<PersonSidType> sidTypes = commonDataManager.loadAll(PersonSidType.class);
        List<PersonSidTypeDto> sidTypeDtos = DTOs.marshalList(PersonSidTypeDto.class, sidTypes);
        List sidTypeSelectItems = new ArrayList();
        for(PersonSidTypeDto t : sidTypeDtos) {
            sidTypeSelectItems.add(new SelectItem(t.getId(), t.getLabel()));
        }
        return sidTypeSelectItems;
    }

    public List<ContactCategoryDto> getContactCategories() {
        CommonDataManager commonDataManager = (CommonDataManager) FacesContextUtils
        .getWebApplicationContext(FacesContext.getCurrentInstance()).getBean(
                "commonDataManager");

        List<ContactCategory> contactCategories = commonDataManager.loadAll(ContactCategory.class);
        List<ContactCategoryDto> contactCategoryDtos = DTOs.marshalList(ContactCategoryDto.class, contactCategories);
        List categories = new ArrayList();
        for(ContactCategoryDto c : contactCategoryDtos) {
            categories.add(new SelectItem(c.getId(), c.getLabel()));
        }
        return categories;
    }

    public boolean isContactSelected() {
        if(selectedContact != null)
            return true;
        return false;
    }

    public ContactDto getSelectedContact() {
        return selectedContact;
    }

    public void setSelectedContact(ContactDto selectedContact) {
        this.selectedContact = selectedContact;
    }

    public List<ContactDto> getContacts() {
        List<ContactDto> contacts = new ArrayList<ContactDto>();

        if(person != null && person.getId() != null) {
            if(person.getContacts() != null) {
                contacts = person.getContacts();
            }
        }

        return contacts;
    }

    public ContactDto getContact() {
        if(contact == null)
            newContact();
        return contact;
    }

    public void setContact(ContactDto contact) {
        this.contact = contact;
    }








    private void newPerson() {
		person = new PersonDto(PersonDto.CONTACTS | PersonDto.LOGS);

		HttpSession session = ThreadHttpContext.requireSession();
        IUserPrincipal currUser = (IUserPrincipal) SessionLoginInfo.getCurrentUser(session);

		CommonDataManager commonDataManager = (CommonDataManager) FacesContextUtils
				.getWebApplicationContext(FacesContext.getCurrentInstance())
				.getBean("commonDataManager");
		User user = commonDataManager.load(User.class, currUser.getId());

		person.setOwner(user);

		PersonSidTypeDto sidTypeDto = new PersonSidTypeDto();
		sidTypeDto.ref((String) null);
		person.setSidType(sidTypeDto);
	}

	public void new_() {
		newPerson();

		currTab = 1;
		editable = true;
	}

	public void modify_() {
		FacesContext context = FacesContext.getCurrentInstance();

		if(selectedPerson == null) {
			context.addMessage(null, new FacesMessage("提示", "请选择需要修改的联系人!"));
            return;
		}

		person = selectedPerson;

		currTab = 1;
		editable = true;
	}

	public void delete_() {
		FacesContext context = FacesContext.getCurrentInstance();

		if(selectedPerson == null) {
			context.addMessage(null, new FacesMessage("提示", "请选择需要删除的联系人!"));
            return;
		}

		try {
            IPeopleService peopleService = (IPeopleService) FacesContextUtils
                    .getWebApplicationContext(FacesContext.getCurrentInstance()).getBean(
                            "peopleService");

            CommonDataManager commonDataManager = (CommonDataManager) FacesContextUtils
                    .getWebApplicationContext(FacesContext.getCurrentInstance()).getBean(
                            "commonDataManager");

            commonDataManager.delete(selectedPerson.unmarshal());

            persons.setRowCount((int) peopleService.listPersonByCurrentUserCount());

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage("提示", "删除联系人失败;" + e.getMessage()));
		}
	}

	public void save_() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
			IPeopleService peopleService = (IPeopleService) FacesContextUtils
					.getWebApplicationContext(FacesContext.getCurrentInstance())
					.getBean("peopleService");

			CommonDataManager commonDataManager = (CommonDataManager) FacesContextUtils
            .getWebApplicationContext(FacesContext.getCurrentInstance()).getBean(
                    "commonDataManager");

			commonDataManager.saveOrUpdate(person.unmarshal());

            persons.setRowCount((int) peopleService.listPersonByCurrentUserCount());

            currTab = 0;
            editable = false;
            context.addMessage(null, new FacesMessage("提示", "联系人保存成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "联系人保存失败" + e.getMessage()));
            e.printStackTrace();
        }
	}

	public void cancel_() {
	    currTab = 0;
	    editable = false;

	    newPerson();
	}

	public void detail_() {
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

    private void newContact() {
        contact = new ContactDto();

        ContactCategoryDto category = new ContactCategoryDto();
        contact.setCategory(category);
        contact.setParty(person);
    }

	public void newContact_() {
	    if(person == null || person.getId() == null) {
	        FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("提示", "请选择需要新增联系方式的联系人!"));
	    }
	    newContact();
	}

	public void modifyContact_() {
	    contact = selectedContact;
	}

    public void deleteContact_() {
        FacesContext context = FacesContext.getCurrentInstance();

        if(selectedContact == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择需要删除的联系方式!"));
            return;
        }

        try {
            CommonDataManager commonDataManager = (CommonDataManager) FacesContextUtils
                    .getWebApplicationContext(FacesContext.getCurrentInstance()).getBean(
                            "commonDataManager");

            person.getContacts().remove(selectedContact);
            commonDataManager.saveOrUpdate(person.unmarshal());

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "删除联系方式失败;" + e.getMessage()));
        }
    }

    public void saveContact_() {
        FacesContext context = FacesContext.getCurrentInstance();

        if(person == null || person.getId() == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择所操作的联系方式对应的联系人!"));
            return;
        }

        try {
            CommonDataManager commonDataManager = (CommonDataManager) FacesContextUtils
            .getWebApplicationContext(FacesContext.getCurrentInstance()).getBean(
                    "commonDataManager");

            person.getContacts().add(contact);
            commonDataManager.saveOrUpdate(person.unmarshal());


            context.addMessage(null, new FacesMessage("提示", "联系方式保存成功"));
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "联系方式保存失败" + e.getMessage()));
            e.printStackTrace();
        }
    }

    public void onRowSelectContact(SelectEvent event) {
        contact = selectedContact;
    }

    public void onRowUnselectContact(UnselectEvent event) {
        newContact();
    }

}
