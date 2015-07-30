$(document).ready(
        function() {

            var itab = $(".itab")[0];
            itab.formatChild = function(obj) {

                var html = $("#child-0").html();
                html = html.replace(/\$base/g, obj.baseName);
                html = html.replace(/\$dir/g, obj.dirName);

                var v = obj.dirName.split(/\//);
                var segs = '';
                for ( var i = 0; i < v.length; i++) {
                    var seg = "<li>" + v[i] + "</li>";
                    segs += seg;
                }
                html = html.replace(/\$segs/g, "<ol class='breadcrumb'>" + segs + "</ol>");

                html = html.replace(/\$href/g, _webApp_ + "files/tree/" + obj.dirName + "/"
                        + obj.baseName);
                html = html.replace(/\$description/g, obj.description);
                html = html.replace(/\$downloads/g, obj.downloads);

                return html;

            };

        });