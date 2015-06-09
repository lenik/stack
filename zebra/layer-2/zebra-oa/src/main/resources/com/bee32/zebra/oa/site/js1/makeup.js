var Features = {
    chosen : 1,
    chosen_dw : 20,
    icheck : 0,
    zzz : 0
};

$(document).ready(function() {

    if (Features.chosen) {
        var selects = $("select:not([multiple])").chosen({
            inherit_select_classes : true,
            // width : "30%",
            disable_search : true,
            search_contains : true,
            no_results_text : "没找到！"
        });
        if (Features.chosen_dw != 0)
            selects.parent().find('.chosen-container').each(function() {
                var w = $(this).css("width");
                if (w != null && w.substr(-2) == 'px') {
                    w = Features.chosen_dw + 1 * w.substr(0, w.length - 2);
                    $(this).css("width", w + "px")
                }
            });
    }

    if (Features.icheck) {
        $("input").iCheck({
            checkboxClass : "icheckbox_flat-blue",
            radioClass : "iradio_square"
        });
    }

    $(".dialog").dialog({
        autoOpen : false
    });

    $(".zu-pickcmd").click(function() {
        var url = $(this).attr("data-url");
        var title = $(this).attr("data-title");
        var $iframe = $("#picker1 iframe");
        $iframe.attr("src", url);

        var inputs = $(this).prevAll("input");
        selection = null;

        $("#picker1").dialog({
            autoOpen : true,
            title : title,
            buttons : [ {
                text : "确定",
                click : function() {
                    if (selection != null)
                        for ( var i = 0; i < inputs.length; i++) {
                            var $input = $(inputs[i]);
                            var name = $input.attr("name");
                            var property = name.replace(/^.*\./, '');
                            if (selection[property] != null)
                                $input.attr("value", selection[property]);
                        }
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
    });

    var $table = $("table.dataTable");
    if ($table.length > 0) {
        var config = {
            // displayStart : 10,

            // lengthMenu : [ [ 10, 20, 50, 100, ], [ 10, 20, 50, 100 ] ],
            paginationType : "bootstrap",
            responsive : true,

            language : {
                emptyTable : "没有相关的记录。",
                info : '当前页 _PAGE_ / 共 _PAGES_ 页。',
                lengthMenu : '每页显示 _MENU_ 条记录',
                loadingRecords : "下载数据中…",
                search : '查找: ',
                zeroRecords : '没有找到相关的记录。',
                paginate : {
                    first : "开始",
                    previous : "前一页",
                    next : "后一页",
                    last : "结束"
                }
            }
        };

        config.autoWidth = false;

        var dataUrl = $table.attr("data-url");
        config.ajax = dataUrl == null ? null : {
            url : dataUrl,
            dataSrc : "tbody"
        };

        var dom = $table.attr("dom");
        if (dom == null)
            dom = 'C<"clear">lfrtip';
        config.dom = dom;

        config.columnDefs = [ {
            targets : "detail",
            visible : false
        }, {
            targets : "no-search",
            searchable : false
        }, {
            targets : "no-sort",
            orderable : false
        } ];

        if (!$table.attr("no-colvis"))
            config.colVis = {
                buttonText : "列",
                label : function(index, title, th) {
                    return (index + 1) + '. ' + title;
                },
                showAll : '显示全部'
            };

        if ($table.attr("no-paginate"))
            config.paginate = false;

        if (!$table.attr("no-tableTools"))
            config.tableTools = {
                sSwfPath : _js_ + "datatables/extensions/TableTools/swf/copy_csv_xls_pdf.swf"
            };

        config.order = [ [ 0, "desc" ] ];

        var itab = $table.parent()[0];
        var dt = itab.dataTable = $table.DataTable(config);

        dt.rows().on('click', 'tr', function(e) {
            var tr = $(this);
            var modexs = $("#zp-right-col").css("width") == "0px";

            var row = dt.row(this); // could be the header.
            var data = row.data();
            if (data == null)
                return null;

            var id = data[0];
            if (id == undefined)
                return;

            if ($(this).hasClass("selected"))
                $(this).removeClass("selected");
            else {
                dt.$("tr.selected").removeClass("selected");
                $(this).addClass("selected");
            }

            if (parent != window) {
                // assert parent.frameElement != null;
                parent.selection = util.DT.getIdLabel(row);
                return;
            }

            var formatChild = itab.formatChild;
            if (formatChild != null) {
                var trs = $("tr", tr.parent());
                for (i = 0; i < trs.length; i++) {
                    var r = dt.row(trs[i]);
                    if (r.child.isShown()) {
                        r.child.hide();
                        tr.removeClass("shown");
                    }
                }

                var obj = util.DT.toObject(row);

                var html = formatChild(obj);
                if (html != null) {
                    row.child(html).show();
                    tr.addClass("shown");
                }
            }

            if (!itab.canOpen()) {
                if (modexs) {
                    location.href = id + "/";
                    return;
                }

                // load the selection
                var xdata = $("#data-" + id);
                var seldiv = $("#zp-infosel-data");
                var seledit = $("#zp-infosel-edit");
                var lastid = seldiv.attr("selid");

                if (id != lastid) {
                    seldiv.show();
                    seldiv.attr("selid", id);

                    if (xdata.length > 0) {
                        seldiv.html(xdata.html());
                    } else {
                        // load on demand, or create from the table...
                        seldiv.html("Loading...");
                    }
                    seledit.attr("href", id + "/");
                } else {
                    // click again on the same row.
                    // location.href = id + "/";
                }
                // e.cancel = true;
                return;
            }

            $(itab).trigger("rowClick", [ row ]);
        });
    } // dt != null

    $("a.helpdoc-switcher").click(function() {
        var $doc = $(this).nextAll(".helpdoc");
        $doc.fadeToggle();
        $(this).toggleClass("active");
    });

    $("#zp-qrchere").qrcode({
        size : 64,
        text : location.href
    });

});
