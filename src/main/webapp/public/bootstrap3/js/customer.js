(function($){
	
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
	
	$('a[data-toggle="tooltip"]').tooltip();
	
	$('span[csserrorclass="error"]').each(function(i){
		if (i == 0)
			$(this).closest('div.form-group').find('.form-control').focus();
		$(this).closest('div.form-group').addClass('has-error');
		console.log('error');
	});
	
	$.jsonValidation = function(json, placement) { // console.log(json);
		placement = placement || 'top';
		$('#alertContainer').find('#alert-error').remove();
		$('input[data-error-field]').tooltip('destroy').closest('div.form-group').removeClass('has-error');
		$.each(json.errorMap, function(key){
			if (key == "alert-error") {
				$('#alertContainer').html($('#tempAlertContainer').html());
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
						, title: '<h6 class="text-danger">' + json.errorMap[key] + '</h6>'
						, trigger: 'manual'
					})
					.tooltip('show')
					.closest('div.form-group')
					.addClass('has-error');
				
				var nextDiv = $input.next('div');
				var ele_arrow = nextDiv.find('.tooltip-arrow');
				var ele_inner = nextDiv.find('.tooltip-inner');
				ele_arrow.css('border-' + (input_placement || placement) + '-color', '#f2dede');
				ele_inner.css('background-color', '#f2dede');
				if ((input_placement || placement) == 'right') {
					ele_arrow.css('margin-top', '5px');
					ele_inner.css('width', '200px');
				}
			}
		});
	}
	
	/*var loginHtml = '';
	loginHtml += '<form class="loginForm">';
	loginHtml += '<div class="form-group">';
	loginHtml += '<label for="login_name">Your Mobile or Email</label>';
	loginHtml += '<input type="text" id="login_name" class="form-control" placeholder="" data-error-field/>';
	loginHtml += '</div>';
	loginHtml += '<div class="form-group">';
	loginHtml += '<label for="password">Password</label>';
	loginHtml += '<input type="password" id="password" class="form-control" placeholder="" data-error-field/>';
	loginHtml += '</div>';
	loginHtml += '<div class="form-group">';
	loginHtml += '<button type="button" data-loading-text="loading..." class="btn btn-success btn-block" id="signin-btn" style="margin-bottom:10px;">Login</button>';
	loginHtml += '</div>';
	loginHtml += '</form>';
	
	var log_opt = {
		html: true
		, trigger: 'click'
		, placement: 'bottom'
		, title: 'CyberPark Customer Login'
		, content: loginHtml
		, container: 'body'	
	}
	
	$('#login').popover(log_opt).on('shown.bs.popover', function () {
		var ctx = $(this).attr('data-ctx');
		$(document).keypress(function(e){
			if ($('#loginForm input:focus').length > 0 && event.keyCode == 13) {
				$('#signin-btn').trigger('click');
			}
		});
		
		$('#signin-btn').on("click", function(){
			var $btn = $(this);
			var data = {
				login_name: $('#login_name').val()
				, password: $('#password').val()
			};
			$btn.button('loading');
			$.post(ctx + '/login', data, function(json){
				if (json.hasErrors) {
					$.jsonValidation(json);
				} else {
					window.location.href= ctx + json.url;
				}
			}, 'json').always(function () {
				$btn.button('reset');
		    });
		});
	});*/
	
})(jQuery);