'use strict';

angular.module('schejewelApp')
	.directive('schejewel.tripView', function (Data) {
		return {
			templateUrl: 'app/directives/schejewel.tripView/schejewel.tripView.html',
			restrict: 'EA',
			scope: {
				tours: '='
			},
			link: function ($scope) {
				$scope.displayDate = Data.getDisplayDate();

				$scope.dayHours = ['6:00am', '6:30am', '7:00am',
					'7:30am', '8:00am', '8:30am', '9:00am',
					'9:30am', '10:00am', '10:30am', '11:00am',
					'11:30am', '12:00', '12:30', '1:00', '1:30',
					'2:00', '2:30', '3:00', '3:30', '4:00', '4:30',
					'5:00', '5:30', '6:00', '6:30', '7:00', '7:30',
					'8:00', '8:30', '9:00', '9:30', '10:00',
					'10:30', '11:00', '11:30', '12:00am'
				];


				function getMinutesSince6(d) {
					var e = new Date(d);
					e.setHours(6);
					e.setMinutes(0);
					return Math.abs((e - d)) / 1000 / 60;
				}

				Date.prototype.addDays = function (days) {
					this.setDate(this.getDate() + days);
					return this;
				};

				$scope.nextDay = function () {
					$scope.displayDate.addDays(1);
					Data.setDisplayDate($scope.displayDate);
				};

				$scope.prevDay = function () {
					$scope.displayDate.addDays(-1);
					Data.setDisplayDate($scope.displayDate);
				};

				$scope.currentDay = function () {
					$scope.displayDate = new Date();
					Data.setDisplayDate($scope.displayDate);
				};

				$scope.currentTimeStyle = function () {
					var top = getMinutesSince6(new Date()) + 83; //this 83 is the offset that the line needs to line up with the top of the page.  Probably not the best way to do it :/
					return {
						position: 'absolute',
						top: top
					};
				};

				$scope.fadeStyle = function (index, events) {
					var r = 200 - (index * 10);
					var g = 0 + (index * 25);
					var b = 0 + (index * 1);

					r = r >= 0 ? r : 0;
					g = g >= 0 ? g : 0;
					b = b >= 0 ? b : 0;

					if (events[index].statusId !== 1) {
						console.log('events[index].startDate', events[index].startDate);
					}
					var height = events[index].duration;
					var startDate = new Date(events[index].startDate +
						' ' + events[index].startTime);

					var top = getMinutesSince6(startDate) + 40;
					return {
						position: 'absolute',
						top: top + 'px',
						left: '0px',
						width: '100%',
						background: 'rgba(' + (events[index].statusId !== 1 ? 255 : r) + ', ' +
							r + ', ' +
							r + ', 1)',
						color: 'white',
						height: height + 'px'
					};
				};

				$scope.hourStyle = function (index) {
					var top = (index * 30);

					return {
						position: 'absolute',
						top: top + 'px'
					};
				};

				$scope.tourColumns = [];

				var orderTours = function () {

				};

				var formatRows = function () {
					orderTours();
					for (var i in $scope.tours) {

					}
				};



				formatRows();
			}
		};
	}).filter('day', function () {
		return function (input, wantedDate) {
			var wantedEvents = [];
			for (var i in input) {
				var current = input[i];
				var eventDate = new Date(current.startDate);
				var isSameDay = (eventDate.getDate() === wantedDate.getDate() &&
					eventDate.getMonth() === wantedDate.getMonth() &&
					eventDate.getFullYear() === wantedDate.getFullYear()
				);
				if (isSameDay) {
					wantedEvents.push(current);
				}
			}


			return wantedEvents;
		};
	});
