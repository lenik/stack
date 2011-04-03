package com.bee32.plover.velocity;

/**
 * <p>
 * Velocity's runtime configuration is controlled by a set of configuration keys listed below.
 * Generally, these keys will have values that consist of either a String, or a comma-separated list
 * of Strings, referred to as a CSV for comma-separated values.
 * </p>
 *
 * <p>
 * There is a set of default values contained in Velocity's jar, found in
 * /src/java/org/apache/velocity/runtime/defaults/velocity.defaults, that Velocity uses as its
 * configuration baseline. This ensures that Velocity will always have a 'correct' value for its
 * configuration keys at startup, although it may not be what you want.
 * </p>
 *
 * <p>
 * Any values specified before init() time will replace the default values. Therefore, you only have
 * toconfigure velocity with the values for the keys that you need to change, and not worry about
 * the rest. Further, as we add more features and configuration capability, you don't have to change
 * your configuration files to suit - the Velocity engine will always have default values.
 * </p>
 *
 * @see <a
 *      href="http://velocity.apache.org/engine/releases/velocity-1.5/developer-guide.html#velocity_configuration_keys_and_values">Configuration
 *      keys and values</a>
 */
public interface VelocityConstants {

    // Runtime Log

    /**
     * <p>
     * <strong>Runtime Log</strong>
     *
     * <p>
     * <code>runtime.log = velocity.log</code><br>
     * </br> Full path and name of log file for error, warning, and informational messages. The
     * location, if not absolute, is relative to the 'current directory'.
     */
    String RUNTIME_LOG = "runtime.log";

    /**
     * <code>runtime.log.logsystem</code><br>
     * </br> This property has no default value. It is used to give Velocity an instantiated
     * instance of a logging class that supports the interface
     * <code>org.apache.velocity.runtime.log.LogChute</code>, which allows the combination of
     * Velocity log messages with your other application log messages. Please see the section <a
     * href="#configuring_logging"><b>Configuring Logging</b></a> for more information. </p>
     */
    String RUNTIME_LOG_LOGSYSTEM = "runtime.log.system";

    /**
     * <code>runtime.log.logsystem.class =org.apache.velocity.runtime.log.AvalonLogChute</code><br>
     * </br> Class to be used for the Velocity-instantiated log system.
     */
    String RUNTIME_LOG_LOGSYSTEM_CLASS = "runtime.log.logsystem.class";

    /**
     * <code>runtime.log.invalid.references = true </code><br>
     * </br> Property to turn off the log output when a reference isn't valid. Good thing to turn
     * off in production, but very valuable for debugging.
     */
    String RUNTIME_LOG_INVALID_REFERENCES = "runtime.log.invalid.references";

    /**
     * <code>runtime.log.logsystem.avalon.logger = <i>name</i> </code><br>
     * </br> Allows user to specify an existing logger <i>name</i> in the Avalon hierarchy without
     * having to wrap with a LogChute interface. <b>Note:</b> You must also specify
     * <code>runtime.log.logsystem.class =org.apache.velocity.runtime.log.AvalonLogChute</code> as
     * the default logsystem may change. There is <b>no</b> guarantee that the Avalon log system
     * will remain the default log system. </p>
     */
    String RUNTIME_LOG_LOGSYSTEM_AVALON_LOGGER = "runtime.log.logsystem.avalon.logger";

    /**
     * <code>input.encoding = ISO-8859-1</code><br>
     * </br> Character encoding for input (templates). Using this, you can use alternative encoding
     * for your templates, such as UTF-8. </p>
     */
    String INPUT_ENCODING = "input.encoding";

    /**
     * <code>output.encoding = ISO-8859-1</code><br>
     * </br> Character encoding for output streams from the VelocityServlet and Anakia.
     */
    String OUTPUT_ENCODING = "output.encoding";

    /**
     * <strong>#foreach() Directive</strong> </p>
     *
     * <p>
     * <code>directive.foreach.counter.name = velocityCount</code><br>
     * </br> Used in the #foreach() directive, defines the string to be used as the context key for
     * the loop count. A template would access the loop count as $velocityCount.
     * </p>
     */
    String DIRECTIVE_FOREACH_COUNTER_NAME = "directive.foreach.counter.name";

