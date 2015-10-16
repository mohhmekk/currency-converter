angular.module('frontendServices', [])
    .service('CurrencyService', ['$http', '$q', function ($http, $q) {
        return {
            getCurrencies: function () {
                var deferred = $q.defer();

                $http.get('/currency/list', {
                    params: {}
                })
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve(response.data);
                        }
                        else {
                            deferred.reject('Error retrieving list of currencies');
                        }
                    });

                return deferred.promise;
            },

            getHistory: function () {
                var deferred = $q.defer();

                $http.get('/currency/history', {
                    params: {}
                })
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve(response.data);
                        }
                        else {
                            deferred.reject('Error retrieving list of exchange history');
                        }
                    });

                return deferred.promise;
            },

            getCurrencyRate: function (currencyLeft, currencyRight) {
                var deferred = $q.defer();

                $http.get('/currency/exchange', {
                    params: {
                        currencyLeft: currencyLeft,
                        currencyRight: currencyRight
                    }
                })
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve(response.data);
                        }
                        else {
                            deferred.reject('Error retrieving rates');
                        }
                    });

                return deferred.promise;
            }
        }
    }])
    .service('UserService', ['$http', '$q', function ($http, $q) {
        return {
            getUserInfo: function () {
                var deferred = $q.defer();

                $http.get('/user')
                    .then(function (response) {
                        if (response.status == 200) {
                            deferred.resolve(response.data);
                        }
                        else {
                            deferred.reject('Error retrieving user info');
                        }
                    });

                return deferred.promise;
            },
            logout: function () {
                $http({
                    method: 'POST',
                    url: '/logout'
                })
                    .then(function (response) {
                        if (response.status == 200) {
                            window.location.reload();
                        }
                        else {
                            console.log("Logout failed!");
                        }
                    });
            }
        };
    }]);