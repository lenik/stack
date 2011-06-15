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
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.OrgSidTypeDto;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.OrgSidType;
import com.bee32.sem.service.IPeopleService;
import com.bee32.sem.user.util.SessionLoginInfo;

@Component
@Scope("view")
public class OrgAdminBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int currTab;
	private boolean editable;

	private LazyDataModel<OrgDto> orgs;
	private OrgDto selectedOrg;
	private OrgDto org;

	private ContactDto selectedContact;
	private ContactDto contact;

	@PostConstruct
	public void init() {
		orgs = new LazyDataModel<OrgDto>() {

			@Override
			public List<OrgDto> load(int first, int pageSize,
					String sortField, boolean sortOrder,
					Map<String, String> filters) {
				IPeopleService peopleService = (IPeopleService) FacesContextUtils
						.getWebApplicationContext(
								FacesContext.getCurrentInstance()).getBean(
								"peopleService");

				return peopleService.listOrgByCurrentUser(first, pageSize);
			}

		};

		IPeopleService peopleService = (IPeopleService) FacesContextUtils
				.getWebApplicationContext(FacesContext.getCurrentInstance())
				.getBean("peopleService");

		orgs.setRowCount((int) peopleService.listOrgByCurrentUserCount());

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
	        newOrg();
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

        List<OrgSidType> sidTypes = commonDataManager.loadAll(OrgSidType.class);
        List<OrgSidTypeDto> sidTypeDtos = DTOs.marshalList(OrgSidTypeDto.class, sidTypes);
        List sidTypeSelectItems = new ArrayList();
        for(OrgSidTypeDto t : sidTypeDtos) {
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

        if(org != null && org.getId() != null) {
            if(org.getContacts() != null) {
                contacts = org.getContacts();
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








    private void newOrg() {
		org = new OrgDto(OrgDto.CONTACTS | OrgDto.LOGS);

		HttpSession session = ThreadHttpContext.requireSession();
        IUserPrincipal currUser = (IUserPrincipal) SessionLoginInfo.getCurrentUser(session);

		CommonDataManager commonDataManager = (CommonDataManager) FacesContextUtils
				.getWebApplicationContext(FacesContext.getCurrentInstance())
				.getBean("commonDataManager");
		User user = commonDataManager.load(User.class, currUser.getId());

		org.setOwner(user);

		OrgSidTypeDto sidTypeDto = new OrgSidTypeDto();
		sidTypeDto.ref((String) null);
		org.setSidType(sidTypeDto);
	}

	public void new_() {
		newOrg();

		currTab = 1;
		editable = true;
	}

	public void modify_() {
		FacesContext context = FacesContext.getCurrentInstance();

		if(selectedOrg == null) {
			context.addMessage(null, new FacesMessage("提示", "请选择需要修改的联系人!"));
            return;
		}

		org = selectedOrg;

		currTab = 1;
		editable = true;
	}

	public void delete_() {
		FacesContext context = FacesContext.getCurrentInstance();

		if(selectedOrg == null) {
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

            commonDataManager.delete(selectedOrg.unmarshal());

            orgs.setRowCount((int) peopleService.listOrgByCurrentUserCount());

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

			commonDataManager.saveOrUpdate(org.unmarshal());

            orgs.setRowCount((int) peopleService.listOrgByCurrentUserCount());

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

	    newOrg();
	}

	public void detail_() {
	    if(selectedOrg == null) {
	        FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("提示", "请选择需要查看详细信息的联系人!"));
            return;
	    }

	    currTab = 1;
	    org = selectedOrg;
	}


    public void onRowSelect(SelectEvent event) {

    }

    public void onRowUnselect(UnselectEvent event) {

    }

    private void newContact() {
        contact = new ContactDto();

        ContactCategoryDto category = new ContactCategoryDto();
        contact.setCategory(category);
        contact.setParty(org);
    }

	public void newContact_() {
	    if(org == null || org.getId() == null) {
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

            org.getContacts().remove(selectedContact);
            commonDataManager.saveOrUpdate(org.unmarshal());

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "删除联系方式失败;" + e.getMessage()));
        }
    }

    public void saveContact_() {
        FacesContext context = FacesContext.getCurrentInstance();

        if(org == null || org.getId() == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择所操作的联系方式对应的联系人!"));
            return;
        }

        try {
            CommonDataManager commonDataManager = (CommonDataManager) FacesContextUtils
            .getWebApplicationContext(FacesContext.getCurrentInstance()).getBean(
                    "commonDataManager");

            org.getContacts().add(contact);
            commonDataManager.saveOrUpdate(org.unmarshal());


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
