package com.bee32.sem.asset.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.asset.dto.PaymentNoteDto;
import com.bee32.sem.asset.entity.FundFlow;
import com.bee32.sem.asset.entity.PaymentNote;
import com.bee32.sem.asset.service.AssetService;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.people.dto.PersonDto;

@ForEntity(FundFlow.class)
public class PaymentNoteAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    PersonDto whoPay;

    public PaymentNoteAdminBean() {
        super(PaymentNote.class, PaymentNoteDto.class, 0);
    }

    public PersonDto getWhoPay() {
        return whoPay;
    }

    public void setWhoPay(PersonDto whoPay) {
        this.whoPay = whoPay;
    }

    /**
     * 显示付款对话框时，把付款人copy到临时变量中
     */
    public void showPayDialog() {
        PaymentNoteDto note = getOpenedObject();
        whoPay = note.getWhoPay();
    }

    public void pay() {
        if (whoPay.isNull()) {
            uiLogger.error("没有付款人");
            return;
        }

        PersonDto whoPayOld = null;
        PaymentNoteDto note =  getOpenedObject();
        try {
            whoPayOld = note.getWhoPay();   //保存原来的付款人
            note.setWhoPay(whoPay);
            BEAN(AssetService.class).pay(note);
            uiLogger.info("付款成功");
        } catch (Exception e) {
            note.setWhoPay(whoPayOld);  //如果出错，则还原旧的付款人
            uiLogger.error("付款错误", e);
        }
    }

}
