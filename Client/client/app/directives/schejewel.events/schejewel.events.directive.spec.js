'use strict';

describe('Directive: schejewel.events', function () {

	// load the directive's module and view
	beforeEach(module('schejewelApp'));
	beforeEach(module('app/directives/schejewel.events/schejewel.events.html'));

	var element, scope;

	beforeEach(inject(function ($rootScope) {
		scope = $rootScope.$new();
	}));

	it('should make hidden element visible', inject(function ($compile) {
		element = angular.element('<schejewel.events></schejewel.events>');
		element = $compile(element)(scope);
		scope.$apply();
	}));
});
