// =============================================================================
// JS
// =============================================================================
app.controller('LanguageSelectorController', function ($scope,
    $location, $translate) {

    // =========================================================================
    // DATA
    // =========================================================================
    $scope.data = {
        languages: { 'en': 'English', 'es': 'Español', 'fr': 'Français', 'it': 'Italiano', 'pt': 'Português' }
    }

    // =========================================================================
    // METHODS
    // =========================================================================
    $scope.language = function (language) {
        // getter
        if (language === undefined) {
            return $translate.use();
        }

        // setter
        $translate.use(language);
    };

    // =========================================================================
    // INIT
    // =========================================================================
    $scope.init = function () {
        // set language from parameter
        var language = $location.search()['lang'];
        if (language !== undefined) {
            $scope.language(language);
        }
    }
});

// =============================================================================
// HTML
// =============================================================================
// directive
app.component('languageSelector', {
    template: `
        <div class="ui dropdown item small text" ng-init="init()">
            <i class="world icon"></i>
            <div class="text" ng-bind="data.languages[language()]">English</div>
            <i class="dropdown icon"></i>
            <div class="menu">
                <div ng-click="language('en')" class="item" >English</div>
                <div ng-click="language('es')" class="item">Español</div>
                <div ng-click="language('fr')" class="item">Français</div>
                <div ng-click="language('it')" class="item">Italiano</div>
                <div ng-click="language('pt')" class="item">Português</div>
            </div>
        </div>
    `,
    controller: "LanguageSelectorController"
});
