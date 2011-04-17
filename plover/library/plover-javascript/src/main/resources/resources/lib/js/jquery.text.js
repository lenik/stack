(function($) {

    $(document).ready(function() {

        $("ol").each(function() {
            var format = this.attr('format');
            if (format != null) {
                var items = $("li", this);
                for ( var i = 0; i < items.length; i++) {
                    var prefix = format.replace('#', (i + 1));

                    // ...
                }
            }
        });

    });

})(jQuery);
