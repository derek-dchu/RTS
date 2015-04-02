<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.min.js"></script>
	<script type="text/javascript">
	var app = angular.module('app',[]);
	app.controller('myc',function($scope,$http){
		$scope.ha = function(){
			$http.get("http://localhost:8080/RTS/rest/kkk");
			alert("123");
		};
	});
	</script>
</head>
<body ng-app="app" ng-controller="myc">
	<input type="button" value="click me" ng-click="ha()">
</body>
</html>