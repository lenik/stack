function searchTag(event) {
    var a = event.target; // IE: event.target is available since IE8+
    var href = a.href;
    var eq = href.indexOf('=');
    var tagId = href.substring(eq + 1);
    var tagName = a.text;
    if (tagName == null) tagName = a.innerText; // IE FIX.

    PrimeFaces.ajax.AjaxRequest({
        formId : 'searchForm',
        source : 'searchForm:searchTag',
        params : [ {
            name : 'id',
            value : tagId
        }, {
            name : 'name',
            value : tagName
        } ],
        process : '@all',
        update : 'searchForm mainForm'
    });
    return false;
}
