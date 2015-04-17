'use strict';

/**
*	Show sidebar when scroll into section two.
*	Hide sidebar when scroll back to section one.
*/

(function($) {
	$( window ).scroll(function() {
		if ( $(document).scrollTop() >= sectionHeight && $('.sidebar-section').hasClass('toggled') ) {
			$('.sidebar-section').removeClass('toggled');
		} else if ($(document).scrollTop() < sectionHeight && !$('.sidebar-section').hasClass('toggled') )  {
			$('.sidebar-section').addClass('toggled');
		}
	});
})(jQuery);
