$(document).ready(
        function() {

            var itab = $(".itab")[0];
            window.itab = itab;

            itab.reload = function() {
                var dt = itab.dataTable;
                dt.ajax.reload();
            };

            itab.open = function(id) {
                var doc = form.id.value;
                var $xtabed = $("#xtabed");
                var $iframe = $("iframe", $xtabed);

                var url = _webApp_ + "acentry/" + (id == null ? "new" : id)
                        + "/?view:=form&event.id=" + doc;
                $iframe.attr("src", url);

                $xtabed.dialog({
                    autoOpen : true,
                    modal : true,
                    title : id == null ? "新建…" : "编辑...",
                    buttons : [ {
                        text : "保存",
                        click : function() {
                            var $iframe = $("iframe", this);
                            var form = $("form", $iframe.contents());
                            form.submit();
                        }
                    }, {
                        text : "关闭",
                        click : function() {
                            $(this).dialog("close");
                        }
                    }, ],
                    modal : true,
                    width : "80%"
                }); // dialog()
            };

            itab.rowClick = function(row) {
                var data = row.data();
                if (data == null)
                    return null;

                var id = data[0];
                if (id == undefined)
                    return;

                itab.open(id);
            };

            $(".cmd-reload").click(itab.reload);
            $(".cmd-add").click(function() {
                itab.open(null);
            });

        }); // ready()
