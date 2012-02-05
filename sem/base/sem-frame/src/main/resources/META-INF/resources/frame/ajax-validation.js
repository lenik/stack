// actionListener = dialog.hideValidated(args)
// instance.__proto__ = object.getClass()
// type.prototype = Object.class

PrimeFaces.widget.Dialog.prototype.hideValidated = function(args) {
    if (args == null) {
        alert("hideValidated 需要参数 args");
        return;
    }
    if (args.validationFailed) {
        this.jq.effect("shake", {
            times : 2
        }, 100);
    } else {
        this.hide();
    }
}
