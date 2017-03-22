app.controller('GameController', function ($scope, $rootScope,
        guiService) {

    // =========================================================================
    // DATA
    // =========================================================================
    $scope.imageLimit = 12;

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    $scope.initialize = function () {
        // accordion
        guiService.accordion();
    };

    // =========================================================================
    // ELEMENT HEIGHT
    // =========================================================================
    $scope.adjustHeight = function (elementToChangeHeigthSelector, diff) {
        return guiService.heightToFooter(elementToChangeHeigthSelector, diff);
    };

    $scope.addFilter = function (conceptEntry) {
        $scope.$parent.addFilter(conceptEntry);
    };
});