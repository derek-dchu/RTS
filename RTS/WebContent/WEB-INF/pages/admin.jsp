<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<c:url value="/j_spring_security_logout" var="logoutUrl"/>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Result Page</title>
<link href="<c:url value="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css" />" rel="stylesheet">
<!-- Include roboto.css to use the Roboto web font, material.css to include the theme and ripples.css to style the ripple effect -->
<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/roboto.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/material.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/ripples.min.css" />" rel="stylesheet">

<script src="<c:url value="/resources/bower_components/jquery/dist/jquery.min.js" />"></script>
<script src="<c:url value="/resources/bower_components/angularjs/angular.min.js" />"></script>
<script src="<c:url value="/resources/bower_components/bootstrap/dist/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/bower_components/bootstrap-material-design/dist/js/ripples.min.js" />"></script>
<script src="<c:url value="/resources/bower_components/bootstrap-material-design/dist/js/material.min.js" />"></script>
<script src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.12.1.js"></script>
<script>
	$(document).ready(function() {
		// This command is used to initialize some elements and make them work properly
		$.material.init();
	});
</script>
		
<script type="text/javascript">
	var app = angular.module('app',['ui.bootstrap']);

	app.controller('myc',function($scope,$http,$filter){
		var addticket ="http://localhost:8080/RTS/rest/admin/addticket";
		var listticket = "http://localhost:8080/RTS/rest/admin/listticket";
		$scope.tableshow = false;
		$scope.formshow = false;
		$scope.inputid = false;
		
		$scope.itemsPerPage = 3;
		$scope.currentPage = 1;
		
		$scope.createNewTicket = function(){
			var dt = $filter('date')($scope.dtime,'yyyy/MM/dd HH:mm');
			var at = $filter('date')($scope.atime,'yyyy/MM/dd HH:mm');
			
			$http({
			    method:'POST',
			    url: addticket,
			    data: $.param({
			    		tid:$scope.tid,
				    	dep:$scope.dep,
				    	des:$scope.des,
				    	dtime:dt,
				    	atime:at,
				    	total:$scope.total,
				    	sold:$scope.sold,
				    	price:$scope.price,
				    	status:$scope.statu
			    }),
			    headers:{'Content-Type':'application/x-www-form-urlencoded'}
			}).success(function(){
				$scope.resetForm();
				$scope.tableshow = false;
				$scope.getAll();
			});
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
			$scope.radis = false;
			$scope.formshow = false;
			$scope.inputid = false;
		};

		$scope.getAll = function(){
			if( $scope.tableshow ){
				$scope.tableshow = false;
			}else{
				$http.get(listticket)
				.success(function(data){
					$scope.tickets = data;
					$scope.tableshow = true;
					$scope.tickets = $filter('orderBy')($scope.tickets,'-ticketid',false);
					$scope.figureOutTodosToDisplay($scope.tickets);
				});
			}
		};

		$scope.radioSelect = function(data){
			$scope.formshow = true;
			$scope.inputid = true;
			
			var selectTicket = JSON.parse(data);
			$scope.tid = selectTicket.ticketid;
			$scope.dep = selectTicket.dep;
			$scope.des = selectTicket.des;
	    	$scope.total = selectTicket.total;
	    	$scope.sold = selectTicket.sold;
	    	$scope.price = selectTicket.price;
	    	$scope.statu = selectTicket.enable;
		};

		$scope.showForm = function(){
			if($scope.formshow){
				$scope.formshow = false;
			}else{
				$scope.formshow = true;
			}
			$scope.inputid = false;
		};
		
		$scope.figureOutTodosToDisplay = function(data) {
		    var begin = (($scope.currentPage - 1) * $scope.itemsPerPage);
		    var end = begin + $scope.itemsPerPage;
		    $scope.paget = data.slice(begin, end);
		  };
	});

</script>
<style type="text/css">
table, th, td {
	border: 1px solid black;
	font-size: 110%;
}

table{
	margin-left: auto;
    margin-right: auto;
}

.table{
	width: 80%;
}

.label{
	font-size: 90%;
}

