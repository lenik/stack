(function($) {
    $("#aQuit").click(function() {
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
    });

    $(document).ready(function() {
        $.ajaxSetup({
            cache : false
        });
        $("ul.sf-menu").superfish();

        var logo = $(".logo-container");
        logo.mouseover(function() {
            logo.hide().delay(5000).fadeIn();
        });
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
    doc.write("<div align='center'>" + node.html() + "</div>\n");
    doc.write("</body></html>\n");
    doc.close();

    // Remove all buttons: should query with jQuery.
    var buttons = doc.body.querySelectorAll('button');
    for ( var i = 0; i < buttons.length; i++) {
        var button = buttons[i];
        button.parentElement.removeChild(button);
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
