'use strict';

describe('Directive: schejewel.admin.resources', function () {

	// load the directive's module and view
	beforeEach(module('schejewelApp'));
	beforeEach(module(
		'app/directives/schejewel.admin.resources/schejewel.admin.resources.html'
	));

	var element, scope;

	beforeEach(inject(function ($rootScope) {
		scope = $rootScope.$new();
	}));

	it('should make hidden element visible', inject(function ($compile) {
		element = angular.element(
			'<schejewel.admin.resources></schejewel.admin.resources>');
		element = $compile(element)(scope);
		scope.$apply();

	}));
});
