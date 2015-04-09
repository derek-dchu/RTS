<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Result Page</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script src="<c:url value="/resources/bower_components/angularjs/angular.min.js" />"></script>
<script type="text/javascript">
	var app = angular.module('app',[]);
	app.controller('myc',function($scope,$http){
		var listalltransaction = "http://localhost:8080/RTS/rest/transaction";
		var canceltransaction = "http://localhost:8080/RTS/rest/transaction/cancel";

		var username = document.getElementById("logedUsername").innerHTML ; 
		$scope.textshow = false;
		$scope.trid = 0;
		$scope.transactions = [];

		$http({
			method:'GET',
			url:listalltransaction,
			params:{
				username:username
			}
		}).success(function(data){
			/* $scope.transactions = data; */
			/* var returndata = JSON.parse(data); */
				$scope.transactions= data;		
		});

		$scope.radioSelect = function(data){
			var selectTran = JSON.parse(data);
			$scope.trid = selectTran.tid;
			if(selectTran.status === 'b'){
				$scope.textshow = true;
			}else{
				$scope.textshow = false;
			}
		};

		$scope.cancel =function(){
			$http({
			    method:'POST',
			    url: canceltransaction,
			    data: $.param({
			    		trid:$scope.trid,
			    		amount:$scope.amount
			    }),
			    headers:{'Content-Type':'application/x-www-form-urlencoded'}
			}).success(function(){
				$http({
					method:'GET',
					url:listalltransaction,
					params:{
						username:username
					}
				}).success(function(data){
					$scope.transactions = data;
					$scope.$apply();
				})
			});

		};
	});
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
<body ng-app="app" ng-controller="myc">
<h1 style="display:none;" id="logedUsername" ><sec:authentication property="name"/></h1>
<c:url value="/j_spring_security_logout" var="logoutUrl"/>
<button type="button"><a href="${logoutUrl}">Log Out</a></button>

<div>
	<table>
		<thead>
			<tr>
				<th>Departure</th>
				<th>Destination</th>
				<th>Quantity</th>
				<th>Status</th>
				<th>Time</th>
				<th>Cancel</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="tr in transactions| orderBy:'-ttime'">
				<td>{{tr.ticket.dep}}</td>
				<td>{{tr.ticket.des}}</td>
				<td>{{tr.qt}}</td>
				<td ng-class="{red:tr.status=='c'}">{{tr.status}}</td>
				<td>{{tr.ttime}}</td>
				<td><input type="radio" ng-model="$parent.radis" value="{{tr}}" ng-change="$parent.radioSelect($parent.radis)"/></td>
			</tr>
		</tbody>
	</table>
</div>

<form ng-submit="cancel()" ng-show="textshow" >
	<div>
		<label>Amount:</label>
		<input type="text" ng-model="amount">
	</div>
</form>
<button type="button" ng-show="textshow" ng-click="cancel()">Cancel</button>

</body>
</html>