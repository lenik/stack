// actionListener = dialog.hideValidated(args)
// instance.__proto__ = object.getClass()
// type.prototype = Object.class

(function() {

    var _showDialog = PrimeFaces.widget.Dialog.prototype.show;
    PrimeFaces.widget.Dialog.prototype.show = function() {
        _showDialog.apply(this, arguments);

        var datatable = this.jq.find(".ui-datatable");
        if (datatable.length == 0)
            return;
        datatable = datatable[0];

        var form = $(datatable).parents("form");
        if (form.length == 0)
            return;
        form = form[0];

        PrimeFaces.ajax.AjaxRequest({
            formId : form.id,
            source : this.id,
            process : "@all",
            update : datatable.id
        // there may >1 datatables.
        });
    };

    var _showTab = PrimeFaces.widget.TabView.prototype.show;
    PrimeFaces.widget.TabView.prototype.show = function(newPanel) {
        _showTab.apply(this, arguments);

        var datatable = $(newPanel).find(".ui-datatable");
        if (datatable.length == 0)
            return;
        datatable = datatable[0];

        var form = $(datatable).parents("form");
        if (form.length == 0)
            return;
        form = form[0];

        PrimeFaces.ajax.AjaxRequest({
            formId : form.id,
            source : this.id,
            process : "@all",
            update : datatable.id
        // there may >1 datatables.
        });
    };

})(jQuery);
