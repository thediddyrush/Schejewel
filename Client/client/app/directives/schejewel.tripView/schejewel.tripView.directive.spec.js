'use strict';

describe('Directive: schejewel.tripView', function () {

	// load the directive's module and view
	beforeEach(module('schejewelApp'));
	beforeEach(module(
		'app/directives/schejewel.tripView/schejewel.tripView.html'));

	var element, scope;

	beforeEach(inject(function ($rootScope) {
		scope = $rootScope.$new();
	}));

	it('should make hidden element visible', inject(function ($compile) {
		element = angular.element('<schejewel.trip-view></schejewel.trip-view>');
		element = $compile(element)(scope);
		scope.$apply();
		console.log('element', element);

		// expect(element.text()).toBe('this is the schejewel.tripView directive');
	}));
});
