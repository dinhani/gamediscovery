// =============================================================================
// HTML
// =============================================================================
app.component('languageSelector', {
    templateUrl: 'language-selector.html',
    controller: "LanguageSelectorController"
});

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
