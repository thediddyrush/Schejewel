'use strict';

angular.module('schejewelApp')
  .config(function ($stateProvider) {
    $stateProvider
      .state('create', {
        url: '/resources/create',
        templateUrl: 'app/resources/create/create.html',
        controller: 'CreateCtrl'
      });
  });