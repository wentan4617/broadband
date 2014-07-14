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
	 
	$.jsonValidation = function(json, placement) { // console.log(json);
		placement = placement || 'top';
		var $alertContainer = $('#alertContainer');
		$alertContainer.find('#alert-success').remove();
		$alertContainer.find('#alert-error').remove();
		$('[data-error-field]').tooltip('destroy').closest('div.form-group').removeClass('has-error');
		$.each(json.errorMap, function(key){
			if (key == "alert-error") {
				$('#alertContainer').html($('#tempAlertErrorContainer').html());
				$('#text-error').text(json.errorMap[key]);
				$('#alert-error').show('normal');
			} else {
				var $input = $('#' + key.replace('.','\\.'));
				var input_placement = $input.attr('data-placement'); // console.log(input_placement);
				$input
					.focus()
					.tooltip({
						html: true
						, placement: (input_placement || placement)
						, title: '<span class="text-danger">' + json.errorMap[key] + '</span>'
						, trigger: 'manual'
					})
					.tooltip('show')
					.closest('div.form-group')
					.addClass('has-error');
				
				var ele_tooltip = $input.next('div'); 
				var ele_arrow = ele_tooltip.find('.tooltip-arrow');
				var ele_inner = ele_tooltip.find('.tooltip-inner');
				ele_arrow.css('border-' + (input_placement || placement) + '-color', '#f2dede');
				ele_inner.css('background-color', '#f2dede');
				if ((input_placement || placement) == 'right') {
					ele_arrow.css('top', '6.62%');
					ele_arrow.css('margin-top', '5px');
					ele_inner.css('width', '200px');
				}
			}
		});
		$.each(json.successMap, function(key){
			if (key == "alert-success") {
				$('#alertContainer').html($('#tempAlertSuccessContainer').html());
				$('#text-success').text(json.successMap[key]);
				$('#alert-success').show('normal');
			} 
		});
		
		return json.hasErrors;
	}
	
})($);