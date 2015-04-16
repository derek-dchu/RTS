
(function($, angular) {
	
var app = angular.module('indexPage', ['ngMessages']);
	
app.controller('mainController', 
		['$http', '$filter', '$location', 'anchorSmoothScroll', '$scope',
		 function($http, $filter, $location, anchorSmoothScroll, $scope) {
	var that = this;
	var host = $location.host();
	var port = $location.port();
	var root = "http://" + host + ":" + port;
	var searchTicketUrl = root + "/RTS/rest/search/searchticket";
	var buyTicketUrl = root + "/RTS/rest/buy";
	var regUserUrl = root + "/RTS/rest/sys/reg";
		
	this.regUser = function(email, password, firstName, lastName, number, name, expiry, cvc) {
		$('#reg_submit').addClass('disabled').html('please wait...');

		$http({
		    method:'POST',
		    url: regUserUrl,
		    headers:{'Content-Type':'application/x-www-form-urlencoded'},
		    data: $.param({
			    	firstName: firstName,
			    	lastName: lastName,
			    	email: email,
			    	password: password,
			    	cnum: parseInt(number.split(' ').join('')),
			    	cdate: parseInt(expiry.split(' / ').join('')),
			    	cvc: parseInt(cvc)
		    })
		}).success(function() {
			$('#reg_submit').removeClass('disabled').html('submit');
		}).error(function() {
			$('#reg_submit').removeClass('disabled').html('submit');
		});
	};
	
	this.timeTypes = [
		{label: "Any Time", value: null},
		{label: "Depart At", value: "D"},
		{label: "Arrive By", value: "A"}
	];
	
	this.dep = "";
	this.des = "";

	this.time = new Date();
	/* eliminate second part */
	this.time = new Date(this.time.getFullYear(), this.time.getMonth(), this.time.getDate(), this.time.getHours(), this.time.getMinutes());

	this.timeType = this.timeTypes[0];
	this.tickets = [];
	this.selectedTicket = null;

	/* ticket list order */
	this.predicate = 'dtime';
	this.reverse = false;
	
	this.searchTicket = function() {
		var dtime = null,
			atime = null;
		if (that.timeType.value === "D") {
			dtime = $filter('date')(that.time,'yyyy/MM/dd HH:mm');
		}
		
		if (that.timeType.value === "A") {
			atime = $filter('date')(that.time,'yyyy/MM/dd HH:mm');
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

			/* to fix orderBy problem, convert price to float here */
			that.tickets.forEach(function(elm, ind, arr) {
				elm.price = parseFloat(elm.price);
			});

			$.drawStuff(that.tickets);
		}).error(function(error) {
			this.tickets = [];
		});

		if ($location.path() !== '/tickets') $location.hash('tickets');
		anchorSmoothScroll.scrollTo('ticket_page');
	};
	
	this.buy = function(quantity) {
		$('#buy_submit').addClass('disabled').html('please wait...');
		
		var username = $("#user_name").text();
		var ticketid = that.selectedTicket.ticketid;
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
		}).success(function() {
			$('#buy_submit').removeClass('disabled').html('buy');
		}).error(function() {
			$('#buy_submit').removeClass('disabled').html('buy');
		});
	};

	this.selectTicket = function(ticket) {
		that.selectedTicket = ticket;
		$.drawChart(ticket.sold, ticket.available);
	};

	this.setPredicate = function(predicate, isReverse) {
		that.predicate = predicate;
		that.reverse = isReverse;
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

/* Confirmation validator */
app.directive('confirm', function() {
    return {
        require: "ngModel",
        scope: {
            otherModel: "=confirm"
        },
        link: function(scope, element, attributes, ngModel) {
            ngModel.$validators.confirm = function(modelValue, viewValue) {
            	if (ngModel.$isEmpty(modelValue)) {
            		// consider empty models to be valid
            		return true;
            	}

            	if (modelValue === scope.otherModel.$modelValue) {
            		return true;
            	}
            	return false;
            };
 
            scope.$watch("otherModel.$modelValue", function() {
                ngModel.$validate();
            });
        }
    };
});

/* Luhn algorithm validator, by Avraham Plotnitzky. */
app.directive('creditcardNumber', function() {
	var luhnCheckFast = function(sequence) {
		var ca, sum = 0, mul = 1;
	    var len = sequence.length;
	    while (len--)
	    {
	        ca = parseInt(sequence.charAt(len),10) * mul;
	        sum += ca - (ca>9)*9;// sum += ca - (-(ca>9))|9
	        // 1 <--> 2 toggle.
	        mul ^= 3; // (mul = 3 - mul);
	    };
	    return (sum%10 === 0) && (sum > 0);
	}

	return {
		require: "ngModel",
		link: function(scope, element, attributes, ngModel) {
			ngModel.$validators.creditCardNumber = function(modelValue, viewValue) {
				if (ngModel.$isEmpty(modelValue)) {
					return true;
				}
				var accountNumber = modelValue.split(' ').join('');
				if (luhnCheckFast(accountNumber)) return true;
				return false;
			}
		}
	}
});

} )(jQuery, angular);