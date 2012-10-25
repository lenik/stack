var etypes = new Hashtable();

function SelectedDay() {
    this.day = 0;
}

function AttendanceType() {
    this.icon = 'default';
    this.value = '-';
    this.name = 'notavailable';
}

$(".a-types-edit").each(
        function() {

            var icon = $(this).children(".a-types-icon-td-edit").children(".a-types-icon-edit")
                    .text().trim();
            var value = $(this).children(".a-types-value-td-edit").children(".a-types-value-edit")
                    .text().trim();
            var name = $(this).children(".a-types-name-td-edit").children(".a-types-name-edit")
                    .text().trim();

            var type = new AttendanceType();
            type.icon = icon;
            type.value = value;
            type.name = name;

            etypes.put(value, type);
        });

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

    var morning = $(this).children(".a-morning-edit").text().trim();
    var afternoon = $(this).children(".a-afternoon-edit").text().trim();
    var evening = $(this).children(".a-evening-edit").text().trim();

    var mt = etypes.get(morning);
    var at = etypes.get(afternoon);
    var et = etypes.get(evening);

    // $(".a-m-icon").text(mt.icon);
    $(".a-m-icon-i-edit").attr('src', mt.icon);
    $(".a-m-value-edit").text(mt.value);
    $(".a-m-name-edit").text(mt.name);
    // $(".a-a-icon").text(at.icon);
    $(".a-a-icon-i-edit").attr('src', at.icon);
    $(".a-a-value-edit").text(at.value);
    $(".a-a-name-edit").text(at.name);
    // $(".a-e-icon").text(et.icon);
    $(".a-e-icon-i-edit").attr('src', et.icon);
    $(".a-e-value-edit").text(et.value);
    $(".a-e-name-edit").text(et.name);

}

);

// TODO 加入没有选中日期的判断
$(".a-m-types-edit").children(".a-m-types-c-edit").each(
        function() {

            $(this).bind(
                    "click",
                    function() {
                        var clicktext = $(this).text().trim();
                        var selectedday = eTempDay.day;
                        var clicktype = types.get(clicktext);

                        if (selectedday != 0 && eselectStatus == 1) {
                            $("#editCalendarView" + selectedday).children(".a-morning-edit").text(
                                    clicktext);
                            $("#editCalendarView" + selectedday).children(".a-morning-i-edit")
                                    .attr('src', clicktype.icon);
                            // $(".a-m-icon").text(clicktype.icon);
                            $(".a-m-icon-i-edit").attr('src', clicktype.icon);
                            $(".a-m-value-edit").text(clicktype.value);
                            $(".a-m-name-edit").text(clicktype.name);

                            var attendanceData = $("#editDialog\\:form\\:attendanceData").val()
                                    .trim();
                            var index = attendanceData.indexOf(selectedday);
                            // 1:A,A,A like
                            var tmpstring1 = selectedday < 10 ? attendanceData.substring(index,
                                    index + 7) : attendanceData.substring(index, index + 8);
                            var index2 = tmpstring1.indexOf(":");
                            var tmpstring2 = tmpstring1.substring(index2 + 1);
                            var prifix = tmpstring1.substring(0, index2 + 1);
                            // var morning = tmpstring2.substring(0, 1);
                            var morning = clicktext;
                            var afternoon = tmpstring2.substring(2, 3);
                            // var afternoon = clicktext;
                            var evening = tmpstring2.substring(4);
                            var toreplace = prifix + morning + "," + afternoon + "," + evening;
                            var replaced = attendanceData.replace(tmpstring1, toreplace);
                            $("#editDialog\\:form\\:attendanceData").val(replaced);
                        }

                    });

        });

$(".a-a-types-edit").children(".a-a-types-c-edit").each(
        function() {

            $(this).bind(
                    "click",
                    function() {
                        var clicktext = $(this).text().trim();
                        var selectedday = eTempDay.day;
                        var clicktype = types.get(clicktext);

                        if (selectedday != 0 && eselectStatus == 1) {
                            $("#editCalendarView" + selectedday).children(".a-afternoon-edit")
                                    .text(clicktext);
                            $("#editCalendarView" + selectedday).children(".a-afternoon-i-edit")
                                    .attr('src', clicktype.icon);
                            // $(".a-a-icon").text(clicktype.icon);
                            $(".a-a-icon-i-edit").attr('src', clicktype.icon);
                            $(".a-a-value-edit").text(clicktype.value);
                            $(".a-a-name-edit").text(clicktype.name);

                            var attendanceData = $("#editDialog\\:form\\:attendanceData").val()
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
                            var afternoon = clicktext;
                            var evening = tmpstring2.substring(4);
                            var toreplace = prifix + morning + "," + afternoon + "," + evening;
                            var replaced = attendanceData.replace(tmpstring1, toreplace);
                            $("#editDialog\\:form\\:attendanceData").val(replaced);
                        }
                    });

        });

$(".a-e-types-edit").children(".a-e-types-c-edit").each(
        function() {

            $(this).bind(
                    "click",
                    function() {

                        var clicktext = $(this).text().trim();
                        var selectedday = eTempDay.day;
                        var clicktype = types.get(clicktext);

                        if (selectedday != 0 && eselectStatus == 1) {
                            $("#editCalendarView" + selectedday).children(".a-evening-edit").text(
                                    clicktext);
                            $("#editCalendarView" + selectedday).children(".a-evening-i-edit")
                                    .attr('src', clicktype.icon);
                            // $(".a-e-icon").text(clicktype.icon);
                            $(".a-e-icon-i-edit").attr('src', clicktype.icon);
                            $(".a-e-value-edit").text(clicktype.value);
                            $(".a-e-name-edit").text(clicktype.name);

                            var attendanceData = $("#editDialog\\:form\\:attendanceData").val()
                                    .trim();
                            var index = attendanceData.indexOf(selectedday);
                            var tmpstring1 = selectedday < 10 ? attendanceData.substring(index,
                                    index + 7) : attendanceData.substring(index, index + 8);
                            var index2 = tmpstring1.indexOf(":");
                            var tmpstring2 = tmpstring1.substring(index2 + 1);
                            var prifix = tmpstring1.substring(0, index2 + 1);
                            var morning = tmpstring2.substring(0, 1);
                            var afternoon = tmpstring2.substring(2, 3);
                            var evening = clicktext;
                            var toreplace = prifix + morning + "," + afternoon + "," + evening;
                            var replaced = attendanceData.replace(tmpstring1, toreplace);
                            $("#editDialog\\:form\\:attendanceData").val(replaced);
                        }

                    });

        });