    /**
     * <code>directive.foreach.counter.initial.value = 1</code><br>
     * </br> Default starting value for the loop counter reference in a #foreach() loop. </p>
     */
    String DIRECTIVE_FOREACH_COUNTER_INITIAL_VALUE = "directive.foreach.counter.initial.value";

    /**
     * <code>directive.foreach.maxloops = -1</code><br>
     * </br> Maximum allowed number of loops for a #foreach() statement.
     */
    String DIRECTIVE_FOREACH_MAXLOOPS = "directive.foreach.maxloops";

    /**
     * <p>
     * <strong>#set() Directive</strong>
     * </p>
     *
     * <p>
     * <code>directive.set.null.allowed = false</code><br>
     * </br> If true, having a right hand side of a #set() statement with an invalid reference or
     * null value will set the left hand side to null. If false, the left hand side will stay the
     * same.
     */
    String DIRECTIVE_SET_NULL_ALLOWED = "directive.set.null.allowed";

    /**
     * <p>
     * <strong>#include() and #parse() Directive</strong>
     * </p>
     *
     * <p>
     * <code>directive.include.output.errormsg.start =  &lt;!-- include error :  </code><br>
     */
    String DIRECTIVE_INCLUDE_OUTPUT_ERRORMSG_START = "directive.include.output.errormsg.start";

    /**
     * <code>directive.include.output.errormsg.end =   see error log --&gt; </code><br>
     * </br> Defines the beginning and ending tags for an in-stream error message in the case of a
     * problem with the #include() directive. If both the .start and .end tags are defined, an error
     * message will be output to the stream, of the form '.start msg .end' where .start and .end
     * refer to the property values. Output to the render stream will only occur if both the .start
     * and .end (next) tag are defined. </p>
     */
    String DIRECTIVE_INCLUDE_OUTPUT_ERRORMSG_END = "directive.include.output.errormsg.end";

    /**
     * <code>directive.parse.max.depth = 10</code><br>
     * </br> Defines the allowable parse depth for a template. A template may #parse() another
     * template which itself may have a #parse() directive. This value prevents runaway #parse()
     * recursion. </p>
     */
    String DIRECTIVE_PARSE_MAX_DEPTH = "directive.parse.max.depth";

    /**
     * <p>
     * <strong>Resource Management</strong>
     * </p>
     *
     * <p>
     * <code>resource.manager.logwhenfound = true</code><br>
     * </br> Switch to control logging of 'found' messages from resource manager. When a resource is
     * found for the first time, the resource name and classname of the loader that found it will be
     * noted in the runtime log.
     * </p>
     */
    String RESOURCE_MANAGER_LOGWHENFOUND = "resource.manager.logwhenfound";

    /**
     * <code>resource.manager.cache.class</code> <br>
     * </br> Declares the class to be used for resource caching. The current default is
     * <code>org.apache.velocity.runtime.resource.ResourceCacheImpl</code> which uses a LRU Map to
     * prevent data from being held forever. You can set the size of the LRU Map using the parameter
     * <code>resource.manager.defaultcache.size</code>. The dafault value of the default cache size
     * is currently 89. </p>
     */
    String RESOURCE_MANAGER_CACHE_CLASS = "resource.manager.cache.class";

    /**
     * <code>resource.manager.defaultcache.size</code> <br>
     * </br> Sets the size of the default implementation of the resource manager resource cache.
     * </p>
     */
    String RESOURCE_MANAGER_DEFAULTCACHE_SIZE = "resource.manager.defaultcache.size";

    /**
     * <code>resource.loader = &lt;name&gt; (default = file)</code><br>
     * </br> <i>Multi-valued key. Will accept CSV for value.</i> Public name of a resource loader to
     * be used. This public name will then be used in the specification of the specific properties
     * for that resource loader. Note that as a multi-valued key, it's possible to pass a value like
     * &quot;file, class&quot; (sans quotes), indicating that following will be configuration values
     * for two loaders. </p>
     */
    String RESOURCE_LOADER = "resource.loader";

    /**
     * <code>&lt;name&gt;.loader.description = Velocity File Resource Loader</code><br>
     * </br> Description string for the given loader. </p>
     */
    String _LOADER_DESCRIPTION = ".loader.description";

