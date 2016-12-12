'use strict';

/* jshint ignore:start */
angular.module('schejewelApp')
	.factory('Auth', function Auth($location, $rootScope, $http, $cookieStore, $q) {
		var Base64 = {
				_keyStr: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=',
				encode: function (e) {
					var t = '';
					var n, r, i, s, o, u, a;
					var f = 0;
					e = Base64._utf8_encode(e);
					while (f < e.length) {
						n = e.charCodeAt(f++);
						r = e.charCodeAt(f++);
						i = e.charCodeAt(f++);
						s = n >> 2;
						o = (n & 3) << 4 | r >> 4;
						u = (r & 15) << 2 | i >> 6;
						a = i & 63;
						if (isNaN(r)) {
							u = a = 64
						} else if (isNaN(i)) {
							a = 64
						}
						t = t + this._keyStr.charAt(s) + this._keyStr.charAt(o) + this._keyStr.charAt(
							u) + this._keyStr.charAt(a)
					}
					return t
				},
				decode: function (e) {
					var t = '';
					var n, r, i;
					var s, o, u, a;
					var f = 0;
					e = e.replace(/[^A-Za-z0-9\+\/\=]/g, "");
					while (f < e.length) {
						s = this._keyStr.indexOf(e.charAt(f++));
						o = this._keyStr.indexOf(e.charAt(f++));
						u = this._keyStr.indexOf(e.charAt(f++));
						a = this._keyStr.indexOf(e.charAt(f++));
						n = s << 2 | o >> 4;
						r = (o & 15) << 4 | u >> 2;
						i = (u & 3) << 6 | a;
						t = t + String.fromCharCode(n);
						if (u != 64) {
							t = t + String.fromCharCode(r)
						}
						if (a != 64) {
							t = t + String.fromCharCode(i)
						}
					}
					t = Base64._utf8_decode(t);
					return t
				},
				_utf8_encode: function (e) {
					e = e.replace(/\r\n/g, "\n");
					var t = "";
					for (var n = 0; n < e.length; n++) {
						var r = e.charCodeAt(n);
						if (r < 128) {
							t += String.fromCharCode(r)
						} else if (r > 127 && r < 2048) {
							t += String.fromCharCode(r >> 6 | 192);
							t += String.fromCharCode(r & 63 | 128)
						} else {
							t += String.fromCharCode(r >> 12 | 224);
							t += String.fromCharCode(r >> 6 & 63 | 128);
							t += String.fromCharCode(r & 63 | 128)
						}
					}
					return t
				},
				_utf8_decode: function (e) {
					var t = "";
					var n = 0;
					var c2, c1, c3;
					var r = c1 = c2 = 0;
					while (n < e.length) {
						r = e.charCodeAt(n);
						if (r < 128) {
							t += String.fromCharCode(r);
							n++
						} else if (r > 191 && r < 224) {
							c2 = e.charCodeAt(n + 1);
							t += String.fromCharCode((r & 31) << 6 | c2 & 63);
							n += 2
						} else {
							c2 = e.charCodeAt(n + 1);
							c3 = e.charCodeAt(n + 2);
							t += String.fromCharCode((r & 15) << 12 | (c2 & 63) << 6 | c3 & 63);
							n += 3
						}
					}
					return t
				}
			}
			/* ignore:end */
		var currentUser = {};

		var api_url =
			'http://Default-Environment-69yjsdh6ez.elasticbeanstalk.com/api/';
		if ($cookieStore.get('token')) {
			var token = $cookieStore.get('token');
			var splitToken = token.split('.')[0];
			var decoded = Base64.decode(splitToken);

			currentUser = JSON.parse(decoded);
		};

		var setupCookie = function (token) {
			if (!token) {
				var token = $cookieStore.get('token');
				var splitToken = token.split('.')[0];
				var decoded = Base64.decode(splitToken);
				currentUser = JSON.parse(decoded);
			} else {
				$cookieStore.put('token', token);

				var splitToken = token.split('.')[0];
				var decoded = Base64.decode(splitToken);
				currentUser = JSON.parse(decoded);
			}
		};

		var login = function (username, password) {
			var deferred = $q.defer();

			$http.post(api_url + 'login', {
				'username': username,
				'password': password
			}).
			success(function (data, status, headers, config) {
				var token = headers()['x-auth-token'];
				setupCookie(token);
				deferred.resolve(currentUser);
			}).
			error(function (err) {
				deferred.reject(err);
			}.bind(this));

			return deferred.promise;
		};

		return {

			/**
			 * Authenticate user and save token
			 *
			 * @param  {Object}   user     - login info
			 * @param  {Function} callback - optional
			 * @return {Promise}
			 */
			login: function (username, password) {
				return login(username, password);
			},

			/**
			 * Delete access token and user info
			 *
			 * @param  {Function}
			 */
			logout: function () {
				$cookieStore.remove('token');
				currentUser = {};
				// $location.path('/login');
			},



			register: function (username, password) {
				var deferred = $q.defer();

				$http.post(api_url + 'user', {
					'username': username,
					'password': password
				}).
				success(function (data, status, headers, config) {
					deferred.resolve(login(username, password));
				}).
				error(function (err) {
					this.logout();
					deferred.reject(err);
				}.bind(this));

				return deferred.promise;
			},


			/**
			 * Gets all available info on authenticated user
			 *
			 * @return {Object} user
			 */
			getCurrentUser: function () {
				return currentUser;
			},



			/**
			 * Get auth token
			 */
			getToken: function () {
				return $cookieStore.get('token');
			},


			/**
			 * Check if a user is logged in
			 *
			 * @return {Boolean}
			 */
			isLoggedIn: function () {
				var deferred = $q.defer();
				deferred.resolve(currentUser && !!currentUser.username);
				return deferred.promise;
			},



		};
	});
