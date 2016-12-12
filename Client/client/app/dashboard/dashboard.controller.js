'use strict';

angular.module('schejewelApp')
	.controller('DashboardCtrl', function ($scope, Auth, Data) {
		Data.getResources(new Date()).then(function (data) {
			console.log('data', data);
		});

		$scope.events = [{
			title: 'Pick up group',
			datetime: '1428333624006'
		}, {
			title: 'Go fishing',
			datetime: '1438333624006'
		}, {
			title: 'Eat Dinner',
			datetime: '1438333624006'
		}, {
			title: 'Return to Ships',
			datetime: '1438333624006'
		}, {
			title: 'Pick up next group',
			datetime: '1438333624006'
		}, {
			title: 'Go fishing',
			datetime: '1438333624006'
		}];

		$scope.messages = [{
			from: 'Joe',
			subject: 'Flat tire on bus',
			message: 'Hey, I\'m just following up on the flat tire on the bus.  Any progress for that?',
			datetime: '1428333624006'
		}, {
			from: 'Jim',
			subject: 'Sick',
			message: 'I woke up with a fever today. I won\'t be able to make it in',
			datetime: '1438333624006'
		}, {
			from: 'Jack',
			subject: 'Company party',
			message: 'This is a reminder about the company party.  It is this friday at Jim\'s house.  It starts at 7.  Make sure to bring the food item you were assigned',
			datetime: '1438333624006'
		}];


		$scope.visibleDirective = 'calendar';

	});
