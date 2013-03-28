package com.bee32.sem.track.web;

import java.util.Date;
import java.util.Locale;

import org.activiti.explorer.I18nManager;
import org.activiti.explorer.util.time.HumanTime;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.track.dto.IssueDto;
import com.bee32.sem.track.entity.Issue;

public abstract class AbstractIssueView
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public AbstractIssueView(ICriteriaElement... criteriaElements) {
        super(Issue.class, IssueDto.class, 0, criteriaElements);
    }

    /**
     * Need to care about dependencies..
     */
    public String relativeDateTo(Date date) {
        I18nManager i18nManager = BEAN(I18nManager.class);
        i18nManager.setLocale(Locale.getDefault());
        String humanTime = new HumanTime(i18nManager).format(date);
        return humanTime;
    }

}
