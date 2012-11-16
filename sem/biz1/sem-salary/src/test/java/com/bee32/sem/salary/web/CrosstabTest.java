package com.bee32.sem.salary.web;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperRunManager;

import org.junit.Assert;

public class CrosstabTest
        extends Assert {
    public static void main(String[] args)
            throws Exception {
        ClassLoader salaryLoader = SalaryAdminBean.class.getClassLoader();
        InputStream reportTemplate = salaryLoader.getResourceAsStream("resources/3/15/6/4/report2.jrxml");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", "this is a test title");
        byte[] pdf = JasperRunManager.runReportToPdf(reportTemplate, params);

    }
}
