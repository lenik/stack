var Features = {
    chosen : 1,
    chosen_dw : 20,
    icheck : 0,
    zzz : 0
};

$(document).ready(function() {

    if (Features.chosen) {
        var selects = $("select").chosen({
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

    $(".zu-pickcmd").click(function() {
        var url = $(this).attr("data-url");
        var title = $(this).attr("data-title");
        var $iframe = $("#picker1 iframe");
        $iframe.attr("src", url);

        var inputs = $(this).prevAll("input");
        selection = null;

        $("#picker1").dialog({
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

    var $dt = $('.dataTable');
    if ($dt != null) {
        var dataUrl = $dt.attr("data-url");

        var dt = window.dt = $dt.DataTable({
            columnDefs : [ {
                targets : "detail",
                visible : false
            }, {
                targets : "no-search",
                searchable : false
            }, {
                targets : "no-sort",
                orderable : false
            } ],
            colVis : {
                buttonText : "列",
                label : function(index, title, th) {
                    return (index + 1) + '. ' + title;
                },
                showAll : '显示全部'
            },
            // displayStart : 10,
            dom : 'C<"clear">lfrtip',
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
            },
            // lengthMenu : [ [ 10, 20, 50, 100, ], [ 10, 20, 50, 100 ] ],
            paginationType : "bootstrap",
            responsive : true,
            tableTools : {
                sSwfPath : _js_ + "datatables/extensions/TableTools/swf/copy_csv_xls_pdf.swf"
            },
            ajax : dataUrl == null ? null : {
                url : dataUrl,
                dataSrc : "tbody"
            }
        });

        dt.rows().on('click', 'tr', function(e) {
            var modexs = $("#zp-right-col").css("width") == "0px";

            var row = dt.row(this); // could be the header.
            var id = row.data()[0];
            if (id == undefined)
                return;

            if ($(this).hasClass("selected"))
                $(this).removeClass("selected");
            else {
                dt.$("tr.selected").removeClass("selected");
                $(this).addClass("selected");
            }

            if (parent != null) {
                var cmap = {};
                var headers = dt.columns().header();
                for (i = 0; i < headers.length; i++) {
                    var dataField = $(headers[i]).attr("data-field");
                    cmap[dataField] = i;
                }
                var labelFields = [ "label", "subject", "fullName" ];
                var labelFieldIndex = null;
                for (i = 0; i < labelFields.length; i++) {
                    var columnIndex = cmap[labelFields[i]];
                    if (columnIndex != null) {
                        labelFieldIndex = columnIndex;
                        break;
                    }
                }
                var label = null;
                if (labelFieldIndex != null) {
                    label = row.data()[labelFieldIndex];
                    label = label.trimRight();
                    if (label.substr(0, 1) == "<" && label.substr(-1) == ">") {
                        label = label.replace(/^<.*?>/, '');
                        label = label.replace(/<.*?>$/, '');
                    }
                }

                parent.selection = {
                    id : id,
                    label : label
                };
            }

            if (modexs) {
                location.href = id + "/";
                return;
            }

            // load the selection
            var seldiv = $("#zp-infosel-data");
            var seledit = $("#zp-infosel-edit");
            var data = $("#data-" + id);
            var lastid = seldiv.attr("selid");

            if (id != lastid) {
                seldiv.show();
                seldiv.attr("selid", id);

                if (data.length > 0) {
                    seldiv.html(data.html());
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
        });
    } // dt != null

});
