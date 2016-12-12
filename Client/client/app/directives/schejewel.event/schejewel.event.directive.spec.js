'use strict';

describe('Directive: schejewel.event', function () {

  // load the directive's module and view
  beforeEach(module('schejewelApp'));
  beforeEach(module('app/directives/schejewel.event/schejewel.event.html'));

  var element, scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<schejewel.event></schejewel.event>');
    element = $compile(element)(scope);
    scope.$apply();
    expect(element.text()).toBe('this is the schejewel.event directive');
  }));
});