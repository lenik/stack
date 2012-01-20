package com.bee32.sem.process.verify.builtin.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;

import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.icsf.principal.UserDao;
import com.bee32.icsf.principal.UserDto;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.plover.orm.web.basic.BasicEntityController;
import com.bee32.plover.orm.web.util.DataTableDxo;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;
import com.bee32.sem.process.SEMProcessModule;
import com.bee32.sem.process.verify.builtin.SingleVerifierPolicy;
import com.bee32.sem.process.verify.builtin.dto.SingleVerifierPolicyDto;

@RequestMapping(SingleVerifierPolicyController.PREFIX + "/*")
public class SingleVerifierPolicyController
        extends BasicEntityController<SingleVerifierPolicy, Integer, SingleVerifierPolicyDto> {

    public static final String PREFIX = SEMProcessModule.PREFIX + "/v1";

    @Inject
    UserDao userDao;

    @Override
    protected void fillDataRow(DataTableDxo tab, SingleVerifierPolicyDto item) {
        tab.push(item.getLabel());
        tab.push(item.getDescription());

        int max = 3;
        StringBuilder names = null;
        for (PrincipalDto responsible : item.getResponsibles()) {
            if (max <= 0) {
                names.append(", etc.");
                break;
            }

            if (names == null)
                names = new StringBuilder();
            else
                names.append(", ");

            names.append(responsible.getDisplayName());

            max--;
        }
        tab.push(names == null ? "" : names.toString());
    }

    @Override
    protected void fillFormExtra(ActionRequest req, ActionResult result) {
        List<UserDto> users = DTOs.marshalList(UserDto.class, 0, userDao.list());
        result.put("users", users);
    }

}
