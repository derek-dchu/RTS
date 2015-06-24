(function($, google) {

	$.drawChart = function(sold, available) {
		// Create the data table.
		var dataTable = new google.visualization.DataTable();
		dataTable.addColumn('string', 'Topping');
		dataTable.addColumn('number', 'Slices');
		dataTable.addRows([
			['Sold', Number(sold)],
			['Ava', Number(available)]
		]);

		// Set chart options
		var options = {
			'title':'Tickets Have Been Sold',
		    'width': $('#info_chart').width(),
		    'height': $('#info_chart').height()
		};

		// Instantiate and draw our chart, passing in some options.
		var chart = new google.visualization.PieChart(document.querySelector('#sold_chart'));
		chart.draw(dataTable, options);
	}

	/**
	*    @Param: data List of tickets
	*/
	$.drawStuff = function(data) {
		var dataTable = new google.visualization.DataTable();
		dataTable.addColumn('number','Ticket ID');
		dataTable.addColumn('number', 'Sold');
		dataTable.addColumn('number', 'Available');
		dataTable.addRows(data.length);
		data.forEach(function( item, i ) {
			dataTable.setValue(i, 0, item.ticketid);
			dataTable.setValue(i, 1, item.sold);
			dataTable.setValue(i, 2, item.available);
		});
		  	  
		// Set chart options
		var options = {
		    title: 'Information',
		    	width: 300,
		    	height: 300
		};
		    
		//instantiate and draw our chart, passing in the options
		var chart = new google.visualization.ColumnChart(document.querySelector('#info_chart'));
		chart.draw(dataTable, options);
	}

})(jQuery, google);