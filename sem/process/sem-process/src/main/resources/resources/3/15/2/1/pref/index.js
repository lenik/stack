var table1Tools = {
    _view : {
        name : "查看",
        icon : "etool16/insp_sbook.gif",
        href : "content.htm?id=$id"
    },

    edit : {
        name : "编辑",
        icon : "etool16/editor_area.gif",
        callback : function(node, aData) {
            var id = aData[0];
            // dialog
            // ajax & save
        }
    },

    del : {
        name : "删除",
        icon : "etool16/delete_edit.gif",
        callback : function(node, aData) {
            var id = aData[0];
            var info = $(node).parents("table").attr('title');
            if (!window.confirm("您确定要删除该 " + info + " 记录吗？"))
                return;
            // ajax & remove
        }
    }
};

$(document).ready(function() {
    var oTable = $("#table1").dataTable_SEM();
});