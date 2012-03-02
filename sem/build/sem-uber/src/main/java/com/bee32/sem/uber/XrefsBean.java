package com.bee32.sem.uber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.unit.xgraph.EntityGraphTool;
import com.bee32.plover.orm.unit.xgraph.EntityPartialRefs;
import com.bee32.plover.orm.unit.xgraph.EntityRef;
import com.bee32.plover.orm.unit.xgraph.EntityRefDto;
import com.bee32.plover.orm.unit.xgraph.EntityXrefMap;
import com.bee32.plover.orm.unit.xgraph.EntityXrefMetadata;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class XrefsBean
        extends SimpleEntityViewBean
        implements ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public XrefsBean() {
        super(EntityRef.class, EntityRefDto.class, 0);
    }

    @Override
    protected <E extends Entity<?>> List<E> loadImpl(SevbLazyDataModel<E, ?> def, int first, int pageSize,
            String sortField, SortOrder sortOrder, Map<String, String> filters) {
        // return def._loadImpl(first, pageSize, sortField, sortOrder, filters);

        String typeAbbr = ctx.view.getRequest().getParameter("type");
        if (typeAbbr == null) {
            uiLogger.error("没有指定数据类型。");
            return Collections.emptyList();
        }

        Class<? extends Entity<?>> entityType;
        try {
            entityType = (Class<? extends Entity<?>>) ABBR.expand(typeAbbr);
        } catch (ClassNotFoundException e) {
            uiLogger.error("数据类型无效。");
            return Collections.emptyList();
        }

        if (requestWindow.isEmpty()) {
            uiLogger.error("没有指定对象标识:request-window 为空。");
            return Collections.emptyList();
        }

        Serializable id = requestWindow.iterator().next();
        Entity<?> entity = ctx.data.access(entityType).get(id);

        EntityGraphTool tool = ctx.bean.getBean(EntityGraphTool.class);
        EntityXrefMap refMap = tool.getReferences(entity);

        int index = 0;
        List<EntityRef> refs = new ArrayList<EntityRef>();
        for (Entry<EntityXrefMetadata, EntityPartialRefs> entry : refMap.entrySet()) {
            EntityXrefMetadata metadata = entry.getKey();
            for (Entity<?> client : entry.getValue().getList()) {
                EntityRef ref = new EntityRef(entity, metadata, client);
                ref.setOrder(++index * 10);
                refs.add(ref);
            }
        }

        @SuppressWarnings("unchecked")
        List<E> _refs = (List<E>) refs;
        return _refs;
    }

}