form {
	font-size: 120%;
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
<h1 id="logedUsername" style="display: none;"> <sec:authentication property="name"/></h1>
nav
		<div class="navbar navbar-default">
    		<div class="navbar-header">
        		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
            		<span class="icon-bar"></span>
            		<span class="icon-bar"></span>
            		<span class="icon-bar"></span>
       			</button>
        		<a class="navbar-brand" href="/RTS">RTS</a>
    		</div>
    		<div class="navbar-collapse collapse navbar-responsive-collapse">
		        <ul class="nav navbar-nav navbar-right">
		            <li><a><sec:authentication property="name"/></a></li>
		            <li><a href="${logoutUrl}">Log Out</a></li>
		        </ul>
    		</div>
		</div>
		
<br>
<br>
<button  class="btn btn-primary btn-raised" type="button" ng-click="getAll()" >List All</button>
<button  class="btn btn-warning btn-raised" type="button" ng-click="showForm()" ng-model="ant">Add New Ticket</button>

<!-- Tickets Table -->
<div ng-show="tableshow">
	<table class="table table-hover table-center">
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
	    <tr ng-class='{danger:ticket.enable==0}' ng-repeat="ticket in paget|filter:{ticketid:tid,dep:dep,des:des,dtime:dtime,atime:atime,total:total,sold:sold,price:price,enable:statu}" >
	      <td>{{ticket.ticketid}}</td>
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
	<div>
	<pagination class="pagination"  
    items-per-page="itemsPerPage"
    total-items="tickets.length" 
    ng-model="currentPage" 
    ng-change="figureOutTodosToDisplay(tickets)"></pagination>
</div>
</div>

<br>
<br>
<!-- Ticket Form -->
<div>
	<form class="form-horizontal" ng-submit="createNewTicket()" name="ticketform" ng-show="formshow" novalidate>
		<div class="col-lg-3"></div>
		<div class="col-lg-2">
			<div ng-show="inputid" class="form-group">
				<div class="col-lg-4"></div>
				<lable class="col-lg-4 label label-primary">Ticket ID:</lable>
				<div class="col-lg-12"><input type="text" ng-model="tid" class="text-center form-control input-lg"></div>
			</div>

			<div class="form-group">
				<div class="col-lg-4"></div>
				<lable class="col-lg-4 label label-primary">Departure:</lable>
				<div class="col-lg-12 input-group">
					<input type="text" name="dep" ng-model="dep" class="text-center form-control input-lg" required>
					<span class="input-group-addon" ng-show="ticketform.dep.$invalid">
      				<i class="mdi-content-report"></i></span>
				</div>
			</div>

			<div class="form-group">
				<div class="col-lg-4"></div>
				<lable class="col-lg-4 label label-primary">Destination:</lable>
				<div class="col-lg-12 input-group">
					<input type="text" name="des" ng-model="des" class="text-center form-control input-lg" required>
					<span class="input-group-addon" ng-show="ticketform.des.$invalid">
      				<i class="mdi-content-report"></i></span>
				</div>
			</div>
		</div>

		<div class="col-lg-2">
			<div class="form-group">
				<div class="col-lg-4"></div>
				<lable class="col-lg-4 label label-primary">DepartTime:</lable>
				<div class="col-lg-12">
					<input type="datetime-local" ng-model="dtime" class="text-center form-control input-lg" placeholder="2015/01/01 00:00">
				</div>
			</div>

			<div class="form-group">
				<div class="col-lg-4"></div>
				<lable class="col-lg-4 label label-primary">ArraiveTime:</lable>
				<div class="col-lg-12">
					<input type="datetime-local" ng-model="atime" class="text-center form-control input-lg" placeholder="2015/01/01 00:00">
				</div>
			</div>


			<div ng-show="inputid" class="form-group">
				<div class="col-lg-4"></div>
				<lable class="col-lg-4 label label-primary">Status:</lable>
				<div class="col-lg-12"><input type="text" ng-model="statu" class="text-center form-control input-lg"></div>
			</div>
		</div>

		<div class="col-lg-2">
			<div class="form-group">
				<div class="col-lg-4"></div>
				<lable class="col-lg-4 label label-primary">Total:</lable>
				<div class="col-lg-12 input-group">
					<input type="text" name="total" ng-model="total" class="text-center form-control input-lg" required>
					<span class="input-group-addon" ng-show="ticketform.total.$invalid">
      				<i class="mdi-content-report"></i></span>
				</div>
			</div>
			
			<div ng-show="inputid" class="form-group">
				<div class="col-lg-4"></div>
				<lable class="col-lg-4 label label-primary">Sold:</lable>
				<div class="col-lg-12"><input type="text" ng-model="sold" class="text-center form-control input-lg"></div>
			</div>

			<div class="form-group">
				<div class="col-lg-4"></div>
				<lable class="col-lg-4 label label-primary">Price:</lable>
				<div class="col-lg-12 input-group">
					<span class="input-group-addon">$</span>
					<input type="text" name="price" ng-model="price" class="form-control text-center input-lg" required>
					<span class="input-group-addon" ng-show="ticketform.price.$invalid">
      				<i class="mdi-content-report"></i></span>
				</div>
			</div>
		</div>
			
		<div class="col-lg-10 col-lg-offset-5">
			<button type="button" class="btn btn-flat btn-primary" ng-click="resetForm()">Reset</button>
			<button type="button" class="btn btn-flat btn-danger" ng-click="createNewTicket()" ng-disabled="ticketform.dep.$error.required || ticketform.des.$error.required || ticketform.total.$error.required ||ticketform.price.$error.required">Submit</button>
		</div>
	</form>
</div>
</body>
</html>