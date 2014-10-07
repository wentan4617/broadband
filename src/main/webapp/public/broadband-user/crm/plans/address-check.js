(function($){
	
	var ctx = $('#result_tmpl').attr('data-ctx')
		, select_plan_id = $('#checkResult').attr('data-select_plan_id')
		, select_plan_type = $('#checkResult').attr('data-select_plan_type')
		, isShowContinue = $('#checkResult').attr('data-isShowContinue')
	
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
			$.get(ctx + '/crm/plans/address/check/' + address, function(broadband){ //console.log(broadband);
				broadband.ctx = ctx;
				broadband.type = select_plan_type;
				broadband.selected_id = select_plan_id;
				broadband.isShowContinue = isShowContinue;
				$('#checkResult').html(tmpl('result_tmpl', broadband));
				if (broadband.adsl_available) { //alert('adsl');
					$('#adsl').click(function(){
						window.location.href = ctx + '/broadband-user/crm/plans/define/ADSL';
					});
				}
				if (broadband.vdsl_available) {
					$('#vdsl').click(function(){ //alert('vdsl');
						window.location.href = ctx + '/broadband-user/crm/plans/define/VDSL';
					});
				}
				if (broadband.ufb_available) {
					$('#ufb').click(function(){ //alert('ufb');
						window.location.href = ctx + '/broadband-user/crm/plans/define/UFB';
					});
				}
		   	}).always(function(){ l.stop(); });
		} else {
			alert('Please enter a real address.');
		}
	});
	
})(jQuery);