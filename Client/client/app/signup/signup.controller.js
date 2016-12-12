'use strict';

angular.module('schejewelApp')
    .controller('SignupCtrl', function($scope) {
        $scope.signup = function() {
            if ($scope.email) {
                console.log('$scope.email', $scope.email);
                console.log('$scope.password', $scope.password);
                $scope.tried = true;
            }
        };
    });
