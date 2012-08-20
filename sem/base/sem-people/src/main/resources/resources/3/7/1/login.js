function calcDigest() {
    var p = $.sha1($("#loginForm\\:passPlain").val());

    var c = $("#loginForm\\:challenge").val();
    var p2 = $.sha1(c + p + c);

    $("#loginForm\\:password").val(p2);
}

function handleLoginRequest(xhr, status, args) {
    if (args.validationFailed || !args.loggedIn) {
        // Do nothing.
    } else {
        var welcome = document.WEB_APP;
        if (welcome == '')
            welcome = '/';
        window.location.href = welcome;
    }
}