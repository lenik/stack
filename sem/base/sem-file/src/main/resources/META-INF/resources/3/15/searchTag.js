function searchTag(event) {
    var a = event.srcElement;
    var href = a.href;
    var eq = href.indexOf('=');
    var tagId = href.substring(eq + 1);
    var tagName = a.text;

    PrimeFaces.ajax.AjaxRequest({
        formId : 'searchForm',
        source : 'searchForm:searchTag',
        params : {
            id : tagId,
            name : tagName
        },
        process : '@all',
        update : 'searchForm mainForm'
    });
    return false;
}
