function loadAclAndShow(dialog) {

    var form = dialog.jq.find('form')[0];
    var loadAclButton = dialog.jq.find('#securityDialog\\:form\\:loadAclButton')[0];

    PrimeFaces.ajax.AjaxRequest({
        formId : form.id,
        source : loadAclButton.id,
        process : '@all',
        update : form.id
    });

    dialog.show();

};
