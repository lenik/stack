function openTab(url) {
    var form = document.createElement("form");
    form.action = url;
    form.target = "_blank";
    form.submit();
}

function openExtensionDetail(name, id) {
    var url = "https://chrome.google.com/webstore/detail/" + name + "/" + id + "?hl=zh-cn";
    openTab(url);
}

(function($) {

    window.checkChromeExtension = function(retvar, id, sig, tag) {
        if (tag == null)
            tag = "img";

        eval(retvar + " = null; ");

        $.ajax("chrome-extension://" + id + "/" + sig).done(function() {
            eval(retvar + " = 1");
        }).fail(function() {
            eval(retvar + " = 0");
        });
    };

    window.suggestViewer = function(filename) {
        var lastDot = filename.lastIndexOf('.');
        if (lastDot == -1)
            return true;
        var ext = filename.substring(lastDot + 1);
        switch (ext) {
        case "doc":
        case "docx":
        case "xls":
        case "xlsx":
            if (window.chrome != null) {
                var ver = parseInt(window.navigator.userAgent.match(/Chrome\/(\d+)/)[1]);
                if (ver < 28) {
                    $('#upgradeChromeDialog').dialog({
                        modal : true,
                        buttons : [ {
                            text : '是的',
                            click : function() {
                                $(this).dialog("close");
                                openTab("http://www.google.com/intl/zh-cn/chrome/browser/");
                            }
                        }, {
                            text : '取消',
                            click : function() {
                                $(this).dialog("close");
                            }
                        } ]
                    });
                    return false;
                }

                if (!officeViewer) {
                    $('#installOfficeViewerDialog').dialog(
                            {
                                modal : true,
                                buttons : [
                                        {
                                            text : '是的',
                                            click : function() {
                                                $(this).dialog("close");
                                                openExtensionDetail("chrome-office-viewer-beta",
                                                        "gbkeegbaiigmenfmjfclcdgdpimamgkj");
                                            }
                                        }, {
                                            text : '取消',
                                            click : function() {
                                                $(this).dialog("close");
                                            }
                                        } ]
                            });
                    return false;
                }
            }
            break;
        }
        return true;
    };

})(j$100);

var officeViewer;
checkChromeExtension("officeViewer", "gbkeegbaiigmenfmjfclcdgdpimamgkj", "views/qowt.html");
