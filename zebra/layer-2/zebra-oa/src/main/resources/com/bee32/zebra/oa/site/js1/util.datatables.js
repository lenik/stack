(function($) {

    util.DT = {

        getIdLabel : function(row) {
            var dt = row.table();
            var data = row.data();
            if (data == null)
                throw "Bad row";

            var id = data[0];
            var cmap = {};
            var headers = dt.columns().header();
            for (i = 0; i < headers.length; i++) {
                var dataField = $(headers[i]).attr("data-field");
                cmap[dataField] = i;
            }

            var labelFields = [ "label", "subject", "fullName" ];
            var labelFieldIndex = null;
            for (i = 0; i < labelFields.length; i++) {
                var columnIndex = cmap[labelFields[i]];
                if (columnIndex != null) {
                    labelFieldIndex = columnIndex;
                    break;
                }
            }
            var label = null;
            if (labelFieldIndex != null) {
                label = row.data()[labelFieldIndex];
                label = label.trimRight();
                if (label.substr(0, 1) == "<" && label.substr(-1) == ">") {
                    label = label.replace(/^<.*?>/, '');
                    label = label.replace(/<.*?>$/, '');
                }
            }

            return {
                id : id,
                label : label
            };
        },

        toObject : function(row) {
            var dt = row.table();
            var data = row.data();
            var id = data[0];
            var obj = {};
            var headers = dt.columns().header();
            for (i = 0; i < headers.length; i++) {
                var dataField = $(headers[i]).attr("data-field");
                obj[dataField] = data[i];
            }
            return obj;
        }

    };

})(jQuery);
