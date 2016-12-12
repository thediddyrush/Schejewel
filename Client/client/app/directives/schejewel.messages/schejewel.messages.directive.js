'use strict';

angular.module('schejewelApp')
    .directive('schejewel.messages', function() {
        return {
            templateUrl: 'app/directives/schejewel.messages/schejewel.messages.html',
            restrict: 'EA',
            scope: {
                'messages': '=',
                'hide': '=',
                'value': '='
            },
            link: function($scope) {}
        };
    });
