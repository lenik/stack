package com.bee32.sem.process.verify.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

public class ChooseVerifyPolicyDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseVerifyPolicyDialogBean.class);

    String header = "选择审核策略";

    public ChooseVerifyPolicyDialogBean() {
        super(VerifyPolicy.class, VerifyPolicyDto.class, 0);
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
