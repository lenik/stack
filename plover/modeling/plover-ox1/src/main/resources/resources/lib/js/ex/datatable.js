var ICON_PREFIX = "http://st1.cdn.bee32.com/style/default/icons/";
var SEM = {};

(function($) {
    $.extend(SEM, {
        dataTableOptions : {
            bProcessing : true,
            bServerSide : false,
            bAutoWidth : false,
            bPaginate : true,
            sPaginationType : "full_numbers",
            bFilter : false,
            bInfo : true,

            fnServerData : function(sSource, aoData, fnCallback) {
                var map = document.parameterMap;
                if (map != null)
                    for ( var name in map) {
                        var val = map[name];
                        aoData.push({
                            name : name,
                            value : val
                        });
                    }

                $.ajax({
                    "dataType" : 'json',
                    "type" : "POST",
                    "url" : sSource,
                    "data" : aoData,
                    "success" : fnCallback
                });
            },

            aoColumnDefs : [ {
                aTargets : [ 'id', 'version' ],
                bVisible : false
            }, ],

            fnRowCallback : function(node, aData, iDisplayIndex, iDisplayIndexFull) {
                var id = aData[0];
                var tableNode = this;
                var tableId = tableNode.attr('id');

                var toolboxNode = $('td.toolbox', node);
                if (toolboxNode.length == 0) {
                    toolboxNode = $('td', node).eq(-1);
                    if (toolboxNode.length == 0)
                        throw "Where is toolbox cell?";
                }
                toolboxNode.html('');

                var tools;
                try {
                    tools = eval(tableId + 'Tools');
                } catch (e) {
                    tools = SEM.entityTools;
                }

                for ( var toolname in tools) {
                    if (toolname.substr(0, 1) == '_')
                        continue;

                    var tool = tools[toolname];

                    var a = $('<a />');
                    a.attr('title', tool.name);

                    var href = tool.href;
                    if (href != null) {
                        href = href.replace("$id", id);
                        a.attr('href', href);
                    } else
                        a.attr('href', '#');

                    if (tool.callback != null) {
                        a.click((function(cb) {
                            return function() {
                                return cb(node, aData);
                            };
                        })(tool.callback));
                    }

                    if (tool.icon != null) {
                        var img = $("<img border='0' />");
                        img.attr("alt", tool.name);
                        img.attr("src", ICON_PREFIX + tool.icon);
                        a.append(img);
                    }

                    toolboxNode.append(a);
                }
                return node;
            },

            oLanguage : {
                oPaginate : {
                    sFirst : "??????",
                    sPrevious : "??????",
                    sNext : "??????",
                    sLast : "??????"
                },
                sInfo : "_START_ ??? _END_, ??? _TOTAL_",
                sInfoEmpty : "?????????????????????",
                sLengthMenu : "?????? _MENU_ ???",
                sEmptyTable : "????????????",
                sProcessing : "????????????..."
            }
        }, // dataTableOptions

        entityTools : {
            view : {
                name : "??????",
                icon : "etool16/insp_sbook.gif",
                href : "content.do?id=$id"
            },

            edit : {
                name : "??????",
                icon : "etool16/editor_area.gif",
                href : "editForm.do?id=$id"
            },

            del : {
                name : "??????",
                icon : "etool16/delete_edit.gif",
                callback : function(node, aData) {
                    var id = aData[0];
                    var info = $(node).parents("table").attr('title');
                    if (!window.confirm("????????????????????? " + info + " ????????????"))
                        return;
                    window.location = "delete.do?id=" + id;
                }
            }
        }, // entityTools
        _dummy : null
    });

    $.fn.dataTableExt.oApi.fnReloadAjax = function(oSettings, sNewSource, fnCallback) {
        if (typeof sNewSource != 'undefined') {
            oSettings.sAjaxSource = sNewSource;
        }
        this.fnDraw(this);
    }

    $.fn.dataTable_SEM = function(opts) {
        if (opts == null)
            opts = SEM.dataTableOptions;
        else
            opts = $.extend({}, SEM.dataTableOptions, opts);

        var model = this.dataTable(opts);

        model.getSelection = function() {
            var selection = [];
            var trNodes = this.fnGetNodes();
            for ( var i = 0; i < trNodes.length; i++)
                if ($(trNodes[i]).hasClass('row_selected'))
                    selection.push(trNodes[i]);
            return selection;
        };

        return this[0].model = model;
    };
})(jQuery);