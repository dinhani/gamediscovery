// directive
app.directive('gdMessage', function () {
    return {
        restrict: 'A',     
        controller: "MessageController",
        templateUrl: '/app/views/desktop/components/message.html',
        scope: {            
            title: "@",
            message: "=",
            level: "@",
            icon: "@"
        }
    };
});

// controller
app.controller('MessageController', function ($scope){
});

