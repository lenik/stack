package com.bee32.plover.servlet.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.free.FilePath;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public abstract class CompositeController
        extends _CompositeController {

    public static final String PREFIX_ATTRIBUTE = "__PLOVER_PREFIX";
    public static final String PATH_PARAMETER_ATTRIBUTE = "__PLOVER_PATH_PARAM";
    public static final String ACTION_NAME_ATTRIBUTE = "__PLOVER_ACTION_NAME";

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
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse resp)
            throws Exception {
        String internalPath; // internal path always starts with a slash /.
        {
            String path = getRequestPath(request);
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

            String actionNameTest = internalPath.substring(lastSlash);
            assert actionNameTest.startsWith("/");
            actionNameTest = actionNameTest.substring(1);

            IActionHandler handler = actionMap.get(actionNameTest);
            if (handler != null) {
                request.setAttribute(PREFIX_ATTRIBUTE, _prefix);

                String pathWithoutActionName = internalPath.substring(0, lastSlash);
                request.setAttribute(PATH_PARAMETER_ATTRIBUTE, pathWithoutActionName);

                request.setAttribute(ACTION_NAME_ATTRIBUTE, actionNameTest);

                ActionRequest req = new ActionRequest(request);
                ActionResult result = null; // TODO new ResultView(null);
                result.setResponse(resp);
                return handler.handleRequest(req, result);
            }
        }

        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "(internal) path: " + internalPath);
        return null;
    }

    protected void addHandler(IActionHandler handler) {
        if (handler == null)
            throw new NullPointerException("handler");
        String handlerName = handler.getName();
        addHandler(handlerName, handler);
    }

    protected void addHandler(String name, IActionHandler handler) {
        if (name == null)
            throw new NullPointerException("name");
        if (handler == null)
            throw new NullPointerException("handler");
        handler.setPrefix(_prefix);
        actionMap.put(name, handler);
    }

}
