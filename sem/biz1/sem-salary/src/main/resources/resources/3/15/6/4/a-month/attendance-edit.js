function SelectedDay() {
    this.day = 0;
}

function AttendanceType() {
    this.icon = 'default';
    this.value = '-';
    this.name = 'notavailable';
}

eselectStatus = 0;

var eTempDay = new SelectedDay();

$(".calendarView-edit-available").click(function() {
    var clickDay = new SelectedDay();
    clickDay.day = $(this).children(".calendar-view-day").text().trim();

    if (eselectStatus == 0) {
        eTempDay = clickDay;
        $(this).css("background-color", "yellow");
        eselectStatus = 1;
    } else if (eselectStatus == 1) {
        if (clickDay.day == eTempDay.day) {
            $(this).css("background-color", "#D0FFD0");
            eselectStatus = 0;
        }
        if (clickDay.day != eTempDay.day) {
            $("#editCalendarView" + eTempDay.day).css("background-color", "#D0FFD0");
            eTempDay = clickDay;
            $(this).css("background-color", "yellow");
        }
    }

    var morning = $(this).children(".a-morning-i-edit").attr('alt').trim();
    var afternoon = $(this).children(".a-afternoon-i-edit").attr('alt').trim();
    var evening = $(this).children(".a-evening-i-edit").attr('alt').trim();

    // $(".a-m-icon-i-edit").attr('src', morning.attr('src').trim());
    // $(".a-m-value-edit").text(morning.attr('alt').trim());
    // $(".a-m-name-edit").text(morning.attr('title').trim());
    //
    // $(".a-a-icon-i-edit").attr('src', afternoon.attr('src').trim());
    // $(".a-a-value-edit").text(afternoon.attr('alt').trim());
    // $(".a-a-name-edit").text(afternoon.attr('title').trim());
    //
    // $(".a-e-icon-i-edit").attr('src', evening.attr('src').trim());
    // $(".a-e-value-edit").text(evening.attr('alt').trim());
    // $(".a-e-name-edit").text(evening.attr('title').trim());

    $(".a-m-types-c-edit").each(function() {
        if (morning == $(this).attr('alt').trim())
            $(this).css('border', '2px solid red');
        else
            $(this).css('border', '0');

    });

    $(".a-a-types-c-edit").each(function() {
        if (afternoon == $(this).attr('alt').trim())
            $(this).css('border', '2px solid red');
        else
            $(this).css('border', '0');
    });

    $(".a-e-types-c-edit").each(function() {
        if (evening == $(this).attr('alt').trim())
            $(this).css('border', '2px solid red');
        else
            $(this).css('border', '0');
    });

}

);

// TODO 加入没有选中日期的判断
$(".a-m-types-edit").children(".a-m-types-c-edit").each(
        function() {

            $(this).bind(
                    "click",
                    function() {
                        var clickType = new AttendanceType();
                        clickType.value = $(this).attr('alt').trim();
                        clickType.icon = $(this).attr('src').trim();
                        clickType.name = $(this).attr('title').trim();
                        var selectedday = eTempDay.day;

                        if (selectedday != 0 && eselectStatus == 1) {
                            $("#editCalendarView" + selectedday).children(".a-morning-i-edit")
                                    .attr('src', clickType.icon).attr('alt', clickType.value);
                            // $(".a-m-icon").text(clicktype.icon);

                            wrapAttendanceData(selectedday, clickType, 'morning');
                            $(".a-m-types-c-edit").css('border', '0');
                            $(this).css("border", "2px solid red");
                        }

                    });

        });

$(".a-a-types-edit").children(".a-a-types-c-edit").each(
        function() {

            $(this).bind(
                    "click",
                    function() {
                        var clickType = new AttendanceType();
                        clickType.value = $(this).attr('alt').trim();
                        clickType.icon = $(this).attr('src').trim();
                        clickType.name = $(this).attr('title').trim();

                        var selectedday = eTempDay.day;

                        if (selectedday != 0 && eselectStatus == 1) {
                            $("#editCalendarView" + selectedday).children(".a-afternoon-i-edit")
                                    .attr('src', clickType.icon).attr('alt', clickType.value);
                            // $(".a-a-icon").text(clicktype.icon);

                            wrapAttendanceData(selectedday, clickType, 'afternoon');
                            $(".a-a-types-c-edit").css('border', '0');
                            $(this).css("border", "2px solid red");
                        }
                    });

        });

$(".a-e-types-edit").children(".a-e-types-c-edit").each(
        function() {

            $(this).bind(
                    "click",
                    function() {
                        var clickType = new AttendanceType();
                        clickType.value = $(this).attr('alt').trim();
                        clickType.icon = $(this).attr('src').trim();
                        clickType.name = $(this).attr('title').trim();

                        var selectedday = eTempDay.day;

                        if (selectedday != 0 && eselectStatus == 1) {
                            $("#editCalendarView" + selectedday).children(".a-evening-i-edit")
                                    .attr('src', clickType.icon).attr('alt', clickType.value);
                            // $(".a-e-icon").text(clicktype.icon);

                            wrapAttendanceData(selectedday, clickType, 'evening');
                            $(".a-e-types-c-edit").css('border', '0');
                            $(this).css("border", "2px solid red");
                        }

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