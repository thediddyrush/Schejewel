'use strict';

angular.module('schejewelApp')
    .controller('FooterCtrl', function($scope) {
        $scope.links = [{
            label: 'Contact us',
            url: ''
        }, {
            label: 'About us',
            url: ''
        }, {
            label: 'Partners',
            url: ''
        }, {
            label: 'Feedback',
            url: ''
        }];
    });
