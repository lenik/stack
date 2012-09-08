package com.bee32.sem.asset.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.asset.dto.AccountInitDto;
import com.bee32.sem.asset.dto.AccountInitItemDto;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.entity.AccountInit;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.ScrollEntityViewBean;
import com.bee32.sem.service.PeopleService;

@ForEntity(AccountInit.class)
public class AccountInitAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    public AccountInitAdminBean() {
        super(AccountInit.class, AccountInitDto.class, 0);
    }

    /**
     * 在页面上使用，使用户选择部门时只出现本公司的部门
     *
     * @return
     */
    public Integer getSelfOrgId() {
        return BEAN(PeopleService.class).getSelfOrg().getId();
    }

    //此方法内容为空，使去掉“发生于这个月”的条件限制，资产初始化不用这个限制
    @Override
    protected void addInitialRestrictions() {
    }

    public void setAccountSubject(AccountSubjectDto subject) {
        // TODO 检查 subject 是否末级科目：在选择对话框中加入 leafOnly 选项。
        AccountInitItemDto item = itemsMBean.getOpenedObject();
        item.setSubject(subject);
        item.setDebitSide(subject.isDebitSign());
    }

    /*************************************************************************
     * Section: MBeans
     *************************************************************************/
    final ListMBean<AccountInitItemDto> itemsMBean = ListMBean.fromEL(this, //
            "openedObject.items", AccountInitItemDto.class);

    public ListMBean<AccountInitItemDto> getItemsMBean() {
        return itemsMBean;
    }

}
