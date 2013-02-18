package com.bee32.ape.html.apex.beans.standalone;

import java.util.List;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.form.AbstractFormType;
import org.activiti.explorer.form.UserFormType;

import com.bee32.ape.engine.beans.AbstractPec;

public class ApexFormPec
        extends AbstractPec {

    @Override
    public void processEngineConfigure(ProcessEngineConfigurationImpl configuration) {
        List<AbstractFormType> customFormTypes = configuration.getCustomFormTypes();
        customFormTypes.add(new UserFormType());
    }

}
