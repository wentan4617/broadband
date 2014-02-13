/**
	create module 'tmApp'
*/
var tmModule = angular.module('tmApp', []);

/**
	create controller 'UserSigninFormController'
*/
tmModule.controller('UserSigninFormController', function($scope, $http, $location) {
	
	$scope.user = {
		login_name: '',
		login_name_error: false,
		login_name_css: '',
		password: '',
		password_error: false,
		password_css: ''

	};

	$scope.userSigninFormSubmit = function() {

		if (!$scope.user.login_name || $scope.user.login_name == '') {
			$scope.user.login_name_css = 'has-error';
			$scope.user.login_name_error = true;
		} else {
			$scope.user.login_name_css = '';
			$scope.user.login_name_error = false;
		}
		if (!$scope.user.password || $scope.user.password == '') {
			$scope.user.password_css = 'has-error';
			$scope.user.password_error = true;
		} else {
			$scope.user.password_css = '';
			$scope.user.password_error = false;
		}

		if ($scope.userSigninForm.$valid) {

			$http.post('/broadband/login', {
				login_name: $scope.user.login_name,
				password: $scope.user.password
			}).success(function(data) {
				if (data.type == "success") {
					console.log('aaa');
					$location.path("/broadband/user");
				} else if (data.type == "error") {

				}
			})
		}

	};
});