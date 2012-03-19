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

function print(node) {
    var win = window.open();
    var doc = win.document;

    // typeof(jQuery-node) == 'function'.
    if (typeof (node) == 'string') {
        node = $(node);
    } else if (typeof (node) == 'object') {
        node = $(node);
    }

    doc.open();
    doc.write("<html><head>");
    doc.write("<title>" + document.title + "</title>");

    // CSS here...
    var links = $(document.head).find('link');
    for ( var i = 0; i < links.length; i++) {
        var link = $(links[i]).html();
        // doc.write(link);
    }

    doc.write("</head><body>");
    doc.write(node.html());
    doc.write("</body></html>");
    win.print();
    win.close();
}
