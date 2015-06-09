(function($) {

    /**
     * Run this function just before close the child frame to reload the parent.
     * 
     * TODO change to event-based handler.
     */
    window.iframeDone = function() {
        // Check if this is a child frame.
        if (window.frameElement != null) {
            var parentItabId = $(window.frameElement).attr("parentItabId");
            var parentItab = parent.itabs[parentItabId];
            if (parentItab != null)
                parentItab.reload();
        }
    };

})(jQuery);
