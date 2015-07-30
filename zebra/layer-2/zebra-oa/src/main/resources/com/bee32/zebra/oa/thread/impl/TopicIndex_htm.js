(function($) {

    function redraw() {
        var data;
        var monthValSum = $("#monthvalsum .data").text();
        data = util.flot.parse("xy-map", monthValSum, -12);
        util.flot.plotXYBarCurve($("#monthvalsum .view"), data);

        var catDistrib = $("#catdist .data").text();
        data = util.flot.parse("label-data*", catDistrib);
        util.flot.plotXYPie($("#catdist .view"), data);

        var phaseDistrib = $("#phasedist .data").text();
        data = util.flot.parse("label-data*", phaseDistrib);
        util.flot.plotXYPie($("#phasedist .view"), data);
    }

    $(document).ready(function() {
        redraw();
    });
    $(window).resize(function() {
        redraw();
    });

})(jQuery);
