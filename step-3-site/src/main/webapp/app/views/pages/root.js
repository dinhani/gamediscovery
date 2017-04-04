app.controller('RootController', function ($scope, $rootScope, $window, $location, $translate,
    guiService, urlService) {

    // =========================================================================
    // DATA
    // =========================================================================
    $rootScope.data = {
        filters: [],
        games: [],
        gamesTotalResults: 0,
        isSearchingGames: false
    };

    // window
    $rootScope.data.window = {};
    $rootScope.data.window.height = 700;

    // meta
    $rootScope.data.title = "Game Discovery | The most advanced game search engine";
    $rootScope.data.header = "Game Discovery";
    $rootScope.data.description = "Check out new games with the same characteristics of your favorite games";

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    guiService.dropdown();
    guiService.accordion();

    // =========================================================================
    // WATCHERS
    // =========================================================================
    $scope.$watch(function () {
        return window.innerHeight;
    }, function (value) {
        $rootScope.height = value;
        $rootScope.data.window.height = value;
    });


    // =========================================================================
    // PAGE CHANGES
    // =========================================================================
    $scope.changeToMain = function () {
        // change to main
        $window.location = urlService.main();
    };

    $scope.changeToSearch = function () {
        // change to search if not in it
        if ($location.path().indexOf(urlService.recommendations()) !== 0) {
            $location.path(urlService.recommendations());
        }
        // trigger search with current values
        $scope.search();
    };

    // =========================================================================
    // AVOID UNNECESSARY RELOAD
    // =========================================================================
    // reload on back button
    $window.onpopstate = function () {
        $scope.$broadcast('onpopstate');
    };
});
