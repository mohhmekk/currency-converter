angular.module('currencyExchangeApp', ['frontendServices', 'spring-security-csrf-token-interceptor'])
    .controller('CurrencyExchangeCtrl', ['$scope', 'CurrencyService', 'UserService', '$timeout',
        function ($scope, CurrencyService, UserService, $timeout) {

            $scope.vm = {
                currencies: [],
                exchangesHistory: [],
                currencyLeft: null,
                currencyRight: null,
                exchangeData: null,
                isSelectionEmpty: true,
                value: 0,
                convertResult: null,
                errorMessages: [],
                infoMessages: []
            };

            updateUserInfo();
            loadHistoryData();
            loadCurrencyData();

            function showErrorMessage(errorMessage) {
                clearMessages();
                $scope.vm.errorMessages.push({description: errorMessage});
            }

            function updateUserInfo() {
                UserService.getUserInfo()
                    .then(function (userInfo) {
                        $scope.vm.userName = userInfo.userName;
                    },
                    function (errorMessage) {
                        showErrorMessage(errorMessage);
                    });
            }

            function markAppAsInitialized() {
                if ($scope.vm.appReady == undefined) {
                    $scope.vm.appReady = true;
                }
            }

            function loadCurrencyData() {
                CurrencyService.getCurrencies()
                    .then(function (data) {

                        $scope.vm.errorMessages = [];

                        $scope.vm.currencies = _.map(data, function (currency) {
                            currency.desc = currency.shortName + ' - ' + currency.name;
                            return currency;
                        });

                        markAppAsInitialized();

                        if ($scope.vm.currencies && $scope.vm.currencies.length == 0) {
                            showInfoMessage("No currency found.");
                        }
                    },
                    function (errorMessage) {
                        showErrorMessage(errorMessage);
                        markAppAsInitialized();
                    });
            }

            function loadHistoryData() {
                CurrencyService.getHistory()
                    .then(function (data) {

                        $scope.vm.errorMessages = [];

                        $scope.vm.exchangesHistory = _.map(data, function (exchange) {
                            exchange.desc = exchange.fromCurrency.shortName + ' = ' + exchange.value + ' ' + exchange.toCurrency.shortName;
                            exchange.date = exchange.exchangeDate;
                            return exchange;
                        });

                        if ($scope.vm.exchangesHistory && $scope.vm.exchangesHistory.length == 0) {
                            showInfoMessage("No History found.");
                        }
                    },
                    function (errorMessage) {
                        showErrorMessage(errorMessage);
                    });
            }

            function clearMessages() {
                $scope.vm.errorMessages = [];
                $scope.vm.infoMessages = [];
            }

            function showInfoMessage(infoMessage) {
                $scope.vm.infoMessages = [];
                $scope.vm.infoMessages.push({description: infoMessage});
                $timeout(function () {
                    $scope.vm.infoMessages = [];
                }, 1000);
            }

            $scope.getExchange = function () {
                var left = $scope.vm.currencyLeft;
                var right = $scope.vm.currencyRight;

                if (left == right) {
                    showErrorMessage("Please select different currencies");
                    return;
                }

                CurrencyService.getCurrencyRate(left, right)
                    .then(function (data) {
                        clearMessages();
                        //showInfoMessage("currency exchange loaded successful.");

                        $scope.vm.exchangeData = data;
                        $scope.vm.value = 1;
                        updateConversion();
                    },
                    function () {
                        clearMessages();
                        $scope.vm.errorMessages.push({description: "currency exchange data load failed."});
                    });
            };

            $scope.getAnotherExchange = function () {
                $scope.vm.exchangeData = null;
                //reloading history
                loadHistoryData();

            };

            $scope.switchCurrency = function () {
                var leftC = $scope.vm.exchangeData.leftCurrency;
                var value = $scope.vm.exchangeData.value;

                $scope.vm.exchangeData.value = $scope.vm.exchangeData.inverseValue;
                $scope.vm.exchangeData.inverseValue = value;

                $scope.vm.exchangeData.leftCurrency = $scope.vm.exchangeData.rightCurrency;
                $scope.vm.exchangeData.rightCurrency = leftC;

                updateConversion();
            }

            $scope.$watch('vm.value', function (newValue, oldValue) {
                updateConversion();
            });

            function updateConversion() {
                if ($scope.vm.exchangeData != null) {
                    $scope.vm.convertResult = Number($scope.vm.value * $scope.vm.exchangeData.value).toFixed(2);
                }
            }

            function prepareCurrencyDto(currencies) {
                return _.chain(currencies)
                    .map(function (currency) {
                        return {
                            id: currency.id,
                        }
                    })
                    .value();
            }

            $scope.logout = function () {
                UserService.logout();
            }


        }]);
