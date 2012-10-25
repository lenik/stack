var types = new Hashtable();

function SelectedDay() {
    this.day = 0;
}

function AttendanceType() {
    this.icon = 'default';
    this.value = '-';
    this.name = 'notavailable';
}

$(".a-types").each(function() {

    var icon = $(this).children(".a-types-icon-td").children(".a-types-icon").text().trim();
    var value = $(this).children(".a-types-value-td").children(".a-types-value").text().trim();
    var name = $(this).children(".a-types-name-td").children(".a-types-name").text().trim();

    var type = new AttendanceType();
    type.icon = icon;
    type.value = value;
    type.name = name;

    types.put(value, type);
});

selectStatus = 0;

var tempDay = new SelectedDay();

$(".calendarView-available").click(function() {
    var clickDay = new SelectedDay();
    clickDay.day = $(this).attr("id");

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
            $("#" + tempDay.day).css("background-color", "#D0FFD0");
            tempDay = clickDay;
            $(this).css("background-color", "yellow");
        }
    }

    var morning = $(this).children(".a-morning").text().trim();
    var afternoon = $(this).children(".a-afternoon").text().trim();
    var evening = $(this).children(".a-evening").text().trim();

    var mt = types.get(morning);
    var at = types.get(afternoon);
    var et = types.get(evening);

    // $(".a-m-icon").text(mt.icon);
    $(".a-m-icon-i").attr('src', mt.icon);
    $(".a-m-value").text(mt.value);
    $(".a-m-name").text(mt.name);
    // $(".a-a-icon").text(at.icon);
    $(".a-a-icon-i").attr('src', at.icon);
    $(".a-a-value").text(at.value);
    $(".a-a-name").text(at.name);
    // $(".a-e-icon").text(et.icon);
    $(".a-e-icon-i").attr('src', et.icon);
    $(".a-e-value").text(et.value);
    $(".a-e-name").text(et.name);

}

);

// TODO 加入没有选中日期的判断
$(".a-m-types").children(".a-m-types-c").each(
        function() {

            $(this).bind(
                    "click",
                    function() {
                        var clicktext = $(this).text().trim();
                        var selectedday = tempDay.day;
                        var clicktype = types.get(clicktext);

                        if (selectedday != 0 && selectStatus == 1) {
                            $("#" + selectedday).children(".a-morning").text(clicktext);
                            $("#" + selectedday).children(".a-morning-i").attr('src',
                                    clicktype.icon);
                            // $(".a-m-icon").text(clicktype.icon);
                            $(".a-m-icon-i").attr('src', clicktype.icon);
                            $(".a-m-value").text(clicktype.value);
                            $(".a-m-name").text(clicktype.name);

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
                            var morning = clicktext;
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
                        var clicktext = $(this).text().trim();
                        var selectedday = tempDay.day;
                        var clicktype = types.get(clicktext);

                        if (selectedday != 0 && selectStatus == 1) {
                            $("#" + selectedday).children(".a-afternoon").text(clicktext);
                            $("#" + selectedday).children(".a-afternoon-i").attr('src',
                                    clicktype.icon);
                            // $(".a-a-icon").text(clicktype.icon);
                            $(".a-a-icon-i").attr('src', clicktype.icon);
                            $(".a-a-value").text(clicktype.value);
                            $(".a-a-name").text(clicktype.name);

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
                            var afternoon = clicktext;
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

                        var clicktext = $(this).text().trim();
                        var selectedday = tempDay.day;
                        var clicktype = types.get(clicktext);

                        if (selectedday != 0 && selectStatus == 1) {
                            $("#" + selectedday).children(".a-evening").text(clicktext);
                            $("#" + selectedday).children(".a-evening-i").attr('src',
                                    clicktype.icon);
                            // $(".a-e-icon").text(clicktype.icon);
                            $(".a-e-icon-i").attr('src', clicktype.icon);
                            $(".a-e-value").text(clicktype.value);
                            $(".a-e-name").text(clicktype.name);

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
                            var evening = clicktext;
                            var toreplace = prifix + morning + "," + afternoon + "," + evening;
                            var replaced = attendanceData.replace(tmpstring1, toreplace);
                            $("#monthView\\:editingAttendanceData").val(replaced);
                        }

                    });

        });