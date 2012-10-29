function AttendanceType() {
    this.icon = 'default';
    this.value = '-';
    this.name = 'notavailable';
}

$(".m-types-c").each(function() {
    $(this).bind('click', function() {

        var selectedday = $("#currentDay").text().trim();

        var clickType = new AttendanceType();
        clickType.value = $(this).attr('alt').trim();
        clickType.icon = $(this).attr('src').trim();
        clickType.name = $(this).attr('title').trim();

        $(".m-types-c").css("border", "0px");
        $(this).css("border", "2px solid red");

        wrapAttendanceData(selectedday, clickType, 'morning');
    });
});

$(".a-types-c").each(function() {
    $(this).bind('click', function() {

        var selectedday = $("#currentDay").text().trim();

        var clickType = new AttendanceType();
        clickType.value = $(this).attr('alt').trim();
        clickType.icon = $(this).attr('src').trim();
        clickType.name = $(this).attr('title').trim();

        $(".a-types-c").css("border", "0px");
        $(this).css("border", "2px solid red");

        wrapAttendanceData(selectedday, clickType, 'afternoon');
    });
});

$(".e-types-c").each(function() {
    $(this).bind('click', function() {

        var selectedday = $("#currentDay").text().trim();

        var clickType = new AttendanceType();
        clickType.value = $(this).attr('alt').trim();
        clickType.icon = $(this).attr('src').trim();
        clickType.name = $(this).attr('title').trim();

        $(".e-types-c").css("border", "0px");
        $(this).css("border", "2px solid red");

        wrapAttendanceData(selectedday, clickType, 'evening');
    });
});

function wrapAttendanceData(selectedDay, clickType, time) {
    var hidden = $("#editDialog\\:form\\:attendanceData");
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