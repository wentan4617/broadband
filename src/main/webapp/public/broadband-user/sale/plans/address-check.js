(function($){
	
	var ctx = $('#result_tmpl').attr('data-ctx')
		, select_plan_id = $('#checkResult').attr('data-select_plan_id')
		, select_plan_type = $('#checkResult').attr('data-select_plan_type')
		, select_customer_type = $('#checkResult').attr('data-select_customer_type')
	
	$(document).keypress(function(e){
		if (event.keyCode == 13) {
			$('#goCheck').trigger('click');
		}
	});
	
	$('#goCheck').click(function(){
		var address = $('#address').val();
		address = $.trim(address.replace(/[\/]/g,' ').replace(/[\\]/g,' ')); //console.log(address);
		if (address != '') {
			var l = Ladda.create(this); l.start();
			$.get(ctx + '/sale/plans/address/check/' + address, function(broadband){ //console.log(broadband);
				broadband.ctx = ctx;
				broadband.type = select_plan_type;
				broadband.selected_id = select_plan_id;
				broadband.select_customer_type = select_customer_type;
				$('#checkResult').html(tmpl('result_tmpl', broadband));
				if (broadband.adsl_available) { //alert('adsl');
					$('#adsl').click(function(){
						window.location.href = ctx + '/broadband-user/sale/plans/order?select_plan_type=ADSL';
					});
				}
				if (broadband.vdsl_available) {
					$('#vdsl').click(function(){ //alert('vdsl');
						window.location.href = ctx + '/broadband-user/sale/plans/order?select_plan_type=VDSL';
					});
				}
				if (broadband.ufb_available) {
					$('#ufb').click(function(){ //alert('ufb');
						window.location.href = ctx + '/broadband-user/sale/plans/order?select_plan_type=UFB';
					});
				}
		   	}).always(function(){ l.stop(); });
		} else {
			alert('Please enter a real address.');
		}
	});
	
})(jQuery);