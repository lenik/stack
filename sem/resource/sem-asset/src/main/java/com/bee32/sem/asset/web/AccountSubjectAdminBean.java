package com.bee32.sem.asset.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.misc.CodeTreeEntityViewBean;

@ForEntity(AccountSubject.class)
public class AccountSubjectAdminBean
        extends CodeTreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    AccountSubjectDto parentSubject;

    public AccountSubjectAdminBean() {
        super(AccountSubject.class, AccountSubjectDto.class, 0);
    }

    public AccountSubjectDto getParentSubject() {
        return parentSubject;
    }

    public void setParentSubject(AccountSubjectDto parentSubject) {
        this.parentSubject = parentSubject;
        AccountSubjectDto subject = getActiveObject();
        subject.setName(parentSubject.getName());
        subject.setDebitSign(parentSubject.isDebitSign());
        subject.setCreditSign(parentSubject.isCreditSign());
    }

}
