package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;

import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.servlet.mvc.ResultView;

public class DeleteHandler
        extends EntityHandler {

    @Override
    public ResultView handleRequest(HttpServletRequest req, ResultView view)
            throws Exception {
        String idString = req.getParameter("id");
        Serializable id;
        try {
            id = parseRequiredId(idString);
        } catch (Exception e) {
            return Javascripts.alertAndBack("非法对象标识: " + idString + ": " + e.getMessage()).dump(req, resp);
        }

        Object entity = dataManager.fetch(getEntityType(), id);
        if (entity != null)
            try {
                dataManager.delete(entity);
            } catch (DataIntegrityViolationException e) {
                return Javascripts.alertAndBack("不能删除正在使用中的对象。" + hint(id)).dump(req, view);
            }

        resp.sendRedirect("index.htm");
        return null;
    }

}
