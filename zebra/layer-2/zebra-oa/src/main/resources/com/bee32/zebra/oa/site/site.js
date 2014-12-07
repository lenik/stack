$(document).ready(function() {

    var dt = $('.dataTable').DataTable({
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
        }
    });

    dt.rows().on('click', 'tr', function() {
        var row = dt.row(this); // could be the header.
        var cell0 = dt.cell(this, 0);
        var id = cell0.data();
        if (id == undefined)
            return;
        // load the selection
        var outer = $("#infosel-data");
        var data = $("#data-" + id);
        outer.html(data.html());
    });

});