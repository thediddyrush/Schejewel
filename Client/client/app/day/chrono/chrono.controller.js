'use strict';

angular.module('schejewelApp')
    .controller('ChronoCtrl', function($scope) {
        $scope.tours = [{
            id: 'wjosdf92jfds',
            events: [{
                title: 'Ship arrives'
            }, {
                title: 'lunch'
            }]
        }, {
            events: [{
                title: 'Ship arrives'
            }, {
                title: 'lunch'
            }]
        }];
    });
