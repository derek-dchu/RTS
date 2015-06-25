<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>RTS | confirmation</title>

		<link href="<c:url value="/resources/bower_components/bootstrap/dist/css/bootstrap.min.css" />" rel="stylesheet">
		<!-- Include roboto.css to use the Roboto web font, material.css to include the theme and ripples.css to style the ripple effect -->
		<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/roboto.min.css" />" rel="stylesheet">
		<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/material.min.css" />" rel="stylesheet">
		<link href="<c:url value="/resources/bower_components/bootstrap-material-design/dist/css/ripples.min.css" />" rel="stylesheet">
	</head>
	<body>
		<div class="jumbotron">
			<h1>Thank you for your confirmation</h1>
			<p>Redirect to main page in <span class="count">5</span> seconds...</p>
		</div>

		<script src="<c:url value="/resources/bower_components/jquery/dist/jquery.min.js" />"></script>
		<script src="<c:url value="/resources/bower_components/bootstrap/dist/js/bootstrap.min.js" />"></script>
		<script src="<c:url value="/resources/bower_components/bootstrap-material-design/dist/js/ripples.min.js" />"></script>
		<script src="<c:url value="/resources/bower_components/bootstrap-material-design/dist/js/material.min.js" />"></script>
		
		<script type="text/javascript">
			$(document).ready(function() {
			    window.setInterval(function() {
					var count = $('.count');
			        var timeLeft = eval(count.text());
			        if(timeLeft == 0) {
			                window.location= ("/rts");
			        } else {
			            count.html(parseInt(timeLeft) - 1);
			        }
			    }, 1000); 
			});   
		</script>
	</body>
</html>