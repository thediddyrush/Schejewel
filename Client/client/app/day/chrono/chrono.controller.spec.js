'use strict';

describe('Controller: ChronoCtrl', function () {

  // load the controller's module
  beforeEach(module('schejewelApp'));

  var ChronoCtrl, scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ChronoCtrl = $controller('ChronoCtrl', {
      $scope: scope
    });
  }));

  it('should ...', function () {
    expect(1).toEqual(1);
  });
});
