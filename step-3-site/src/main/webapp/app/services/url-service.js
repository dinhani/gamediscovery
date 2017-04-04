app.service('urlService', function () {

    this.main = function () {
        var url = "/";
        return url;
    };

    this.recommendations = function (filters) {
        // base
        var url = "/recommendations";
        
        // query params
        url = url + this._searchParams(filters);

        return url;
    };

    // =========================================================================
    // INTERNAL
    // =========================================================================
    this._searchParams = function (conceptEntries) {
        var uids = _.map(conceptEntries, function (c) {
            if (_.isString(c)) {
                return c;
            }
            if (_.isObject(c)) {
                return c.uid;
            }
        });

        // append params to url
        if (uids.length) {
            return "/" + uids.join("/");
        } else {
            return "";
        }
    };
});