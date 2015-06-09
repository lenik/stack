(function($) {

    function redraw() {
        var data;
        var monthTrends = $("#monthcreation .data").text();
        data = util.flot.parse("xy-map", monthTrends, -12);
        util.flot.plotXYBarCurve($("#monthcreation .view"), data);

        var userDistrib = $("#userdist .data").text();
        data = util.flot.parse("label-data*", userDistrib, 9);
        util.flot.plotXYPie($("#userdist .view"), data);
    }

    $(document).ready(function() {
        redraw();
    });
    $(window).resize(function() {
        redraw();
    });

})(jQuery);
