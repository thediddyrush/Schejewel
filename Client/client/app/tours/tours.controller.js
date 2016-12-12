'use strict';

angular.module('schejewelApp')
    .controller('ToursCtrl', function ($scope, Data) {
        Data.getTours().then(function (tours) {
            console.log('tours', tours);
            $scope.tours = tours;
        });


        // {
        // 	title: 'Crab Fishing Tour',
        // 	personCount: 58,
        // 	events: [{
        // 		title: 'Event 1',
        // 		startTime: new Date(2015, 3, 3, 6, 0, 0),
        // 		endTime: new Date(2015, 3, 3, 7, 0, 0),
        // 	}, {
        // 		title: 'Event 3',
        // 		startTime: new Date(2015, 3, 3, 7, 0, 0),
        // 		endTime: new Date(2015, 3, 3, 11, 0, 0),
        // 	}, {
        // 		title: 'Event 4',
        // 		startTime: new Date(2015, 3, 3, 11, 0, 0),
        // 		endTime: new Date(2015, 3, 3, 12, 0, 0),
        // 	}]
        // }

    });
