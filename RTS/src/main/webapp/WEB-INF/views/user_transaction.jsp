<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<c:url value="/j_spring_security_logout" var="logoutUrl"/>
<sec:authentication property="name" var="userName"/>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Result Page</title>
<link href="<c:url value="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/roboto.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/material.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/ripples.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/sidebar2.css" />" rel="stylesheet">

<script src="<c:url value="/resources/bower_components/jquery/dist/jquery.min.js" />"></script>
<script src="<c:url value="/resources/bower_components/angular/angular.min.js" />"></script>
<script src="<c:url value="/resources/bower_components/bootstrap/dist/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/resources/bower_components/bootstrap-material-design/dist/js/ripples.min.js" />"></script>
<script src="<c:url value="/resources/bower_components/bootstrap-material-design/dist/js/material.min.js" />"></script>
<script src="<c:url value="/resources/js/angu-fixed-header-table.js" />"></script>

<script>
	$(document).ready(function() {
		// This command is used to initialize some elements and make them work properly
		$.material.init();
	});
</script>

<script type="text/javascript">
	var app = angular.module('app',['anguFixedHeaderTable']);
	app.controller('myc',function($scope,$http,$filter,$location){
		var host = $location.host();
		var port = $location.port();
		var root = "http://" + host + ":" + port;
		var listalltransaction = root + "/RTS/rest/transaction";
		var canceltransaction = root + "/RTS/rest/transaction/cancel";

		var username = document.getElementById("logedUsername").innerHTML ; 
		$scope.textshow = true;
		$scope.trid = 0;
		$scope.qt = 0;
		$scope.transactions = [];
		$scope.itemsPerPage = 5;
		$scope.currentPage = 1;
		var paget = null;

		$http({
			method:'GET',
			url:listalltransaction,
			params:{
				username:username
			}
		}).success(function(data){
				$scope.transactions = data;
		});

		$scope.radioSelect = function(data){
			var selectTran = JSON.parse(data);
			$scope.trid = selectTran.tid;
			$scope.qt = selectTran.qt;
			if(selectTran.status === 'b' || selectTran.status === 'B'){
				$scope.textshow = false;
			}else{
				$scope.textshow = true;
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

		$scope.sideshow = function(){
			$scope.sidedes = "";
			$scope.sidedep = "";
			$scope.sidestatus = "";
			angular.element('.sidebar-section').toggleClass('toggled')
		};

		$scope.reset = function(){
			$scope.textshow = true;
			$scope.radis = false;
			$scope.amount = "";
		};
	});
</script>

<style type="text/css">


</style>
</head>


<body ng-app="app" ng-controller="myc">

<!-- NAV -->
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
            <li><a id="logedUsername">${userName}</a></li>
            <li><a href="${logoutUrl}">Log Out</a></li>
        </ul>
	</div>
</div>

<!-- Transaction Table -->
<section id="sectionpage" class="sidebar-section toggled">
<div>
	<div>
		<table class="table table-hover table-center" fixed-header>
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
				<tr ng-class="{danger:tr.status=='c'}" ng-repeat="tr in transactions|filter:{ticket:{dep:sidedep,des:sidedes},status:sidestatus}|orderBy:'-ttime'">
					<td>{{tr.ticket.dep}}</td>
					<td>{{tr.ticket.des}}</td>
					<td>{{tr.qt}}</td>
					<td>{{tr.status}}</td>
					<td>{{tr.ttime}}</td>
					<td>
						<div class="radio radio-primary">
							<label>
								<input type="radio" ng-model="$parent.radis" value="{{tr}}" 
								ng-change="$parent.radioSelect($parent.radis)"/>
								<span class="ripple"></span>
								<span class="circle"></span>
								<span class="check"></span>
							</label>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<br>

	<!-- Cancel Form -->
	<form ng-submit="cancel()" ng-class="{'formshow': textshow}"  name="form" novalidate>
	<div class="row">
		<div class="col-lg-4"></div>
		<div class="col-lg-2" style="margin-left: 4%;">
			<div class="form-group" style="margin-left: 30px;" ng-class="{'has-error':form.amount.$invalid}">
				<label ng-class="{'label-danger':form.amount.$invalid}" class="col-lg-4 label label-primary" style="margin-left: 80px;">Amount:</label>
				<div class="col-lg-12">
					<input type="number" ng-model="amount" name="amount" min="0" max="{{qt}}" class="text-center form-control input-lg" required>
				</div>
				<button class="btn btn-primary btn-flat" type="button" ng-click="reset()" style="margin-left: 15px;">Reset</button>
				<button class="btn btn-flat btn-danger" ng-disabled="form.amount.$invalid" style="margin-left: 10px;" type="button" ng-click="cancel()">Submit</button>
			</div>
		</div>
		</div>
	</form>

	<!-- side bar button -->
	<div class="row">
		<a class="btn btn-primary btn-fab btn-raised mdi-action-grade page-wrapper" ng-mouseover="sideshow()"></a>
	</div>
</div>

<div class="sidebar-wrapper">
	<form class="form-horizontal" style="margin-top:5px;">
		<div class="col-lg-3"></div>
		<div class="col-lg-2">
			<div class="form-group">
				<label class="text-primary">Departure</label>
				<div>
					<input ng-model="sidedep" type="text">
				</div>
			</div>
		</div>
		
		<div class="col-lg-1"></div>

		<div class="col-lg-2">
			<div class="form-group">
				<label class="text-primary">Destination</label>
				<div>
					<input ng-model="sidedes" type="text">
				</div>
			</div>
		</div>

		<div class="col-lg-1"></div>

		<div class="col-lg-2">
			<div class="form-group">
				<label class="text-primary">Status</label>
				<div>
					<input ng-model="sidestatus" type="text">
				</div>
			</div>
		</div>
	</form>
</div>
</section>
</body>
</html>