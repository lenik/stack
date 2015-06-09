(function($) {

    util.flot = {
        parse : function(mode, text, limit, linesep, fieldsep) {
            if (linesep == undefined)
                linesep = ";";
            if (fieldsep == undefined)
                fieldsep = "\t";

            var lines = $.grep(text.split(linesep), function(s) {
                return s.trim().length;
            });

            var start = 0;
            var end = lines.length;
            if (limit != undefined) {
                if (limit >= 0)
                    end = Math.min(lines.length, limit);
                else
                    start = Math.max(0, lines.length + limit);
            }

            switch (mode) {
            case "xy-map":
                var xmap = [];
                var ymap = [];

                for ( var i = start; i < end; i++) {
                    var line = lines[i].trim();
                    var fields = line.split(fieldsep);
                    var x = fields[0], y = fields[1];
                    xmap.push([ i, x ]);
                    ymap.push([ i, y ]);
                }

                return {
                    xmap : xmap,
                    ymap : ymap
                };

            case "label-data*":
                var data = [];
                var other = 0;
                for ( var i = 0; i < lines.length; i++) {
                    var line = lines[i].trim();
                    var fields = line.split(fieldsep);
                    var x = fields[0], y = fields[1] * 1;
                    if (x == "null")
                        x = "未设置";
                    if (i >= start && i < end)
                        data.push({
                            label : x,
                            data : y
                        });
                    else
                        other += y;
                }
                data.push({
                    label : "其它",
                    data : other
                });
                return data;
            }
        },

        plotXYBarCurve : function(q, data) {
            q.plot([ {
                data : data.ymap,
                bars : {
                    show : true
                }
            }, {
                data : data.ymap,
                lines : {
                    show : true
                },
                curvedLines : {
                    apply : true
                }
            } ], {
                bars : {
                    align : "center",
                    barWidth : 0.5
                },
                grid : {
                    // hoverable : true,
                    borderWidth : 0
                },
                series : {
                    curvedLines : {
                        active : true
                    }
                },
                xaxis : {
                    ticks : data.xmap
                }
            });
        },

        plotXYPie : function(q, data) {
            q.plot(data, {
                grid : {
                    hoverable : true,
                    borderWidth : 0
                },
                legend : {
                    show : false
                },
                series : {
                    pie : {
                        show : true,
                        radius : 1,
                        label : {
                            show : true,
                            radius : 0.8,
                            formatter : function(label, series) {
                                // var val=Math.round(series.data[0][1]);
                                var val = Math.round(series.percent) + "%";
                                return "<div class='pie-label'>" + label + "<br>" + val + "</div>";
                            }
                        }
                    }
                }
            });
        }
    };

})(jQuery);