function SelectedDay() {
    this.day = 0;
}

function AttendanceType() {
    this.icon = 'default';
    this.value = '-';
    this.name = 'notavailable';
}

selectStatus = 0;

var tempDay = new SelectedDay();

$(".calendarView-batch-available").click(function() {
    var clickDay = new SelectedDay();
    clickDay.day = $(this).children(".calendar-view-day").text().trim();

    if (selectStatus == 0) {
        tempDay = clickDay;
        $(this).css("background-color", "yellow");
        selectStatus = 1;
    } else if (selectStatus == 1) {
        if (clickDay.day == tempDay.day) {
            $(this).css("background-color", "#D0FFD0");
            selectStatus = 0;
        }
        if (clickDay.day != tempDay.day) {
            $("#batchEditView" + tempDay.day).css("background-color", "#D0FFD0");
            tempDay = clickDay;
            $(this).css("background-color", "yellow");
        }
    }

    var morning = new AttendanceType();
    morning.icon = $(this).children(".a-morning-i").attr('src').trim();
    morning.value = $(this).children(".a-morning-i").attr('alt').trim();
    morning.name = $(this).children(".a-morning-i").attr('title').trim();

    var afternoon = new AttendanceType();
    afternoon.icon = $(this).children(".a-afternoon-i").attr('src').trim();
    afternoon.value = $(this).children(".a-afternoon-i").attr('alt').trim();
    afternoon.name = $(this).children(".a-afternoon-i").attr('title').trim();

    var evening = new AttendanceType();
    evening.icon = $(this).children(".a-evening-i").attr('src').trim();
    evening.value = $(this).children(".a-evening-i").attr('alt').trim();
    evening.name = $(this).children(".a-evening-i").attr('title').trim();

    // $(".a-m-icon").text(mt.icon);
    $(".a-m-icon-i").attr('src', morning.icon);
    $(".a-m-value").text(morning.value);
    $(".a-m-name").text(morning.name);
    // $(".a-a-icon").text(at.icon);
    $(".a-a-icon-i").attr('src', afternoon.icon);
    $(".a-a-value").text(afternoon.value);
    $(".a-a-name").text(afternoon.name);
    // $(".a-e-icon").text(et.icon);
    $(".a-e-icon-i").attr('src', evening.icon);
    $(".a-e-value").text(evening.value);
    $(".a-e-name").text(evening.name);

}

);

// TODO 加入没有选中日期的判断
$(".a-m-types").children(".a-m-types-c").each(
        function() {

            $(this).bind(
                    "click",
                    function() {
                        var clickType = new AttendanceType();
                        clickType.value = $(this).attr('alt').trim();
                        clickType.icon = $(this).attr('src').trim();
                        clickType.name = $(this).attr('title').trim();

                        var selectedday = tempDay.day;

                        if (selectedday != 0 && selectStatus == 1) {
                            $("#batchEditView" + selectedday).children(".a-morning-i").attr('src',
                                    clickType.icon).attr('alt', clickType.value);
                            // $(".a-m-icon").text(clicktype.icon);
                            $(".a-m-icon-i").attr('src', clickType.icon);
                            $(".a-m-name").text(clickType.name);

                            var attendanceData = $("#monthView\\:editingAttendanceData").val()
                                    .trim();
                            var index = attendanceData.indexOf(selectedday);
                            // 1:A,A,A like
                            var tmpstring1 = selectedday < 10 ? attendanceData.substring(index,
                                    index + 7) : attendanceData.substring(index, index + 8);
                            var index2 = tmpstring1.indexOf(":");
                            var tmpstring2 = tmpstring1.substring(index2 + 1);
                            var prifix = tmpstring1.substring(0, index2 + 1);
                            // var morning = tmpstring2.substring(0, 1);
                            var morning = clickType.value;
                            var afternoon = tmpstring2.substring(2, 3);
                            // var afternoon = clicktext;
                            var evening = tmpstring2.substring(4);
                            var toreplace = prifix + morning + "," + afternoon + "," + evening;
                            var replaced = attendanceData.replace(tmpstring1, toreplace);
                            $("#monthView\\:editingAttendanceData").val(replaced);
                        }

                    });

        });

$(".a-a-types").children(".a-a-types-c").each(
        function() {

            $(this).bind(
                    "click",
                    function() {
                        var clickType = new AttendanceType();
                        clickType.icon = $(this).attr('src').trim();
                        clickType.value = $(this).attr('alt').trim();
                        clickType.name = $(this).attr('title').trim();

                        var selectedday = tempDay.day;

                        if (selectedday != 0 && selectStatus == 1) {
                            $("#batchEditView" + selectedday).children(".a-afternoon-i").attr(
                                    'src', clickType.icon).attr('alt', clickType.value);
                            // $(".a-a-icon").text(clicktype.icon);
                            $(".a-a-icon-i").attr('src', clickType.icon);
                            $(".a-a-name").text(clickType.name);

                            var attendanceData = $("#monthView\\:editingAttendanceData").val()
                                    .trim();
                            var index = attendanceData.indexOf(selectedday);
                            // 1:A,A,A like
                            var tmpstring1 = selectedday < 10 ? attendanceData.substring(index,
                                    index + 7) : attendanceData.substring(index, index + 8);
                            var index2 = tmpstring1.indexOf(":");
                            var tmpstring2 = tmpstring1.substring(index2 + 1);
                            var prifix = tmpstring1.substring(0, index2 + 1);
                            var morning = tmpstring2.substring(0, 1);
                            // var afternoon = tmpstring2.substring(2,3);
                            var afternoon = clickType.value;
                            var evening = tmpstring2.substring(4);
                            var toreplace = prifix + morning + "," + afternoon + "," + evening;
                            var replaced = attendanceData.replace(tmpstring1, toreplace);
                            $("#monthView\\:editingAttendanceData").val(replaced);
                        }
                    });

        });

$(".a-e-types").children(".a-e-types-c").each(
        function() {

            $(this).bind(
                    "click",
                    function() {

                        var clickType = new AttendanceType();
                        clickType.icon = $(this).attr('src').trim();
                        clickType.value = $(this).attr('alt').trim();
                        clickType.name = $(this).attr('title').trim();

                        var selectedday = tempDay.day;

                        if (selectedday != 0 && selectStatus == 1) {
                            $("#batchEditView" + selectedday).children(".a-evening-i").attr('src',
                                    clickType.icon).attr('alt', clickType.value);
                            // $(".a-e-icon").text(clicktype.icon);
                            $(".a-e-icon-i").attr('src', clickType.icon);
                            $(".a-e-name").text(clickType.name);

                            var attendanceData = $("#monthView\\:editingAttendanceData").val()
                                    .trim();
                            var index = attendanceData.indexOf(selectedday);
                            var tmpstring1 = selectedday < 10 ? attendanceData.substring(index,
                                    index + 7) : attendanceData.substring(index, index + 8);
                            var index2 = tmpstring1.indexOf(":");
                            var tmpstring2 = tmpstring1.substring(index2 + 1);
                            var prifix = tmpstring1.substring(0, index2 + 1);
                            var morning = tmpstring2.substring(0, 1);
                            var afternoon = tmpstring2.substring(2, 3);
                            var evening = clickType.value;
                            var toreplace = prifix + morning + "," + afternoon + "," + evening;
                            var replaced = attendanceData.replace(tmpstring1, toreplace);
                            $("#monthView\\:editingAttendanceData").val(replaced);
                        }

                    });

        });