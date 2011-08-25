package com.bee32.sem.mail.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.springframework.context.annotation.Scope;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.sem.mail.MailFlags;
import com.bee32.sem.mail.dto.MailDeliveryDto;
import com.bee32.sem.mail.dto.MailFolderDto;
import com.bee32.sem.mail.entity.MailFolder;

@Scope("view")
public class MailManageBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    public static final String TABVIEW = "mainForm:mainTab";
    public static final String TAB_TABVIEW = "mainForm:fistTab";

    String selectedItemId;
    List<SelectItem> folderItems;
    List<MailDeliveryDto> mails;

    MailManageBean() {
        initFolderItems();
    }

    public void listMails() {
        int folderId = Integer.parseInt(selectedItemId);
        if (folderId >= 0) {
            MailFolder folder = serviceFor(MailFolder.class).getOrFail(folderId);
            MailFolderDto folderDto = DTOs.marshal(MailFolderDto.class, MailFolderDto.MAILS, folder);
            mails = folderDto.getMails();
        }
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
        TabView tabView = (TabView) findComponent(TABVIEW);
        Tab tab = new Tab();
        tab.setTitle("TESTTAB");
        tab.setParent(tabView);
        tabView.getChildren().add(tab);
    }

    public void gotoWritebox() {
        // XXX
    }

    public String getSelectedItemId() {
        return selectedItemId;
    }

    public void setSelectedItemId(String selectedItemId) {
        this.selectedItemId = selectedItemId;
    }

    public List<SelectItem> getFolderItems() {
        return folderItems;
    }

    public List<MailDeliveryDto> getMails() {
        return mails;
    }

}
