(function($){
	
	$(':checkbox').iCheck({ checkboxClass: 'icheckbox_square-green', radioClass: 'iradio_square-green' });
	
	$('#online_payment').click(function(){
		if ($('#termckb').prop('checked')) {
			$('#checkoutForm').submit();
		} else {
			alert("You must agree to CyberPark terms, in order to continue to buy and register as a member.");
		}
	});
	
	$('#bank_depoist').click(function(){
		if ($('#termckb').prop('checked')) {
			var $form = $('#checkoutForm');
			$form.attr('action', ctx + '/plans/order/bankdeposit');
			$form.submit();
		} else {
			alert("You must agree to CyberPark terms, in order to continue to buy and register as a member.");
		}
	});
	
	var i = 0;
	
	$('#addVoucher').click(function(){ // 
		obj = { index: i };
		$('#voucherFormContainer').append(tmpl('voucher_form_tmpl', obj));
		$('a[data-index]').off('click').click(function(){ // console.log(1);
			$(this).closest('div[data-index]').remove();
		});
		$('a[data-apply]').off('click').click(function(){ // console.log(parent);
			var parent = $(this).closest('div[data-index]');
			var index = $(this).attr('data-index');
			var voucher = {
				serial_number: parent.find("#serial_number" + index).val()
				, card_number: parent.find("#card_number" + index).val()
				, index: index
			}; // console.log(voucher);
			var l = Ladda.create(this); l.start();
		 	$.post(ctx + '/plans/order/online-pay-by-voucher', voucher, function(json){
				if (!$.jsonValidation(json, 'top')) {
					var v = json.model;
					parent.empty();
					parent.append(tmpl('voucher_form_result_tmpl', v));
					cancelVoucher(); 
					total_vprice += v.face_value; //console.log(total_vprice);
					TAAV();
				}
			}).always(function() { l.stop(); });
		});
		i++;
	});
	
	function cancelVoucher() {
		$('a[data-voucher-cancel]').off('click').click(function(){
			var parent = $(this).closest('div[data-index]');
			var voucher = {
				serial_number: $(this).attr('data-voucher-serial-number')
				, card_number: $(this).attr('data-voucher-card-number')
			}; // console.log(voucher);
			var l = Ladda.create(this); l.start();
		 	$.post(ctx + '/plans/plan-topup/cancel-voucher-apply', voucher, function(json){
		 		if (!$.jsonValidation(json)) {
		 			var v = json.model;
		 			parent.remove();
		 			total_vprice -= v.face_value; //console.log(total_vprice);
		 			TAAV();
		 		}
		 	}).always(function() { l.stop(); });
		});
	}
	
	cancelVoucher();
	
	function TAAV() {
		if (total_vprice > 0) { //console.log('show');
			var TAAVPrice = (order_price - total_vprice).toFixed(2);
			$('#vPrice').text('NZ$ -' + total_vprice.toFixed(2));
			$('#TAAVPrice').text('NZ$ ' + TAAVPrice);
			$('#TAAV').show();
			$('#voucherTR').show();
		} else {
			$('#TAAV').hide();
			$('#voucherTR').hide();
		}
	}
	
	TAAV();
	
})(jQuery);