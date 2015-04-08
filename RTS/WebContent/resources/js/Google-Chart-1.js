google.load('visualization', '1.0', {'packages':['corechart']});
google.load("visualization", "1.0", {'packages':['bar']});

/*s: Sold a:available  */
function drawChart(s,a) {

// Create the data table.
var data = new google.visualization.DataTable();

data.addColumn('string', 'Topping');
data.addColumn('number', 'Slices');
data.addRows([
  ['Sold', Number(s)],
  ['Ava', Number(a)]
]);

// Set chart options
var options = {'title':'How Many Tickets Have Been Sold',
               'width':300,
               'height':300};

// Instantiate and draw our chart, passing in some options.
var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
chart.draw(data, options);
}


/*data: respones from rest.
  Data => Ticket.bean
*/
function drawStuff(data) {
		var data2 = new google.visualization.DataTable();
		 data2.addColumn('string','Ticket ID');
		  data2.addColumn('number', 'Sold');
		  data2.addColumn('number', 'available');
		  data2.addRows($(data.ticket).length);
		  $(data.ticket).each(function(i,item){
/*			  
			  data2.setValue(i,0,item.tid);
			  data2.setValue(i,1,item.sold);
			  data2.setValue(i,2,item.available);*/
		  })
	  	  
	    //set the chart options
	    var options = {
	        title: 'Information',
	        width: 400,
	        height: 300
	    };
	    //instantiate and draw our chart, passing in the options
	    var chart = new google.visualization.ColumnChart(document.querySelector('#chart'));
	    chart.draw(data2, options);
	}