var table1Tools = $.extend({}, SEM.entityTools, {

    verify : {
        name : "审核",
        icon : "obj16/taskmrk_tsk.gif",
        callback : function(node, aData) {
            var id = aData[0];
            $("#selectedId").val(id);
            $("#verifyDialog").dialog('open');
        }
    }

});

$("#verifyDialog").dialog({
    autoOpen : false,
    modal : true,
    width : 500,
    height : 320,
    buttons : {
        '取消' : function() {
            $(this).dialog('close');
        },
        '拒绝' : function() {
            doVerify(false);
        },
        '允许' : function() {
            doVerify(true);
        }
    }
});

function doVerify(allowed) {
    var id = $("#selectedId").val();
    var rejectedReason = $("#rejectedReason").val();

    $.ajax({
        url : "verify.do",
        type : "POST",
        dataType : "json",
        data : {
            'id' : id,
            'allowed' : allowed,
            'rejectedReason' : rejectedReason
        },
        success : function(data) {
            var dtab = $("#table1").dataTable();
            dtab.fnReloadAjax("data.do");
            $("#verifyDialog").dialog('close');
        },
        error : function(xhr, errmsg) {
            alert("向远程发送命令时失败：" + errmsg);
            $("#verifyDialog").dialog('close');
        }
    });
    return true;
}
