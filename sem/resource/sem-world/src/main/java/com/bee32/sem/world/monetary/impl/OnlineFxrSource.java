package com.bee32.sem.world.monetary.impl;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.EnterpriseService;
import com.bee32.sem.misc.i18n.ICurrencyAware;
import com.bee32.sem.world.monetary.IFxrSource;

public abstract class OnlineFxrSource
        extends EnterpriseService
        implements IFxrSource, ICurrencyAware {

    static Logger logger = LoggerFactory.getLogger(OnlineFxrSource.class);

    static final int INTERVAL_DEBUG = 1; // each minute.
    static final int INTERVAL_PRODUCT = 12 * 60; // twice a day.

    @Override
    public int getPreferredInterval() {
        return INTERVAL_DEBUG;
    }

    protected String httpGet(String uri)
            throws IOException {
        HttpClient client = new HttpClient();
        GetMethod method = SpamHelper.prepareGet(uri);

        String html;
        try {
            client.executeMethod(method);
            html = method.getResponseBodyAsString();
        } finally {
            method.releaseConnection();
        }

        return html;
    }

}
