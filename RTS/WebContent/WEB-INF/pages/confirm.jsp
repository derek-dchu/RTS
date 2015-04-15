<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>RTS | confirmation</title>

    <script src="<c:url value="/resources/bower_components/jquery/dist/jquery.min.js" />"></script>
</head>
<body>
	<h1>Thank you for your confirmation</h1>
	<p>Redirect to main page in <span class="count">5</span> seconds...</p>
	
	<script type="text/javascript">
		$(document).ready(function() {
		    window.setInterval(function() {
		    var timeLeft = $(".count").html();                     
		        if(eval(timeLeft) == 0) {
		                window.location= ("/RTS");        
		        } else {
		            $(".count").html(parseInt(timeLeft) - 1);
		        }
		    }, 1000); 
		});   
	</script>
</body>
</html>