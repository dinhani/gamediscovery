// =============================================================================
// JS
// =============================================================================
app.controller('SearchResultsController', function ($scope, $rootScope, hotkeys,
    backendService, guiService) {

    // =============================================================================
    // DATA
    // =============================================================================
    $scope.data = {
        selectedGame: null,
        selectedGameLinks: []
    }

    // =========================================================================
    // METHODS
    // =========================================================================
    $scope.showGame = function (game) {
        // do load game information
        backendService.getConceptEntry("game", game)
            .then(success => {
                $scope.data.selectedGame = JSOG.decode(success.data);
                guiService.showModal('game-details', {
                    onHidden: $scope.closeGame
                });
                guiService.accordion();
            })


        // do load game links
        backendService.getGameLinks(game)
            .then(success => $scope.data.selectedGameLinks = success.data);
    }

    $scope.closeGame = function () {
        $scope.data.selectedGame = null;
        $scope.data.selectedGameLinks = [];
    }

    $scope.loadMoreGames = function () {
        let currentPage = Math.floor($scope.$ctrl.games.length / 32);
        let pageToLoad = currentPage + 1;
        $rootScope.$broadcast('search-games', { page: pageToLoad })
    };

    // =========================================================================
    // PAGINATION
    // =========================================================================
    $scope.nextGame = function () {
        $scope.showGame($scope.$ctrl.games[getSelectedGameNextIndex()].game);
    };

    $scope.previousGame = function () {
        $scope.showGame($scope.$ctrl.games[getSelectedGamePreviousIndex()].game);
    };

    function getSelectedGameIndex() {
        // null check
        if ($scope.data.selectedGame === null) {
            return 0;
        }

        // check
        return _.findIndex($scope.$ctrl.games, function (game) {
            return $scope.data.selectedGame.uid === game.game.uid;
        });
    }

    function getSelectedGameNextIndex() {
        // index check
        var gameIndex = getSelectedGameIndex();
        gameIndex++;
        if (gameIndex >= $scope.$ctrl.games.length) {
            gameIndex = 0;
        }
        return gameIndex;
    }

    // get previous game index
    function getSelectedGamePreviousIndex() {
        // index check
        var gameIndex = getSelectedGameIndex();
        gameIndex--;
        if (gameIndex < 0) {
            gameIndex = $scope.$ctrl.games.length - 1;
        }
        return gameIndex;
    }

    // =========================================================================
    // SHORTCUTS
    // =========================================================================
    hotkeys.bindTo($scope)
        // PREVIOUS GAME
        .add({
            combo: 'left',
            callback: function (e) {
                if ($scope.data.selectedGame !== null) {
                    $scope.previousGame();
                }
            }
        })
        // NEXT GAME
        .add({
            combo: 'right',
            callback: function (e) {
                if ($scope.data.selectedGame !== null) {
                    $scope.nextGame();
                }
            }
        });

})

// =============================================================================
// HTML
// =============================================================================
app.component('searchResults', {
    template: `
        <div id="search-results">
            <!-- HEADER -->
            <h1 class="ui top attached big header" >
                {{$ctrl.gamesTotalResults}} <span translate>games found
            </h1>

            <!-- CONTENT -->
            <div class="ui bottom attached segment">

                <!-- LOADING -->
                <div class="big text" ng-show="$ctrl.searching">
                    <br/>
                    <span translate>Loading</span>...
                    <br/><br/><br/>
                </div>

                <!-- GAMES -->
                <ol class="ui eight column doubling grid">
                    <li class="column" ng-repeat="game in $ctrl.games track by $index">
                        <game-card game="game.game" index="{{$index + 1}}" show-game="showGame(game)"></game-card>
                    </li>
                </ol>

                <!-- LOAD MORE -->
                <div class="ui one column grid" ng-show="$ctrl.games && $ctrl.games.length < $ctrl.gamesTotalResults">

                    <!-- BUTTON -->
                    <div class="column">
                        <a ng-click="loadMoreGames()" class="ui big {{$ctrl.searching ? 'disable' : ''}} button"
                            translate>Load more games</a>
                    </div>
                </div>
            </div>

            <!-- GAME DETAILS -->
            <game-details class="ui modal" game="data.selectedGame" game-links="data.selectedGameLinks"></game-details>
        </div>
    `,
    controller: 'SearchResultsController',
    bindings: {
        games: '=',
        gamesTotalResults: '=',
        searching: '='
    }
});
