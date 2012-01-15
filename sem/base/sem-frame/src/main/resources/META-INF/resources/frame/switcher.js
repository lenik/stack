function toggleDetailPanel(event) {
    var entry = $(event.srcElement).parent('.entry');
    entry.siblings('.detailPanel').toggle();
    entry.children('.ui-icon-circle-triangle-s').toggle();
    entry.children('.ui-icon-circle-triangle-e').toggle();
}