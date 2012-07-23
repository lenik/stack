package com.bee32.sem.account.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.account.dto.NotePayableDto;
import com.bee32.sem.account.dto.NoteReceivableDto;
import com.bee32.sem.account.entity.BillTypes;
import com.bee32.sem.account.entity.NotePayable;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.service.PeopleService;

public class NotePayableAdminBean extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public NotePayableAdminBean() {
        super(NotePayable.class, NotePayableDto.class, 0, new Equals("class", "PNOTE"));
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
            NotePayableDto note = (NotePayableDto) dto;



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



}
