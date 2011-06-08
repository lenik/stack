package com.bee32.plover.orm.ext.basic;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.ext.util.EntityAction;
import com.bee32.plover.servlet.mvc.ResultView;

public class ContentHandler
        extends EntityHandler {

    @Override
    public ResultView handleRequest(HttpServletRequest req, ResultView view)
            throws Exception {
        String _id = req.getParameter("id");
        K id = parseRequiredId(_id);

        E entity = dataManager.get(getEntityType(), id);
        if (entity == null)
            return Javascripts.alertAndBack("查阅的对象不存在。" //
                    + ClassUtil.getDisplayName(getEntityType()) + " [" + id + "]" //
            ).dump(req, resp);

        Dto dto = newDto(_dtoSelection);

        doAction(EntityAction.LOAD, entity, dto);

        view.entity = entity;
        view.dto = dto;
        view.put("it", dto);
        return view;
    }

}
