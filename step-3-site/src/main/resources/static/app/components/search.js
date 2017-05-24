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

// =============================================================================
// HTML
// =============================================================================
app.component("search", {
    template: `
        <div id="search" ng-init="init()">
            <!-- TEMPLATE -->
            <script type="text/ng-template" id="tag-template">
                <span style="font-weight:bold">{{$getDisplayText()}}</span>
                <span class='alias' ng-if="data.alias">({{data.alias}})</span>
                <br/>
                {{data.type.name}}
            </script>

            <!-- HEADER -->
            <div id="search-header" class="ui top attached big header" translate>
                Game search
            </div>

            <!-- INPUT -->
            <div class="ui bottom attached segment">
                <tags-input id="search-input" ng-model="$ctrl.filters" key-property="uid" display-property="name" placeholder="{{'Add filter' | translate}}"
                    add-from-autocomplete-only="true" replace-spaces-with-dashes="false"
                    on-tag-added="filtersChanged()" on-tag-removed="filtersChanged()">
                    <auto-complete source="searchFilters($query)" debounce-delay="200" min-length="1" template="tag-template"></auto-complete>
                </tags-input>

                <!-- EXAMPLES -->
                <div class="ui horizontal list">
                    <div class="item small text"><span translate>e.g.</span>:</div>
                    <div class="item small text"><a href="/recommendations/game-the-witcher-3-wild-hunt"      ng-click="addFilterFromExample({uid: 'game-the-witcher-3-wild-hunt', name: 'The Witcher 3: Wild Hunt'}, $event)">the witcher 3</a></div>
                    <div class="item small text"><a href="/recommendations/platform-playstation-4"            ng-click="addFilterFromExample({uid: 'platform-playstation-4', name: 'PlayStation  4'}, $event)">playstation 4</a></div>
                    <div class="item small text"><a href="/recommendations/gamemode-single-player"            ng-click="addFilterFromExample({uid: 'gamemode-single-player', name: 'Single Player'}, $event)">single player</a></div>
                    <div class="item small text"><a href="/recommendations/genre-open-world"                  ng-click="addFilterFromExample({uid: 'genre-open-world', name: 'Open World'}, $event)">open world</a></div>
                    <div class="item small text"><a href="/recommendations/theme-fantasy"                     ng-click="addFilterFromExample({uid: 'theme-fantasy', name: 'Fantasy'}, $event)">fantasy</a></div>
                    <div class="item small text"><a href="/recommendations/mechanics-magic"                   ng-click="addFilterFromExample({uid: 'mechanics-magic', name: 'Magic'}, $event)">magic</a></div>
                    <div class="item small text"><a href="/recommendations/creature-dragon"                   ng-click="addFilterFromExample({uid: 'creature-dragon', name: 'Dragon'}, $event)">dragon</a></div>
                </div>
            </div>
        </div>
    `,
    controller: "SearchController",
    bindings: {
        filters: "=",
        games: "=",
        gamesTotalResults: "=",
        searching: "="
    }
});
