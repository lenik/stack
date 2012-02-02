package com.bee32.plover.faces.tag.composite;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.FaceletHandler;
import javax.faces.view.facelets.FacetHandler;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;
import javax.free.UnexpectedException;

import org.apache.myfaces.view.facelets.AbstractFaceletContext;
import org.apache.myfaces.view.facelets.TemplateClient;
import org.apache.myfaces.view.facelets.TemplateContext;
import org.apache.myfaces.view.facelets.TemplateManager;
import org.apache.myfaces.view.facelets.tag.composite.CompositeComponentResourceTagHandler;
import org.apache.myfaces.view.facelets.tag.composite.InsertFacetHandler;

public class InsertRawFacetHandler
        extends InsertFacetHandler {

    static final Logger log = Logger.getLogger(InsertRawFacetHandler.class.getName());

    public InsertRawFacetHandler(TagConfig config) {
        super(config);
    }

    @Override
    public String getFacetName(FaceletContext ctx) {
        return _name.getValue(ctx);
    }

    @Override
    public void apply(FaceletContext ctx, UIComponent parent)
            throws IOException {

        String facetName = _name.getValue(ctx);

        AbstractFaceletContext actx = (AbstractFaceletContext) ctx;
        // actx.includeCompositeComponentDefinition(parent, facetName);
        TemplateClient ccClient;
        TemplateContext tctx = actx.popTemplateContext();
        try {
            ccClient = tctx.getCompositeComponentClient();
        } finally {
            actx.pushTemplateContext(tctx);
        }

        while (ccClient instanceof TemplateManager) {
            ccClient = getProtectedTarget(ccClient);
        }
        if (ccClient == null)
            throw new NullPointerException("No cc client.");

        // Instead of ccClient.apply(actx, parent, facetName), we'll drop the enclosing <f:facet>
        if (!(ccClient instanceof CompositeComponentResourceTagHandler))
            throw new RuntimeException("ccClient isn't a resource tag handler.");

        CompositeComponentResourceTagHandler ccClientHandler = (CompositeComponentResourceTagHandler) ccClient;
        TagHandler facetHandler = getFacetHandler(ctx, ccClientHandler, facetName);
        if (facetHandler != null) {
            TemplateContext itc = actx.popTemplateContext();
            try {
                // facetHandler.apply(ctx, parent);
                FaceletHandler facetNextHandler = getNextHandler(facetHandler);
                facetNextHandler.apply(ctx, parent);
            } finally {
                actx.pushTemplateContext(itc);
            }
        }
    }

    static TemplateClient getProtectedTarget(TemplateClient client) {
        Field _targetField;
        try {
            _targetField = client.getClass().getDeclaredField("_target");
        } catch (NoSuchFieldException e) {
            return null;
        }
        _targetField.setAccessible(true);
        try {
            return (TemplateClient) _targetField.get(client);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    static final Field _facetHandlersMapField;
    static final Field _facetHandlersField;
    static {
        try {
            _facetHandlersMapField = CompositeComponentResourceTagHandler.class.getDeclaredField("_facetHandlersMap");
            _facetHandlersField = CompositeComponentResourceTagHandler.class.getDeclaredField("_facetHandlers");
            _facetHandlersMapField.setAccessible(true);
            _facetHandlersField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new UnexpectedException(e);
        }
    }

    static TagHandler getFacetHandler(FaceletContext ctx, CompositeComponentResourceTagHandler client, String facetName) {
        try {
            Map<String, TagHandler> _facetHandlersMap = (Map<String, TagHandler>) _facetHandlersMapField.get(client);
            if (_facetHandlersMap == null) {
                Map<String, TagHandler> map = new HashMap<String, TagHandler>();

                Collection<FaceletHandler> _facetHandlers = (Collection<FaceletHandler>) _facetHandlersField
                        .get(client);
                if (_facetHandlers != null) {
                    for (FaceletHandler handler : _facetHandlers) {
                        if (!(handler instanceof TagHandler))
                            throw new UnexpectedException("Facet-handler is not a tag-handler: " + handler);

                        String name = null;
                        if (handler instanceof FacetHandler)
                            name = ((FacetHandler) handler).getFacetName(ctx);
                        else if (handler instanceof InsertFacetHandler)
                            name = ((InsertFacetHandler) handler).getFacetName(ctx);
                        else
                            throw new UnexpectedException("Unknown facet type.");

                        map.put(name, (TagHandler) handler);
                    }
                }

                _facetHandlersMap = map;
                _facetHandlersMapField.set(client, _facetHandlersMap);
            }
            return _facetHandlersMap.get(facetName);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    static final Field nextHandlerField;
    static {
        try {
            nextHandlerField = TagHandler.class.getDeclaredField("nextHandler");
            nextHandlerField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new UnexpectedException(e);
        }
    }

    static FaceletHandler getNextHandler(TagHandler handler) {
        try {
            return (FaceletHandler) nextHandlerField.get(handler);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
