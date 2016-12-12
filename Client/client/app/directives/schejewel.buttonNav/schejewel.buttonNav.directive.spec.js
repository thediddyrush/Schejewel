'use strict';

describe('Directive: schejewel.buttonNav', function () {

	// load the directive's module and view
	beforeEach(module('schejewelApp'));
	beforeEach(module(
		'app/directives/schejewel.buttonNav/schejewel.buttonNav.html'));

	var element, scope;

	beforeEach(inject(function ($rootScope) {
		scope = $rootScope.$new();
	}));

	it('should make hidden element visible', inject(function ($compile) {
		element = angular.element(
			'<schejewel.button-nav></schejewel.button-nav>');
		element = $compile(element)(scope);
		scope.$apply();
	}));
});