    /**
     * <code>&lt;name&gt;.resource.loader.class = org.apache.velocity.runtime.resource.loader.FileResourceLoader</code>
     * <br>
     * </br> Name of implementation class for the loader. The default loader is the file loader.
     * </p>
     */
    String _RESOURCE_LOADER_CLASS = ".resource.loader.class";

    /**
     * <code>&lt;name&gt;.resource.loader.path = .</code><br>
     * </br> <i>Multi-valued key. Will accept CSV for value.</i> Root(s) from which the loader loads
     * templates. Templates may live in subdirectories of this root. ex. homesite/index.vm This
     * configuration key applies currently to the FileResourceLoader and JarResourceLoader. </p>
     */
    String _RESOURCE_LOADER_PATH = ".resource.loader.path";

    /**
     * <p>
     * <code>&lt;name&gt;.resource.loader.cache = false</code><br>
     * </br> Controls caching of the templates in the loader. Default is false, to make life easy
     * for development and debugging. This should be set to true for production deployment. When
     * 'true', the <code>modificationCheckInterval</code> property applies. This allows for the
     * efficiency of caching, with the convenience of controlled reloads - useful in a hosted or ISP
     * environment where templates can be modifed frequently and bouncing the application or servlet
     * engine is not desired or permitted.
     * </p>
     */
    String _RESOURCE_LOADER_CACHE = ".resource.loader.cache";

    /**
     * <code>&lt;name&gt;.resource.loader.modificationCheckInterval = 2</code><br>
     * </br> This is the number of seconds between modification checks when caching is turned on.
     * When this is an integer &gt; 0, this represents the number of seconds between checks to see
     * if the template was modified. If the template has been modified since last check, then it is
     * reloaded and reparsed. Otherwise nothing is done. When &lt;= 0, no modification checks will
     * take place, and assuming that the property <code>cache</code> (above) is true, once a
     * template is loaded and parsed the first time it is used, it will not be checked or reloaded
     * after that until the application or servlet engine is restarted. </p>
     */
    String _RESOURCE_LOADER_MODIFICATIONCHECKINTERVAL = "resource.loader.modificationCheckInterval";

    /**
     * <strong>Velocimacro</strong>
     *
     * <p>
     * <code>velocimacro.library = VM_global_library.vm </code><br>
     * </br> <i>Multi-valued key. Will accept CSV for value.</i> Filename(s) of Velocimacro library
     * to be loaded when the Velocity Runtime engine starts. These Velocimacros are accessable to
     * all templates. The file is assumed to be relative to the root of the file loader resource
     * path.
     * </p>
     */
    String VELOCIMACRO_LIBRARY = "velocimacro.library";

    /**
     * <p>
     * <code>velocimacro.permissions.allow.inline = true</code><br>
     * </br> Determines of the definition of new Velocimacros via the #macro() directive in
     * templates is allowed. The default value is true, meaning any template can define and use new
     * Velocimacros. Note that depending on other properties, those #macro() statements can replace
     * global definitions.
     * </p>
     */
    String VELOCIMACRO_PERMISSIONS_ALLOW_INLINE = "velocimacro.permissions.allow.inline";

    /**
     * <code>velocimacro.permissions.allow.inline.to.replace.global = false </code><br>
     * </br> Controls if a Velocimacro defind 'inline' in a template can replace a Velocimacro
     * defined in a library loaded at startup. </p>
     */
    String VELOCIMACRO_PERMISSIONS_ALLOW_INLINE_TO_REPLACE_GLOBAL = "velocimacro.permissions.allow.inline.to.replace.global";

    /**
     * <p>
     * <code>velocimacro.permissions.allow.inline.local.scope = false</code><br>
     * </br> Controls 'private' templates namespaces for Velocimacros. When true, a #macro()
     * directive in a template creates a Velocimacro that is accessable only from the defining
     * template. This means that Velocimacros cannot be shared unless they are in the global or
     * local library loaded at startup. (See above.) It also means that templates cannot interfere
     * with each other. This property also allows a technique where there is a 'default' Velocimacro
     * definition in the global or local library, and a template can 'override' the implementation
     * for use within that template. This occurrs because when this property is true, the template's
     * namespace is searched for a Velocimacro before the global namespace, therefore allowing the
     * override mechanism.
     * </p>
     */
    String VELOCIMACRO_PERMISSIONS_ALLOW_INLINE_LOCAL_SCOPE = "velocimacro.permissions.allow.inline.local.scope";

