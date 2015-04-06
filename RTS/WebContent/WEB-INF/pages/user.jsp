<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.min.js"></script>
<title>Result Page</title>
<script type="text/javascript">
	var app = angular.module("app",[]);
	app.controller("myc",function($scope,$http){
		$scope.createUser = function(){
			$http({
			    method:'POST',
			    url: "http://localhost:8080/RTS/rest/user/reguser",
			    data: $.param({
			    		email:$scope.email,
			    		psw:$scope.psw
			    }),
			    headers:{'Content-Type':'application/x-www-form-urlencoded'}
			});
		};
	});

</script>
</head>

<body ng-app="app" ng-controller="myc">
<h1>USER</h1>
<c:url value="/j_spring_security_logout" var="logoutUrl"/>
<button type="button"><a href="${logoutUrl}">Log Out</a></button>

<form ng-submit="createUser()">
	<div>
		<input type="text" ng-model="email">
	</div>

	<div>
		<input type="text" ng-model="psw">
	</div>
</form>

<button ng-click="createUser()">Submit</button>
</body>
</html>
