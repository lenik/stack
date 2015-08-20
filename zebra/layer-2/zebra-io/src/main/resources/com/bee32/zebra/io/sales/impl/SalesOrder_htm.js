(function($) {

    window.reloadDelivery = function() {
        location.href = "?#s-delivery";
    };

    $("#mkdelivery").click(function(e) {
        var form = $(e.target).parents("form");
        form.submit();
        $("#reloadhint").fadeIn();
    });

})(jQuery);