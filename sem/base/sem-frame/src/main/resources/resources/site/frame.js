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
