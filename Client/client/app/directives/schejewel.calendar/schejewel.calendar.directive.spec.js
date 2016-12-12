'use strict';

describe('Directive: schejewel.calendar', function () {

	// load the directive's module and view
	beforeEach(module('schejewelApp'));
	beforeEach(module(
		'app/directives/schejewel.calendar/schejewel.calendar.html'));

	var element, scope;

	beforeEach(inject(function ($rootScope) {
		scope = $rootScope.$new();
	}));

	it('should make hidden element visible', inject(function ($compile) {
		element = angular.element('<schejewel.calendar></schejewel.calendar>');
		element = $compile(element)(scope);
		scope.$apply();
	}));
});
