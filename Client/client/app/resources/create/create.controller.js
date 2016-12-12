'use strict';

angular.module('schejewelApp')
	.controller('CreateCtrl', function ($scope, Data) {
		Data.getTours().then(function (tours) {
			$scope.tours = tours;
			console.log('tours', tours);
		});
	});
