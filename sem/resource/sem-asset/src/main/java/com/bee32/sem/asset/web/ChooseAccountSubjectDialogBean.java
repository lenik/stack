package com.bee32.sem.asset.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.asset.dto.AccountSubjectDto;
import com.bee32.sem.asset.entity.AccountSubject;
import com.bee32.sem.asset.util.AssetCriteria;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChooseAccountSubjectDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseAccountSubjectDialogBean.class);

    String header = "选择资金科目";

    String subjectPrefix = null;

    public ChooseAccountSubjectDialogBean() {
        super(AccountSubject.class, AccountSubjectDto.class, 0);
    }


    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        super.composeBaseRestrictions(elements);
        if(subjectPrefix != null) {
            elements.add(AssetCriteria.subjectWithPrefix(subjectPrefix));
        }
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

//    // TODO 检测是否末级科目
//    String name = selectedAccountSubject.getName();
//    int subAccountSubjectCount = ctx.data.access(AccountSubject.class).count(//
//            new Like("id", "%" + name + "%"), //
//    if (subAccountSubjectCount !=1) {
//        uiLogger.error("所选择科目不是末级科目!");
//        return;
//    }


    public String getSubjectPrefix() {
        return subjectPrefix;
    }

    public void setSubjectPrefix(String subjectPrefix) {
        this.subjectPrefix = subjectPrefix;
        refreshRowCount();
    }
}
