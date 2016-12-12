'use strict';

angular.module('schejewelApp')
    .directive('schejewel.admin.resources', function(Data) {
        return {
            templateUrl: 'app/directives/schejewel.admin.resources/schejewel.admin.resources.html',
            restrict: 'EA',
            link: function($scope) {
                Data.getResources().then(function(data) {
                    $scope.resources = data;
                });

                $scope.addResource = function() {
                    if (!$scope.resources) {
                        $scope.resources = [];
                    }
                    $scope.resources.push({
                        name: 'New item'
                    });
                };

                $scope.toggleEdit = function(index) {
                    $scope.resources[index].editable = !$scope.resources[index].editable;
                };

                $scope.deleteResource = function(index) {
                    $scope.resources.splice(index, 1);
                };
            }
        };
    });
