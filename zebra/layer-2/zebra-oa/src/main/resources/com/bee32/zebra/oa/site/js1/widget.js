$(document).ready(function() {

    // jQuery UI
    // $("input[type=checkbox]").button();
    // $(".button").button();

    /**
     * ⇱ WIDGET: Toggle Button.
     * 
     * @example <div class="btn-group" data-toggle="buttons"> <label class="btn
     *          btn-default"> <input type="radio" value="f" name="gender">女</label>
     *          <label class="btn btn-default active"> <input type="radio"
     *          value="m" name="gender">男</label> </div>
     */
    $(".btn-toggle").click(function() {
        $(this).find(".btn").toggleClass("active");
        $(this).find(".btn").toggleClass("btn-default");

        if ($(this).find(".btn-primary").size() > 0)
            $(this).find(".btn").toggleClass("btn-primary");
        if ($(this).find(".btn-danger").size() > 0)
            $(this).find(".btn").toggleClass("btn-danger");
        if ($(this).find(".btn-success").size() > 0)
            $(this).find(".btn").toggleClass("btn-success");
        if ($(this).find(".btn-info").size() > 0)
            $(this).find(".btn").toggleClass("btn-info");
    });

    /**
     * ⇱ WIDGET: tagsinput workaround.
     * 
     * Fixed: tagsinput doesn't convert <select> well.
     */
    $("select[data-role=tagsinput-obj]").each(function() {
        var $select = $(this);
        $select.tagsinput({
            itemValue : "id",
            itemText : "label"
        });
        $("option", this).each(function() {
            var id = $(this).val();
            var label = $(this).text();
            $select.tagsinput("add", {
                id : id,
                label : label
            });
        });
    });

    /**
     * ⇱ WIDGET: File Upload Dialog.
     */
    $(".fileupload-dialog").each(function() {

        this.open = function() {
            $(this).dialog({
                autoOpen : true,
                title : "上传…",
                buttons : [ {
                    text : "确定",
                    click : function() {
                        $(this).dialog("close");
                    }
                }, {
                    text : "取消",
                    click : function() {
                        $(this).dialog("close");
                    }
                }, ],
                modal : true,
                width : "80%"
            });
        };

        var sendbtn = $(".fileupload", this);
        var progress = $('.progress', this);
        var alert_success = $(".alert-success", this);
        var message = $(".message", alert_success);
        var binds = $(this).attr("data-bind");
        var forform_q = $(this).attr("data-forform");
        var forform = $("#" + forform_q);

        // Initialize the jQuery File Upload widget:
        sendbtn.fileupload({
            dataType : "json",

            start : function(e, data) {
                var onstart = sendbtn.attr("onstart");
                if (onstart != null) {
                    var handler = new Function("e", onstart);
                    handler(e);
                }
                alert_success.fadeOut();
                progress.fadeIn();
            },

            done : function(e, data) {
                progress.fadeOut();
                alert_success.fadeIn();

                $.each(data.result.files, function(index, file) {
                    $(binds).val(file.name);
                    var uploadedNameRef = $("[data-role=uploaded-name]", forform);
                    uploadedNameRef.val(file.name);

                    message.html("");
                    message.append($("<p/>").text(file.name + " (" + file.size + " 字节)"));
                });

                var ondone = sendbtn.attr("ondone");
                if (ondone != null) {
                    var handler = new Function("e", "files", ondone);
                    var files = data.result.files;
                    handler(e, files[0]);
                }
            },

            progressall : function(e, data) {
                var percent = data.loaded / data.total * 100;
                percent = Math.floor(percent);
                var bar = $(".progress-bar", progress);
                bar.css('width', percent + '%');
                bar.text("已传输 " + data.loaded + " / " + data.total + " 字节 (" + percent + "%)");
            }
        });

    });

});

$(document).ready(function() {
    window.itabs = {};
    $(".itab").each(function() {
        var itab = this;
        var $itab = $(this);
        var itabId = $itab.attr("id");
        var edivId = itabId + "ed";
        var $ediv = $("#" + edivId);
        window.itabs[itabId] = itab;

        itab.reload = function() {
            var dt = itab.dataTable;
            dt.ajax.reload();
        };

        itab.canOpen = function(id) {
            var editurl = $itab.data("editurl");
            return editurl != null;
        };

        itab.open = function(id) {
            var editurl = $itab.data("editurl");
            if (editurl == null)
                return;

            var url = editurl.replace("ID", id == null ? "new" : id);
            var $iframe = $("iframe", $ediv);
            $iframe.attr("src", url);

            $ediv.dialog({
                autoOpen : true,
                modal : true,
                title : id == null ? "新建…" : "编辑...",
                buttons : [ {
                    text : "保存",
                    click : function() {
                        var $iframe = $("iframe", this);
                        var form = $("form", $iframe.contents());
                        form.submit();
                    }
                }, {
                    text : "关闭",
                    click : function() {
                        $(this).dialog("close");
                    }
                }, ],
                modal : true,
                width : "80%"
            }); // dialog()
        };

        $itab.bind("rowClick", function(e, row) {
            var data = row.data();
            if (data == null)
                return null;

            var id = data[0];
            if (id == undefined)
                return;

            itab.open(id);
        });

        $(".btn-reload", $itab).click(itab.reload);
        $(".btn-add", $itab).click(function() {
            itab.open(null);
        });

    });
});