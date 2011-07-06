package com.bee32.sem.file;

import java.io.IOException;
import java.util.Locale;

import org.mortbay.jetty.servlet.FilterHolder;
import org.primefaces.webapp.filter.FileUploadFilter;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMFileUnit.class)
public class SEMFileModuleTest
        extends SEMTestCase {

    public SEMFileModuleTest() {
        super();
    }

    @Override
    protected boolean isDebugMode() {
        return true;
    }

    @Override
    protected int getRefreshPeriod() {
        return 10;
    }

    @Override
    protected String getLoggedInUser() {
        return "admin";
    }

    public static void main(String[] args)
            throws IOException {
        new SEMFileModuleTest().browseAndWait(SEMFileModule.PREFIX + "/index-rich.jsf");
    }

    static {
        Locale zh = Locale.forLanguageTag("zh-CN");
        Locale.setDefault(zh);
    }

	@Override
	protected void configureContext() {
		super.configureContext();

		FilterHolder filter = stl.addFilter(FileUploadFilter.class, "*.jsf", 0);
	}



}
