package com.bee32.plover.servlet.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.free.FilePath;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public abstract class CompositeController
        extends _CompositeController {

    public static final String PATH_PARAMETER_ATTRIBUTE = "CC::PATH_PARAM";
    public static final String ACTION_NAME_ATTRIBUTE = "CC::ACTION_NAME";

    Map<String, IActionHandler> actionMap = new HashMap<String, IActionHandler>();

    /**
     * <ol>
     * <li>(hostname) http://...
     * <li>(prefix) /prefix1
     * <li>(path-param) /sub1
     * <li>(action-name) /action1/subAction1
     * <li>(ext) .doXXX
     * <li>(query-string) ?...
     * </ol>
     */
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        String internalPath; // internal path always starts with a slash /.
        {
            String path = getRequestPath(req);
            assert path.startsWith(_prefix);
            internalPath = path.substring(_prefix.length());

            /*
             * String _internalPath = (String)
             * req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
             * assertEquals(_internalPath + "/", internalPath);
             */
        }

        internalPath = FilePath.stripExtension(internalPath);

        int lastSlash = internalPath.length();
        while (true) {
            lastSlash = internalPath.lastIndexOf('/', lastSlash - 1);
            if (lastSlash == -1)
                break;

            String aName = internalPath.substring(lastSlash);
            assert aName.startsWith("/");
            aName = aName.substring(1);

            IActionHandler handler = actionMap.get(aName);
            if (handler != null)
                return handler.handleRequest(req, resp);
        }

        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "(internal) path: " + internalPath);
        return null;
    }

    protected void addAction(IActionHandler handler) {
        if (handler == null)
            throw new NullPointerException("handler");
        String handlerName = handler.getName();
        actionMap.put(handlerName, handler);
    }

    protected void addAction(String name, IActionHandler handler) {
        if (name == null)
            throw new NullPointerException("name");
        if (handler == null)
            throw new NullPointerException("handler");
        actionMap.put(name, handler);
    }

}
