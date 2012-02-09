package com.bee32.sem.asset.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.asset.dto.AccountInitDto;
import com.bee32.sem.asset.dto.AccountInitItemDto;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.entity.AccountInit;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.ScrollEntityViewBean;

@ForEntity(AccountInit.class)
public class AccountInitAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    ListMBean<AccountInitItemDto> itemsMBean = ListMBean.fromEL(this, "openedObject.items", AccountInitItemDto.class);

    public AccountInitAdminBean() {
        super(AccountInit.class, AccountInitDto.class, 0);
    }

    public ListMBean<AccountInitItemDto> getItemsMBean() {
        return itemsMBean;
    }

    public void setAccountSubject(AccountSubjectDto subject) {
        // TODO 检查 subject 是否末级科目：在选择对话框中加入 leafOnly 选项。
        AccountInitItemDto item = itemsMBean.getOpenedObject();
        item.setSubject(subject);
        item.setDebitSide(subject.isDebitSign());
    }

}
