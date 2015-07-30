function previewAvatar(file) {
    var $avatar = $("#avatar");
    $avatar.attr("src", file.url);
    $avatar.attr("name", file.name);
    $("#avatar-div").fadeIn();
}

function saveAvatar() {
    alert('saved');
}