package com.bee32.plover.restful;

import java.io.IOException;

public interface IRestfulView {

    /**
     * Get the order in which the available view is queried.
     *
     * @return Order integer, the smaller value indicates higher priority.
     */
    int getPriority();

    boolean isFallback();

    /**
     * Render the object of specific class.
     *
     * @param clazz
     *            The class to be renderred as, must be assignable from the object.
     * @param obj
     *            The object to be rendererd.
     * @param req
     *            Restful request object.
     * @param resp
     *            Restful response object.
     * @return <code>true</code> if the corresponding view is defined and processed. Otherwise,
     *         <code>false</code> is returned.
     */
    boolean render(Class<?> clazz, Object obj, IRestfulRequest req, IRestfulResponse resp)
            throws IOException;

    /**
     * The same as {@link #render(Class, Object, IRestfulRequest, IRestfulResponse)}, with the
     * actual object class.
     *
     * @param obj
     *            The object to be rendererd.
     * @param req
     *            Restful request object.
     * @param resp
     *            Restful response object.
     * @return <code>true</code> if the corresponding view is defined and processed. Otherwise,
     *         <code>false</code> is returned.
     */
    boolean render(Object obj, IRestfulRequest req, IRestfulResponse resp)
            throws IOException;

    boolean renderTx(Class<?> clazz, Object obj, IRestfulRequest req, IRestfulResponse resp)
            throws IOException;

    boolean renderTx(Object obj, IRestfulRequest req, IRestfulResponse resp)
            throws IOException;

}
