function AttendanceType() {
    this.name = 'default';
    this.value = '-';
    this.icon = 'default';
}
var selectedTool = null;

var currentDay = $("#contentForm\\:currentDay").val().trim();

$(".selection-tool").each(function() {
    $(this).bind('click', function() {
        var clickType = new AttendanceType();
        clickType.name = $(this).attr("title").trim();
        clickType.value = $(this).attr("alt").trim();
        clickType.icon = $(this).attr("src").trim();

        selectedTool = clickType;
        $("li").css("background-color", "");
        $(this).parent().css("background-color", "yellow");
        $(".day-right").css("cursor", "url(" + selectedTool.icon + "),default");
    });
});

$(".morningType").click(function() {
    if (selectedTool != null) {

        var hidden = $(this).parent().parent().children().first().children('input:hidden');
        $(this).attr('alt', selectedTool.value);
        $(this).attr('src', selectedTool.icon);
        $(this).attr('title', selectedTool.name);

        wrapAttendanceData(hidden, currentDay, selectedTool, 'morning');
    }

});

$(".afternoonType").click(function() {
    if (selectedTool != null) {

        var hidden = $(this).parent().parent().children().first().children('input:hidden');
        $(this).attr('alt', selectedTool.value);
        $(this).attr('src', selectedTool.icon);
        $(this).attr('title', selectedTool.name);

        wrapAttendanceData(hidden, currentDay, selectedTool, 'afternoon');
    }

});

$(".eveningType").click(function() {
    if (selectedTool != null) {

        var hidden = $(this).parent().parent().children().first().children('input:hidden');
        $(this).attr('alt', selectedTool.value);
        $(this).attr('src', selectedTool.icon);
        $(this).attr('title', selectedTool.name);

        wrapAttendanceData(hidden, currentDay, selectedTool, 'evening');
    }

});

function wrapAttendanceData(hidden, selectedDay, clickType, time) {
    var data = hidden.val().trim();
    var index = (selectedDay - 1) * 10;
    if (time == 'morning')
        index += 2;
    if (time == 'afternoon')
        index += 4;
    if (time == 'evening')
        index += 6;

    data = data.substring(0, index) + clickType.value + data.substring(index + 1);
    hidden.val(data);
}