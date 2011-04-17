var ICON_PREFIX = "http://static.secca-project.com/style/default/icons/";
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
                        a.click(function() {
                            return tool.callback(node, aData);
                        });
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
                    sFirst : "首页",
                    sPrevious : "前页",
                    sNext : "后页",
                    sLast : "末页"
                },
                sInfo : "_START_ 到 _END_, 共 _TOTAL_",
                sInfoEmpty : "没有记录可显示",
                sLengthMenu : "显示 _MENU_ 条",
                sEmptyTable : "没有记录",
                sProcessing : "正在处理..."
            }
        }, // dataTableOptions

        entityTools : {
            view : {
                name : "查看",
                href : "content.htm?id=$id",
                icon : "etool16/insp_sbook.gif"
            },

            edit : {
                name : "编辑",
                href : "editForm.htm?id=$id",
                icon : "etool16/editor_area.gif"
            },

            del : {
                name : "删除",
                callback : function(node, aData) {
                    var id = aData[0];
                    var info = $(node).parents("table").attr('title');
                    if (!window.confirm("您确定要删除该 " + info + " 记录吗？"))
                        return;
                    window.location = "delete.htm?id=" + id;
                },
                icon : "etool16/delete_edit.gif"
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

        return this.dataTable(opts);
    };

})(jQuery);