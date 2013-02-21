package com.bee32.sem.process.web;

import java.util.Date;
import java.util.Locale;

import org.activiti.explorer.I18nManager;
import org.activiti.explorer.util.time.HumanTime;

import com.bee32.sem.misc.AbstractSimpleEntityView;

public abstract class AbstractApexView
        extends AbstractSimpleEntityView {

    private static final long serialVersionUID = 1L;

    public String relativeDateTo(Date date) {
        I18nManager i18nManager = BEAN(I18nManager.class);
        i18nManager.setLocale(Locale.getDefault());
        String humanTime = new HumanTime(i18nManager).format(date);
        return humanTime;
    }

}
