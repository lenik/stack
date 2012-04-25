function calcDigest() {
    var p = jQuery.sha1(jQuery("#editDialog\\:form\\:passPlain").val());
    jQuery("#editDialog\\:form\\:password").val(p);
}

function calcDigestConfirm() {
    var p = jQuery.sha1(jQuery("#editDialog\\:form\\:passPlainConfirm").val());
    jQuery("#editDialog\\:form\\:passwordConfirm").val(p);
}
