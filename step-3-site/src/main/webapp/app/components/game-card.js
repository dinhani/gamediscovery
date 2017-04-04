// directive
app.directive('gdConceptCard', function () {
    return {
        restrict: 'A',        
        templateUrl: '/app/views/components/concept-card.html',
        scope: {
            index: "@",
            conceptEntry: "="            
        }
    };
});