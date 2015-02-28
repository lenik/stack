function showUploadDialog() {

    $("#uploadDialog").dialog({
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

}

$(document).ready(function() {

    // Initialize the jQuery File Upload widget:
    $('#fileupload').fileupload({
        dataType : "json",

        done : function(e, data) {
            var alertDone = $("#alert-done");
            alertDone.fadeIn();
            var message = $(".message", alertDone);
            $.each(data.result.files, function(index, file) {
                message.html("");
                message.append($("<p/>").text(file.name + " (" + file.size + " 字节)"));
                $("#incoming").val(file.name);
            });
        },

        progressall : function(e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css('width', progress + '%');
        }

    });

});