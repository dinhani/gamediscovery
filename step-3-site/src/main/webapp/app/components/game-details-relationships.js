// =============================================================================
// JS
// =============================================================================
app.controller('GameDetailsRelationshipsController', function ($scope, $rootScope) {

    $scope.addFilter = function (filter) {
        let simplifiedFilter = { uid: filter.uid, name: filter.name };
        $rootScope.$broadcast('add-filter', { filter: simplifiedFilter });
    };
});

// =============================================================================
// HTML
// =============================================================================
// directive
app.component('gameDetailsRelationships', {
    template: `
        <table class="ui striped celled {{$ctrl.bottomAttached ? 'bottom attached' : ''}} table">
            <tr ng-repeat="relationship in $ctrl.relationships" ng-if="$ctrl.game[relationship].length" style="padding: 0.5rem;">

                <!-- HEADER -->
                <td class="three wide">
                    <h4 class="small header" translate>
                        {{$ctrl.game.type.relationships[relationship].name}}
                    </h4>
                </td>

                <!-- DATA -->
                <td class="thirteen wide">
                    <div class="ui four column doubling grid">
                        <span class="column small text" ng-repeat="conceptEntry in $ctrl.game[relationship]">
                            <a class="clickable" ng-click="addFilter(conceptEntry)">{{conceptEntry.name}}</a>
                        </span>
                    </div>
                </td>
            </tr>
        </table>
    `,
    controller: "GameDetailsRelationshipsController",
    bindings: {
        game: "=",
        relationships: "=",
        bottomAttached: "="
    }
});
