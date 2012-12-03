(function($) {

    function sessionQuit()  {
        $.ajax({
            "dataType" : "json",
            "type" : "POST",
            "url" : document.WEB_APP + "/logout.do",
            "success" : function(data) {
                if (data.result.toUpperCase() == "SUCCESS") {
                    alert("成功退出!");
                    location.href = document.WEB_APP;
                } else {
                    alert(data.message);
                }
            },
            "error" : function() {
                alert(arguments[1]);
            }
        });
        return false;
    }

    window.setupSessionTimeout = function() {
        var sessionController = $(".session-controller");
        sessionController.css("display", "none");

        var sessionMesg = $("#sessionMesg");
        var timeoutInterval = document.sessionTimeout * 1000;
        var sessionTimeout = new Date().getTime() + timeoutInterval;

        if (window.sessionAlertHandle != null)
            window.clearInterval(window.sessionAlertHandle);

        window.setTimeout(function() {
                sessionController.css("display", "block");

                window.sessionAlertHandle = window.setInterval(function() {
                    var remaining = sessionTimeout - new Date();
                    sessionMesg.text( Math.floor(remaining / 1000) );

                    var visibility = sessionMesg.css("visibility") == "visible" ? "hidden" : "visible";
                    sessionMesg.css("visibility", visibility);

                    if (remaining <= 0) {
                        window.clearInterval(window.sessionAlertHandle);
                        sessionMesg.css("display", "none");
                        // sessionController.css("display", "none");
                        sessionController.text("由于长时间没有操作，已自动注销。");
                        sessionQuit();
                    }
                }, 250);
            }, timeoutInterval - 10000);
    }

    $("#aQuit").click(sessionQuit);

    $(document).ready(function() {
        $.ajaxSetup({
            cache : false
        });
        $("ul.sf-menu").superfish();

        var logo = $(".logo-container");
        logo.mouseover(function() {
            logo.hide().delay(5000).fadeIn();
        });

        window.setupSessionTimeout();

        var dataTable = $("#mainForm\\:dataTable");
        if (dataTable.length != 0) {
            var exportMenu = $("#mainForm\\:exportMenu");
            dataTable[0].oncontextmenu = function(event) {
                exportMenu.css({
                    left: event.x+"px",
                    top: window.scrollY + event.y + "px",
                });
                exportMenu.show();
            };
            exportMenu.mouseleave(function() {
                exportMenu.hide();
            });
            dataTable.click(function() {
                exportMenu.hide();
            });
        }
    });

})(jQuery);

function showXrefs() {
    var entityTypeInput = $("#mainForm\\:entityType")[0];
    var entityTypeAbbr = entityTypeInput.value;

    var dataTable = $("#mainForm\\:dataTable");
    var ids = dataTable.find(".ui-state-highlight .entity-id");

    if (ids.length == 0)
        return;

    var xrefLoc = "3/12/3/4/xref/?type=" + encodeURI(entityTypeAbbr + "&pkey=" + ids.text());
    location.href = document.WEB_APP + "/" + xrefLoc;
}

function bcastSystem(evt, data) {
    var div = $('#sysmsg');
    div.text(data);
    sysbar.show();
}

function bcastUserMail(evt, data) {
}

function print(node, parent) {
    var title = document.title;
    title = title.replace(/管理/g, '');

    var win = window.open();
    var doc = win.document;
    // var elements;
    // typeof(jQuery-node) == 'function'.
    if (typeof (node) == 'string') {
        var q = node;
        q = q.replace(/:/g, '\\:');
        var outer = $;
        if (parent != null)
            outer = $(parent);
        var elements = outer.find(q);
        if (elements.length == 0) {
            alert("Nothing to print, selector: " + q);
            return;
        }
        node = $(elements[0]);
    } else if (typeof (node) == 'object') {
        var element = node;
        node = $(element);
    }

    doc.open();
    doc.write("<html><head>");
    doc.write("<title>" + title + "</title>\n");

    // CSS here...
    var links = $(document.head).find('link');
    for ( var i = 0; i < links.length; i++) {
        var link = links[i];
        // $(link).html() doesn't work.
        doc.write(link.outerHTML);
    }

    doc.write("</head><body class='print'>");
    doc.write("<div align='center' class='print-header'><h1>" + title + "</h1></div>\n");
    var centered = false;
    if (centered)
        doc.write("<div align='center'>" + node.html() + "</div>\n");
    else
        doc.write(node.html());
    doc.write("</body></html>\n");
    doc.close();

    removeElements(doc.body, 'button');
    removeElements(doc.body, '[aria-hidden="true"]');
    // removeElements(doc.body, '.ui-tabs-nav');
    var removeDeactives = false;
    if (removeDeactives) {
        var actives = $(doc.body).find('.ui-state-active');
        for ( var i = 0; i < actives.length; i++) {
            var parent = actives[i].parentElement;
            var deactives = $(parent).children(":not(.ui-state-active')");
            removeElements(deactives);
        }
    }

    win.print();

    if (typeof (win.onafterprint) != 'undefined') {
        win.onafterprint = function() {
            win.close();
        }
    } else {
        // alert("请在打印完成后点击确定。");
        // win.close();
    }
}

function removeElements(outer, q) {
    // var elements = outer.querySelectorAll(q);
    var elements = q == null ? outer : $(outer).find(q);
    for ( var i = 0; i < elements.length; i++) {
        var e = elements[i];
        e.parentElement.removeChild(e);
    }
}

function _open(href) {
     var newWindow = window.open(href, '_blank');
     newWindow.focus();
     return false;
}
