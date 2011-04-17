var exampleTools = {};
jQuery.extend(exampleTools, SEM.entityTools);

var table1;

$(document).ready(function() {

    var tbody = $('#example tbody');
    tbody.click(function(event) {
        var tr = $(event.target.parentNode);
        var tableNode = tr.parents("table")[0];
        var table = tableNode.model;

        var aoData = table.fnSettings().aoData;

        tr.toggleClass('row_selected');
        return;

        $(aoData).each(function() {
            $(this.nTr).removeClass('row_selected');
        });
        tr.addClass('row_selected');
    });

    table1 = $('#example').dataTable_SEM();
});

function deleteSelection(event) {
    var selection = table1.getSelection();
    if (selection == null)
        return;
    $(selection).each(function() {
        table1.fnDeleteRow(this);
    });
}
