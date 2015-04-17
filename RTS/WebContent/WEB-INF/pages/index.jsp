<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- define variable -->
<c:url value="/j_spring_security_logout" var="logoutUrl"/>
<c:url value="/user_transaction.html" var="userTransaction"/>
<sec:authentication property="name" var="userName"/>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>RTS | A simple yet effective rail-way ticket system</title>

		<!-- Load spinner first -->
		<link href="<c:url value="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css" />" rel="stylesheet">
		<!-- Include roboto.css to use the Roboto web font, material.css to include the theme and ripples.css to style the ripple effect -->
		<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/roboto.min.css" />" rel="stylesheet">
		<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/material.min.css" />" rel="stylesheet">
		<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/ripples.min.css" />" rel="stylesheet">

		<link href="<c:url value="/resources/dist/css/index.min.css" />" rel="stylesheet">

		<script src="<c:url value="/resources/bower_components/jquery/dist/jquery.min.js" />"></script>
		<!-- Set section height onload -->
		<script type="text/javascript">
		/* Set section height equals to window height onload and save the height in global as sectionHeight */
		//window.onload=function(){sectionHeight=$(window).height(),$.each($("section"),function(){$(this).height(sectionHeight)})};
		$(document).ready(function(){sectionHeight=$(window).height(),$.each($("section"),function(){$(this).height(sectionHeight)})});
		</script>
		<script type="text/javascript">
			// hide alert instead of removing it
			$(function(){
					$("[data-hide]").on("click", function(){
					$(this).closest("." + $(this).attr("data-hide")).hide();
				});
			});
		</script>

		<script src="<c:url value="/resources/bower_components/angular/angular.min.js" />"></script>
		
	</head>

	<body ng-app="indexPage" ng-controller="mainController as mainCtrl">
	
		<!--Loading  -->
		<!-- <div id="loader-wrapper">
			<div id="loader"></div>
		</div> -->

		<!-- Popup alert -->
		<div class="head-alert">
			<div class="container-fluid">
				<div id="alert_reg_fail" class="alert alert-dismissable alert-danger">
					<button type="button" class="close" data-hide="alert">
						<i class="mdi-content-clear"></i>
					</button>
					<strong>Oh snap!</strong> Please try submitting again with another email.
				</div>
				<div id="alert_reg_success" class="alert alert-dismissable alert-success">
					<button type="button" class="close" data-hide="alert">
						<i class="mdi-content-clear"></i>
					</button>
					<strong>Well done!</strong> You successfully registrate with our system. Please check for your <a href="{{ 'http://www.' + reg_form.reg_email.$modelValue.split('@')[1] }}" class="alert-link" target="_blank">confirmation email</a>.
				</div>
				<div id="alert_buy_success" class="alert alert-dismissable alert-success">
					<button type="button" class="close" data-hide="alert">
						<i class="mdi-content-clear"></i>
					</button>
					<strong>Well done!</strong> You successfully place an order.</a>.
				</div>
			</div>
		</div>

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
							<li class="dropdown">
								<a data-target="#" class="dropdown-toggle" data-toggle="dropdown"><span id="user_name">${userName}</span></a>
								<ul class="dropdown-menu" style="background-color: rgba(0,0,0,0.5);">
                    				<li><a href="${userTransaction}" style="color: white">View Transaction</a></li>
                    			</ul>
							</li>
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

			<!-- Introduction Area -->
			<div class="intro-container darken">
				
				<!-- Title -->
				<div class="col-xs-12 title">
					<h1>RAILWAY TICKET SYSTEM</h1>
					<p>Simple Yet Effective</p>
				</div>

        <!-- Ticket Search Form -->
				<div class="col-xs-12 text-center search-form-container">
					<form name="search_form" class="form-inline clearfix" id="search_form" ng-submit="search_form.$valid && mainCtrl.searchTicket()">
						<fieldset class="form-group pull-left">
							<label class="sr-only">Departure Station</label>
							<input id="from" type="text" ng-model="mainCtrl.dep" placeholder="From" required>
						</fieldset>
						<fieldset class="form-group pull-left">
							<label class="sr-only">Destination Station</label>
							<input id="to" type="text" ng-model="mainCtrl.des" placeholder="To" required>
						</fieldset>
						<fieldset class="form-group pull-left">
							<select id="time_type" ng-model="mainCtrl.timeType" ng-options="type.label for type in mainCtrl.timeTypes">
							</select>
						</fieldset>
						<fieldset class="form-group pull-left">
							<label class="sr-only">Time</label>
							<input id="time" type="datetime-local" ng-model="mainCtrl.time" placeholder="yyyy-mm-ddTHH:MM">
						</fieldset>

						<fieldset class="form-group pull-left">
							<button type="submit" class="form-inline btn btn-danger" id="submit_search">Search</button>
						</fieldset>
					</form>
				</div>
			</div>
		</section>

		<!-- Ticket list page -->
		<section id="ticket_page" class="sidebar-section toggled">
			<div class="sidebar-wrapper">
				<form name="modify_form">
					<ul class="sidebar-nav">
						<li class="sidebar-brand form-group">
							<legend class="text-primary">Modify Trip</legend>
						</li>
						<li class="form-group">
							<label class="text-primary">Travel From</label>
							<input name="from" type="text" ng-model="mainCtrl.dep" placeholder="address " required>
						</li>
						<li class="form-group">
							<label class="text-primary">Travel To</label>
							<input name="to" type="text" ng-model="mainCtrl.des" placeholder="address" required>
						</li>
						<li class="form-group">
							<label class="text-primary">Travel Time</label>
							<select ng-model="mainCtrl.timeType" ng-options="type.label for type in mainCtrl.timeTypes">
							</select>
							<input name="time" type="datetime-local" ng-model="mainCtrl.time" placeholder="yyyy-mm-ddThh:MM">
						</li>
						
						<li class="form-group">
							<button type="submit" class="form-inline btn btn-danger" id="submit_modify" ng-click="mainCtrl.searchTicket()">Modify</button>
						</li>

						<sec:authorize access="isAuthenticated()">
							<li><a href="${userTransaction}" style="color: white">View Transaction</a></li>
						</sec:authorize>
					</ul>
				</form>
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
											<a href="javascript:void(0)" ng-click="mainCtrl.setPredicate('-dtime', false)">
												<i class="mdi-navigation-arrow-drop-down">
													<span></span>
												</i>
											</a>
											<a href="javascript:void(0)" ng-click="mainCtrl.setPredicate('dtime', false)">
												<i class="mdi-navigation-arrow-drop-up">
													<span></span>
												</i>
											</a>
										</th>
										<th>
											Arrive By
											<a href="javascript:void(0)" ng-click="mainCtrl.setPredicate('-atime', false)">
												<i class="mdi-navigation-arrow-drop-down">
													<span></span>
												</i>
											</a>
											<a href="javascript:void(0)" ng-click="mainCtrl.setPredicate('atime', false)">
												<i class="mdi-navigation-arrow-drop-up">
													<span></span>
												</i>
											</a>
										</th>
										<th>Available</th>
										<th>
											Price
											<a href="javascript:void(0)" ng-click="mainCtrl.setPredicate('price', true)">
												<i class="mdi-navigation-arrow-drop-down">
													<span></span>
												</i>
											</a>
											<a href="javascript:void(0)" ng-click="mainCtrl.setPredicate('price', false)">
												<i class="mdi-navigation-arrow-drop-up">
													<span></span>
												</i>
											</a>
										</th>
										<th>Select</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="ticket in mainCtrl.tickets | orderBy:mainCtrl.predicate:mainCtrl.reverse track by ticket.ticketid" ng-class="{success: ticket == mainCtrl.selectedTicket}">
										<td>{{ticket.dep}}</td>
										<td>{{ticket.des}}</td>
										<td>{{ticket.dtime}}</td>
										<td>{{ticket.atime}}</td>
										<td>{{ticket.total - ticket.sold}}</td>
										<td>{{ticket.price}}</td>
										<td>
											<div class="radio radio-primary">
												<label>
													<input type="radio" value="{{ ticket }}" ng-checked="ticket == mainCtrl.selectedTicket" ng-model="mainCtrl.selectedTicket" ng-change="mainCtrl.selectTicket(ticket)">
													<span class="ripple"></span>
													<span class="circle"></span>
													<span class="check"></span>
												</label>
											</div>
										</td>
									</tr>
								</tbody>
							</table>

							<!-- Show info when there is no matching tickets -->
							<div class="table-info" ng-show="mainCtrl.tickets.length === 0">
								<div class="alert alert-dismissable alert-warning">
									<button type="button" class="close" data-dismiss="alert">
										<i class="mdi-content-clear">
										</i>
									</button>
									<strong>PROBLEM WITH YOUR ITINERARY</strong><br>
									Please modify your itinerary and try searching again.
								</div>
							</div>

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
														<span>Travel From</span>
													</i>
												</div>
												<div class="row-content">
													<h4 class="list-group-item-heading">Travel From</h4>
														<p class="list-group-item-text text-primary">{{ mainCtrl.selectedTicket.dep }}</p>
												</div>
											</div>
											<div class="list-group-separator"></div>
											<div class="list-group-item">
												<div class="row-action-primary">
													<i class="mdi-maps-place">
														<span>Travel To</span>
													</i>
												</div>
												<div class="row-content">
													<h4 class="list-group-item-heading">Travel To</h4>
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
								<form name="buy_form" class="form-horizontal" ng-submit="buy_form.$valid && mainCtrl.buy(ticketQuantity)" novalidate>
									<fieldset>
										<div class="form-group" ng-class="{'has-error': mainCtrl.selectedTicket != null && buy_form.ticket_quantity.$dirty && buy_form.ticket_quantity.$invalid}">
											<label for="ticket_quantity" class="control-label">Quantity</label>
											<input type="number" name="ticket_quantity" id="ticket_quantity" class="form-control" ng-model="ticketQuantity" min="0" max="{{ mainCtrl.selectedTicket.total - mainCtrl.selectedTicket.sold }}" required>
											<div ng-messages="buy_form.ticket_quantity.$error" class="text-danger ng-messages">
												<div ng-message="max" ng-show="buy_form.$dirty">Quantity exceeds maximum available volume</div>
											</div>
										</div>
									</fieldset>
									
									<fieldset class="form-group">
										<div style="margin: 10px 1px">
											<p class="text-primary pull-left">Total Price:&nbsp</p>
											<p class="pull-left">{{ mainCtrl.selectedTicket == null || ticketQuantity == undefined ? 0 : (ticketQuantity-0) * mainCtrl.selectedTicket.price | currency:"USD$":2 }}</p>
										</div>
										<sec:authorize access="isAuthenticated()">
											<button style="margin-top: -10px;" type="submit" id="buy_submit" class="btn btn-primary pull-right">Buy</button>
										</sec:authorize>
										<sec:authorize access="isAnonymous()">
											<a style="margin-top: -10px;" class="btn btn-primary pull-right" href="javascript:void(0)" data-toggle="modal" data-target="#login_form">Buy</a>
										</sec:authorize>
									</fieldset>
								</form>
							</div>

							<!-- Google Chart -->
							<div class="container-fluid" style="padding: 0 35px;">
								<div class="row">
									<div class="col-sm-12 col-lg-6">
										<div class="panel panel-primary">
											<div class="panel-heading">
												<h3 class="panel-title">Information</h3>
											</div>
											<div class="panel-body">
												<div id="info_chart"></div>
											</div>
										</div>
									</div>
									<div class="col-sm-12 col-lg-6">
										<div class="panel panel-primary">
											<div class="panel-heading">
												<h3 class="panel-title">Ticket Statics</h3>
											</div>
											<div class="panel-body">
												<div id="sold_chart"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>

		<!-- Login Form -->
		<div class="modal fade" id="login_form">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header clearfix">
						<legend class="pull-left" style="width: inherit">Log In</legend>
						<button type="button" class="close" data-dismiss="modal">
							<i class="mdi-content-clear">
							</i>
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
									<div class="col-lg-8 col-lg-offset-2">
										<button type="reset" class="btn btn-default btn-raised pull-left">Clear</button>
										<button type="submit" class="btn btn-primary btn-raised pull-right">Go!</button>
									</div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>

		<!--Registration Form -->
		<div class="modal fade" id="reg_form" role="dialog" tabindex="-1" aria-labelledby="reg_form_label" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header clearfix">
						<legend class="modal-title pull-left" id="reg_form_label" style="width: inherit">Registration</legend>
						<button type="button" class="close" data-dismiss="modal">
							<i class="mdi-content-clear">
							</i>
						</button>
					</div>
					<div class="modal-body">
						<form name="reg_form" id="reg_form" class="form-horizontal" ng-submit="reg_form.$valid && mainCtrl.regUser(reg_email, reg_password, reg_firstname, reg_lastname, number, name, expiry, cvc)" novalidate>
							<fieldset>
								<div class="form-group" ng-class="{'has-error': reg_form.reg_email.$dirty && reg_form.reg_email.$invalid}">
									<label for="reg_email" class="col-lg-4 control-label">Email</label>
									<div class="col-lg-6">
										<input type="email" name="reg_email" class="form-control" id="reg_email" placeholder="Email" ng-model="reg_email" required>
										<div ng-messages="reg_form.$dirty && reg_form.reg_email.$error" class="text-danger ng-messages">
											<div ng-message="required">Email is required</div>
										</div>
									</div>
								</div>
								<div class="form-group" ng-class="{'has-error': reg_form.reg_email_confirm.$dirty && reg_form.reg_email_confirm.$invalid}">
									<label for="reg_email_confirm" class="col-lg-4 control-label">Confirm Email</label>
									<div class="col-lg-6">
										<input type="email" name="reg_email_confirm" class="form-control" id="reg_email_confirm" placeholder="Confirm Email" ng-model="reg_email_confirm" required confirm="reg_form.reg_email">
										<div ng-messages="reg_form.reg_email_confirm.$error" class="text-danger ng-messages">
											<div ng-message="confirm">Email doesn't match</div>
										</div>
									</div>
								</div>
								<div class="form-group" ng-class="{'has-error': reg_form.reg_password.$dirty && reg_form.reg_password.$invalid}">
									<label for="reg_password" class="col-lg-4 control-label">Password</label>
									<div class="col-lg-6">
										<input type="password" name="reg_password" class="form-control" id="reg_password" placeholder="Password" ng-model="reg_password" required>
										<div ng-messages="reg_form.$dirty && reg_form.reg_password.$error" class="text-danger ng-messages">
											<div ng-message="required">Password is required</div>
										</div>
									</div>
								</div>
								<div class="form-group" ng-class="{'has-error': reg_form.reg_password_confirm.$dirty && reg_form.reg_password_confirm.$invalid}">
									<label for="reg_password_confirm" class="col-lg-4 control-label">Confirm Password</label>
									<div class="col-lg-6">
										<input type="password" name="reg_password_confirm" class="form-control" id="reg_password_confirm" placeholder="Confirm Password" ng-model="reg_password_confirm" required confirm="reg_form.reg_password">
										<div ng-messages="reg_form.reg_password_confirm.$error" class="text-danger ng-messages">
											<div ng-message="confirm">Password doesn't match</div>
										</div>
									</div>
								</div>
								<div class="form-group" ng-class="{'has-error': reg_form.reg_firstname.$dirty && reg_form.reg_firsrname.$invalid}">
									<label for="reg_firstname" class="col-lg-4 control-label">First Name</label>
									<div class="col-lg-6">
										<input type="text" name="reg_firstname" class="form-control" id="reg_firstname" placeholder="First Name" ng-model="reg_firstname" required>
									</div>
								</div>
								<div class="form-group" ng-class="{'has-error': reg_form.reg_lastname.$dirty && reg_form.reg_lastname.$invalid}">
									<label for="reg_lastname" class="col-lg-4 control-label">Last Name</label>
									<div class="col-lg-6">
										<input type="text" name="reg_lastname" class="form-control" id="reg_lastname" placeholder="Last Name" ng-model="reg_lastname" required>
									</div>
								</div>

								<!-- credit card info -->
								<div class="form-group">
									<label class="col-sm-12 control-lable" style="text-align:center">Credit Card</label>
									<br/>
									<br/>
									<div class="card-wrapper" ng-class="{invalid: reg_form.number.$dirty && reg_form.number.$invalid, valid: reg_form.number.$valid}"></div>
									<div class="container-fluid creditcard-form">
										<div class="row">
											<div class="col-sm-6">
												<div class="form-group" ng-class="{'has-error': reg_form.number.$dirty && reg_form.number.$invalid}">
													<input class="form-control" placeholder="Card number" type="text" name="number" ng-model="number" ng-minlength="19" required creditcard-number ng-model-options="{ debounce: 1000 }">
													<div ng-messages="reg_form.number.$error" class="text-danger ng-messages">
														<div ng-message="minlength">Account number is too short</div>
														<div ng-message="creditCardNumber">Account number is invalid</div>
													</div>
												</div>
											</div>
											<div class="col-sm-6">
												<div class="form-group">
													<input class="form-control" placeholder="Full name" type="text" name="name" ng-model="name">
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-6">
												<div class="form-group">
													<input class="form-control" placeholder="MM/YY" type="text" name="expiry" ng-model="expiry">
												</div>
											</div>
											<div class="col-sm-6">
												<div class="form-group">
													<input class="form-control" placeholder="CVC" type="text" name="cvc" ng-model="cvc">
												</div>
											</div>
										</div>
									</div>
								</div>

								<div class="form-group">
									<div class="col-lg-8 col-lg-offset-2">
										<button type="reset" class="btn btn-default btn-raised pull-left">Clear</button>
										<button id="reg_submit" type="submit" class="btn btn-primary btn-raised pull-right">Submit</button>
									</div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>

	<!-- load script here -->
	<script type="text/javascript">
		$(window).resize(function(){sectionHeight=$(window).height(),$.each($("section"),function(){$(this).height(sectionHeight)})});
	</script>

	<script src="<c:url value="/resources/bower_components/angular-messages/angular-messages.js" />"></script>
	<script src="<c:url value="/resources/bower_components/bootstrap/dist/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/resources/bower_components/bootstrap-material-design/dist/js/ripples.min.js" />"></script>
	<script src="<c:url value="/resources/bower_components/bootstrap-material-design/dist/js/material.min.js" />"></script>
	<script>
		$(document).ready(function() {
			// Initialize material-design-boostrap
			$.material.init();
		});
	</script>
	<script src="https://www.google.com/jsapi"></script>
	<script>
		google.load('visualization', '1.0', {'packages':['corechart', 'bar']});
	</script>
	<script src="<c:url value="/resources/dist/js/index.min.js" />"></script>
	<script>
		var card = new Card({form:'#reg_form',container:'.card-wrapper',formSelectors:{nameInput: 'input[name="name"]'}});
	</script>

	<!-- close spinner -->
	<!-- <script type="text/javascript">
		$(window).load(function(){$("#loader-wrapper").fadeOut("slow");});
	</script> -->
</body>
</html>