// =============================================================================
// HTML
// =============================================================================
app.component('gameDetailsRelationships', {
    templateUrl: 'game-details-relationships.html',
    controller: "GameDetailsRelationshipsController",
    bindings: {
        game: "=",
        relationships: "=",
        bottomAttached: "="
    }
});


// =============================================================================
// JS
// =============================================================================
app.controller('GameDetailsRelationshipsController', function ($scope, $rootScope) {

    $scope.addFilter = function (filter) {
        let simplifiedFilter = { uid: filter.uid, name: filter.name };
        $rootScope.$broadcast('add-filter', { filter: simplifiedFilter });
    };
});

