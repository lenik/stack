package com.bee32.plover.restful.resource;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bee32.plover.rtx.location.Location;

/**
 * 对象页面列表。
 *
 * @author lenik
 */
public interface IObjectPageDirectory {

    Set<String> getViewNames();

    Set<String> getOperationNames();

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
     */
    List<Location> getPagesForView(String viewName);

    /**
     * Get the preferred page for the specific view for the object.
     *
     * @see StandardViews
     * @see StandardViews#FORMAT_PARAM
     */
    List<Location> getPagesForView(String viewName, Map<String, ?> parameters);

    /**
     * Get the preferred page for the specific operation to the object.
     *
     * @see StandardOperations
     */
    List<Location> getPagesForOperation(String operationName);

    /**
     * Get the preferred page for the specific operation to the object.
     *
     * @see StandardOperations
     */
    List<Location> getPagesForOperation(String operationName, Map<String, ?> parameters);

}
