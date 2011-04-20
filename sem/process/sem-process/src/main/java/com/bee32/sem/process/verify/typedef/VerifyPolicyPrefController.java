package com.bee32.sem.process.verify.typedef;

import java.io.IOException;
import java.util.Map;

import javax.free.NotImplementedException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.orm.util.EntityController;
import com.bee32.sem.process.SEMProcessModule;

@RequestMapping(VerifyPolicyPrefController.PREFIX + "*")
public class VerifyPolicyPrefController
        extends EntityController<VerifyPolicyPref, String> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "/pref/";

    @Override
    protected void preamble(Map<String, Object> metaData) {
        metaData.put(ENTITY_TYPE_NAME, "审核策略配置项");
    }

    @Override
    public void data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        throw new NotImplementedException();
    }

    @Override
    protected Map<String, Object> form(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        return null;
    }

    @Override
    protected ModelAndView createOrUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Boolean createMode = (Boolean) req.getAttribute("create");
        if (createMode)
            throw new NotImplementedException();

        return null;
    }

}