    /**
     * <p>
     * <code>velocimacro.context.localscope = false</code><br>
     * </br> Controls whether reference access (set/get) within a Velocimacro will change the
     * context, or be of local scope in that Velocimacro.
     * </p>
     */
    String VELOCIMACRO_CONTEXT_LOCALSCOPE = "velocimacro.context.localscope";

    /**
     * <p>
     * <code>velocimacro.library.autoreload = false</code><br>
     * </br> Controls Velocimacro library autoloading. When set to <code>true</code> the source
     * Velocimacro library for an invoked Velocimacro will be checked for changes, and reloaded if
     * necessary. This allows you to change and test Velocimacro libraries without having to restart
     * your application or servlet container, just like you can with regular templates. This mode
     * only works when caching is <i>off</i> in the resource loaders (e.g.
     * <code>file.resource.loader.cache = false</code> ). This feature is intended for development,
     * not for production.
     * </p>
     */
    String VELOCIMACRO_LIBRARY_AUTORELOAD = "velocimacro.library.autoreload";

    /**
     * <code>velocimacro.arguments.strict = false</code><br>
     * </br> When set to true, will throw a <code>ParseErrorException</code> when parsing a template
     * containing a macro with an invalid number of arguments. Is set to false by default to
     * maintain backwards compatibility with templates written before this feature became available.
     * </p>
     */
    String VELOCIMACRO_ARGUMENTS_STRICT = "velocimacro.arguments.strict";

    /**
     * <strong>String Interpolation</strong> </p>
     *
     * <p>
     * <code>runtime.interpolate.string.literals = true</code><br>
     * </br> Controls interpolation mechanism of VTL String Literals. Note that a VTL StringLiteral
     * is specifically a string using double quotes that is used in a #set() statement, a method
     * call of a reference, a parameter to a VM, or as an argument to a VTL directive in general.
     * See the VTL reference for further information.
     * </p>
     */
    String RUNTIME_INTERPOLATE_STRING_LITERALS = "runtime.interpolate.string.literals";

    /**
     * <p>
     * <strong>Parser Configuration</strong>
     * </p>
     * <p>
     * <code>parser.pool.class = org.apache.velocity.runtime.ParserPoolImpl</code><br>
     * </br> This property selects the implementation for the parser pool. This class must implement
     * ParserPool. Generally there is no reason to change this though if you are building a high
     * volume web application you might consider including an alternate implementation that
     * automatically adjusts the size of the pool.
     * </p>
     */
    String PARSER_POOL_CLASS = "parser.pool.class";

    /**
     * <code>parser.pool.size = 20</code><br>
     * </br> This property is used by the default pooling implementation to set the number of parser
     * instances that Velocity will create at startup and keep in a pool. The default of 20 parsers
     * should be more than enough for most uses. In the event that Velocity does run out of parsers,
     * it will indicate so in the log, and dynamically create overflow instances as needed. Note
     * that these extra parsers will not be added to the pool, and will be discarded after use. This
     * will result in very slow operation compared to the normal usage of pooled parsers, but this
     * is considered an exceptional condition. A web application using Velocity as its view engine
     * might exhibit this behavior under extremely high concurrency (such as when getting
     * Slashdotted). If you see a corresponding message referencing the
     * <code>parser.pool.size</code> property in your log files, please increment this property
     * immediately to avoid performance degradation. </p>
     */
    String PARSER_POOL_SIZE = "parser.pool.size";

    /**
     * <strong>Pluggable Introspection</strong> </p>
     *
     * <p>
     * <code>runtime.introspector.uberspect = org.apache.velocity.util.introspection.UberspectImpl</code>
     * <br>
     * </br> This property sets the 'Uberspector', the introspection package that handles all
     * introspection strategies for Velocity. The default works just fine, so only replace if you
     * have something really interesting and special to do.
     * </p>
     */
    String RUNTIME_INTROSPECTOR_UBERSPECT = "runtime.introspector.uberspect";

}
