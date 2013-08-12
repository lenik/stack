package com.bee32.plover.orm.web.basic;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.web.EntityHandler;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;

/**
 * 列表处理器
 *
 *
 * 使用 Ajax/DataTable，所以在 index-handler 中实际不涉及任何数据访问。
 */
public class IndexHandler<E extends Entity<K>, K extends Serializable>
        extends EntityHandler<E, K> {

    public IndexHandler(Class<E> entityType) {
        super(entityType);
    }

    @Override
    protected ActionResult _handleRequest(ActionRequest req, ActionResult result)
            throws Exception {

        // Index by data-table:
        // List<AllowListDto> list = AllowListDto.marshalList(0, allowListDao.list());
        // mm.put("list", list);

        /**
         * parameterMap 用于保持 Request Parameters。
         */
        Map<String, String> parameterMap = new HashMap<String, String>();
        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = req.getParameter(paramName);
            parameterMap.put(paramName, paramValue);
        }
        String parameterMapJson = JsonUtil.dump(parameterMap);
        result.put("parameterMap", parameterMapJson);

        return result;
    }

}
