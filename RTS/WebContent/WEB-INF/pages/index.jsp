<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RTS | A simple yet effective rail-way ticket system</title>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.js"></script>
<script type="text/javascript">
	var app = angular.module("indexApp", []);
	app.controller('mainController', ['$http', '$scope', function($http, $scope) {
		var searchTicketUrl ="http://localhost:8080/RTS/rest/search/searchticket";
		var buyticket = "http://localhost:8080/RTS/rest/buy";
		
		$scope.dep = "";
		$scope.des = "";
		$scope.time = "";
		$scope.timeType = "D";
		$scope.tickets = [];
		$scope.tableshow = false;
		$scope.selectedTicketid = null;
		
		$scope.searchTicket = function() {
			var dtime = null,
				atime = null;
			if ($scope.timeType === "D") {
				dtime = $scope.time;
			}
			
			if ($scope.timeType === "A") {
				atime = $scope.time;
			}
			
			$http({
			    method:'POST',
			    url: searchTicketUrl,
			    data: $.param({
			    		des:$scope.des,
			    		dep:$scope.dep,
			    		dtime:dtime,
			    		atime:atime
			    }),
			    headers:{'Content-Type':'application/x-www-form-urlencoded'}
			}).success(function(data){
				$scope.tickets = [data.ticket];
				$scope.tableshow = true;
		})};
		
		$scope.resetForm = function(){
			$scope.dep = "";
			$scope.des = "";
			$scope.time = "";
			$scope.timeType = "D";
			$scope.searchForm.$setPristine();
		};
		
		$scope.buy = function(ticketid){
			var username = document.getElementById("logedUsername").innerHTML ;
			$http({
			    method:'GET',
			    url: buyticket,
			    params: {
			    	tid:ticketid,
				    username:username,
				    qt:$scope.ticketqt
			    }
			});
		};
	}]);


</script>
<style type="text/css">
table, th, td {
	border: 1px solid black;
}

.red{
	color:red;
}

input {
	font-size: 110%;
}

button {
	font-size: 110%;
}
</style>
</head>
<body ng-app="indexApp" ng-controller="mainController">
<h1><font color="blue">RTS</font></h1>

<h1  id="logedUsername" ><sec:authentication property="name"/></h1>
<c:url value="/j_spring_security_logout" var="logoutUrl"/>
<button type="button"><a href="${logoutUrl}">Log Out</a></button>

<!-- Login Form -->
<div class="login-form hidden"></div>
<form name="f" action="<c:url value='j_spring_security_check'/>" method="POST" id="login-form">
	<table>
		<tr>
			<td>Username: </td>
			<td><input type="text" name="j_username" id="j_username"/></td>
		</tr>
		<tr>
			<td>Password: </td>
			<td><input type="password" name="j_password" id="j_password"/></td>
		</tr>
		<tr>
			<td></td>
			<td>
				<button type="reset">Clear</button>
				<button id="signin" type="submit">Sign In</button>
			</td>
		</tr>
	</table>		
</form>

<!-- Search Ticket Form -->
<form name="searchForm">
	<fieldset>
		<label>Departure Station</label>
		<input type="text" ng-model="dep">
	</fieldset>
	<fieldset>
		<label>Destination Station</label>
		<input type="text" ng-model="des">
	</fieldset>
	<fieldset>
		<label>Time</label>
		<input type="text" ng-model="mainCtrl.time">
		<input type="radio" name="time" value="D" ng-model="timeType">Departure<br>
		<input type="radio" name="time" value="A" ng-model="timeType">Arrive
	</fieldset>
	
	<div>
		<button type="button" ng-click="resetForm()">Reset</button>
		<button type="button" ng-click="searchTicket()">Submit</button>
	</div>
</form>

<!-- Result Table -->
<div >
	<table>
	  <thead>
	    <tr>
	      <th style="display:none;">ID</th>
	      <th>Departure</th>
	      <th>Destination</th>
	      <th>DepartTime</th>
	      <th>ArraiveTime</th>
	      <th>Available</th>
	      <th>Price</th>
	      <th>Select</th>
	    </tr>
	  </thead>
	  <tbody>
	    <tr ng-repeat="ticket in tickets" >
	      <td style="display:none;">{{ticket.ticketid}}</td>
	      <td>{{ticket.dep}}</td>
	      <td>{{ticket.des}}</td>
	      <td>{{ticket.dtime}}</td>
	      <td>{{ticket.atime}}</td>
	      <td>{{ticket.total - ticket.sold}}</td>
	      <td>{{ticket.price}}</td>
	      <td><input type="radio" value="{{ticket.ticketid}}" ng-model="$parent.selectedTicketid"/></td>
	    </tr>
	  </tbody>
	</table>
</div>

<div>
	<span>{{selectedTicketid}}</span>
	<input type="text" ng-model="ticketqt">
	<button type="button" ng-click="buy(selectedTicketid)">Buy</button>
</div>

</body>
</html>