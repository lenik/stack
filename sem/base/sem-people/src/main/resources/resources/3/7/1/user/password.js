(function($) {

    function calcDigest() {
        var p = $.sha1($("#passPlain").val());

        jQuery("#editUserForm\\:password").val(p);
    }

    function calcDigestConfirm() {
        var p = $.sha1($("#passPlainConfirm").val());

        $("#editUserForm\\:passwordConfirm").val(p);
    }

})(jQuery);