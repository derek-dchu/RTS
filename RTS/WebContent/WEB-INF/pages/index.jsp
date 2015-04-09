<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- define variable -->
<c:url value="/j_spring_security_logout" var="logoutUrl"/>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>RTS | A simple yet effective rail-way ticket system</title>
	
		<link href="<c:url value="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css" />" rel="stylesheet">
		<!-- Include roboto.css to use the Roboto web font, material.css to include the theme and ripples.css to style the ripple effect -->
		<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/roboto.min.css" />" rel="stylesheet">
		<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/material.min.css" />" rel="stylesheet">
		<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/ripples.min.css" />" rel="stylesheet">
		
		<link href="<c:url value="/resources/css/index.css" />" rel="stylesheet">
	</head>

	<body ng-app="indexPage" ng-controller="mainController">
	
		<!--Loading  -->
		 <div class='dt-loading'>
		 <div class='laying'></div>
		 <div class='layout'></div>
		 </div>
		<!-- nav -->
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
		        <form class="navbar-form navbar-left">
		            <input type="text" class="form-control col-lg-8" placeholder="Search">
		        </form>
		        <ul class="nav navbar-nav navbar-right">
		            <li><a href="javascript:void(0)" ng-click="openLoginForm()">Log In</a></li>
		            <li><a href="${logoutUrl}">Log Out</a></li>
		        </ul>
    		</div>
		</div>
		
		<!-- Login Form -->
		<div class="container login-form">
			<form name="f" action="<c:url value='j_spring_security_check'/>" method="POST" id="login-form" class="form-horizontal">
		    	<fieldset>
			        <legend>Log In</legend>
			        <div class="form-group">
			            <label for="inputEmail" class="col-lg-2 control-label">Email</label>
			            <div class="col-lg-10">
			                <input type="email" name="j_username" class="form-control" id="inputEmail" placeholder="Email">
			            </div>
			        </div>
			        <div class="form-group">
			            <label for="inputPassword" class="col-lg-2 control-label">Password</label>
			            <div class="col-lg-10">
			                <input type="password" name="j_password" class="form-control" id="inputPassword" placeholder="Password">
			            </div>
					</div>
					<div class="form-group">
            			<div class="col-lg-10 col-lg-offset-2">
                			<button class="btn btn-default">Cancel</button>
                			<button type="reset" class="btn">Clear</button>
                			<button id="signin" type="submit" class="btn btn-primary">Submit</button>
                			<input type="checkbox" name="_spring_security_remember_me">
            			</div>
        			</div>
				</fieldset>
			</form>
		</div>


<h1><font color="blue">RTS</font></h1>

<h1  id="logedUsername" ><sec:authentication property="name"/></h1>



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
		<input type="datetime-local" ng-model="mainCtrl.time">
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

	<!-- load script here -->
	<script src="<c:url value="/resources/bower_components/jquery/dist/jquery.min.js" />"></script>
	<script src="<c:url value="/resources/bower_components/angularjs/angular.min.js" />"></script>
	<script src="<c:url value="/resources/bower_components/bootstrap/dist/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/resources/bower_components/bootstrap-material-design/dist/js/ripples.min.js" />"></script>
	<script src="<c:url value="/resources/bower_components/bootstrap-material-design/dist/js/material.min.js" />"></script>
	<script>
		$(document).ready(function() {
			$.material.init();
			setTimeout(function() {
				 $(".dt-loading").fadeOut('slow');
				},3000);
		});
	</script>
	
	<script src="<c:url value="/resources/js/index.js" />"></script>
</body>
</html>