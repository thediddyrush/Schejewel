'use strict';

angular.module('schejewelApp')
	.directive('schejewel.buttonNav', function () {
		return {
			templateUrl: 'app/directives/schejewel.buttonNav/schejewel.buttonNav.html',
			restrict: 'EA',
			scope: {
				visibleDirective: '='
			},
			link: function ($scope) {
				$scope.setVisible = function (directiveName) {
					$scope.visibleDirective = directiveName;
				};
			}
		};
	});
