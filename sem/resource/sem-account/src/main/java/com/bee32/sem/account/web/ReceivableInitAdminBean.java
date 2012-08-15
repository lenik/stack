package com.bee32.sem.account.web;

import java.util.List;

import com.bee32.sem.account.dto.ReceivableInitDto;
import com.bee32.sem.account.entity.ReceivableInit;
import com.bee32.sem.account.service.AccountService;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.service.PeopleService;

public class ReceivableInitAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ReceivableInitAdminBean() {
        super(ReceivableInit.class, ReceivableInitDto.class, 0);
    }

    /**
     * 在页面上使用，使用户选择部门时只出现本公司的部门
     *
     * @return
     */
    public Integer getSelfOrgId() {
        return BEAN(PeopleService.class).getSelfOrg().getId();
    }

    @Override
    protected boolean postValidate(List<?> dtos)
            throws Exception {
        for (Object dto : dtos) {
            ReceivableInitDto init = (ReceivableInitDto) dto;

            if (init.getId() != null)
                continue; // 说明dto的id不为空，当前处于修改状态，不用检测是否唯一

            // 说明dto的id为空，当前处于新增状态
            if (BEAN(AccountService.class).isReceivableInitExisted(init.getParty())) {
                uiLogger.error("此客户已经存在应收初始化记录，不能新增!");
                return false;
            }
        }
        return true;
    }

}
