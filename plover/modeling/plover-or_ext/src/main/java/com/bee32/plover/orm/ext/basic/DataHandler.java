package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;
import java.util.List;

import javax.free.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;

import com.bee32.plover.ajax.JsonUtil;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.javascript.util.Javascripts;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.ext.util.DataTableDxo;
import com.bee32.plover.orm.ext.util.SearchModel;
import com.bee32.plover.orm.util.EntityDto;
import com.bee32.plover.servlet.mvc.ActionRequest;
import com.bee32.plover.servlet.mvc.ActionResult;

public class DataHandler<E extends Entity<K>, K extends Serializable>
        extends EntityHandler<E, K> {

    IEntityListing<E, K> listing;

    public DataHandler(IEntityListing<E, K> listing) {
        if (listing == null)
            throw new NullPointerException("listing");
        this.listing = listing;
    }

    /**
     * Should construct a JSON response.
     */
    @Override
    protected ActionResult _handleRequest(ActionRequest req, ActionResult result)
            throws Exception {
        DataTableDxo tab = new DataTableDxo(req);

        List<? extends E> entityList;

        try {
            entityList = __list(req);
        } catch (ParseException e) {
            // logger.error("Failed to parse search criteria parameters.", e);
            return Javascripts.alertAndBack("Parse Error: " + e.getMessage()).dump(result);
        }

        Integer dtoSelection = eh.getSelection(SelectionMode.INDEX);

        int index = 0;
        for (E entity : entityList) {
            EntityDto<E, K> itemDto = eh.newDto(dtoSelection);

            listing.loadEntry(entity, itemDto);

            tab.push(itemDto.getId());
            tab.push(itemDto.getVersion());

            listing.fillDataRow(tab, itemDto);

            tab.next();
            index++;
        }

        // assert index == entityList.size();
        tab.totalRecords = index;
        tab.totalDisplayRecords = tab.totalRecords;

        return JsonUtil.dump(result.getResponse(), tab.exportMap());
    }

    protected List<? extends E> __list(HttpServletRequest req)
            throws ParseException, ServletException {

        TextMap textMap = TextMap.convert(req);

        SearchModel searchModel = new SearchModel(eh.getEntityType());

        listing.fillSearchModel(searchModel, textMap);

        if (searchModel.isEmpty())
            return (List<? extends E>) dataManager.loadAll(eh.getEntityType());

        DetachedCriteria detachedCriteria = searchModel.getDetachedCriteria();
        int firstResult = searchModel.getFirstResult();
        int maxResults = searchModel.getMaxResults();

        return dataManager.findByCriteria(detachedCriteria, firstResult, maxResults);
    }

}
