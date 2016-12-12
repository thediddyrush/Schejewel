'use strict';

describe('Directive: schejewel.messages', function () {

  // load the directive's module and view
  beforeEach(module('schejewelApp'));
  beforeEach(module('app/directives/schejewel.messages/schejewel.messages.html'));

  var element, scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<schejewel.messages></schejewel.messages>');
    element = $compile(element)(scope);
    scope.$apply();
    expect(element.text()).toBe('this is the schejewel.messages directive');
  }));
});