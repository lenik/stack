(function($) {
    if ($.fn.button.noConflict != null) {
        var button_b = $.fn.button.noConflict();
        $.fn.button_b = button_b;
    }
})(jQuery);

// TODO tagsinput lost values after form reset.
function resetInputs() {
    $("select[data-role=tagsinput]").tagsinput();
}
