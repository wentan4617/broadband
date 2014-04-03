(function($){
	
	if ($.scrollUp) {
		$.scrollUp({
	        scrollName: 'scrollUp', // Element ID
	        topDistance: '300', // Distance from top before showing element (px)
	        topSpeed: 300, // Speed back to top (ms)
	        animation: 'fade', // Fade, slide, none
	        animationInSpeed: 200, // Animation in speed (ms)
	        animationOutSpeed: 200, // Animation out speed (ms)
	        scrollText: '', // Text for element
	        activeOverlay: false // Set CSS color to display scrollUp active point, e.g '#00FFFF'
	    });
	}
	
	$('a[data-toggle="tooltip"]').tooltip();
	
	$('span[csserrorclass="error"]').each(function(i){
		if (i == 0)
			$(this).closest('div.form-group').find('.form-control').focus();
		$(this).closest('div.form-group').addClass('has-error');
	});
	
	$.jsonValidation = function(json, placement){
		console.log(json);
		placement = placement || 'top';
		$('#alertContainer').find('#alert-error').remove();
		$('input[data-error-field]').tooltip('destroy').closest('div.form-group').removeClass('has-error');
		$.each(json.errorMap, function(key){
			if (key == "alert-error") {
				$('#alertContainer').html($('#tempAlertContainer').html());
				$('#text-error').text(json.errorMap[key]);
				$('#alert-error').show('normal');
			} else {
				$('#' + key)
					.tooltip({
						html: true
						, placement: placement
						, title: '<h6 class="text-danger">' + json.errorMap[key] + '</h6>'
						, trigger: 'manual'
					})
					.tooltip('show')
					.closest('div.form-group')
					.addClass('has-error');
			}
		});
		
		$('.tooltip-arrow').css('border-' + placement + '-color', '#f2dede');
		$('.tooltip-inner').css('background-color', '#f2dede')
		
		if (placement == 'right') {
			$('.tooltip-arrow').css('margin-top', '5px');
			$('.tooltip-inner').css('width', '200px');
		}
	}
	
})($);