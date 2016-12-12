'use strict';

angular.module('schejewelApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('tours', {
        url: '/tours',
        templateUrl: 'app/tours/tours.html',
        controller: 'ToursCtrl'
      });
  });