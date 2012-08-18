(function($) {

    var autoOpen = function() {
        var href = location.href;

        var specId = href.indexOf("?id=") != -1 || href.indexOf("&id=") != -1;
        var specMode = href.indexOf("?MODE=") != -1 || href.indexOf("&MODE=") != -1;

        if (specId && specMode) {
            waitbox.show();
            PrimeFaces.ab({
                source : 'mainForm:viewButton',
                update : 'mainForm:toolbar editDialog:form',
                oncomplete : function(xhr, status, args) {
                    waitbox.hide();
                    editdlg.show();
                }
            });
        }
    };

    // $(document).ready(autoOpen);

})(jQuery);
