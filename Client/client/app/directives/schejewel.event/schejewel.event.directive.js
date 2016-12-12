'use strict';

angular.module('schejewelApp')
    .directive('schejewel.event', function() {
        return {
            templateUrl: 'app/directives/schejewel.event/schejewel.event.html',
            restrict: 'EA',
            scope: {
                'event': '='
            },
            link: function($scope) {

            }
        };
    });
