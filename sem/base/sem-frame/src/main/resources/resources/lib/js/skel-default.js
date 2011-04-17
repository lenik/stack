var CONTEXT_ROOT = document.contextPath;
if (CONTEXT_ROOT == null)
    CONTEXT_ROOT = "";

var skel_loadtab = {
    config : [],
    menu : []
};

function skel_bind(phase, href) {
    var q = skel_loadtab[phase];
    if (q == null)
        throw "Invalid phase: " + phase;
    q.push(href);
}

function skel_load_queue(queue, callback) {
    while (queue.length > 0)
        callback = (function(last, cb) {
            return function() {
                // alert("include: " + last + " -> " + cb);
                $.include(last, cb);
            };
        })(queue.pop(), callback);
    callback();
}

var skel_menu = {};
function skel_menu_extend(x) {
    jQuery.extend(true, skel_menu, x);
}


$(document).ready(function() {
    $.include(CONTEXT_ROOT + "/JS-INF/skel-context.js", function() {
        skel_load_queue(skel_loadtab.config, function() {
            skel_load_queue(skel_loadtab.menu, function() {
                skel_apply();
            });
        });
    });
});


function skel_apply() {
    var body = $('body');

    var headBanner = skel_mkheader();
    var footBanner = skel_mkfooter();
    var skelMenu = skel_mkmenu();

    body.prepend(skelMenu);
    body.prepend(headBanner);
    body.append(footBanner);

    var _SUPERFISH_LOC = "http://static.secca-project.com/lib2/superfish-1.4.8/js";
    var marchs = [
	_SUPERFISH_LOC + "/superfish.js",
	_SUPERFISH_LOC + "/hoverIntent.js"
	];
    skel_load_queue(marchs, function() {
	$("#skel-menu").superfish();
    });
}

function skel_mkheader() {
    return $(' \
    <div style="margin:0; width:100%;"> \
        <img src="http://static.secca-project.com/style/default/banner.gif"/> \
    </div> \
    ');
}

function skel_mkfooter() {
    return $(' \
    <div style="clear:both;" /> \
    <div style="height:5px; background-image:url(http://static.secca-project.com/style/default/split.gif); background-repeat:repeat-y"></div> \
    <div style="margin-top:10px;"> \
            <div style="display:inline">版权所有©2010-2011 海宁市智恒软件有限公司</div> \
            <div style="display:inline; margin-left:50px;">联系我们:xjl[at]99jsj.com   superjwl[at]gmail.com</div> \
    </div> \
    ');
}

/**
 * node ::= { label, [image], href, children { node* } }
 *
 * @param node
 *            The start struct node to dump
 * @param prefix
 *            The prefix url.
 */
function _tconv(node, prefix) {
    if (node == null)
        throw "node is null";
    var label = node._label == null ? "(Noname)" : node._label;
    var href = node._href;
    if (href != null) {
        var absolute = href.substring(0, 1) == '/' || href.indexOf("://") != -1
                || href.indexOf("javascript:") != -1;
        if (!absolute)
            if (prefix != '')
                href = prefix + '/' + href;
    }

    var children = [];
    for ( var key in node) {
        if (key.substring(0, 1) == '_')
            continue;
        children.push([ key, node[key] ]);
    }

    var submenu = null;
    if (children.length > 0) {
        submenu = $("<ul />");
        children = children.sort(function(a, b) {
            var an = a[1]._order;
            var bn = b[1]._order;
            if (an != bn) {
                return an < bn ? -1 : 1;
            } else {
                var ak = a[0];
                var bk = b[0];
                return (ak < bk) ? -1 : (ak > bk) ? 1 : 0;
            }
        });
        $.each(children, function(k, child) {
            var node = child[1];
            var sublist = _tconv(node, href);
            if (sublist != null)
                submenu.append(sublist);
        });

        if (submenu.children().length == 0)
            submenu = null;
        href = null;
    }

    /** Hide the empty groups */
    if (href == null && submenu == null)
        return null;

    var entry = $("<a />");
    entry.text(label);
    if (href != null)
        entry.attr("href", href);

    var menuItem = $("<li />");
    menuItem.append(entry);

    if (submenu != null)
        menuItem.append(submenu);

    return menuItem;
}

function skel_mkmenu() {
    var table = $('<table style="width:100%; border-bottom:solid; border-bottom-color:#EDF6FF;"><tr><td /></tr></table>');
    var div = $('tr td', table);

    var css = $('<link rel="stylesheet" type="text/css" media="screen" href="http://static.secca-project.com/lib2/superfish-1.4.8/css/superfish.css"/>');
    div.append(css);

    var menu = _tconv(skel_menu);
    menu = $(menu.children()[1]);
    menu.attr("id", "skel-menu");
    menu.attr("class", "sf-menu");
    menu.attr("style", "clear:left; margin:0px;");

    div.append(menu);
    return div;
}


/*
(function($) {
    var ajax = $.ajax;
    $.ajax = function(s) {
        var oldSuccess = s.success;
        s.success = function(data, textStatus, xhr) {
            var permissionData = eval("(" + xhr.responseText + ")");
            if(permissionData.havePermission != null) {
                if(permissionData.havePermission  == "no") {
                    alert("你没有[" + permissionData.permissionDesc + "]的权限!");
                    window.location = permissionData.gotoAddress;
                }
            }
            oldSuccess.call(data, textStatus, xhr);
        };

        var oldError = s.error;
        s.error = function(xhr, textStatus, errorThrown) {
            var permissionData = eval("(" + xhr.responseText + ")");
            if(permissionData.havePermission != null) {
                if(permissionData.havePermission  == "no") {
                    alert("你没有[" + permissionData.permissionDesc + "]的权限!");
                    window.location = permissionData.gotoAddress;
                }
            }
            oldError.call(xhr, textStatus, errorThrown);
        };

        ajax(s);
    };
})(jQuery);


$(document).ajaxSuccess(function(event, xhr, ajaxOptions) {
    var permissionData = eval("(" + xhr.responseText + ")");
    if(permissionData.havePermission != null) {
        if(permissionData.havePermission  == "no") {
            alert("你没有[" + permissionData.permissionDesc + "]的权限!");
            window.location = permissionData.gotoAddress;
        }
    }
    oldSuccess.call(data, textStatus, xhr);
});

$(document).ajaxError(function(event, xhr, ajaxOptions, thrownError) {
    var permissionData = eval("(" + xhr.responseText + ")");
    if(permissionData.havePermission != null) {
        if(permissionData.havePermission  == "no") {
            alert("你没有[" + permissionData.permissionDesc + "]的权限!");
            window.location = permissionData.gotoAddress;
        }
    }
    oldSuccess.call(data, textStatus, xhr);
});*/