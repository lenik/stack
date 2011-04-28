var table1Tools = $.extend({}, SEM.entityTools, {

    verify : {
        name : "审核",
        icon : "obj16/taskmrk_tsk.gif",
        callback : function(node, aData) {
            var id = aData[0];
            $("#verifyDialog").dialog('open');
        }
    }

});

$("#verifyDialog").dialog({
    autoOpen : false,
    modal : true,
    width : 320,
    height : 240,
    buttons : {
        '允许' : function() {
            doVerify(true);
        },
        '拒绝' : function() {
            doVerify(false);
        },
        '取消' : function() {
            $(this).dialog('close');
        }
    }
});

function doVerify(allowed) {
    $.ajax({
        dataType : "json",
        type : "POST",
        url : "verify.htm",
        data : {
            'allowed' : allowed,
            'rejectedMessage' : $("#rejectedMessage").val()
        },
        success : function(data) {
            var table1 = $("#table1");
            var model = table1.dataTable();
            model.fnReloadAjax("data.htm");
            alert(data.message);
            $(this).dialog('close');
        },
        error : function(data) {
            alert(arguments[1]);
            alert("e-dat: "+data);
        }
    });
    alert("do-verif2y");
    return true;
}
