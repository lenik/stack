$(document).ready(function() {

    $(".zu-calendar tbody td").click(function() {
        var $td = $(this);
        var $table = $td.parents("table");

        var year = $table.attr("data-year");
        var month = $table.attr("data-month");
        var day = $td.attr("data-day");

        if ($td.hasClass("log")) {
            var divId = "log-" + day;
            var div = $(divId);
            div.toggle();
        }
    });

});