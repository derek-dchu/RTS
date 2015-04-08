<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Result Page</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.min.js"></script>
<script type="text/javascript">
	var app = angular.module('userReg',[]);

	app.controller('mainController', ['$http', function($http){
		var regUser = "http://localhost:8080/RTS/rest/sys/reg";
		
		this.regUser = function(){
			$http({
			    method:'POST',
			    url: regUser,
			    data: $.param({
				    	firstName:this.firstName,
				    	lastName:this.lastName,
				    	email:this.email1,
				    	password:this.password1
			    }),
			    headers:{'Content-Type':'application/x-www-form-urlencoded'}});
			this.resetForm();
		};

		this.resetForm = function(){
			this.firstName ="";
			this.lastName = "";
			this.email1 = "";
			this.email2 = "";
	    	this.password1 = "";
	    	this.password2 = "";
			this.userForm.$setPristine();
		};
	}]);

</script>
<style type="text/css">
input {
	font-size: 110%;
}

button {
	font-size: 110%;
}
</style>
</head>
<body ng-app="userReg" ng-controller="mainController as mainCtrl">
<h1>User | Registration</h1>

<br>
<br>
<div>
	<form ng-submit="userReg()" name="mainCtrl.userForm">
		<div>
			<lable>First Name:</lable>
			<div><input type="text" ng-model="mainCtrl.firstName"></div>
		</div>

		<div>
			<lable>Last Name:</lable>
			<div><input type="text" ng-model="mainCtrl.lastName"></div>
		</div>

		<div>
			<lable>Email:</lable>
			<div><input type="email" ng-model="mainCtrl.email1"></div>
		</div>

		<div>
			<lable>Confirm Email:</lable>
			<div><input type="email" ng-model="mainCtrl.email2"></div>
		</div>

		<div>
			<lable>Password:</lable>
			<div><input type="password" ng-model="mainCtrl.password1"></div>
		</div>
		
		<div>
			<lable>Confirm Password:</lable>
			<div><input type="password" ng-model="mainCtrl.password2"></div>
		</div>

		<div>
			<button type="button" ng-click="mainCtrl.resetForm()">Reset</button>
			<button type="button" ng-click="mainCtrl.regUser()">Submit</button>
		</div>
	</form>
</div>
</body>
</html>