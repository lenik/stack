$(document).ready(function() {

    var $dt = $('.dataTable');
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
            search : '查找: ',
            zeroRecords : '没有找到相关的记录。'
        },
        // lengthMenu : [ [ 10, 20, 50, 100, ], [ 10, 20, 50, 100 ] ],
        paginationType : "bootstrap",
        responsive : true,
        tableTools : {
            sSwfPath : _js_ + "datatables/extensions/TableTools/swf/copy_csv_xls_pdf.swf"
        },
        ajax : dataUrl == null ? null : {
            url : "?view:=json",
            dataSrc : ""
        }
    });

    dt.rows().on('click', 'tr', function(e) {
        var row = dt.row(this); // could be the header.
        var id = row.data()[0];
        if (id == undefined)
            return;

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

    $("body").click(function() {
        var seldiv = $("#zp-infosel-data");
        // seldiv.toggle();
    });
    
});