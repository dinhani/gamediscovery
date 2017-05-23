/*removed FileUploader*/
app.service('backendService', function ($http, $q) {

    // =========================================================================
    // REQUEST GENERATOR
    // =========================================================================
    var apiAddress = '/backend';
    //var apiAddress = 'http://game-discovery.herokuapp.com/backend';
    var requestGenerator = {
        concepts: function () {
            return apiAddress + "/concepts";
        },
        conceptEntries: function (conceptId, conceptEntryId, resource) {
            var request = apiAddress + "/concepts";
            if (_.isEmpty(conceptId)) {
                request += "/all";
            } else {
                request += "/" + conceptId;
            }
            if (!_.isEmpty(conceptEntryId)) {
                request += "/" + conceptEntryId;
            }
            if (!_.isEmpty(resource)) {
                request += "/" + resource;
            }
            return request;
        },
        games: function (gameUid, resource) {
            var request = apiAddress + "/games";
            if (!_.isEmpty(gameUid)) {
                request += "/" + gameUid;
            }
            if (!_.isEmpty(resource)) {
                request += "/" + resource;
            }
            return request;
        }
    };

    this.getRequestGenerator = function () {
        return requestGenerator;
    };

    // =========================================================================
    // UPLOADER
    // =========================================================================
    this.getUploader = function () {
        var uploader = new FileUploader();
        return uploader;
    };

    // =========================================================================
    // QUERIES (GAMES)
    // =========================================================================
    this.getRelatedGames = function (filters, categories, page) {

        // prepare request                
        var request = requestGenerator.games("", "related");

        // prepare params        
        var uids = _.map(filters, function (filter) {
            return _.isObject(filter) ? filter.uid : filter;
        });
        var categories = _.map(categories, function (category) {
            return (category.checked) ? category.uid : null;
        });
        categories = _.compact(categories);
        page = _.isUndefined(page) ? 1 : page;

        var params = {'uids': uids, 'categories': categories, 'page': page};

        // do request
        return get(request, {params: params});
    };

    // =========================================================================
    // QUERIES (CONCEPTS AND CONCEPT ENTRIES)
    // =========================================================================
    this.getConcepts = function () {
        // prepare request
        var request = requestGenerator.concepts();

        // do request
        return get(request);
    };

    this.getConceptEntries = function (concept, query, page) {
        // parse params
        var conceptUid = _.isObject(concept) ? concept.uid : concept;
        if (_.isEmpty(conceptUid) && _.isEmpty(query)) {
            conceptUid = "platform";
        }

        // prepare request
        var request = requestGenerator.conceptEntries(conceptUid, "");

        // prepare params
        var params = {};
        if (!_.isEmpty(query)) {
            params.query = query;
            params.page = page;
        }

        // do request
        return get(request, {params: params});
    };

    this.getConceptEntry = function (concept, conceptEntry, fetchRelationships) {
        // parse params
        var conceptUid = _.isObject(concept) ? concept.uid : concept;
        var conceptEntryUid = _.isObject(conceptEntry) ? conceptEntry.uid : conceptEntry;

        // prepare request
        var request = requestGenerator.conceptEntries(conceptUid, conceptEntryUid);

        // prepare params
        var params = {};
        if (fetchRelationships === undefined || fetchRelationships === null) {
            params.fetchRelationships = true;
        } else {
            params.fetchRelationships = fetchRelationships;
        }

        // do request
        return get(request, {params: params});
    };

    this.getGameLinks = function (game) {
        // parse params        
        var gameUid = _.isObject(game) ? game.uid : game;

        // prepare request        
        var request = requestGenerator.games(gameUid, "links");

        // do request
        return get(request);
    };

    // =========================================================================
    // HELPER
    // =========================================================================
    function get(requestUrl, requestParameters) {
        // check parameters
        if (requestParameters === undefined || requestParameters === null) {
            requestParameters = {};
        }
        // canceler parameters
        var canceler = $q.defer();
        requestParameters.timeout = canceler.promise;

        // cache ajax (not working)
        //requestParameters.cache = true;

        // do request        
        var request = $http.get(requestUrl, requestParameters);
        request.abort = function () {
            canceler.resolve();
        };

        // return request
        return request;
    }
});