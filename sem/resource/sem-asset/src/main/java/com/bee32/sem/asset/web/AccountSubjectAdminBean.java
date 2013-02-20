package com.bee32.sem.asset.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.util.AssetCriteria;
import com.bee32.sem.misc.CodeTreeEntityViewBean;

@ForEntity(AccountSubject.class)
public class AccountSubjectAdminBean
        extends CodeTreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    AccountSubjectDto parentSubject;

    public AccountSubjectAdminBean() {
        super(AccountSubject.class, AccountSubjectDto.class, 0);
    }

    public void addSubjectCodeRestriction() {
        setSearchFragment("code", "科目编号含有: " + searchPattern, //
                AssetCriteria.subjectCodeLike(searchPattern));
        searchPattern = null;
    }

    public AccountSubjectDto getParentSubject() {
        return parentSubject;
    }

    public void setParentSubject(AccountSubjectDto parentSubject) {
        this.parentSubject = parentSubject;
        AccountSubjectDto subject = getOpenedObject();
        subject.setName(parentSubject.getName());
        subject.setDebitSign(parentSubject.isDebitSign());
        subject.setCreditSign(parentSubject.isCreditSign());
    }

    @Override
    protected boolean postValidate(List<?> dtos)
            throws Exception {
        for (Object dto : dtos) {
            AccountSubjectDto subject = (AccountSubjectDto) dto;
            if (StringUtils.isEmpty(subject.getLabel())) {
                uiLogger.error("科目编码不能为空");
                return false;
            }

            if (StringUtils.isEmpty(subject.getName())) {
                uiLogger.error("科目名称不能为空");
                return false;
            }
        }

        return true;
    }

    public boolean isCusEditing() {
        if (getCurrentView().equals(StandardViews.EDIT_FORM)) {
            if (getOpenedObjects().isEmpty())
                throw new IllegalStateException("No opened objects for editing");
            return true;
        }
        return false;
    }

}
