function calcDigestOld() {
    var p = $.sha1($("#modifyPasswordForm\\:oldPassPlain").val());

    jQuery("#modifyPasswordForm\\:oldPass").val(p);
}

function calcDigestNew() {
    var p = $.sha1($("#modifyPasswordForm\\:newPassPlain").val());

    jQuery("#modifyPasswordForm\\:newPass").val(p);
}

function calcDigestConfirm() {
    var p = $.sha1($("#modifyPasswordForm\\:confirmPassPlain").val());

    jQuery("#modifyPasswordForm\\:confirmPass").val(p);
}
