angular.module('newUserApp', ['common', 'spring-security-csrf-token-interceptor', 'editableTableWidgets'])
    .controller('NewUserCtrl', ['$scope', '$http', function ($scope, $http) {

        $scope.createUser = function () {
            console.log('Creating user with username ' + $scope.vm.username + ' and password ' + $scope.vm.password);

            $scope.vm.submitted = true;

            if ($scope.form.$invalid) {
                return;
            }

            var postData = {
                username: $scope.vm.username,
                plainTextPassword: $scope.vm.password,
                email: $scope.vm.email,
                birthDate: $scope.vm.birthDate
            };

            $http({
                method: 'POST',
                url: '/user',
                data: postData,
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "text/plain"
                }
            })
                .then(function (response) {
                    if (response.status == 200) {
                        $scope.login($scope.vm.userName, $scope.vm.password);
                    }
                    else {
                        $scope.vm.errorMessages = [];
                        $scope.vm.errorMessages.push({description: response.data});
                        console.log("failed user creation: " + response.data);
                    }
                });
        }
    }])
    .directive("datepicker", function () {
        return {
            restrict: "A",
            require: "ngModel",
            link: function (scope, elem, attrs, ngModelCtrl) {
                var updateModel = function (dateText) {
                    scope.$apply(function () {
                        ngModelCtrl.$setViewValue(dateText);
                    });
                };
                var options = {
                    dateFormat: "mm/dd/yy",
                    maxDate: new Date(),
                    onSelect: function (dateText) {
                        updateModel(dateText);
                    }
                };
                elem.datepicker(options);
            }
        }
    });