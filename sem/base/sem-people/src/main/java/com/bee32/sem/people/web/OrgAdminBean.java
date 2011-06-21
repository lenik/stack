package com.bee32.sem.people.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.component.selectonelistbox.SelectOneListbox;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bee32.icsf.principal.IUserPrincipal;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.sem.people.dto.ContactCategoryDto;
import com.bee32.sem.people.dto.ContactDto;
import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.dto.OrgTypeDto;
import com.bee32.sem.people.dto.PartyTagDto;
import com.bee32.sem.people.entity.ContactCategory;
import com.bee32.sem.people.entity.OrgType;
import com.bee32.sem.people.entity.PartyTag;
import com.bee32.sem.sandbox.UIHelper;
import com.bee32.sem.service.IPeopleService;
import com.bee32.sem.user.util.SessionLoginInfo;

@Component
@Scope("view")
public class OrgAdminBean extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    private int currTab;
	private boolean editable;

	private LazyDataModel<OrgDto> orgs;
	private OrgDto selectedOrg;
	private OrgDto org;

	private ContactDto selectedContact;
	private ContactDto contact;

	private List<String> selectedTagsToAdd;

	private SelectOneListbox tagListbox;

	@PostConstruct
	public void init() {
		orgs = new LazyDataModel<OrgDto>() {

            private static final long serialVersionUID = 1L;

            @Override
            public List<OrgDto> load(int first, int pageSize, String sortField, boolean sortOrder,
                    Map<String, String> filters) {
                IPeopleService peopleService = getBean(IPeopleService.class);
                return peopleService.listOrgByCurrentUser(first, pageSize);
            }

		};

		IPeopleService peopleService = getBean(IPeopleService.class);

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

    public List<SelectItem> getOrgTypes() {
        List<OrgType> orgTypes = getDataManager().loadAll(OrgType.class);
        List<OrgTypeDto> orgTypeDtos = DTOs.marshalList(OrgTypeDto.class, orgTypes);
        return UIHelper.selectItemsFromDict(orgTypeDtos);
    }

    public List<SelectItem> getContactCategories() {
        List<ContactCategory> contactCategories = getDataManager().loadAll(ContactCategory.class);
        List<ContactCategoryDto> contactCategoryDtos = DTOs.marshalList(ContactCategoryDto.class, contactCategories);
        return UIHelper.selectItemsFromDict(contactCategoryDtos);
    }

    public List<SelectItem> getTags() {
        List<PartyTag> partyTags = getDataManager().loadAll(PartyTag.class);
        List<PartyTagDto> partyTagDtos = DTOs.marshalList(PartyTagDto.class, partyTags);
        return UIHelper.selectItemsFromDict(partyTagDtos);
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

    public List<String> getSelectedTagsToAdd() {
        return selectedTagsToAdd;
    }

    public void setSelectedTagsToAdd(List<String> selectedTagsToAdd) {
        this.selectedTagsToAdd = selectedTagsToAdd;
    }

    public SelectOneListbox getTagListbox() {
        return tagListbox;
    }

    public void setTagListbox(SelectOneListbox tagListbox) {
        this.tagListbox = tagListbox;
    }









    private void newOrg() {
		org = new OrgDto(OrgDto.CONTACTS | OrgDto.RECORDS);

		HttpSession session = ThreadHttpContext.requireSession();
        IUserPrincipal currUser = SessionLoginInfo.getCurrentUser(session);

		User user = loadEntity(User.class, currUser.getId());

		org.setOwner(user);

		OrgTypeDto orgTypeDto = new OrgTypeDto();
		orgTypeDto.ref((String) null);
		org.setType(orgTypeDto);
	}

	public void new_() {
		newOrg();

		currTab = 1;
		editable = true;
	}

	public void modify_() {
		FacesContext context = FacesContext.getCurrentInstance();

		if(selectedOrg == null) {
			context.addMessage(null, new FacesMessage("提示", "请选择需要修改的客户/供应商!"));
            return;
		}

		org = selectedOrg;

		currTab = 1;
		editable = true;
	}

	public void delete_() {
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

	public void save_() {
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

	public void cancel_() {
	    currTab = 0;
	    editable = false;

	    newOrg();
	}

	public void detail_() {
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

    private void newContact() {
        contact = new ContactDto();

        ContactCategoryDto category = new ContactCategoryDto();
        contact.setCategory(category);
        contact.setParty(org);
    }

	public void newContact_() {
	    if(org == null || org.getId() == null) {
	        FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("提示", "请选择需要新增联系方式的客户/供应商!"));
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
            org.getContacts().remove(selectedContact);
            getDataManager().saveOrUpdate(org.unmarshal());

        } catch (Exception e) {
            context.addMessage(null, new FacesMessage("提示", "删除联系方式失败;" + e.getMessage()));
        }
    }

    public void saveContact_() {
        FacesContext context = FacesContext.getCurrentInstance();

        if(org == null || org.getId() == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择所操作的联系方式对应的客户/供应商!"));
            return;
        }

        try {
            org.getContacts().add(contact);
            getDataManager().saveOrUpdate(org.unmarshal());


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

    public void addTags() {
        FacesContext context = FacesContext.getCurrentInstance();

        if(org == null || org.getId() == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择所操作的联系方式对应的客户/供应商!"));
            return;
        }

        for(String tagId : selectedTagsToAdd) {
            PartyTag tag = loadEntity(PartyTag.class, tagId);
            PartyTagDto t = DTOs.mref(PartyTagDto.class,tag);
            if(!org.getTags().contains(t))
                org.getTags().add(t);
        }
    }

    public void deleteTag() {
        FacesContext context = FacesContext.getCurrentInstance();

        if(org == null || org.getId() == null) {
            context.addMessage(null, new FacesMessage("提示", "请选择所操作的联系方式对应的客户/供应商!"));
            return;
        }

        for(PartyTagDto t : org.getTags()) {
            String tagId = (String) tagListbox.getValue();

            if(t.getId().equals(tagId)) {
                org.getTags().remove(t);
                return;
            }
        }
    }

}
