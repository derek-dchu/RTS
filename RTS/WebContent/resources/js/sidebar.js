/**
*	Show sidebar when scroll into section two.
*	Hide sidebar when scroll back to section one.
*/

$( window ).scroll(function() {
	if ( $(document).scrollTop() >= sectionHeight && $('.sidebar-section').hasClass('toggled') ) {
		$('.sidebar-section').removeClass('toggled');
	} else if ($(document).scrollTop() < sectionHeight && !$('.sidebar-section').hasClass('toggled') )  {
		$('.sidebar-section').addClass('toggled');
	}
});