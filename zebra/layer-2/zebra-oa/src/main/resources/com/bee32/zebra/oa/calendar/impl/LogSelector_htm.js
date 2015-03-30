$(document).ready(function() {

    // $(".toolbar").buttonset();
    // $("input[type=checkbox]").button();
    // $("#showall").button();
    $("#showall").click(function() {
        $("#showall").toggleClass("on");
        if ($("#showall").hasClass("on")) {
            $(".zu-calendar tbody td.log").addClass("selected");
            $(".zu-leg").show();
        } else {
            $(".zu-calendar tbody td.log").removeClass("selected");
            $(".zu-leg").hide();
        }
    });

    $(".zu-calendar tbody td").click(function() {
        var $td = $(this);
        var $table = $td.parents("table");

        var year = $table.attr("data-year");
        var month = $table.attr("data-month");
        var day = $td.attr("data-day");

        if ($td.hasClass("log")) {
            $(".zu-calendar tbody td").removeClass("selected");
            $td.addClass("selected");

            $(".zu-leg").hide();
            var divQ = "#log-" + day;
            var div = $(divQ);
            div.show();
        }
    });

});