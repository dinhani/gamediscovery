// directive
app.directive('gdGameRelationship', function () {
    return {
        restrict: 'A',
        controller: "GameRelationshipController",
        templateUrl: '/app/views/components/game-relationship.html',
        scope: {
            game: "=",
            relationships: "=",
            bottomAttached: "@"
        },
        link: function (scope) {
            if (scope.bottomAttached === undefined) {
                scope.bottomAttached = false;
            }
        }
    };
});

// controller
app.controller('GameRelationshipController', function ($scope) {

    $scope.addFilter = function (conceptEntry) {
        $scope.$parent.addFilter(conceptEntry);
    };
});