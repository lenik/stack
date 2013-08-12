package com.bee32.sem.purchase.web;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.arch.util.dto.Fmask;
import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.purchase.dto.PurchaseInquiryDto;
import com.bee32.sem.purchase.dto.PurchaseRequestDto;
import com.bee32.sem.purchase.dto.PurchaseRequestItemDto;
import com.bee32.sem.purchase.entity.PurchaseRequestItem;

@ForEntity(PurchaseRequestItem.class)
public class PurchaseRequestItemAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public PurchaseRequestItemAdminBean() {
        super(PurchaseRequestItem.class, PurchaseRequestItemDto.class, 0);
    }

    @Transactional
    public void acceptInquiry() {
        PurchaseInquiryDto selectedInquiry = inquiriesMBean.getLastSelection();
        if (selectedInquiry == null) {
            uiLogger.error("没有选中的询价项。");
            return;
        }

        PurchaseRequestItemDto requestItem = getOpenedObject();
        requestItem.setAcceptedInquiry(selectedInquiry);
        uiLogger.info("采纳成功");
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<PurchaseInquiryDto> inquiriesMBean = ListMBean.fromEL(this, //
            "openedObject.inquiries", PurchaseInquiryDto.class);

    public ListMBean<PurchaseInquiryDto> getInquiriesMBean() {
        return inquiriesMBean;
    }

    /*************************************************************************
     * Section: Persistence
     *************************************************************************/
    @Override
    protected Integer getFmaskOverride(int saveFlags) {
        return Fmask.F_MORE & ~PurchaseRequestDto.PLANS;
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        for (PurchaseRequestItem _purchaseRequestItem : uMap.<PurchaseRequestItem> entitySet()) {
            _purchaseRequestItem.setInquiryCount(_purchaseRequestItem.getInquiries().size());
        }
    }

}
