(function($) {

    $.fn.removeText = function() {
        var text = this.text();
        this.text("");
        return text;
    };

    /**
     * Animate all spinnable spans inside the node.
     */
    $.fn.faSpin = function() {
        $("span", this).addClass("fa-spin");
    };

})(jQuery);
