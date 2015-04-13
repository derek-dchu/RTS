var app = angular.module('indexPage', []);
	
app.controller('mainController', 
		['$http', '$filter', '$location', 'anchorSmoothScroll', '$scope',
		 function($http, $filter, $location, anchorSmoothScroll, $scope) {
	var that = this;
	var searchTicketUrl ="/RTS/rest/search/searchticket";
	var buyTicketUrl = "/RTS/rest/buy";
	
	this.timeTypes = [
		{label: "Any Time", value: null},
		{label: "Depart At", value: "D"},
		{label: "Arrive By", value: "A"}
	];
	
	this.dep = "";
	this.des = "";
	this.time = "";
	this.timeType = this.timeTypes[0];
	this.tickets = [];
	this.tableshow = false;
	this.selectedTicket = null;
	
	this.searchTicket = function() {
		var dtime = null,
			atime = null;
		if (that.timeType.value === "D") {
			dtime = $filter('date')(that.time,'yyyy/MM/dd hh:mm a');
		}
		
		if (that.timeType.value === "A") {
			atime = $filter('date')(that.time,'yyyy/MM/dd hh:mm a');
		}
		
		$http({
			method:'POST',
			url: searchTicketUrl,
			headers:{'Content-Type':'application/x-www-form-urlencoded'},
			data: $.param({
				des: this.des,
				dep: this.dep,
				dtime: dtime,
				atime: atime
			})
		}).success(function(data) {
			that.tickets = data;
			that.tableshow = true;
		});
		
		if ($location.path() !== '/tickets') $location.hash('tickets');
		anchorSmoothScroll.scrollTo('ticket_page');
	};
	
	this.resetForm = function(){
		this.dep = "";
		this.des = "";
		this.time = "";
		this.timeType = this.timeTypes[0];
		this.searchForm.$setPristine();
	};
	
	this.buy = function(ticketid, quantity) {
		var username = $("#user_name").text();
		console.log("current user: ", username);
		console.log("buy ticket: ", ticketid, " quantity: ", quantity);
		$http({
		    method:'GET',
		    url: buyTicketUrl,
		    params: {
		    	tid: ticketid,
			    username: username,
			    qt: quantity
		    }
		});
	};
}]);

app.service('anchorSmoothScroll', function() {
    
    this.scrollTo = function(eID) {

        // This scrolling function 
        // is from http://www.itnewb.com/tutorial/Creating-the-Smooth-Scroll-Effect-with-JavaScript
        
        var startY = currentYPosition();
        var stopY = elmYPosition(eID);
        var distance = stopY > startY ? stopY - startY : startY - stopY;
        if (distance < 100) {
            scrollTo(0, stopY); return;
        }
        var speed = Math.round(distance / 100);
        if (speed >= 20) speed = 20;
        var step = Math.round(distance / 25);
        var leapY = stopY > startY ? startY + step : startY - step;
        var timer = 0;
        if (stopY > startY) {
            for ( var i=startY; i<stopY; i+=step ) {
                setTimeout("window.scrollTo(0, "+leapY+")", timer * speed);
                leapY += step; if (leapY > stopY) leapY = stopY; timer++;
            } return;
        }
        for ( var i=startY; i>stopY; i-=step ) {
            setTimeout("window.scrollTo(0, "+leapY+")", timer * speed);
            leapY -= step; if (leapY < stopY) leapY = stopY; timer++;
        }
        
        function currentYPosition() {
            // Firefox, Chrome, Opera, Safari
            if (self.pageYOffset) return self.pageYOffset;
            // Internet Explorer 6 - standards mode
            if (document.documentElement && document.documentElement.scrollTop)
                return document.documentElement.scrollTop;
            // Internet Explorer 6, 7 and 8
            if (document.body.scrollTop) return document.body.scrollTop;
            return 0;
        }
        
        function elmYPosition(eID) {
            var elm = document.getElementById(eID);
            var y = elm.offsetTop;
            var node = elm;
            while (node.offsetParent && node.offsetParent != document.body) {
                node = node.offsetParent;
                y += node.offsetTop;
            } return y;
        }

    };
    
});