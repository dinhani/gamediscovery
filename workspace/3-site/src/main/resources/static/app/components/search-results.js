// =============================================================================
// HTML
// =============================================================================
app.component('searchResults', {
    templateUrl: 'search-results.html',
    controller: 'SearchResultsController',
    bindings: {
        games: '=',
        gamesTotalResults: '=',
        searching: '='
    }
});

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
