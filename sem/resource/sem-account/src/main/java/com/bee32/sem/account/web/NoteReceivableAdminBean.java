package com.bee32.sem.account.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.account.dto.BalancingDto;
import com.bee32.sem.account.dto.BillDiscountDto;
import com.bee32.sem.account.dto.EndorsementDto;
import com.bee32.sem.account.dto.NoteBalancingDto;
import com.bee32.sem.account.dto.NoteDto;
import com.bee32.sem.account.dto.NoteReceivableDto;
import com.bee32.sem.account.entity.Balancing;
import com.bee32.sem.account.entity.BillDiscount;
import com.bee32.sem.account.entity.BillTypes;
import com.bee32.sem.account.entity.Endorsement;
import com.bee32.sem.account.entity.Note;
import com.bee32.sem.account.entity.NoteBalancing;
import com.bee32.sem.account.entity.NoteReceivable;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.service.PeopleService;

public class NoteReceivableAdminBean extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    BillDiscountDto billDiscount = new BillDiscountDto().create();
    EndorsementDto endorsement = new EndorsementDto().create();
    BalancingDto balancing = new BalancingDto().create();

    public NoteReceivableAdminBean() {
        super(NoteReceivable.class, NoteReceivableDto.class, 0, new Equals("class", "RNOTE"));
    }

    /**
     * 在页面上使用，使用户选择部门时只出现本公司的部门
     * @return
     */
    public Integer getSelfOrgId() {
        return ctx.bean.getBean(PeopleService.class).getSelfOrg().getId();
    }

    @Override
    protected boolean postValidate(List<?> dtos) throws Exception {
        for (Object dto : dtos) {
            NoteReceivableDto note = (NoteReceivableDto) dto;



            if (note.getBillType().getId().equals(ctx.bean.getBean(BillTypes.class).BANK.getId())) {
                //如果为银行承兑汇票
                if(StringUtils.isEmpty(note.getAcceptBank())) {
                    uiLogger.error("本票据为银行承兑汇票，承兑银行不能为空!");
                    return false;
                }
            } else {
                //如果为商业承兑汇票
                if(DTOs.isNull(note.getAcceptOrg())) {
                    uiLogger.error("本票据为商业承兑汇票，承兑公司不能为空!");
                    return false;
                }
            }
        }
        return true;
    }

    public BillDiscountDto getBillDiscount() {
        return billDiscount;
    }

    public void setBillDiscount(BillDiscountDto billDiscount) {
        this.billDiscount = billDiscount;
    }

    public EndorsementDto getEndorsement() {
        return endorsement;
    }

    public void setEndorsement(EndorsementDto endorsement) {
        this.endorsement = endorsement;
    }

    public BalancingDto getBalancing() {
        return balancing;
    }

    public void setBalancing(BalancingDto balancing) {
        this.balancing = balancing;
    }

    public void loadBillDiscount() {
        NoteDto note = getOpenedObject();
        note = reload(note);

        NoteBalancingDto noteBalancing = note.getNoteBalancing();
        if (noteBalancing != null && noteBalancing.getClass().equals(BillDiscountDto.class)) {
            billDiscount = (BillDiscountDto)noteBalancing;
        } else {
            billDiscount =  new BillDiscountDto().create();
        }
    }

    public void loadEndorsement() {
        NoteDto note = getOpenedObject();
        note = reload(note);

        NoteBalancingDto noteBalancing = note.getNoteBalancing();
        if (noteBalancing != null && noteBalancing.getClass().equals(EndorsementDto.class)) {
            endorsement = (EndorsementDto)noteBalancing;
        } else {
            endorsement =  new EndorsementDto().create();
        }
    }

    public void loadBalancing() {
        NoteDto note = getOpenedObject();
        note = reload(note);

        NoteBalancingDto noteBalancing = note.getNoteBalancing();
        if (noteBalancing != null && noteBalancing.getClass().equals(BalancingDto.class)) {
            balancing = (BalancingDto)noteBalancing;
        } else {
            balancing =  new BalancingDto().create();
        }
    }

    public void saveBillDiscount() {
        NoteDto note = getOpenedObject();
        note = reload(note);

        try {
            NoteBalancingDto noteBalancing = note.getNoteBalancing();
            if (!DTOs.isNull(noteBalancing)) {
                if (noteBalancing.getClass().equals(EndorsementDto.class) || noteBalancing.getClass().equals(BalancingDto.class)) {
                    //如果票据已经背书或已经结算，必须先删除，再保存贴现数据
                    NoteBalancing _noteBalancing = noteBalancing.unmarshal();
                    _noteBalancing.getNote().setNoteBalancing(null);

                    ctx.data.access(NoteBalancing.class).deleteById(_noteBalancing.getId());
                }

            }
            billDiscount.setNote(note);

            BillDiscount _billDiscount = (BillDiscount) billDiscount.unmarshal();
            ctx.data.access(Note.class).evict(_billDiscount.getNote());
            ctx.data.access(BillDiscount.class).saveOrUpdate(_billDiscount);
            ctx.data.access(Note.class).evict(_billDiscount.getNote());
            uiLogger.info("贴现成功.");
        } catch (Exception e) {
            uiLogger.error("贴现出错!", e);
        }
    }

    public void saveEndorsement() {
        NoteDto note = getOpenedObject();
        note = reload(note);

        try {
            NoteBalancingDto noteBalancing = note.getNoteBalancing();
            if (!DTOs.isNull(noteBalancing)) {
                if (noteBalancing.getClass().equals(BillDiscountDto.class) || noteBalancing.getClass().equals(BalancingDto.class)) {
                    //如果票据已经贴现或已经结算，必须先删除，再保存背书数据
                    NoteBalancing _noteBalancing = noteBalancing.unmarshal();
                    _noteBalancing.getNote().setNoteBalancing(null);

                    ctx.data.access(NoteBalancing.class).deleteById(_noteBalancing.getId());
                }

            }
            endorsement.setNote(note);

            Endorsement _endorsement = (Endorsement) endorsement.unmarshal();
            ctx.data.access(Note.class).evict(_endorsement.getNote());
            ctx.data.access(Endorsement.class).saveOrUpdate(_endorsement);
            ctx.data.access(Note.class).evict(_endorsement.getNote());
            uiLogger.info("背书成功.");
        } catch (Exception e) {
            uiLogger.error("背书出错!", e);
        }
    }

    public void saveBalancing() {
        NoteDto note = getOpenedObject();
        note = reload(note);

        try {
            NoteBalancingDto noteBalancing = note.getNoteBalancing();
            if (!DTOs.isNull(noteBalancing)) {
                if (noteBalancing.getClass().equals(BillDiscountDto.class) || noteBalancing.getClass().equals(EndorsementDto.class)) {
                    //如果票据已经贴现或已经背书，必须先删除，再保存结算数据
                    NoteBalancing _noteBalancing = noteBalancing.unmarshal();
                    _noteBalancing.getNote().setNoteBalancing(null);

                    ctx.data.access(NoteBalancing.class).deleteById(_noteBalancing.getId());
                }

            }
            balancing.setNote(note);

            Balancing _balancing = (Balancing) balancing.unmarshal();
            ctx.data.access(Note.class).evict(_balancing.getNote());
            ctx.data.access(Balancing.class).saveOrUpdate(_balancing);
            ctx.data.access(Note.class).evict(_balancing.getNote());
            uiLogger.info("结算成功.");
        } catch (Exception e) {
            uiLogger.error("结算出错!", e);
        }
    }
}
