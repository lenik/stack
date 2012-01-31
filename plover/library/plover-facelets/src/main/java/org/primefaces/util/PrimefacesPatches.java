package org.primefaces.util;

import java.util.Iterator;
import java.util.Map.Entry;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;

public class PrimefacesPatches {

    public static UIComponent findParentForm(FacesContext context, UIComponent component) {
        UIComponent current = component;
        UIComponent parent = current.getParent();

        while (parent != null) {
            if (parent instanceof UIForm) {
                return parent;
            }

            if (UIComponent.isCompositeComponent(parent)) {
                // detect facet name
                String facetName = null;
                for (Entry<String, UIComponent> facet : parent.getFacets().entrySet()) {
                    if (facet.getValue() == current) {
                        facetName = facet.getKey();
                        break;
                    }
                }

                if (!UIComponent.COMPOSITE_FACET_NAME.equals(facetName)) {
                    // find component where renderFacet tag is placed
                    UIComponent parentCC = UIComponent.getCompositeComponentParent(parent);
                    if (parentCC == null) {
                        parentCC = context.getViewRoot();
                    }

                    // find render place
                    current = getFacetRenderedPlace(parentCC, facetName);
                    parent = current.getParent();
                    continue;
                }
            }

            current = parent;
            parent = parent.getParent();
        }

        return null;
    }

    private static UIComponent getFacetRenderedPlace(final UIComponent cc, final String facetName) {
        Iterator<UIComponent> iter = cc.getFacetsAndChildren();
        while (iter.hasNext()) {
            UIComponent child = iter.next();
            String facetId = (String) child.getAttributes().get(UIComponent.FACETS_KEY);
            if (facetName.equals(facetId)) {
                return child;
            }

            UIComponent found = getFacetRenderedPlace(child, facetName);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

}
