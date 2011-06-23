(function($) {

    var partyList = null;

    partyListTools = {
        choose : {
            name : "选择",
            icon : "etool16/annotate.gif",
            callback : function(node, aData) {
                if ($("#cid" + aData[0] + "").length > 0)
                    return false;
                var _div = $("<div></div>");
                var _checkbox = $("<input type='checkbox' id='cid" + aData[0]
                        + "' class='ccbox' name='customerId' value='"
                        + aData[0] + "' />");
                _checkbox.attr('checked', 'checked');
                _div.append(_checkbox);
                _div.append(aData[1]);
                $("#details").append(_div);
                return false;
            }
        }
    }

    $("#date").datepicker({
        changeMonth : true,
        changeYear : true,
        showOn : "button"
    });

    // 把 #addDetailDialog 用jquery dialog包装起来
    $("#addPartyDialog").dialog({
        height : 300,
        width : 800,
        autoOpen : false,
        modal : true
    });

    $("#addParty").click(function() {
        $("#addPartyDialog").dialog("open");
        if (partyList == null) {
            partyList = $("#partyList").dataTable_SEM({
                "bRetrieve" : true,
                "bServerSide" : true,
                "bFilter" : false,
                "bPaginate" : true,
                "bSort" : false,
                "bLengthChange" : false,
                "sAjaxSource" : "search.do",
                "fnServerData" : function(sSource, aoData, fnCallback) {
                    $.ajax({
                        "dataType" : 'json',
                        "type" : "POST",
                        "url" : sSource,
                        "data" : aoData,
                        "success" : fnCallback
                    });
                }
            });
        } else {
            customerList.fnReloadAjax("search.do");
        }
        return false;
    });

    $("#search-btn").click(
            function() {
                customerList.fnReloadAjax("search.do?partyName="
                        + $("#search-text").val() + "");
            });

    $("#removeSelectedCustomers").click(function() {
        $(".ccbox:checked").parent().remove();
    });
})(jQuery);