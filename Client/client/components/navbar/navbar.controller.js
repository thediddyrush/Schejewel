'use strict';

angular.module('schejewelApp')
	.controller('NavbarCtrl', function ($scope, $rootScope, $location, Auth) {
		$scope.user = Auth.getCurrentUser();
		$rootScope.$on('action.loggedIn', function () {
			$scope.user = Auth.getCurrentUser();
		});
		$scope.menu = [{
			'title': 'Home',
			'link': '/'
		}, {
			'title': 'Dashboard',
			'link': '/dashboard'
		}, {
			'title': 'Events',
			'link': '/tours'
		}, {
			'title': 'Resources',
			'link': '/resources'
		}, {
			'title': 'Admin',
			'link': '/admin'
		}];

		$scope.isCollapsed = true;

		$scope.isActive = function (route) {
			return route === $location.path();
		};

		$scope.logout = function () {
			Auth.logout();

		};
	});
