'use strict';

function WorkoutsCtrl($scope, $http) {
  $scope.search = function() {
    $http.get('/workouts.json', {params: {query: $scope.query}}).success(function(data) {
      $scope.workouts = data;
    });
  };
}

//WorkoutsCtrl.$inject = ['$scope', '$http'];

function WorkoutCtrl($scope, $http) {
  $scope.workout = function() {
    $http.get('/workouts/15241.json', {params: {query: $scope.workout}}).success(function(data) {
      $scope.workout = data;
    });
  };
}

//WorkoutCtrl.$inject = ['$scope', $http'];

