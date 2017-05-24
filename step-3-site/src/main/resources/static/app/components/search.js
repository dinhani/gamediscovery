// =============================================================================
// HTML
// =============================================================================
app.component("search", {
    templateUrl: 'search.html',
    controller: "SearchController",
    bindings: {
        filters: "=",
        games: "=",
        gamesTotalResults: "=",
        searching: "="
    }
});


// =============================================================================
// JS
// =============================================================================
app.controller('SearchController', function ($scope, $location, $q,
    backendService, urlService, guiService) {

    // =========================================================================
    // DATA
    // =========================================================================
    var previousGameSearches = [];

    // =========================================================================
    // EVENTS
    // =========================================================================
    $scope.$on('search-games', function (event, args) {
        $scope.searchGames(args.page);
    })
    $scope.$on('add-filter', function (event, args) {
        $scope.addFilter(args.filter);
        $scope.searchGames();
    })
    $scope.filtersChanged = function () {
        $location.path(urlService.recommendations($scope.$ctrl.filters));
        $scope.searchGames();
    }

    // =========================================================================
    // METHODS
    // =========================================================================
    $scope.addFilterFromExample = function (filter) {
        $scope.addFilter(filter);
        $location.path(urlService.recommendations($scope.$ctrl.filters));
        $scope.searchGames();
    }

    $scope.addFilter = function (filter) {
        $scope.$ctrl.filters.push(filter);
    }

    $scope.searchFilters = function (query) {
        return backendService.getConceptEntries("", query)
            .then(
            success => JSOG.decode(success.data),
            error => []
            );
    }

    $scope.searchGames = function (page) {
        // do not search if empty
        if (_.isEmpty($scope.$ctrl.filters)) {
            $scope.$ctrl.games = [];
            $scope.$ctrl.gamesTotalResults = 0;
            return;
        }

        // check parameters
        if (_.isUndefined(page)) {
            page = 1;
        }

        // cancel previous searches
        _.each(previousGameSearches, function (previousSearch) {
            previousSearch.abort();
        });
        previousGameSearches = [];

        // prepare search
        $scope.$ctrl.searching = true;
        guiService.hideModal('game-details');

        // do search
        var request = backendService.getRelatedGames($scope.$ctrl.filters, [], page);
        previousGameSearches.push(request);

        request
            .then(function (response) {
                // display games
                let gameSearch = JSOG.decode(response.data);
                if (page === 1) {
                    $scope.$ctrl.games = gameSearch.results;
                } else {
                    $scope.$ctrl.games = $scope.$ctrl.games.concat(gameSearch.results);
                }
                $scope.$ctrl.gamesTotalResults = gameSearch.totalOfResults;
            })
            .finally(function () {
                $scope.$ctrl.searching = false;
            });
    };

    // =========================================================================
    // INIT
    // =========================================================================
    $scope.init = function () {
        // parse URL
        var filterUids = $location.path().split("/");
        filterUids = filterUids.slice(2, filterUids.length);
        loadFilters(filterUids);
    }

    function loadFilters(filterUids) {
        var promises = [];

        // load each concept entry individually
        _.each(filterUids, function (filterUid) {
            var promise = backendService.getConceptEntry("", filterUid, false)
                .then(success => $scope.addFilter(success.data))
            promises.push(promise);
        });

        return $q.all(promises).then(_ => $scope.searchGames());
    };
});
