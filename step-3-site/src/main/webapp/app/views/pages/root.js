app.controller('RootController', function ($scope, $rootScope, $window, $location, $translate,
        guiService, urlService) {

    // =========================================================================
    // DATA
    // =========================================================================
    $rootScope.data = {};

    // window
    $rootScope.data.window = {};
    $rootScope.data.window.height = 700;

    // meta
    $rootScope.data.title = "Game Discovery | The most advanced game search engine";
    $rootScope.data.header = "Game Discovery";
    $rootScope.data.description = "Check out new games with the same characteristics of your favorite games";

    // languages
    $rootScope.data.languages = {'en': 'English', 'es': 'Español', 'fr':'Français', 'it':'Italiano', 'pt': 'Português'};

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    guiService.dropdown();

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
    // ELEMENT HEIGHT
    // =========================================================================
    $scope.adjustHeight = function (elementToChangeHeigthSelector, diff) {
        return guiService.heightToFooter(elementToChangeHeigthSelector, diff);
    };

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

    $scope.changeToContribute = function () {
        // change to contribute
        $location.path(urlService.contribute());
    };

    // =========================================================================
    // AVOID UNNECESSARY RELOAD
    // =========================================================================
    // reload on back button
    $window.onpopstate = function () {
        $scope.$broadcast('onpopstate');
    };

    // =========================================================================
    // LANGUAGE
    // =========================================================================
    $rootScope.language = function (language) {
        // getter
        if (language === undefined) {
            return $translate.use();
        }

        // setter
        $translate.use(language)
                // update GUI if necessary
                .then(function () {
                    if ($rootScope.updateGUI !== undefined) {
                        $rootScope.updateGUI();
                    }
                });
    };
});
