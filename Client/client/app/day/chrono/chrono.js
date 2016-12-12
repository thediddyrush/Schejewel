'use strict';

angular.module('schejewelApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('chrono', {
        url: '/day/chrono',
        templateUrl: 'app/day/chrono/chrono.html',
        controller: 'ChronoCtrl'
      });
  });