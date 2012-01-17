function toggleDetailPanel(event) {
    var entry = $(event.target).parents('.entry');
    entry.siblings('.detailPanel').toggle();
    entry.children('.ui-icon-circle-triangle-s').toggle();
    entry.children('.ui-icon-circle-triangle-e').toggle();
}