function readSingleFile(evt) {
    // Retrieve the first (and only!) File from the FileList object
    var f = evt.target.files[0];

    if (f) {
        var r = new FileReader();
        r.onload = function(e) {
            var contents = e.target.result;

            var reg = /[^\x00-\xff]/; // 匹配中文或双字节标点
            var arr = contents.split(/\n/);
            var pre;
            var components = null; // 构件
            var parts = null; // 零件 和 原材料

            for ( var i = 0; i < arr.length; i++) {
                var str = arr[i];

                if (str.length == 0)
                    continue;
                if (i == 0)
                    continue;
                if (i == 1)
                    continue;

                var tablerow = $("<tr></tr>");
                var item = str.split(',');

                if (i == 2) {
                    var index = item[0].indexOf('、');
                    pre = item[0].substring(0, index);
                    var td1 = $("<td>" + pre + "</td>");
                    var td2 = $("<td>" + item[1] + "</td>");

                    tablerow.append(td1);
                    tablerow.append(td2);

                    components = pre + ',' + item[1] + ',' + item[3] + ',' + item[5];

                    $("#component").children("tbody").append(tablerow);
                    continue;
                }

                var x1;
                var x2; // name
                if (reg.test(x1)) {
                    x1 = item[1].substring(0, 3);
                    x2 = item[7] + "槽钢";
                } else {
                    x1 = item[1].substring(2) + "*c";
                    x2 = item[7] + "钢板";
                }

                if (parts == null)
                    parts = pre + '-' + item[0] + ',' + item[2] + ',' + item[3] + ',' + item[4]
                            + ',' + item[6];
                else
                    parts = parts + ';' + pre + '-' + item[0] + ',' + item[2] + ',' + item[3] + ','
                            + item[4] + ',' + item[6];

                parts = parts + '/' + x2 + ',' + x1;

                $("#part").children("tbody").append(
                        $("<tr><td>" + pre + '-' + item[0] + "</td><td>" + item[3] + "</td><td>"
                                + item[2] + "</td></tr>"));
                $("#raw").children("tbody").append(
                        $("<tr><td>" + x2 + "</td><td>" + x1 + "</td></tr>"));

                $("#tab").children("tbody").append(tablerow);

            }

            $("#mainForm\\:part").val(components + "#" + parts);

            $(".prompt").show();
            $("#mainForm\\:start_import").show();
        }

        r.readAsText(f);
    } else {
        alert("Failed to load file");
    }
}

function start_loadFile(event) {
    var f = event.target.files[0];
    var mask = $("<div style='height:100%; width:100%;background-color:grey;'></div>");
    if (!f) {
        alert("Failed to load file");
    }
    $("body").append(mask);

}

document.getElementById('fileinput').addEventListener('change', readSingleFile, false);