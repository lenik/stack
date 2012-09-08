package com.bee32.sem.data.xref;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.unit.xgraph.EntityXrefMetadata;
import com.bee32.plover.restful.resource.IObjectPageDirectory;
import com.bee32.plover.restful.resource.PageDirectory;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.plover.rtx.location.Location;
import com.bee32.plover.servlet.rtx.LocationVmap;

public class XrefEntry
        implements Serializable {

    private static final long serialVersionUID = 1L;

    static final Logger logger = LoggerFactory.getLogger(XrefEntry.class);

    int order;

    String entityTypeName;
    Serializable entityId;
    String entityLabel;

    EntityXrefMetadata metadata;
    String clientTypeName;
    Serializable clientId;
    String clientLabel;

    LocationVmap clientPage;
    String clientClickAction;

    public XrefEntry(Entity<?> entity, EntityXrefMetadata metadata, Entity<?> client) {
        entityTypeName = ClassUtil.getParameterizedTypeName(entity);
        entityId = entity.getId();
        entityLabel = entity.getEntryLabel();

        this.metadata = metadata;

        clientTypeName = ClassUtil.getParameterizedTypeName(client);
        clientId = client.getId();
        clientLabel = client.getEntryLabel();

        trackClient(client.getClass(), client);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setEntity(Entity<?> entity) {
        if (entity == null)
            throw new NullPointerException("entity");
        entityId = entity.getId();
        entityLabel = entity.getEntryLabel();
        entityTypeName = ClassUtil.getParameterizedTypeName(entity);
    }

    public String getEntityTypeName() {
        return entityTypeName;
    }

    public Serializable getEntityId() {
        return entityId;
    }

    public String getEntityLabel() {
        return entityLabel;
    }

    public EntityXrefMetadata getMetadata() {
        return metadata;
    }

    public String getClientTypeName() {
        return clientTypeName;
    }

    public Serializable getClientId() {
        return clientId;
    }

    public String getClientLabel() {
        return clientLabel;
    }

    public String getClientClickAction() {
        if (clientPage == null)
            return null;
        else {
            String href = clientPage.toString();
            String js = "return _open('" + href + "');";
            return js;
        }
    }

    void trackClient(Class<?> sourceType, Entity<?> source) {
        if (source == null) {
            clientPage = null;
            return;
        }

        IObjectPageDirectory pageDir = PageDirectory.getPageDirectory(source);
        if (pageDir == null) {
            logger.warn("No page for entity type: " + sourceType);
            return;
        }

        Map<String, Object> viewParams = new HashMap<String, Object>();
        viewParams.put("id", source.getId());

        List<Location> locations = pageDir.getPagesForView(StandardViews.CONTENT, viewParams);
        if (locations.isEmpty())
            logger.warn("No content view for entity type: " + sourceType);
        else {
            Location location = locations.get(0);
            clientPage = new LocationVmap(location);
        }
    }

}
