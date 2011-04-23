package com.bee32.sem.process.verify.typedef;

import java.io.IOException;

import javax.free.NotImplementedException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bee32.plover.orm.ext.util.EntityController;
import com.bee32.sem.process.SEMProcessModule;

@RequestMapping(VerifyPolicyPrefController.PREFIX + "*")
public class VerifyPolicyPrefController
        extends EntityController<VerifyPolicyPref, String> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "pref/";

    @Override
    public ModelAndView _data(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        throw new NotImplementedException();
    }

    @Override
    protected ModelAndView _createOrEditForm(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        return null;
    }

    @Override
    protected ModelAndView _createOrEdit(ViewData view, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        boolean createMode = view.isMethod("create");
        if (createMode)
            throw new NotImplementedException();

        return null;
    }

}
