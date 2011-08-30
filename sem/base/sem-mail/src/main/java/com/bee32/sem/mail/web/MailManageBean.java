package com.bee32.sem.mail.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.springframework.context.annotation.Scope;

import com.bee32.icsf.login.LoginInfo;
import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.ox1.principal.IUserPrincipal;
import com.bee32.plover.ox1.principal.User;
import com.bee32.plover.ox1.principal.UserDto;
import com.bee32.sem.mail.MailFlags;
import com.bee32.sem.mail.dto.MailDeliveryDto;
import com.bee32.sem.mail.dto.MailDto;
import com.bee32.sem.mail.dto.MailFolderDto;
import com.bee32.sem.mail.entity.MailDelivery;
import com.bee32.sem.mail.entity.MailFolder;

@Scope("view")
public class MailManageBean
        extends MailManageVdx {

    private static final long serialVersionUID = 1L;

    String selectedItemId;
    String selectedDeliveryId;
    List<SelectItem> folderItems;
    List<MailDeliveryDto> mails;
    MailDeliveryDto activeMail;
    MailDto draft;
    List<UserDto> allUserList;

    MailManageBean() {
        getContentTab().setRendered(false);
        getWriteTab().setRendered(false);
        initFolderItems();
        listMails();
        initUserList();
    }

    public void initUserList() {
        List<User> users = serviceFor(User.class).list();
        allUserList = DTOs.marshalList(UserDto.class, users);
    }

    public void listMails() {
        if (selectedItemId == null)
            selectedItemId = "1";
        int folderId = Integer.parseInt(selectedItemId);
        if (folderId > 0) {
            MailFolder folder = serviceFor(MailFolder.class).getOrFail(folderId);
            MailFolderDto folderDto = DTOs.marshal(MailFolderDto.class, MailFolderDto.MAILS, folder);
            mails = folderDto.getMails();
            Tab tab = (Tab) getBoxTab();
            tab.setTitle(folderDto.getLabel());
        }
        TabView mainTabView = (TabView) getMainTabView();
        mainTabView.setActiveIndex(0);
    }

    public void initFolderItems() {
        List<MailFolder> mailFolderLiset = serviceFor(MailFolder.class).list(Order.asc("order"));
        List<MailFolderDto> mailFolderDtoList = DTOs.marshalList(MailFolderDto.class, MailFolderDto.MAILS,
                mailFolderLiset, true);
        List<SelectItem> folderItems = new ArrayList<SelectItem>();
        for (MailFolderDto folderDto : mailFolderDtoList) {
            String label = folderDto.getLabel();
            int unread = 0;
            List<MailDeliveryDto> mails = folderDto.getMails();
            for (MailDeliveryDto mail : mails) {
                if (!mail.getFlags().contains(MailFlags.READ_MARK))
                    unread++;
            }
            if (unread != 0)
                label += "(" + Integer.toString(unread) + ")";
            SelectItem item = new SelectItem(folderDto.getId(), label);
            folderItems.add(item);
        }
        this.folderItems = folderItems;
    }

    public void gotoInbox() {
        TabView mainTabView = (TabView) getMainTabView();
        mainTabView.setActiveIndex(0);
        selectedItemId = "1";
        listMails();
    }

    public void gotoWritebox() {
        MailDto mail = new MailDto().create();
        IUserPrincipal currentUser = LoginInfo.getInstance().getUser();
        User user = serviceFor(User.class).getOrFail(currentUser.getId());
        UserDto userDto = DTOs.marshal(UserDto.class, user, true);
        mail.setFromUser(userDto);
        draft = mail;
        Tab tab = (Tab) getWriteTab();
        tab.setRendered(true);
        int index = getActiveBoxIndex("写信");

        TabView mainTabView = (TabView) getMainTabView();
        mainTabView.setActiveIndex(index);
    }

    public void getDeliveryContent() {
        long deliveryId = Long.parseLong(selectedDeliveryId);
        MailDelivery delivery = serviceFor(MailDelivery.class).getOrFail(deliveryId);
        MailDeliveryDto deliveryDto = DTOs.marshal(MailDeliveryDto.class, delivery);
        Tab contentTab = (Tab) getContentTab();
        contentTab.setTitle(deliveryDto.getMail().getSubject());
        contentTab.setRendered(true);

        TabView mainTabView = getMainTabView();
        activeMail = deliveryDto;
        mainTabView.setActiveIndex(1);
    }

    public void sendMail() {
        // xxx
    }

    public void onMailItemSelect() {
        // XXX
        System.err.print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    }

    public String getSelectedItemId() {
        return selectedItemId;
    }

    public void setSelectedItemId(String selectedItemId) {
        this.selectedItemId = selectedItemId;
    }

    public String getSelectedDeliveryId() {
        return selectedDeliveryId;
    }

    public void setSelectedDeliveryId(String selectedDeliveryId) {
        this.selectedDeliveryId = selectedDeliveryId;
    }

    public List<SelectItem> getFolderItems() {
        return folderItems;
    }

    public List<MailDeliveryDto> getMails() {
        return mails;
    }

    public MailDeliveryDto getActiveMail() {
        return activeMail;
    }

    public void setActiveMail(MailDeliveryDto activeMail) {
        this.activeMail = activeMail;
    }

    public MailDto getDraft() {
        return draft;
    }

    public void setDraft(MailDto draft) {
        this.draft = draft;
    }

    public List<UserDto> getAllUserList() {
        return allUserList;
    }
}
