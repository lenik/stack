/**
 * Copyright (c) 2006
 * Martin Czuchra, Nicolas Peters, Daniel Polak, Willi Tscheschner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 **/

function printf() {

	var result = arguments[0];
	for (var i=1; i<arguments.length; i++)
		result = result.replace('%' + (i-1), arguments[i]);
	return result;
}

// oryx constants.
var ORYX_LOGLEVEL_TRACE = 5;
var ORYX_LOGLEVEL_DEBUG = 4;
var ORYX_LOGLEVEL_INFO = 3;
var ORYX_LOGLEVEL_WARN = 2;
var ORYX_LOGLEVEL_ERROR = 1;
var ORYX_LOGLEVEL_FATAL = 0;
var ORYX_LOGLEVEL = 3;
var ORYX_CONFIGURATION_DELAY = 100;
var ORYX_CONFIGURATION_WAIT_ATTEMPTS = 10;

if(!ORYX) var ORYX = {};

ORYX = Object.extend(ORYX, {

	//set the path in the config.js file!!!!
	PATH: ORYX.CONFIG.ROOT_PATH,
	//CONFIGURATION: "config.js",

	URLS: [

		/*
		 * No longer needed, since compiled into one source file that
		 * contains all of this files concatenated in the exact order
		 * as defined in build.xml.
		 */

/*
		"scripts/Core/SVG/editpathhandler.js",
		"scripts/Core/SVG/minmaxpathhandler.js",
		"scripts/Core/SVG/pointspathhandler.js",
		"scripts/Core/SVG/svgmarker.js",
		"scripts/Core/SVG/svgshape.js",
		"scripts/Core/SVG/label.js",
		"scripts/Core/Math/math.js",
		"scripts/Core/StencilSet/stencil.js",
		"scripts/Core/StencilSet/property.js",
		"scripts/Core/StencilSet/propertyitem.js",
		"scripts/Core/StencilSet/rules.js",
		"scripts/Core/StencilSet/stencilset.js",
		"scripts/Core/StencilSet/stencilsets.js",
		"scripts/Core/bounds.js",
		"scripts/Core/uiobject.js",
		"scripts/Core/abstractshape.js",
		"scripts/Core/canvas.js",
		"scripts/Core/main.js",
		"scripts/Core/svgDrag.js",
		"scripts/Core/shape.js",
		"scripts/Core/Controls/control.js",
		"scripts/Core/Controls/docker.js",
		"scripts/Core/Controls/magnet.js",
		"scripts/Core/node.js",
		"scripts/Core/edge.js"
*/	],

	alreadyLoaded: [],

	configrationRetries: 0,

	Version: '0.1.1',

	availablePlugins: [],

	/**
	 * The ORYX.Log logger.
	 */
	Log: {

		__appenders: [
			{ append: function(message) {
				console.log(message); }}
		],

		trace: function() {	if(ORYX_LOGLEVEL >= ORYX_LOGLEVEL_TRACE)
			ORYX.Log.__log('TRACE', arguments); },
		debug: function() { if(ORYX_LOGLEVEL >= ORYX_LOGLEVEL_DEBUG)
			ORYX.Log.__log('DEBUG', arguments); },
		info: function() { if(ORYX_LOGLEVEL >= ORYX_LOGLEVEL_INFO)
			ORYX.Log.__log('INFO', arguments); },
		warn: function() { if(ORYX_LOGLEVEL >= ORYX_LOGLEVEL_WARN)
			ORYX.Log.__log('WARN', arguments); },
		error: function() { if(ORYX_LOGLEVEL >= ORYX_LOGLEVEL_ERROR)
			ORYX.Log.__log('ERROR', arguments); },
		fatal: function() { if(ORYX_LOGLEVEL >= ORYX_LOGLEVEL_FATAL)
			ORYX.Log.__log('FATAL', arguments); },

		__log: function(prefix, messageParts) {

			messageParts[0] = (new Date()).getTime() + " "
				+ prefix + " " + messageParts[0];
			var message = printf.apply(null, messageParts);

			ORYX.Log.__appenders.each(function(appender) {
				appender.append(message);
			});
		},

		addAppender: function(appender) {
			ORYX.Log.__appenders.push(appender);
		}
	},

	/**
	 * First bootstrapping layer. The Oryx loading procedure begins. In this
	 * step, all preliminaries that are not in the responsibility of Oryx to be
	 * met have to be checked here, such as the existance of the prototpe
	 * library in the current execution environment. After that, the second
	 * bootstrapping layer is being invoked. Failing to ensure that any
	 * preliminary condition is not met has to fail with an error.
	 */
	load: function() {

		if (ORYX.CONFIG.PREVENT_LOADINGMASK_AT_READY !== true) {
			var waitingpanel = new Ext.Window({renderTo:Ext.getBody(),id:'oryx-loading-panel',bodyStyle:'padding: 8px;background:white',title:ORYX.I18N.Oryx.title,width:'auto',height:'auto',modal:true,resizable:false,closable:false,html:'<span style="font-size:11px;">' + ORYX.I18N.Oryx.pleaseWait + '</span>'})
			waitingpanel.show()
		}

		ORYX.Log.debug("Oryx begins loading procedure.");

		// check for prototype
		if( (typeof Prototype=='undefined') ||
			(typeof Element == 'undefined') ||
			(typeof Element.Methods=='undefined') ||
			parseFloat(Prototype.Version.split(".")[0] + "." +
				Prototype.Version.split(".")[1]) < 1.5)

			throw("Application requires the Prototype JavaScript framework >= 1.5.3");

		ORYX.Log.debug("Prototype > 1.5 found.");

		// continue loading.
		ORYX._load();
	},

	/**
	 * Second bootstrapping layer. The oryx configuration is checked. When not
	 * yet loaded, config.js is being requested from the server. A repeated
	 * error in retrieving the configuration will result in an error to be
	 * thrown after a certain time of retries. Once the configuration is there,
	 * all urls that are registered with oryx loading are being requested from
	 * the server. Once everything is loaded, the third layer is being invoked.
	 */
	_load: function() {
	/*
		// if configuration not there already,
		if(!(ORYX.CONFIG)) {

			// if this is the first attempt...
			if(ORYX.configrationRetries == 0) {

				// get the path and filename.
				var configuration = ORYX.PATH + ORYX.CONFIGURATION;

				ORYX.Log.debug("Configuration not found, loading from '%0'.",
					configuration);

				// require configuration file.
				Kickstart.require(configuration);

			// else if attempts exceeded ...
			} else if(ORYX.configrationRetries >= ORYX_CONFIGURATION_WAIT_ATTEMPTS) {

				throw "Tried to get configuration" +
					ORYX_CONFIGURATION_WAIT_ATTEMPTS +
					" times from '" + configuration + "'. Giving up."

			} else if(ORYX.configrationRetries > 0){

				// point out how many attempts are left...
				ORYX.Log.debug("Waiting once more (%0 attempts left)",
					(ORYX_CONFIGURATION_WAIT_ATTEMPTS -
						ORYX.configrationRetries));

			}

			// any case: continue in a moment with increased retry count.
			ORYX.configrationRetries++;
			window.setTimeout(ORYX._load, ORYX_CONFIGURATION_DELAY);
			return;
		}

		ORYX.Log.info("Configuration loaded.");

		// load necessary scripts.
		ORYX.URLS.each(function(url) {
			ORYX.Log.debug("Requireing '%0'", url);
			Kickstart.require(ORYX.PATH + url) });
	*/
		// configurate logging and load plugins.
		ORYX.loadPlugins();
	},

	/**
	 * Third bootstrapping layer. This is where first the plugin coniguration
	 * file is loaded into oryx, analyzed, and where all plugins are being
	 * requested by the server. Afterwards, all editor instances will be
	 * initialized.
	 */
	loadPlugins: function() {

		// load plugins if enabled.
		if(ORYX.CONFIG.PLUGINS_ENABLED)
			ORYX._loadPlugins()
		else
			ORYX.Log.warn("Ignoring plugins, loading Core only.");

		// init the editor instances.
		init();
	},

	_loadPlugins: function() {

		// load plugin configuration file.
		var source = ORYX.CONFIG.PLUGINS_CONFIG;

		ORYX.Log.debug("Loading plugin configuration from '%0'.", source);

		new Ajax.Request(source, {
			asynchronous: false,
			method: 'get',
			onSuccess: function(result) {

				/*
				 * This is the method that is being called when the plugin
				 * configuration was successfully loaded from the server. The
				 * file has to be processed and the contents need to be
				 * considered for further plugin requireation.
				 */

				ORYX.Log.info("Plugin configuration file loaded.");

				// get plugins.xml content
				var resultXml = result.responseXML;

				// TODO: Describe how properties are handled.
				// Get the globale Properties
				var globalProperties = [];
				var preferences = $A(resultXml.getElementsByTagName("properties"));
				preferences.each( function(p) {

					var props = $A(p.childNodes);
					props.each( function(prop) {
						var property = new Hash();

						// get all attributes from the node and set to global properties
						var attributes = $A(prop.attributes)
						attributes.each(function(attr){property[attr.nodeName] = attr.nodeValue});
						if(attributes.length > 0) { globalProperties.push(property) };
					});
				});


				// TODO Why are we using XML if we don't respect structure anyway?
				// for each plugin element in the configuration..
				var plugin = resultXml.getElementsByTagName("plugin");
				$A(plugin).each( function(node) {

					// get all element's attributes.
					// TODO: What about: var pluginData = $H(node.attributes) !?
					var pluginData = new Hash();
					$A(node.attributes).each( function(attr){
						pluginData[attr.nodeName] = attr.nodeValue});

					// ensure there's a name attribute.
					if(!pluginData['name']) {
						ORYX.Log.error("A plugin is not providing a name. Ingnoring this plugin.");
						return;
					}

					// ensure there's a source attribute.
					if(!pluginData['source']) {
						ORYX.Log.error("Plugin with name '%0' doesn't provide a source attribute.", pluginData['name']);
						return;
					}

					// Get all private Properties
					var propertyNodes = node.getElementsByTagName("property");
					var properties = [];
					$A(propertyNodes).each(function(prop) {
						var property = new Hash();

						// Get all Attributes from the Node
						var attributes = $A(prop.attributes)
						attributes.each(function(attr){property[attr.nodeName] = attr.nodeValue});
						if(attributes.length > 0) { properties.push(property) };

					});

					// Set all Global-Properties to the Properties
					properties = properties.concat(globalProperties);

					// Set Properties to Plugin-Data
					pluginData['properties'] = properties;

					// Get the RequieredNodes
					var requireNodes = node.getElementsByTagName("requires");
					var requires;
					$A(requireNodes).each(function(req) {
						var namespace = $A(req.attributes).find(function(attr){ return attr.name == "namespace"})
						if( namespace && namespace.nodeValue ){
							if( !requires ){
								requires = {namespaces:[]}
							}

							requires.namespaces.push(namespace.nodeValue)
						}
					});

					// Set Requires to the Plugin-Data, if there is one
					if( requires ){
						pluginData['requires'] = requires;
					}


					// Get the RequieredNodes
					var notUsesInNodes = node.getElementsByTagName("notUsesIn");
					var notUsesIn;
					$A(notUsesInNodes).each(function(not) {
						var namespace = $A(not.attributes).find(function(attr){ return attr.name == "namespace"})
						if( namespace && namespace.nodeValue ){
							if( !notUsesIn ){
								notUsesIn = {namespaces:[]}
							}

							notUsesIn.namespaces.push(namespace.nodeValue)
						}
					});

					// Set Requires to the Plugin-Data, if there is one
					if( notUsesIn ){
						pluginData['notUsesIn'] = notUsesIn;
					}


					var url = ORYX.PATH + ORYX.CONFIG.PLUGINS_FOLDER + pluginData['source'];

					ORYX.Log.debug("Requireing '%0'", url);

					// Add the Script-Tag to the Site
					//Kickstart.require(url);

					ORYX.Log.info("Plugin '%0' successfully loaded.", pluginData['name']);

					// Add the Plugin-Data to all available Plugins
					ORYX.availablePlugins.push(pluginData);

				});

			},
			onFailure:this._loadPluginsOnFails
		});

	},

	_loadPluginsOnFails: function(result) {

		ORYX.Log.error("Plugin configuration file not available.");
	}
});

ORYX.Log.debug('Registering Oryx with Kickstart');
Kickstart.register(ORYX.load);
