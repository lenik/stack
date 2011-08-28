package com.bee32.sem.mail.web;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.html.HtmlOutputText;
import javax.faces.model.SelectItem;
import javax.free.Strings;

import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.springframework.context.annotation.Scope;

import com.bee32.plover.criteria.hibernate.Order;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.mail.MailFlags;
import com.bee32.sem.mail.dto.MailDeliveryDto;
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

    MailManageBean() {
        getDetailTab().setRendered(false);
        initFolderItems();
        listMails();
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

    public void getDeliveryContent() {
        long deliveryId = Long.parseLong(selectedDeliveryId);
        MailDelivery delivery = serviceFor(MailDelivery.class).getOrFail(deliveryId);
        MailDeliveryDto deliveryDto = DTOs.marshal(MailDeliveryDto.class, delivery);
        TabView mainTabView = getMainTabView();
        Tab tab = new Tab();
        tab.setTitle(Strings.ellipse(deliveryDto.getMail().getSubject(), 7));
        // XXX set tab closable
        mainTabView.getChildren().add(tab);

        HtmlOutputText outputMailSubject = new HtmlOutputText();
        outputMailSubject.setValue(deliveryDto.getMail().getSubject());
        tab.getChildren().add(outputMailSubject);
    }

    public void onMailItemSelect() {
        // XXX
        System.err.print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
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

}
