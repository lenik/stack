function loadAclAndShow(dialog) {

    var form = dialog.jq.find('form')[0];
    var actionSource = dialog.jq.find('.x-load-acl-button')[0];

    PrimeFaces.ajax.AjaxRequest({
        formId : form.id,
        source : actionSource.id,
        process : '@all',
        update : form.id
    });

    dialog.show();

};
