<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- define variable -->
<c:url value="/j_spring_security_logout" var="logoutUrl"/>
<sec:authentication property="name" var="userName"/>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>RTS | A simple yet effective rail-way ticket system</title>
		
		<link href="<c:url value="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css" />" rel="stylesheet">
		<!-- Include roboto.css to use the Roboto web font, material.css to include the theme and ripples.css to style the ripple effect -->
		<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/roboto.min.css" />" rel="stylesheet">
		<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/material.min.css" />" rel="stylesheet">
		<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/ripples.min.css" />" rel="stylesheet">
		<link href="<c:url value="/resources/css/sidebar.css" />" rel="stylesheet">

		<link href="<c:url value="/resources/css/index.css" />" rel="stylesheet">

		<!-- Set section height onload -->
		<script type="text/javascript">
		window.onload = function() {
			console.log("window height: ", $( window ).height());
			sectionHeight = $( window ).height();
			$.each($('section'), function() {
				$(this).height( sectionHeight );
			});
		};
		</script>
	</head>

	<body ng-app="indexPage" ng-controller="mainController as mainCtrl">
	
		<!--Loading  -->
		<!-- <div class='dt-loading'>
			<div class='laying'></div>
			<div class='layout'></div>
		</div> -->

		<!-- Header -->
		<header>
			<!-- nav -->
			<div class="navbar navbar-default bg-transparent">
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
						<sec:authorize access="isAuthenticated()">
							<li><a><span id="user_name">${userName}</span></a></li>
							<li><a href="${logoutUrl}">Log Out</a></li>
						</sec:authorize>
						<sec:authorize access="isAnonymous()">
							<ul class="breadcrumb bg-transparent">
								<li><a href="javascript:void(0)" data-toggle="modal" data-target="#login_form">Log In</a></li>
								<li><a href="javascript:void(0)" data-toggle="modal" data-target="#reg_form">Reg</a></li>
							</ul>
						</sec:authorize>
					</ul>
				</div>
			</div>
		</header>
		
		<!-- Landing Page -->
		<section id="landing_page">
			<!-- background image -->
			<!-- <div class="bg-img">
				<img src="<c:url value="/resources/img/bg-large.jpg" />" alt="backgroud image" />
			</div> -->

			<!-- Introduction Area -->
			<div class="intro-container darken">
				
				<!-- Title -->
				<div class="col-xs-12 title">
					<h1>RAILWAY TICKET SYSTEM</h1>
					<p>Simple Yet Effective</p>
				</div>

        <!-- Ticket Search Form -->
				<div class="col-xs-12 text-center search-form-container">
					<form name="searchForm" class="form-inline clearfix" id="search_form">
						<fieldset class="form-group pull-left">
							<label class="sr-only">Departure Station</label>
							<input id="from" type="text" ng-model="mainCtrl.dep" placeholder="From" required>
						</fieldset>
						<fieldset class="form-group pull-left">
							<label class="sr-only">Destination Station</label>
							<input id="to" type="text" ng-model="mainCtrl.des" placeholder="To" required>
						</fieldset>
						<fieldset class="form-group pull-left">
							<label class="sr-only">Time</label>
							<input id="time" type="datetime-local" ng-model="mainCtrl.time">
						</fieldset>
						<fieldset class="form-group pull-left">
							<select id="time_type" ng-model="mainCtrl.timeType" ng-options="type.label for type in mainCtrl.timeTypes">
							</select>
						</fieldset>
						
						<div class="form-group pull-left">
							<button type="button" class="form-inline btn btn-danger" id="submit_search" ng-click="mainCtrl.searchTicket()">Search</button>
						</div>
					</form>
				</div>
			</div>
		</section>

		<!-- Ticket list page -->
		<section id="ticket_page" class="sidebar-section toggled">
			<div class="sidebar-wrapper">
				<ul class="sidebar-nav">
					<li class="sidebar-brand">
						<a herf="javascript:void(0)">Modify Trip</a>
					</li>
				</ul>
			</div>
			<div class="page-content-wrapper ticket-list">
				<div class="container-fluid">
					<div class="row">
						<div class="col-lg-12">
							<h4 class="text-primary">ONE WAY / SELECT A TICKET FOR PURCHASE</h4>
							<table class="table table-striped table-hover">
								<thead>
									<tr>
										<th style="display:none;">ID</th>
										<th>Departure</th>
										<th>Destination</th>
										<th>
											Depart At
											<a href="javascript:void(0)" ng-click="mainCtrl.predicate = '-dtime'; mainCtrl.reverse = false">
												<i class="mdi-navigation-arrow-drop-down">
													<span></span>
												</i>
											</a>
											<a href="javascript:void(0)" ng-click="mainCtrl.predicate = 'dtime'; mainCtrl.reverse = false">
												<i class="mdi-navigation-arrow-drop-up">
													<span></span>
												</i>
											</a>
										</th>
										<th>
											Arrive By
											<a href="javascript:void(0)" ng-click="mainCtrl.predicate = '-atime'; mainCtrl.reverse = false">
												<i class="mdi-navigation-arrow-drop-down">
													<span></span>
												</i>
											</a>
											<a href="javascript:void(0)" ng-click="mainCtrl.predicate = 'atime'; mainCtrl.reverse = false">
												<i class="mdi-navigation-arrow-drop-up">
													<span></span>
												</i>
											</a>
										</th>
										<th>Available</th>
										<th>
											Price
											<a href="javascript:void(0)" ng-click="mainCtrl.predicate = '-price'; mainCtrl.reverse = false">
												<i class="mdi-navigation-arrow-drop-down">
													<span></span>
												</i>
											</a>
											<a href="javascript:void(0)" ng-click="mainCtrl.predicate = 'price'; mainCtrl.reverse = false">
												<i class="mdi-navigation-arrow-drop-up">
													<span></span>
												</i>
											</a>
										</th>
										<th>Select</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="ticket in mainCtrl.tickets | orderBy:mainCtrl.predicate:mainCtrl.reverse track by ticket.ticketid" ng-class="{success: $index == mainCtrl.selectedIndex}">
										<td>{{ticket.dep}}</td>
										<td>{{ticket.des}}</td>
										<td>{{ticket.dtime}}</td>
										<td>{{ticket.atime}}</td>
										<td>{{ticket.total - ticket.sold}}</td>
										<td>{{ticket.price | number:2 | num}}</td>
										<td><input type="radio" value="{{ $index }}" ng-model="mainCtrl.selectedIndex" ng-change="mainCtrl.selectTicket(ticket)" /></td>
									</tr>
								</tbody>
							</table>

							<div class="panel panel-primary">
								<div class="panel-heading">
									<h3 class="panel-title">Ticket info</h3>
								</div>
								<div class="panel-body">
									<div class="col-lg-4">
										<div class="list-group">
											<div class="list-group-item">
												<div class="row-action-primary">
													<i class="mdi-maps-directions-train">
														<span>From Station</span>
													</i>
												</div>
												<div class="row-content">
													<h4 class="list-group-item-heading">From</h4>
														<p class="list-group-item-text text-primary">{{ mainCtrl.selectedTicket.dep }}</p>
												</div>
											</div>
											<div class="list-group-separator"></div>
											<div class="list-group-item">
												<div class="row-action-primary">
													<i class="mdi-maps-place">
														<span>Too</span>
													</i>
												</div>
												<div class="row-content">
													<h4 class="list-group-item-heading">To</h4>
														<p class="list-group-item-text text-primary">{{ mainCtrl.selectedTicket.des }}</p>
												</div>
											</div>
										</div>
									</div>
									<div class="col-lg-4">
										<div class="list-group">
											<div class="list-group-item">
												<div class="row-action-primary">
													<i class="mdi-av-play-arrow">
														<span>Depart At</span>
													</i>
												</div>
												<div class="row-content">
													<h4 class="list-group-item-heading">Depart At</h4>
														<p class="list-group-item-text text-primary">{{ mainCtrl.selectedTicket.dtime }}</p>
												</div>
											</div>
											<div class="list-group-separator"></div>
											<div class="list-group-item">
												<div class="row-action-primary">
													<i class="mdi-av-stop">
														<span>Arrive By</span>
													</i>
												</div>
												<div class="row-content">
													<h4 class="list-group-item-heading">Arrive By</h4>
														<p class="list-group-item-text text-primary">{{ mainCtrl.selectedTicket.atime }}</p>
												</div>
											</div>
										</div>
									</div>
									<div class="col-lg-4">
										<div class="list-group">
											<div class="list-group-item">
												<div class="row-action-primary">
													<i class="mdi-maps-local-atm">
														<span>Price</span>
													</i>
												</div>
												<div class="row-content">
													<h4 class="list-group-item-heading">Price</h4>
														<p class="list-group-item-text text-primary">{{ mainCtrl.selectedTicket.price | currency:"USD$":2 }}</p>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div id="buy_form">
								<p>{{ mainCtrl.selectedTicket.ticketid }}</p>
								<p>{{ mainCtrl.selectedIndex }}</p>
								<p>{{buy_form.ticket_quantity.$error}}</p>
								<form name="buy_form" class="form-horizontal" novalidate>
									<fieldset>
										<div class="form-group" ng-class="{'has-error': buy_form.ticket_quantity.$error.required || buy_form.ticket_quantity.$error.number || buy_form.ticket_quantity.$error.min || buy_form.ticket_quantity.$error.max}">
											<label for="ticket_quantity" class="control-label">Quantity</label>
											<input type="number" name="ticket_quantity" id="ticket_quantity" class="form-control" ng-model="ticketQuantity" min="0" max="{{ mainCtrl.selectedTicket.total - mainCtrl.selectedTicket.sold }}" required quantity>
										</div>
									</fieldset>

									
									
									<button type="button" class="btn btn-primary" ng-click="mainCtrl.buy(selectedTicket.ticketid, ticketQuantity)">Buy</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>

		
		<!-- Google Chart -->
		<div id="chart_div"></div>
		<div id="chart"></div>
		
		<!-- Login Form -->
		<div class="modal" id="login_form">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header clearfix">
						<legend class="pull-left" style="width: inherit">Log In</legend>
						<button type="button" class="close" data-dismiss="modal">
							<i class="mdi-content-clear"></i>
						</button>
					</div>
					<div class="modal-body">
						<form name="login_form" action="<c:url value='j_spring_security_check'/>" method="POST" class="form-horizontal" novalidate>
							<fieldset>
								<div class="form-group" ng-class="{'has-error': login_form.j_username.$error.email}">
									<label for="login_email" class="col-lg-2 control-label">Email</label>
									<div class="col-lg-10">
										<input type="email" name="j_username" class="form-control" id="login_email" placeholder="Email" ng-model="j_username" required>
									</div>
								</div>
								<div class="form-group">
									<label for="login_password" class="col-lg-2 control-label">Password</label>
									<div class="col-lg-10">
										<input type="password" name="j_password" class="form-control" id="login_password" placeholder="Password" ng-model="j_password" required>
										<div class="checkbox">
											<label>
												<input type="checkbox" name="_spring_security_remember_me">&nbsp;Remember me
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-lg-10 col-lg-offset-2">
										<button type="reset" class="btn btn-default btn-raised pull-left">Clear</button>
										<button type="submit" class="btn btn-primary btn-raised pull-right">Go!</button>
									</div>
								</div>
							</fieldset>
						</form>
						<br />
					</div>
				</div>
			</div>
		</div>

		<!--Registration Form -->
		<div class="modal" id="reg_form">
			<div class="modal-header clearfix"></div>
		</div>

        
        





	<!-- load script here -->
	<script src="<c:url value="/resources/bower_components/jquery/dist/jquery.js" />"></script>
	<script src="<c:url value="/resources/bower_components/angularjs/angular.js" />"></script>
	<script src="<c:url value="/resources/bower_components/bootstrap/dist/js/bootstrap.min.js" />"></script>
		<script src="<c:url value="/resources/bower_components/bootstrap-material-design/dist/js/ripples.min.js" />"></script>
	<script src="<c:url value="/resources/bower_components/bootstrap-material-design/dist/js/material.min.js" />"></script>
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<script type="text/javascript">
		google.load('visualization', '1.0', {'packages':['corechart']});
		google.load("visualization", "1.0", {'packages':['bar']});
	</script>
	<script>
		$(document).ready(function() {
			// Initialize material-design-boostrap
			$.material.init();

			setTimeout(function() {
				 $(".dt-loading").fadeOut('slow');
				}, 3000);
		});
	</script>

	<script src="<c:url value="/resources/js/Google-Chart-1.js" />"></script>
	<script src="<c:url value="/resources/js/index.js" />"></script>
	<script src="<c:url value="/resources/js/sidebar.js" />"></script>
</body>
</html>