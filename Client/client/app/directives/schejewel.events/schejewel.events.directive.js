'use strict';

angular.module('schejewelApp')
    .directive('schejewel.events', function() {
        return {
            templateUrl: 'app/directives/schejewel.events/schejewel.events.html',
            restrict: 'EA',
            scope: {
                'events': '='
            },
            link: function($scope) {}
        };
    });
