var app = angular.module('app', [
    'ngCookies',
    'ngTagsInput',
    'pascalprecht.translate',
    'dcbImgFallback',
    'cfp.hotkeys'
]);

app.config(['$compileProvider', '$locationProvider', 'hotkeysProvider',
    function ($compileProvider, $locationProvider, hotkeysProvider) {
        // DEBUG
        $compileProvider.debugInfoEnabled(false);

        // HTML
        $locationProvider.html5Mode({
            enabled: true
        });

        // SHORTCUTS
        hotkeysProvider.includeCheatSheet = false;
    }
]);

app.controller('AppController', function ($scope, $rootScope, $window, $location,
    guiService, urlService) {

    // =========================================================================
    // DATA
    // =========================================================================
    $scope.data = {
        filters: [],
        games: [],
        gamesTotalResults: 0,
        isSearchingGames: false
    };

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    guiService.dropdown();
    guiService.accordion();


    // =========================================================================
    // PAGE CHANGES
    // =========================================================================
    $scope.changeToMain = function () {
        $window.location = urlService.main();
    };

    // =========================================================================
    // AVOID UNNECESSARY RELOAD
    // =========================================================================
    // reload on back button
    $window.onpopstate = function () {
        $scope.$broadcast('onpopstate');
    };
});
