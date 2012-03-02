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

    var xform = $("#xform")[0];
    if (xform.type == null)
        $(xform).append("<input type='hidden' name='type' />");
    if (xform.pkey == null)
        $(xform).append("<input type='hidden' name='pkey' />");
    // Cuz a lot of JSF stuff in the form, so get rid of it by post form.
    // xform.method = "get";
    xform.action = document.WEB_APP + "/3/12/3/4/xref/";
    xform.type.value = entityTypeAbbr;
    xform.pkey.value = ids.text();
    xform.submit();
}
