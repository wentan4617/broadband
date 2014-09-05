(function($) {
	
	var ctx = $('#select_plan_tmpl').attr('data-ctx')
		, select_plan_id = $('#select_plan_tmpl').attr('data-select_plan_id')
		, select_plan_type = $('#select_plan_tmpl').attr('data-select_plan_type')
		, customer_address = $('#order_modal_tmpl').attr('data-customer-address')
		, select_modem_container = $('#select-modem')
		, prepay_months_container = $('#prepay-month')
		, open_term_container = $('#open-term')
		, sale_id = $('#select_plan_tmpl').attr('data-sale-id')
		
	var	application = $('#application_tmpl') 
	, cellphone = application.attr('data-cellphone')
	, email = application.attr('data-email')
	, title = application.attr('data-title')
	, first_name = application.attr('data-first_name')
	, last_name = application.attr('data-last_name')
	, identity_type = application.attr('data-identity_type')
	, identity_number = application.attr('data-identity_number')
	, transition_provider_name  = application.attr('data-transition_provider_name')
	, transition_account_holder_name = application.attr('data-transition_account_holder_name')
	, transition_account_number = application.attr('data-transition_account_number')
	, transition_porting_number  = application.attr('data-transition_porting_number')
	
	var plan = {};
	var modems = null;
	var modem_selected = null;
	var naked = false;
	var modem_name = "";
	var isContract = false; //(select_plan_type == 'UFB' ? true : false);
	var contract_name = "";
	var order_broadband_type = "Transfer Broadband Connection";
	var prepay_months = 1;
	var discount_desc = '';
	var connection_date = 'ASAP';
	var hardware_class = '';
	var cal;
	
	var months_selected = "1";
	if (select_plan_type == 'ADSL') {
		months_selected = "1";
	} else if (select_plan_type == 'VDSL') {
		if (sale_id == null || sale_id == '') {
			months_selected = "1";
		} else {
			months_selected = "12";
		}
	} else if (select_plan_type == 'UFB') {
		months_selected = "12";
	}
	var hardware_id_selected = "0";
	var hardware_value_selected = "withoutmodem";
	var broadband_value_selected = "transition";
	
	var price = {
		plan_price: 0
		, service_price: 0
		, addons_price: 0
		, modem_price: 0
		, discount_price: 0
		
		, save_service_price: 0
		, save_modem_price: 0
	};
		
	function loadingPlans() {
		var url = ctx + '/plans/loading/' + select_plan_id + "/" + select_plan_type;
		$.get(url, function(planTypeMap){
			
			var planMap = planTypeMap[select_plan_type];
			var plansClothed = planMap['plansClothed'];
			var plansNaked = planMap['plansNaked'];
			
			var obj = {
				ctx: ctx
				, sale_id: sale_id
				, select_plan_id: select_plan_id 
				, select_plan_type: select_plan_type
				, plansClothed: plansClothed
				, plansNaked: plansNaked
			};
			
			var $div = $('#select-plan');
			$div.html(tmpl('select_plan_tmpl', obj));
			
			// loadingModems
			if (select_plan_type == 'ADSL') {
				hardware_class = 'router-adsl';
			} else if (select_plan_type == 'VDSL') {
				hardware_class = 'router-vdsl';
			} else if (select_plan_type == 'UFB') {
				hardware_class = 'router-ufb';
			}
			var url = ctx + "/plans/hardware/loading/" + hardware_class;
			$.get(url, function(hardwares){ //console.log(hardwares);
				modems = hardwares;
				//flushModems();
				
				
				$('div[data-plan-id]').click(function(){ //alert('a');
					var $div = $(this);
					$('div[data-plan-id]').removeClass('alert-danger').addClass('alert-success');
					$('div[data-plan-id] span[data-icon] > span').css('opacity', '0');
					$div.addClass('alert-danger');
					$div.find('span[data-icon] > span').css('opacity', '1');
					
					var plan_id = Number($div.attr('data-plan-id'));
					var plan_sort = $div.attr('data-plan_sort');
					
					if (plan_sort == 'CLOTHED') {
						for (var i = 0, len = plansClothed.length; i < len; i++) {
							if (plansClothed[i].id == plan_id) {
								plan = plansClothed[i];
								break;
							}
						}
					} else if (plan_sort == 'NAKED') {
						for (var i = 0, len = plansNaked.length; i < len; i++) {
							if (plansNaked[i].id == plan_id) {
								plan = plansNaked[i];
								break;
							}
						}
					}
					
					naked = plan.pstn > 0 ? false : true;
					
					price.plan_price = plan.plan_price;
					price.service_price = plan.transition_fee;
					
					//console.log(plan);
					flushOpenTerm();
				});
				
				flushApplication();
				
				var $div = $('div[data-plan-id="' + select_plan_id + '"]');
				if ($div.length == 0) {
					$div = $('div[data-plan-id]:first');
				}
				$div.trigger('click');
				var sort = $div.attr('data-plan_sort');
				if (sort == 'CLOTHED') {
					$('#plans a:first').tab('show');
				} else if (sort == 'NAKED') {
					$('#plans a:last').tab('show');
				}
				
			});
			
		});
	}
	
	loadingPlans();
	
	function flushOpenTerm() {
		var obj = {
			ctx: ctx
			, select_plan_type: select_plan_type
			, sale_id: sale_id
		};
		var $div = $('#open-term');
		$div.html(tmpl('open_term_tmpl', obj));
		
		$('input[name="contract"]').iCheck({ checkboxClass : 'icheckbox_square-green' , radioClass : 'iradio_square-green' });
		$('input[name="contract"]').on('ifChecked', function(){
			var value = this.value;
			if (value == 'open-term') {
				isContract = false;
				contract_name = '';
				prepay_months_container.show('fast');
				flushPrepayMonth();
			} else if (value == '12months') {
				isContract = true;
				contract_name = '12 months contract';
				discount_desc = '';
				select_modem_container.show('fast');
				prepay_months_container.hide('fast');
				prepay_months = 1;
				price.discount_price = 0;
				modem_selected = null;
				flushModems();
				flushBroadbandOptions();
			}
			
		});
		
		var contract_oo = $('input[name="contract"][value="' + (isContract ? '12months' : 'open-term') + '"]');
		contract_oo.iCheck('check');
	}
	
	function flushPrepayMonth() {
		var obj = {
			ctx: ctx
			, price: price
			, select_plan_type: select_plan_type
			, sale_id: sale_id
		};
		var $div = $('#prepay-month');
		$div.html(tmpl('prepay_month_tmpl', obj));
		$('input[name="prepaymonths"]').iCheck({ checkboxClass : 'icheckbox_square-green' , radioClass : 'iradio_square-green' });
		$('input[name="prepaymonths"]').on('ifChecked', function(){
			var value = Number(this.value); 
			months_selected = value;
			if (value == 1) {
				select_modem_container.show('fast');
				discount_desc = '';
				prepay_months = 1;
				price.discount_price = 0;
				modem_selected = null;
			} else if (value == 3) {
				select_modem_container.show('fast');
				discount_desc = '3% off the total price of 3 months plan';
				prepay_months = 3;
				price.discount_price = parseInt(price.plan_price * 3 * 0.03);
				modem_selected = null;
			} else if (value == 6) {
				select_modem_container.show('fast');
				discount_desc = '7% off the total price of 6 months plan';
				prepay_months = 6;
				price.discount_price = parseInt(price.plan_price * 6 * 0.07);
				modem_selected = null;
			} else if (value == 12) {
				
				if (select_plan_type == 'VDSL' && sale_id != null && sale_id != '') {
					discount_desc = '<span class="text-danger">free iPad Mini 16G, free VOIP Wifi modem, 200 calling minutes of 40 countries</span>';
					prepay_months = 12;
					price.discount_price = 0;
					open_term_container.hide('fast');
					prepay_months_container.hide('fast');
				} else {
					discount_desc = '15% off the total price of 12 months plan with free modem';
					prepay_months = 12;
					price.discount_price = parseInt(price.plan_price * 12 * 0.15);
				}
				
				select_modem_container.hide('fast');
				
				modem_selected = modems && $.extend({}, modems[0]);
				if (modem_selected != null) modem_selected.hardware_price = 0;
				//console.log(modem_selected);
			}
			
			price.modem_price = 0;
			price.save_modem_price = 0;
			modem_name = "";
			
			flushModems();
			flushBroadbandOptions();
		});
		
		var months_oo = $('input[name="prepaymonths"][value="' + months_selected + '"]');
		months_oo.iCheck('check');
	}

	//flushPrepayMonth();
	
	/*function loadingModems() {
		if (select_plan_type == 'ADSL') {
			hardware_class = 'router-adsl';
		} else if (select_plan_type == 'VDSL') {
			hardware_class = 'router-vdsl';
		} else if (select_plan_type == 'UFB') {
			hardware_class = 'router-ufb';
		}
		var url = ctx + "/plans/hardware/loading/" + hardware_class;
		$.get(url, function(hardwares){ //console.log(hardwares);
			modems = hardwares;
			flushModems();
		});
	}*/
	
	function flushModems() {
		var obj = {
			ctx: ctx
			, prepay_months: prepay_months
			, isContract: isContract
			, hardwares: modems
		};
		var $div = $('#select-modem');
		$div.html(tmpl('select_modem_tmpl', obj));		
		
		$('input[name="modem"]').iCheck({ checkboxClass : 'icheckbox_square-green' , radioClass : 'iradio_square-green' });
		$('input[name="modem"]').on('ifChecked', function(){
			var $modem = $(this);
			var id = Number($modem.attr('data-id'));
			var value = this.value;
			hardware_id_selected = id;
			hardware_value_selected = value;
			
			if (value == 'withoutmodem') {
				modem_selected = null;
				price.modem_price = 0;
				price.save_modem_price = 0;
				modem_name = '';
			} else { //console.log('id: ' + id + ", modems: " + modems.length);
				if (modems != null && modems.length > 0) {
					for (var i = 0, len = modems.length; i < len; i++) {
						var modem = modems[i];
						if (modem.id == id) {
							modem_selected = modem;
							if (!isContract) {
								if (prepay_months == 1) {
									price.modem_price = Number(modem.hardware_price);
									price.save_modem_price = 0;
								} else if (prepay_months == 3 || prepay_months == 6) {
									price.modem_price = modem.hardware_price -  parseInt((modem.hardware_price/12)*prepay_months);
									price.save_modem_price = parseInt((modem.hardware_price/12)*prepay_months);
								}
							} else {
								price.modem_price = parseInt(modem.hardware_price/2);
								price.save_modem_price = parseInt(modem.hardware_price/2);
							}
							modem_name = modem.hardware_name;
							break;
						}
					}
				}
			}
			flushOrderModal();
		});
		
		if (prepay_months != 12) {
			var modem_oo = $('input[name="modem"][data-id="' + hardware_id_selected + '"]').first();
			modem_oo.iCheck('check');
		}
		
	}
	
	//loadingModems();
	
	function flushApplication() {
		var obj = { 
			ctx: ctx 
			, prepay_months: prepay_months
			, plan: plan
			, cellphone: cellphone
			, email: email
			, title: title
			, first_name: first_name
			, last_name: last_name
			, identity_type: identity_type
			, identity_number: identity_number
			, transition_provider_name: transition_provider_name
			, transition_account_holder_name: transition_account_holder_name
			, transition_account_number: transition_account_number
			, transition_porting_number: transition_porting_number
		};
		var $div = $('#application');
		$div.html(tmpl('application_tmpl', obj));
		
		$('#m-get-it-now').click(confirm);
		
		cal = $('#sandbox-container div');
		cal.datepicker({
		    format: "dd/mm/yyyy",
		    startDate: '+7d',
		    daysOfWeekDisabled: "0,6",
		    todayHighlight: true,
		}).on('changeDate', function(e){
			$('input[name="connection_date"]').iCheck('uncheck');
			connection_date = e.format();
		});
		
		$('input[name="connection_date"]').on('ifChecked', function(){
			cal.datepicker('update', null);
		});
		
		$('input[name="connection_date"]').iCheck({ checkboxClass : 'icheckbox_square-green' , radioClass : 'iradio_square-green' });
		$('input[name="connection_date"]').iCheck('check');
		$('.selectpicker').selectpicker(); 
		
	}
	
	//flushApplication();
	
	function flushBroadbandOptions() {
		var obj = { 
			ctx: ctx 
			, prepay_months: prepay_months
			, isContract: isContract
			, plan: plan
		};
		var $div = $('#broadband-options');
		$div.html(tmpl('broadband_options_tmpl', obj));
		
		$('input[name="order_broadband_type"]').iCheck({ checkboxClass : 'icheckbox_square-green' , radioClass : 'iradio_square-green' });
		$('input[name="order_broadband_type"]').on('ifChecked', function(){
			var value = this.value;
			broadband_value_selected = value;
			var startDate = "+7d";
			if (value == 'transition') {
				order_broadband_type = "Transfer Broadband Connection";
				$('#transitionContainer').show('fast');
				price.service_price = plan.transition_fee;
				price.save_service_price = 0;
				startDate = "+7d";
			} else if (value == 'new-connection') {
				order_broadband_type = "New Connection Only";
				$('#transitionContainer').hide('fast');
				if (prepay_months == 1) {
					if (!isContract) {
						price.service_price = parseInt(plan.plan_new_connection_fee);
						price.save_service_price = 0;
					} else {
						price.service_price = 49;
						price.save_service_price = plan.plan_new_connection_fee - 49;
					}
				} else if (prepay_months == 3 || prepay_months == 6) {
					price.service_price = plan.plan_new_connection_fee - parseInt((plan.plan_new_connection_fee/12)*prepay_months);
					price.save_service_price = parseInt((plan.plan_new_connection_fee/12)*prepay_months);
				} else if (prepay_months == 12) {
					price.service_price = 0;
					price.save_service_price = 0;
				}
				startDate = "+12d";
			} else if (value == 'jackpot') {
				order_broadband_type = "New Connection & Phone Jack Installation";
				$('#transitionContainer').hide('fast');
				price.service_price = plan.jackpot_fee;
				startDate = "+12d";
			}
			
			flushOrderModal();
			
			cal.datepicker('setStartDate', startDate);
			$('input[name="connection_date"]').iCheck('check');
		});
		
		var broadband_oo = $('input[value="' + broadband_value_selected + '"]');
		broadband_oo.iCheck('check');
	}
	
	//flushBroadbandOptions();
	
	function flushOrderModal() {
		var obj = {
			ctx: ctx
			, sale_id: sale_id
			, select_plan_id: select_plan_id
			, select_plan_type: select_plan_type
			, customer_address: customer_address
			, plan: plan
			, price: price
			, naked: naked
			, modem_name: modem_name
			, isContract: isContract
			, contract_name: contract_name
			, order_broadband_type: order_broadband_type
			, prepay_months: prepay_months
			, discount_desc: discount_desc
		};
		//console.log(obj.price);
		var $div = $('#order-modal');
		$div.html(tmpl('order_modal_tmpl', obj));
		
		$('#get-it-now').click(confirm);
	}
	
	/*
	 * GET IT NOW
	 */
	
	function confirm() {
		var url = ctx + '/plans/order/confirm';
		var l = Ladda.create(this); l.start();
		var customer = {
			address: customer_address
			, cellphone: $('#cellphone').val()
			, email: $('#email').val()
			, title: $('#title').val()
			, first_name: $('#first_name').val()
			, last_name: $('#last_name').val()
			/*, identity_type: $('#identity_type').val()
			, identity_number: $('#identity_number').val()*/
			, customer_type: 'personal'
			, customerOrder: {
				order_broadband_type: $('input[name="order_broadband_type"]:checked').val()
				, prepay_months: prepay_months
				, contract: (contract_name == '' ? 'open term' : contract_name)
				, connection_date: connection_date
				, plan: plan
				, sale_id: sale_id
				, hardwares: [modem_selected]
			}
		};
			
		if (customer.customerOrder.order_broadband_type == 'transition') {
			customer.customerOrder.transition_provider_name = $('#customerOrder\\.transition_provider_name').val();
			customer.customerOrder.transition_account_holder_name = $('#customerOrder\\.transition_account_holder_name').val();
			customer.customerOrder.transition_account_number = $('#customerOrder\\.transition_account_number').val();
			customer.customerOrder.transition_porting_number = $('#customerOrder\\.transition_porting_number').val();
		}
	 	
		//console.log(customer);
		//console.log(JSON.stringify(customer));
		
	 	$.ajax({
			type: 'post'
			, contentType:'application/json;charset=UTF-8'         
	   		, url: url
		   	, data: JSON.stringify(customer)
		   	, dataType: 'json'
		   	, success: function(json){  //console.log(json.model);
				if (!$.jsonValidation(json, 'right')) { 
					window.location.href = ctx + json.url;
				} 
		   	}
		}).always(function(){ l.stop(); });	
	}
	
})(jQuery);