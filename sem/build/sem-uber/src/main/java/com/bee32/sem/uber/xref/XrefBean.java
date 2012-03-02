package com.bee32.sem.uber.xref;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import javax.free.ParseException;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.unit.xgraph.EntityGraphTool;
import com.bee32.plover.orm.unit.xgraph.EntityPartialRefs;
import com.bee32.plover.orm.unit.xgraph.EntityXrefMap;
import com.bee32.plover.orm.unit.xgraph.EntityXrefMetadata;
import com.bee32.plover.orm.util.DataViewBean;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.orm.web.EntityHelper;

public class XrefBean
        extends DataViewBean
        implements ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    Class<? extends Entity<?>> entityType;
    List<Serializable> requestWindow = new ArrayList<Serializable>();

    List<XrefEntry> entries;

    public XrefBean()
            throws ClassNotFoundException {
        String typeAbbr = ctx.view.getRequest().getParameter("type");
        if (typeAbbr == null)
            throw new NullPointerException("type");

        entityType = (Class<? extends Entity<?>>) ABBR.expand(typeAbbr);

        String requestIdList = ctx.view.getRequest().getParameter("pkey");
        if (requestIdList != null) {
            EntityHelper<?, ?> eh = EntityHelper.getInstance(entityType);
            for (String _id : requestIdList.split(",")) {
                _id = _id.trim();
                try {
                    Serializable id = eh.parseId(_id);
                    requestWindow.add(id);
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e.getMessage(), e);
                }
            }
        }
    }

    public List<XrefEntry> getEntries() {
        if (entries == null) {
            synchronized (this) {
                if (entries == null) {
                    entries = scan();
                }
            }
        }
        return entries;
    }

    List<XrefEntry> scan() {
        if (requestWindow.isEmpty()) {
            uiLogger.error("没有指定对象标识:request-window 为空。");
            return Collections.emptyList();
        }

        Serializable id = requestWindow.iterator().next();
        Entity<?> entity = ctx.data.access(entityType).get(id);

        EntityGraphTool tool = ctx.bean.getBean(EntityGraphTool.class);
        EntityXrefMap refMap = tool.getReferences(entity);

        int index = 0;
        List<XrefEntry> xrefs = new ArrayList<XrefEntry>();
        for (Entry<EntityXrefMetadata, EntityPartialRefs> entry : refMap.entrySet()) {
            EntityXrefMetadata metadata = entry.getKey();
            for (Entity<?> client : entry.getValue().getList()) {
                XrefEntry xref = new XrefEntry(entity, metadata, client);
                xref.setOrder(++index * 10);
                xrefs.add(xref);
            }
        }

        return xrefs;
    }

}
