package com.bee32.plover.faces;

public interface FacesConstants {

    // Initialization Parameters

    /**
     * A semicolon (;) delimitted list of paths to Facelet tag libraries, relative to your
     * application's root. These libraries will be loaded when the first request hits the
     * FaceletViewHandler for page compilation.
     *
     * @example /WEB-INF/facelets/foo.taglib.xml; /WEB-INF/facelets/bar.taglib.xml
     */
    String LIBRARIES = "facelets.LIBRARIES";

    /**
     * A semicolon (;) delimitted list of class names of type com.sun.facelets.tag.TagDecorator,
     * with a no-argument constructor. These decorators will be loaded when the first request hits
     * the FaceletViewHandler for page compilation.
     *
     * @example com.sun.facelets.tag.jsf.html.HtmlDecorator
     */
    String DECORATORS = "facelets.DECORATORS";

    /**
     * false Setting this to true will cause the FaceletViewHandler to print out debug information
     * in an easy to use screen when an error occurs during the rendering process.
     *
     * @example <code>true</code>
     */
    String DEVELOPMENT = "facelets.DEVELOPMENT";

    /**
     * -1 The buffer size to set on the response when the ResponseWriter is generated. By default
     * the value is -1, which will not assign a buffer size on the response. This should be
     * increased if you are using development mode in order to guarantee that the response isn't
     * partially rendered when an error is generated.
     *
     * @example 8192
     */
    String BUFFER_SIZE = "facelets.BUFFER_SIZE";

    /**
     * 2 When a page is requested, what interval in seconds should the compiler check for changes.
     * If you don't want the compiler to check for changes once the page is compiled, then use a
     * value of -1. Setting a low refresh period helps during development to be able to edit pages
     * in a running application.
     *
     * @example -1
     */
    String REFRESH_PERIOD = "facelets.REFRESH_PERIOD";

    /**
     * Optionally provide an alternate ResourceResolver that will replace the default logic of
     * allowing the FacesContext resolve the resource URL.
     *
     * @example com.sun.facelets.impl.DefaultResourceResolver
     * @example my.company.IDEResourceResolver
     */
    String RESOURCE_RESOLVER = "facelets.RESOURCE_RESOLVER";

    /**
     * A semicolon (;) delimitted list of resources that Facelets should use. If no resource paths
     * are specified, Facelets will handle all requests (DEFAULT). If one or more paths are
     * specified, Facelets will only use the ones specified, otherwise fall back on the parent or
     * default ViewHandler (JSP). Note, this requires the FacesServlet in your web.xml to be mapped
     * with a prefix for capturing multiple file types
     *
     * @example ex: /faces/*. /demos/*; *.xhtml
     */
    String VIEW_MAPPINGS = "facelets.VIEW_MAPPINGS";

    /**
     * A boolean value that tells the compiler to skip comments (default is true). Even if you
     * comment out code in your page, the tags will not be compiled but expressions (EL) will be
     * treated as if they were inlined-- still compiled and evaluated for output in the document.
     * Skipping comments will remove all comments completely from the document.
     *
     * @example true
     */
    String SKIP_COMMENTS = "facelets.SKIP_COMMENTS";

    /**
     * Possible values:
     * <ul>
     * <li>Production
     * <li>Development
     * </ul>
     */
    String PROJECT_STAGE = "javax.faces.PROJECT_STAGE";

    String DEFAULT_SUFFIX = "javax.faces.DEFAULT_SUFFIX";

    String RI_VALIDATE_XML = "com.sun.faces.validateXml";
    String RI_VERIFY_OBJECTS = "com.sun.faces.verifyObjects";

    String MF_VALIDATE_XML = "org.apache.myfaces.VALIDATE_XML";
    String MF_STRICT_XHTML_LINKS = "org.apache.myfaces.STRICT_XHTML_LINKS";

    String DISABLE_VALIDATOR = "javax.faces.validator.DISABLE_DEFAULT_BEAN_VALIDATOR";

}
