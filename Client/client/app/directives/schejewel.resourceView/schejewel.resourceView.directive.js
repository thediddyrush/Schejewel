'use strict';

angular.module('schejewelApp')
	.directive('schejewel.resourceView', function (Data) {
		return {
			templateUrl: 'app/directives/schejewel.resourceView/schejewel.resourceView.html',
			restrict: 'EA',
			link: function ($scope) {

				$scope.formatDate = function(n){
					return n<10? '0'+n:''+n;
				}

				$scope.ViewDate;

				$scope.getDateString = function(){
					$scope.ViewDate = Data.getDisplayDate();
					if($scope.ViewDate === undefined){
						var today = new Date();
						$scope.ViewDate = today;
						Data.setDisplayDate($scope.ViewDate);
					}
				}

				$scope.rawResourceData;


				$scope.fetchResources = function(){
					Data.getResources($scope.ViewDate).then(function(data) {
						$scope.rawResourceData = data;
						$scope.convertDataToCalendar();
						$scope.loadResourceButtons();
						$scope.InitAllValues();
	        });
				}

				$scope.resourceNameList = []; //This stores all name and color combinations for the data fetched.

				$scope.resourceData =
				[
					{
						title  : 'Bus1',
						start  : '2015-03-29T18:30:00',
						end  : '2015-03-29T20:30:00'
					},
					{
						title  : 'Bus2',
						start  : '2015-03-29T18:30:00',
						end  : '2015-03-29T20:30:00'
					}
				];

				$scope.activeResources = [];
				$scope.resourceList = [];

				$scope.colors = ['#0099CC', '#378006', '#ff0000', '#40e0d0', '#008000', '#468499', '#800000', '#0000ff', '#333333']
				//$scope.calendarData = {start:2015-03-29T20:41:02+00:00, end:2015-03-29T20:41:06+00:0};
				$scope.eventList = {events:[]}
		    $scope.events = {}

				$scope.InitAllValues = function(){
					$scope.activeResources = [];
					for(var i = 0; i < $scope.resourceNameList.length; i++){
						$scope.activeResources.push($scope.resourceNameList[i]);
					}
					$scope.createNewEventList($scope.activeResources);
				}

				$scope.addMinutes = function (time, minsToAdd) {
				function D(J){ return (J<10? '0':'') + J;};
				var piece = time.split(':');
				var mins = piece[0]*60 + +piece[1] + +minsToAdd;
				return D(mins%(24*60)/60 | 0) + ':' + D(mins%60);
				}

				$scope.convertDataToCalendar = function(){
					//move/convert rawResourceData to resourceData
					$scope.resourceData = []
					for(var i = 0; i < $scope.rawResourceData.length; i++){
						var newResourceEntry =  {};
						newResourceEntry.title = $scope.rawResourceData[i].name;
						newResourceEntry.start = $scope.rawResourceData[i].startDate + "T" + $scope.rawResourceData[i].startTime; //Weird date format uses 'T'
						var endTime = $scope.addMinutes($scope.rawResourceData[i].startTime, String($scope.rawResourceData[i].duration))
						newResourceEntry.end = $scope.rawResourceData[i].startDate + "T" + endTime + ":00";
						$scope.resourceData.push(newResourceEntry);
					}
				}

				$scope.createNewEventList = function(events){
					$scope.eventList = {events: []};
					for(var j = 0; j < events.length; j++){
						for(var i = 0; i < $scope.resourceData.length; i++){
							if($scope.resourceData[i].title === events[j].name){
								var event = $scope.resourceData[i];
								event.backgroundColor = $scope.colors[events[j].color];
								$scope.eventList.events.push(event);
							}
						}
					}
					$('#calendar').fullCalendar('removeEvents')
					$('#calendar').fullCalendar('removeEventSource', $scope.eventList);
					$('#calendar').fullCalendar('addEventSource', $scope.eventList);
					$('#calendar').fullCalendar('refetchEvents');
				}

				$scope.loadResourceButtons = function(){
					$('.resourceButton').remove();
					$scope.resourceList = [];
					$scope.resourceNameList = [];
					for(var i = 0; i < $scope.resourceData.length; i++){
						var newResource = true;
						for(var j = 0; j < $scope.resourceList.length; j++){
							if($scope.resourceData[i].title === $scope.resourceList[j]){
								newResource = false;
							}
						}
						if(newResource){
							$scope.resourceList.push($scope.resourceData[i].title);
						}
					}
					for(var i = 0; i < $scope.resourceList.length; i++){
						var resourceData = {name: $scope.resourceList[i], color : i};
						$scope.resourceNameList.push(resourceData);
						$('.buttonHolder').append('<button style="margin-bottom: 20px;"  value= "' + i + '"class="resourceButton" title="'+ $scope.resourceList[i] +'">'+ $scope.resourceList[i] +'</button>');
					}
				}

				$(document).on( "click", '.resourceButton', function () {
					var alreadyIn = false;
					for(var i = 0; i < $scope.activeResources.length; i++){
						if($scope.activeResources[i].name === this.title){
							$scope.activeResources.splice(i, 1);
							alreadyIn = true;
						}
					}
					if(!alreadyIn){
						var nameColorPair = {name: "", color:-1};
						nameColorPair.name = this.title;
						nameColorPair.color = this.value;
						$scope.activeResources.push(nameColorPair);
					}
					$scope.createNewEventList($scope.activeResources);
				});
				$scope.getDateString();
				$scope.fetchResources();
				var date = $scope.ViewDate;
				$('#calendar').fullCalendar({
					allDaySlot: false,
					defaultView: 'agendaDay',
					aspectRatio: 1.5,
					minTime: "06:00:00",
					events: $scope.eventList,
					viewRender: function (element) {
						$scope.resourceData = [];
						$scope.eventList = {events: []};
						$scope.activeResources = [];
						$scope.ViewDate = (element.end.toDate());
						Data.setDisplayDate($scope.ViewDate);
						$scope.fetchResources();
						$('#calendar').fullCalendar('removeEvents')
						$('#calendar').fullCalendar('removeEventSource', $scope.eventList);
						$('#calendar').fullCalendar('addEventSource', $scope.eventList);
						$('#calendar').fullCalendar('refetchEvents');
    			}
				});
				$('#calendar').fullCalendar( 'gotoDate', date);
			}
		};
	});
