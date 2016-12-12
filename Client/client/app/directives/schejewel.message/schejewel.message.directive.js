'use strict';

angular.module('schejewelApp')
    .directive('schejewel.message', function() {
        return {
            templateUrl: 'app/directives/schejewel.message/schejewel.message.html',
            restrict: 'EA',
            scope: {
                'message': '='
            },
            link: function($scope) {

            }
        };
    });
