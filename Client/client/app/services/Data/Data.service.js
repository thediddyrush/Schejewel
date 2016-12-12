'use strict';

angular.module('schejewelApp')
    .service('Data', function ($q, $http, $cookieStore, Auth) {
        var url =
            'http://Default-Environment-69yjsdh6ez.elasticbeanstalk.com/api/';
        var Token = Auth.getToken();

        return {
            getResources: function (date) {

                /*
					Ryan, I changed the Data.getCookie and Data.setCookie
					methods to take a javascript date object, rather than
					a string.  That way it is easier to save it.  It broke
					 the getResources method though.  Before you were passing
					 this method a dateString.  Not this method accepts a
					date object.  So you just have to turn the date object
					in to a date string and it should work again.  I don't
					know the format that the date string is supposed to be
					in, but the following line should get you close I think.
					Can you finish it and delete this comment?
				*/


                // This formats the dateString like 2015-03-02
                var dateString = '' +
                    (date.getYear() + 1900) + '-' +
                    (date.getMonth() > 9 ? (parseInt(date.getMonth())+1).toString() : '0' + (parseInt(date.getMonth())+1).toString()) + '-' +
                    (date.getDate() > 9 ? date.getDate() : '0' + date.getDate());


                // This formats the dateString in milliseconds.  ie 1431561600000
                // I thought this one should work, but it doesn't seem to be
                //dateString = date.getTime();
                console.log('dateString', dateString);
                var deferred = $q.defer();
                if (dateString === undefined) {
                    $http.defaults.headers.common['X-AUTH-TOKEN'] =
                        Token;
                    $http.get(url + 'resources').success(function (data, status, headers, config) {
                        deferred.resolve(data);
                    });
                } else {
                    $http.defaults.headers.common['X-AUTH-TOKEN'] =
                        Token;
                    console.log(url + 'resources/' + dateString);
                    $http.get(url + 'resources/' + dateString /*+milliseconds.getTime()*/ )
                        .success(
                            function (data, status, headers, config) {
                                deferred.resolve(data);
                            });
                }

                return deferred.promise;
            },
            getTours: function () {
                var deferred = $q.defer();
                $http.defaults.headers.common['X-AUTH-TOKEN'] = Token;
                $http.get(url + 'tours').success(function (data) {
                    deferred.resolve(data);
                });

                return deferred.promise;
            },
            addTour: function (tourData) {
                var deferred = $q.defer();
                if (tourData === undefined) {
                    return;
                } else {
                    $http.defaults.headers.common['X-AUTH-TOKEN'] =
                        Token;
                    $http.post(url + 'tour/' + tourData).success(
                        function (data) {
                            deferred.resolve(data);
                        });
                }

                return deferred.promise;
            },
            getDisplayDate: function () { //YYYY-MM-DD
                return new Date($cookieStore.get('date'));
            },
            setDisplayDate: function (dateString) { //YYYY-MM-DD
                $cookieStore.put('date', dateString);
            }

        };
    });
