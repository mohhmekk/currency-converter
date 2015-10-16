///////////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Defines the javascript files that need to be loaded and their dependencies.
//
///////////////////////////////////////////////////////////////////////////////////////////////////////////

require.config({
    paths: {
        angular: '../bower_components/angular/angular',
        angularMessages: '../bower_components/angular-messages/angular-messages',
        csrfInterceptor: '../bower_components/spring-security-csrf-token-interceptor/dist/spring-security-csrf-token-interceptor.min',
        lodash: "../bower_components/lodash/dist/lodash",
        jQuery: "./datetimepicker/jquery", // needed only by the date time picker
        frontendServices: 'frontend-services',
        currencyExchangeApp: "currency-exchange-app"
    },
    shim: {
        jQuery: {
            exports: "jQuery"
        },
        angular: {
            exports: "angular"
        },
        csrfInterceptor: {
            deps: ['angular']
        },
        angularMessages: {
            deps: ['angular']
        },
        frontendServices: {
            deps: ['angular', 'lodash', 'csrfInterceptor']
        },
        currencyExchangeApp: {
            deps: ['lodash', 'angular', 'angularMessages', 'frontendServices']
        }
    }
});

require(['currencyExchangeApp'], function () {

    angular.bootstrap(document.getElementById('currencyExchangeApp'), ['currencyExchangeApp']);

});