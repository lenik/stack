package com.bee32.plover.orm.ext.basic;

import java.util.List;

import javax.free.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;

import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.ext.util.EntityAction;
import com.bee32.plover.orm.ext.util.SearchModel;
import com.bee32.plover.servlet.mvc.ResultView;

public class DataHandler
        extends EntityHandler {

    /**
     * Should construct a JSON response.
     */
    @Override
    public ResultView handleRequest(HttpServletRequest req, ResultView view)
            throws Exception {
        DataTableDxo tab = new DataTableDxo(req);

        List<? extends E> entityList;

        try {
            entityList = __list(req);
        } catch (ParseException e) {
            // logger.error("Failed to parse search criteria parameters.", e);
            return Javascripts.alertAndBack("Parse Error: " + e.getMessage()).dump(view);
        }

        int index = 0;
        for (E entity : entityList) {
            Dto item = newDto(_dtoSelection);

            doAction(EntityAction.LOAD, entity, item);

            tab.push(item.getId());
            tab.push(item.getVersion());

            fillDataRow(tab, item);

            tab.next();
            index++;
        }

        // assert index == entityList.size();
        tab.totalRecords = index;
        tab.totalDisplayRecords = tab.totalRecords;

        return JsonUtil.dump(resp, tab.exportMap());
    }

    protected List<? extends E> __list(HttpServletRequest req)
            throws ParseException, ServletException {

        TextMap textMap = TextMap.convert(req);

        SearchModel searchModel = new SearchModel(getEntityType());

        fillSearchModel(searchModel, textMap);

        if (searchModel.isEmpty())
            return dataManager.loadAll(getEntityType());

        DetachedCriteria detachedCriteria = searchModel.getDetachedCriteria();
        int firstResult = searchModel.getFirstResult();
        int maxResults = searchModel.getMaxResults();

        return dataManager.findByCriteria(detachedCriteria, firstResult, maxResults);
    }

    protected abstract void fillDataRow(DataTableDxo tab, Dto dto);

    protected void fillSearchModel(SearchModel model, TextMap request)
            throws ParseException {
    }

}
