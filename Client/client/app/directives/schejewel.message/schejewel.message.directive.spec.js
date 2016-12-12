'use strict';

describe('Directive: schejewel.message', function () {

	// load the directive's module and view
	beforeEach(module('schejewelApp'));
	beforeEach(module('app/directives/schejewel.message/schejewel.message.html'));

	var element, scope;

	beforeEach(inject(function ($rootScope) {
		scope = $rootScope.$new();
	}));

	it('should make hidden element visible', inject(function ($compile) {
		element = angular.element('<schejewel.message></schejewel.message>');
		element = $compile(element)(scope);
		scope.$apply();
	}));
});
