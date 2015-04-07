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
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular.min.js"></script>
<script type="text/javascript">
	var app = angular.module('app',[]);

	app.controller('myc',function($scope,$http){
		var addticket ="http://localhost:8080/RTS/rest/admin/addticket";
		var listticket = "http://localhost:8080/RTS/rest/admin/listticket";

		$scope.tableshow = false;
		
		$scope.createNewTicket = function(){
			$http({
			    method:'POST',
			    url: addticket,
			    data: $.param({
			    		tid:$scope.tid,
				    	dep:$scope.dep,
				    	des:$scope.des,
				    	dtime:$scope.dtime,
				    	atime:$scope.atime,
				    	total:$scope.total,
				    	sold:$scope.sold,
				    	price:$scope.price,
				    	status:$scope.statu
			    }),
			    headers:{'Content-Type':'application/x-www-form-urlencoded'}});
			$scope.resetForm();
			$scope.getAll();
		};

		$scope.resetForm = function(){
			$scope.tid ="";
			$scope.dep = "";
			$scope.des = "";
	    	$scope.dtime = "";
	    	$scope.atime = "";
	    	$scope.total = "";
	    	$scope.sold = "";
	    	$scope.price = "";
	    	$scope.statu = "";
			$scope.ticketform.$setPristine();
		};

		$scope.getAll = function(){
			$http.get(listticket)
			.success(function(data){
				$scope.tickets = data.ticket;
				$scope.tableshow = true;
			});
		};

		$scope.radioSelect = function(data){
			var selectTicket = JSON.parse(data);
			$scope.tid = selectTicket.ticketid;
			$scope.dep = selectTicket.dep;
			$scope.des = selectTicket.des;
	    	$scope.dtime = selectTicket.dtime;
	    	$scope.atime = selectTicket.atime;
	    	$scope.total = selectTicket.total;
	    	$scope.sold = selectTicket.sold;
	    	$scope.price = selectTicket.price;
	    	$scope.statu = selectTicket.enable;
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

<br>
<br>
<button type="button" ng-click="getAll()" id="getall">List All</button>
<div ng-show="tableshow">
	<table>
	  <thead>
	    <tr>
	      <th>Ticket ID</th>
	      <th>Departure</th>
	      <th>Destination</th>
	      <th>DepartTime</th>
	      <th>ArraiveTime</th>
	      <th>Total</th>
	      <th>Available</th>
	      <th>Sold</th>
	      <th>Price</th>
	      <th>Status</th>
	      <th>Selector</th>
	    </tr>
	  </thead>
	  <tbody>
	    <tr ng-repeat="ticket in tickets|filter:{ticketid:tid,dep:dep,des:des,dtime:dtime,atime:atime,total:total,sold:sold,price:price,enable:statu}" >
	      <td ng-class='{red:ticket.enable==0}'>{{ticket.ticketid}}</td>
	      <td>{{ticket.dep}}</td>
	      <td>{{ticket.des}}</td>
	      <td>{{ticket.dtime}}</td>
	      <td>{{ticket.atime}}</td>
	      <td>{{ticket.total}}</td>
	      <td>{{ticket.available}}</td>
	      <td>{{ticket.sold}}</td>
	      <td>{{ticket.price}}</td>
	      <td>{{ticket.enable}}</td>
	      <td><input type="radio" ng-model="$parent.radis" value="{{ticket}}" ng-change="$parent.radioSelect($parent.radis)"/></td>
	    </tr>
	  </tbody>
	</table>
</div>

<br>
<br>
<div>
	<form ng-submit="createNewTicket()" name="ticketform">
		<div>
			<lable>Ticket ID:</lable>
			<div><input type="text" ng-model="tid"></div>
		</div>

		<div>
			<lable>Departure:</lable>
			<div><input type="text" ng-model="dep"></div>
		</div>

		<div>
			<lable>Destination:</lable>
			<div><input type="text" ng-model="des"></div>
		</div>

		<div>
			<lable>DepartTime:</lable>
			<div><input type="text" ng-model="dtime"></div>
		</div>

		<div>
			<lable>ArraiveTime:</lable>
			<div><input type="text" ng-model="atime"></div>
		</div>

		<div>
			<lable>Total:</lable>
			<div><input type="text" ng-model="total"></div>
		</div>
		
		<div>
			<lable>Sold:</lable>
			<div><input type="text" ng-model="sold"></div>
		</div>

		<div>
			<lable>Price:</lable>
			<div><input type="text" ng-model="price"></div>
		</div>

		<div>
			<lable>Status:</lable>
			<div><input type="text" ng-model="statu"></div>
		</div>

		<div>
			<button type="button" ng-click="resetForm()">Reset</button>
			<button type="button" ng-click="createNewTicket()">Submit</button>
		</div>
	</form>
</div>
</body>
</html>