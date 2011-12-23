package com.bee32.plover.restful.resource;

import java.util.Collection;
import java.util.Map;

import com.bee32.plover.rtx.location.Location;

/**
 * 对象页面列表。
 *
 * @author lenik
 */
public interface IObjectPageDirectory {

    Collection<String> getPagesForView();

    Collection<String> getPagesForOperation();

    /**
     * Get the base location (or, the prefix).
     *
     * @return <code>null</code> if base-location is not applicable for the specific object.
     */
    Location getBaseLocation();

    /**
     * Get the preferred page for the specific view for the object.
     *
     * @see StandardViews
     * @see StandardViews#FORMAT_PARAM
     */
    Location getPageForView(String viewName, Map<String, ?> parameters);

    /**
     * Get the preferred page for the specific operation to the object.
     *
     * @see StandardOperations
     */
    Location getPageForOperation(String operationName, Map<String, ?> parameters);

}
