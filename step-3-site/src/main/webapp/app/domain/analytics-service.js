app.service('analyticsService', function ($analytics, $location, $timeout) {

    this.trackEvent = function (category, event, value) {
        $analytics.eventTrack(event, {category: category, label: value});
    };

    this.trackPage = function () {
        $timeout(function () {            
            $analytics.pageTrack($location.absUrl());
        }, 250);
    };

    this.trackSearch = function (searchTerm) {
        if (typeof ga !== 'undefined') {
            ga('send', 'pageview', '/backend/concepts/all?query=' + searchTerm);
        }
    };

    this.trackSpeed = function (category, event, timing) {
        if (typeof ga !== 'undefined') {            
            ga('send', 'timing', category, event, timing);
        }
    };
});