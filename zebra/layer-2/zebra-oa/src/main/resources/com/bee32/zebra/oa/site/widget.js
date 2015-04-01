$(document).ready(function() {

    // jQuery UI
    // $("input[type=checkbox]").button();
    // $(".button").button();

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

    // Fixed: tagsinput doesn't convert <select> well.
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