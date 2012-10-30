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

    var attendanceData = hidden.val().trim();

    var index = attendanceData.indexOf(selectedDay);
    var tmpstring1 = selectedDay < 10 ? attendanceData.substring(index, index + 7) : attendanceData
            .substring(index, index + 8);
    var index2 = tmpstring1.indexOf(":");
    var tmpstring2 = tmpstring1.substring(index2 + 1);
    var prifix = tmpstring1.substring(0, index2 + 1);
    var morning = tmpstring2.substring(0, 1);
    var afternoon = tmpstring2.substring(2, 3);
    var evening = tmpstring2.substring(4);

    if (time == 'morning')
        morning = clickType.value;
    if (time == 'afternoon')
        afternoon = clickType.value;
    if (time == 'evening')
        evening = clickType.value;

    var toreplace = prifix + morning + "," + afternoon + "," + evening;
    var replaced = attendanceData.replace(tmpstring1, toreplace);

    hidden.val(replaced);
}