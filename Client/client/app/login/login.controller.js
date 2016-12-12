'use strict';

angular.module('schejewelApp')
    .controller('LoginCtrl', function ($scope, $rootScope, $http, $location, Auth) {
        $scope.email = 'jacob@gmail.com';
        $scope.password = '1234';
        $scope.register = false;

        Auth.isLoggedIn().then(function (loggedIn) {
            if (loggedIn) {
                $location.path('/dashboard');
            }
        });
        $scope.validate = function () {
            if ($scope.email && $scope.password) {
                Auth.login($scope.email, $scope.password).then(function (user) {
                    $scope.user = user;
                    $rootScope.$emit('action.loggedIn', {});
                    $location.path('/dashboard');
                }, function (reason) {
                    $scope.message = reason.message;
                });
            }
        };

        $scope.doRegister = function () {
            if ($scope.email && $scope.password) {
                Auth.register($scope.email, $scope.password).then(function (user) {
                    $scope.user = user;
                    $rootScope.$emit('action.loggedIn', {});
                    $location.path('/dashboard');
                });
            }
        };

        $scope.toggleRegister = function () {
            $scope.register = !$scope.register;
        };
    });
