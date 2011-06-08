package com.bee32.plover.orm.ext.basic;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.servlet.mvc.ResultView;

/**
 * 使用 Ajax/DataTable，所以在 index-handler 中实际不涉及任何数据访问。
 */
public class IndexHandler
        extends EntityHandler {

    EntityMetaData metaData;

    @Override
    public ResultView handleRequest(HttpServletRequest req, ResultView view)
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
        view.put("parameterMap", parameterMapJson);

        return view;
    }

}
