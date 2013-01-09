$("#issueMainForm\\:commentView").slideUp();

$("#issueMainForm\\:addComment").click(function() {
    $("#issueMainForm\\:commentView").slideDown();
    $(this).hide();
});

$("#issueMainForm\\:cancelComment").click(function() {
    $("#issueMainForm\\:commentView").slideUp();
    $("#issueMainForm\\:addComment").show();
});

function clearContent() {
    $("#issueMainForm\\:commentContent").val("");
}
