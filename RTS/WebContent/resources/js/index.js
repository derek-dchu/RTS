var app = angular.module("indexPage", []);
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
				$scope.tickets = data;
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
		
		$scope.openLoginForm = function() {
			alert("open!");
		};
	}]);