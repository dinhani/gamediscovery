var app = angular.module('app', ['ngCookies',
    'pascalprecht.translate', 'cfp.hotkeys', 'dcbImgFallback',
    'angulartics', 'angulartics.google.analytics']);

app.config(['$compileProvider', '$httpProvider', '$locationProvider', '$analyticsProvider', 'hotkeysProvider',
    function ($compileProvider, $httpProvider, $locationProvider, $analyticsProvider, hotkeysProvider) {
        // HTML
        $locationProvider.html5Mode({
            enabled: true
        });

        // SHORTCUTS
        hotkeysProvider.includeCheatSheet = false;

        // ANALYTICS
        $analyticsProvider.virtualPageviews(false);

        // DEBUG
        $compileProvider.debugInfoEnabled(false);
    }
]);