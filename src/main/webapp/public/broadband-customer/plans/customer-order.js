(function($) {
	
	var select_plan_tmpl = $('#select_plan_tmpl')
		, ctx = select_plan_tmpl.attr('data-ctx')
		, select_plan_group = select_plan_tmpl.attr('data-select_plan_group')
		, select_plan_id = select_plan_tmpl.attr('data-select_plan_id')
		, select_plan_type = select_plan_tmpl.attr('data-select_plan_type')
		, select_customer_type = select_plan_tmpl.attr('data-select_customer_type')
		, sale_id = select_plan_tmpl.attr('data-sale-id')
		, promotion = select_plan_tmpl.attr('data-promotion') == 'true' ? true : false
		, neworder = select_plan_tmpl.attr('data-neworder') == 'true' ? true : false
		
		, customer_address = $('#order_modal_tmpl').attr('data-customer-address')
		, select_modem_container = $('#select-modem')
		, prepay_months_container = $('#prepay-month')
		, open_term_container = $('#open-term')
		
		
	var open_term_tmpl = $('#open_term_tmpl')
		, contract = open_term_tmpl.attr('data-contract')
		
	var prepay_month_tmpl = $('#prepay_month_tmpl')
	
	var select_modem_tmpl = $('#select_modem_tmpl')
	
	var broadband_options_tmpl = $('#broadband_options_tmpl')
		
	var	application = $('#application_tmpl') 
		, cellphone = application.attr('data-cellphone')
		, email = application.attr('data-email')
		, title = application.attr('data-title')
		, first_name = application.attr('data-first_name')
		, last_name = application.attr('data-last_name')
		, identity_type = application.attr('data-identity_type')
		, identity_number = application.attr('data-identity_number')
		, _transition_provider_name = application.attr('data-_transition_provider_name')
		, transition_provider_name  = application.attr('data-transition_provider_name')
		, transition_account_holder_name = application.attr('data-transition_account_holder_name')
		, transition_account_number = application.attr('data-transition_account_number')
		, transition_porting_number  = application.attr('data-transition_porting_number')
		, org_type = application.attr('data-org_type')
		, org_name = application.attr('data-org_name')
		, org_trading_name = application.attr('data-org_trading_name')
		, org_register_no = application.attr('data-org_register_no')
		, org_incoporate_date = application.attr('data-org_incoporate_date')
		, holder_name = application.attr('data-holder_name')
		, holder_job_title = application.attr('data-holder_job_title')
		, holder_phone = application.attr('data-holder_phone')
		, holder_email = application.attr('data-holder_email')
	
	var plan = {};
	var modems = [];
	var modem_selected = null;
	var naked = false;
	var has_voip = false;
	var modem_name = '';
	var isContract = false;
	if (select_customer_type == 'personal') {
		if (select_plan_group == 'plan-topup') {
			isContract = false;
		} else {
			isContract = (select_plan_type == 'VDSL' && sale_id == '20023' ? true : false);
			if (contract == '12 months contract') {
				isContract = true;
			} else if (contract == 'open term') {
				isContract = false;
			}
		}
	} else if (select_customer_type == 'business') {
		isContract = true;
	}
	
	var contract_name = '';
	var order_broadband_type = 'Transfer Broadband Connection';
	var prepay_months = prepay_month_tmpl.attr('data-prepay_months');
	var discount_desc = '';
	var connection_date = 'ASAP';
	var hardware_class = '';
	var cal;
	
	var months_selected = '1';
	if (select_plan_type == 'ADSL') {
		months_selected = '1';
	} else if (select_plan_type == 'VDSL') {
		if (sale_id == '10023') {
			months_selected = '12';
		} else if (sale_id == '20023') {
			months_selected = '3';
		} else {
			months_selected = '1';
		}
	} else if (select_plan_type == 'UFB') {
		months_selected = '12';
	}
	if (prepay_months != '') {
		months_selected = prepay_months;
	}
	var hardware_id_selected = select_modem_tmpl.attr('data-hardware_id_selected'); //console.log(hardware_id_selected == '');
	if (hardware_id_selected == '') {
		hardware_id_selected = '0';
	} //console.log(hardware_id_selected);
	var hardware_value_selected = 'withoutmodem';
	var broadband_value_selected = broadband_options_tmpl.attr('data-order_broadband_type');
	var has_promotion_code = false;
	var promotion_rates = 0;
	
	var price = {
		plan_price: 0
		, service_price: 0
		, addons_price: 0
		, modem_price: 0
		, discount_price: 0
		, promotion_price: 0
		
		, save_service_price: 0
		, save_modem_price: 0
	};
		
	function loadingPlans() {
		var url = ctx + '/plans/loading';
		$.get(url, function(planTypeMap){
			
			var planMap = planTypeMap[select_plan_type];
			var plansClothed = planMap['plansClothed']; //console.log(plansClothed);
			var plansNaked = planMap['plansNaked'];
			
			var o = {
				ctx: ctx
				, select_plan_group: select_plan_group
				, select_plan_id: select_plan_id 
				, select_plan_type: select_plan_type
				, sale_id: sale_id
				, plansClothed: plansClothed
				, plansNaked: plansNaked
			};
			$('#select-plan').html(tmpl('select_plan_tmpl', o));
			
			// promotion code 
			
			$('#code_apply').click(function(){
				var code = $('#promotion_code').val();
				var ir = {
					promotion_code: code	
				};
				var l = Ladda.create(this); l.start();
				$.post(ctx + '/plans/order/apply/promotion-code', ir, function(json){
					if (!$.jsonValidation(json)) { 
						var ir = json.model; //console.log(ir);
						if (ir != null) {
							has_promotion_code = true;
							promotion_rates = ir.invitee_rate/100;
						} else {
							has_promotion_code = false;
						}
						flushOrderModal();
						
					} 
					
				}).always(function(){ l.stop(); });	
			});
			
			// loadingModems
			var url = ctx + '/plans/hardware/loading'; //console.log(url);
			$.get(url, function(hardwares){ //console.log(hardwares);
				modems = hardwares;
				flushApplication();
				
				$('div[data-plan-id]').click(function(){ //alert('a');
					$('div[data-plan-id]').removeClass('alert-danger').addClass('alert-success');
					$('div[data-plan-id] span[data-icon] > span').css('opacity', '0');
					var $div = $(this);
					$div.addClass('alert-danger');
					$div.find('span[data-icon] > span').css('opacity', '1');
					
					var plan_id = Number($div.attr('data-plan-id'));
					var plan_sort = $div.attr('data-plan_sort');
					
					//console.log(plan_sort);
					
					if (plan_sort == 'CLOTHED') {
						$.each(plansClothed, function(){
							if (this.id == plan_id) {
								plan = $.extend({}, this);
								return false;
							}
						});
					} else if (plan_sort == 'NAKED') {
						$.each(plansNaked, function(){
							if (this.id == plan_id) {
								plan = $.extend({}, this);
								return false;
							}
						});
					}
					
					//console.log(plan);
					//console.log(promotion);
					if (promotion && select_plan_type == 'VDSL') { //console.log('promotion');
						plan.plan_name = plan.plan_name.replace(/Homeline/g, 'VoIP Homeline');
						naked = (plan.pstn_count = 0) > 0 ? false : true;
						has_voip = (plan.voip_count = 1) > 0 ? true : false;
					} else { //console.log('normal');
						naked = plan.pstn_count > 0 ? false : true;
						has_voip = plan.voip_count > 0 ? true : false;
					}
					
					//console.log(naked);
					
					price.plan_price = plan.plan_price;
					price.service_price = plan.transition_fee;
					
					flushOpenTerm();
				});
				
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
	
	function flushPromotion() {
		if (has_promotion_code) {
			var total = price.plan_price * prepay_months - price.discount_price + price.service_price + price.modem_price;
			price.promotion_price = parseInt(total * promotion_rates);
		} else {
			price.promotion_price = 0;
		} //console.log(price);
		
		var o = {
			price: price
			, prepay_months: prepay_months
			, has_promotion_code: has_promotion_code
			, promotion_rates: promotion_rates
		};
		$('#promotion_content').html(tmpl('promotion_code_tmpl', o));
		$('#cancel-promotion').click(function(){
			$.post(ctx + '/plans/order/cancel/promotion-code', function(){
				has_promotion_code = false;
				$('#promotion_content').html('');
				flushOrderModal();
			});
		});
	}
	
	function flushOpenTerm() {
		var o = {
			ctx: ctx
			, select_plan_group: select_plan_group
			, select_customer_type: select_customer_type
			, select_plan_type: select_plan_type
			, sale_id: sale_id
		};
		$('#open-term').html(tmpl('open_term_tmpl', o));
		
		$('input[name="contract"]').iCheck({ checkboxClass: 'icheckbox_square-green', radioClass: 'iradio_square-green' });
		$('input[name="contract"]').on('ifChecked', function(){
			var value = this.value;
			if (value == 'open-term') {
				isContract = false;
				contract_name = '';
				//prepay_months_container.show('fast');
				if (select_plan_type == 'UFB' && months_selected != '12') {
					months_selected = '12';
				}
				flushPrepayMonth();
				
			} else if (value == '12months') {
				isContract = true;
				contract_name = '12 months contract';
				discount_desc = '';
				//select_modem_container.show('fast');
				//prepay_months_container.hide('fast');
				//prepay_months = 1;
				//price.discount_price = 0;
				//modem_selected = null;
				//flushModems();
				//flushBroadbandOptions();
				flushPrepayMonth();
			}
			
		});
		
		var contract_oo = $('input[name="contract"][value="' + (isContract ? '12months' : 'open-term') + '"]');
		contract_oo.iCheck('check');
	}
	
	function flushPrepayMonth() {
		var o = {
			ctx: ctx
			, price: price
			, select_plan_group: select_plan_group
			, select_customer_type: select_customer_type
			, select_plan_type: select_plan_type
			, isContract: isContract
			, sale_id: sale_id
		};
		$('#prepay-month').html(tmpl('prepay_month_tmpl', o));
		$('input[name="prepaymonths"]').iCheck({ checkboxClass: 'icheckbox_square-green', radioClass: 'iradio_square-green' });
		$('input[name="prepaymonths"]').on('ifChecked', function(){
			var value = Number(this.value); 
			months_selected = value;
			if (value == 1) {
				//select_modem_container.show('fast');
				discount_desc = '';
				prepay_months = 1;
				price.discount_price = 0;
				modem_selected = null;
			} else if (value == 3) {
				//select_modem_container.show('fast');
				if (select_plan_type == 'VDSL' && sale_id == '20023') {
					discount_desc = '<span class="text-danger">1 TB Portable Hard Drive (cost NZD$90), 100mins/mth International calling for 40 countries</span>';
					prepay_months = 3;
					price.discount_price = 0;
					//open_term_container.hide('fast');
					$.each(modems, function(){
						if (this.id == 8) {
							modem_selected = $.extend({}, this);
							return false;
						}
					});
					//console.log(modem_selected);
					if (modem_selected != null) {
						//modem_selected.hardware_price = 0;
						hardware_id_selected = modem_selected.id; //console.log(hardware_id_selected);
						hardware_value_selected = 'open-term'; //console.log(hardware_value_selected);
					}
				} else {
					//console.log('running');
					discount_desc = '3% off the total price of 3 months plan';
					prepay_months = 3;
					price.discount_price = parseInt(price.plan_price * 3 * 0.03);
					modem_selected = null;
				}
				
			} else if (value == 6) {
				//select_modem_container.show('fast');
				discount_desc = '7% off the total price of 6 months plan';
				prepay_months = 6;
				price.discount_price = parseInt(price.plan_price * 6 * 0.07);
				modem_selected = null;
			} else if (value == 12) {
				if (select_plan_type == 'VDSL' && sale_id == '10023') {
					discount_desc = '<span class="text-danger">free iPad Mini 16G, free VoIP Wifi modem, 200 calling minutes of 40 countries</span>';
					prepay_months = 12;
					price.discount_price = 0;
					//open_term_container.hide('fast');
					//prepay_months_container.hide('fast');
				} else {
					discount_desc = '15% off the total price of 12 months plan with free modem';
					prepay_months = 12;
					price.discount_price = parseInt(price.plan_price * 12 * 0.15);
				}
				
				//select_modem_container.hide('fast');
				
				if (has_voip) { //console.log('has voip');
					$.each(modems, function(){
						if (this.support_voip) {
							modem_selected = $.extend({}, this);
							return false;
						}
					});
				} else { //console.log('no voip');
					$.each(modems, function(){
						modem_selected = $.extend({}, this);
						return false;
					});
				}
				
				//modem_selected = modems && $.extend({}, modems[0]);
				//console.log(modem_selected);
				if (modem_selected != null) {
					//modem_selected.hardware_price = 0;
					hardware_id_selected = modem_selected.id; //console.log(hardware_id_selected);
					hardware_value_selected = 'open-term'; //console.log(hardware_value_selected);
				}
				
			}
			
			price.modem_price = 0;
			price.save_modem_price = 0;
			modem_name = '';
			
			flushModems();
			flushBroadbandOptions();
		});
		
		var months_oo = $('input[name="prepaymonths"][value="' + months_selected + '"]');
		months_oo.iCheck('check');
	}
	
	function flushModems() {
		var o = {
			ctx: ctx
			, select_plan_type: select_plan_type
			, sale_id: sale_id
			, prepay_months: prepay_months
			, isContract: isContract
			, hardwares: modems
		};
		if (prepay_months == 12) {
			o.hardwares = [modem_selected];
		}
		if (select_plan_type == 'VDSL' && sale_id == '20023' && prepay_months == 3) {
			o.hardwares = [modem_selected];
		}
		$('#select-modem').html(tmpl('select_modem_tmpl', o));		
		
		$('input[name="modem"]').iCheck({ checkboxClass: 'icheckbox_square-green', radioClass: 'iradio_square-green' });
		$('input[name="modem"]').on('ifChecked', function(){
			var $modem = $(this);
			var id = Number($modem.attr('data-id'));
			var value = this.value;
			hardware_id_selected = id; //console.log(hardware_id_selected);
			hardware_value_selected = value; //console.log(hardware_value_selected);
			
			if (value == 'withoutmodem') {
				modem_selected = null;
				price.modem_price = 0;
				price.save_modem_price = 0;
				modem_name = '';
			} else { //console.log('id: ' + id + ", modems: " + modems.length);
				$.each(modems, function(){
					if (this.id == id) {
						modem_selected = this;
						if (isContract) {
							if (prepay_months == 1 || prepay_months == 3 || prepay_months == 6) {
								if (sale_id == '20023' && select_plan_type == 'VDSL') {
									price.modem_price = parseInt(0);
									price.save_modem_price = parseInt(this.hardware_price);
								} else {
									if (this.id == 3 || this.id == 1) {
										price.modem_price = parseInt(0);
										price.save_modem_price = parseInt(this.hardware_price);
									} else {
										price.modem_price = this.hardware_price - parseInt(this.hardware_price/2);
										price.save_modem_price = parseInt(this.hardware_price/2);
									}
								}
							} else if (prepay_months == 12) {
								price.modem_price = parseInt(0);
								price.save_modem_price = parseInt(this.hardware_price);
							}
						} else {
							if (prepay_months == 1) {
								price.modem_price = Number(this.hardware_price);
								price.save_modem_price = 0;
							} else if (prepay_months == 3 || prepay_months == 6) {
								price.modem_price = this.hardware_price -  parseInt((this.hardware_price/12)*prepay_months);
								price.save_modem_price = parseInt((this.hardware_price/12)*prepay_months);
							} else if (prepay_months == 12) {
								price.modem_price = parseInt(0);
								price.save_modem_price = parseInt(this.hardware_price);
							}
						}
						modem_name = this.hardware_name;
						return false;
					}
				});
			}
			flushOrderModal();
		});
		
		var count = $('input[name="modem"][data-id="' + hardware_id_selected + '"]').length;
		if (count > 0) {
			$('input[name="modem"][data-id="' + hardware_id_selected + '"]').iCheck('check');
		} else {
			$('input[name="modem"]').first().iCheck('check');
		}
		
	}
	
	//loadingModems();
	
	function flushApplication() {
		var o = { 
			ctx: ctx 
			, neworder: neworder
			, select_customer_type: select_customer_type
			, prepay_months: prepay_months
			, plan: plan
			, cellphone: cellphone
			, email: email
			, title: title
			, first_name: first_name
			, last_name: last_name
			, identity_type: identity_type
			, identity_number: identity_number
			, _transition_provider_name: _transition_provider_name
			, transition_provider_name: transition_provider_name
			, transition_account_holder_name: transition_account_holder_name
			, transition_account_number: transition_account_number
			, transition_porting_number: transition_porting_number
			, org_type: org_type
			, org_name: org_name
			, org_trading_name: org_trading_name
			, org_register_no: org_register_no
			, org_incoporate_date: org_incoporate_date
			, holder_name: holder_name
			, holder_job_title: holder_job_title
			, holder_phone: holder_phone
			, holder_email: holder_email
		};
		$('#application').html(tmpl('application_tmpl', o));
		$('input[name="connection_date"]').iCheck({ checkboxClass: 'icheckbox_square-green', radioClass: 'iradio_square-green' });
		$('.selectpicker').selectpicker(); 
		$('.input-group.date').datepicker({
		    format: "yyyy-mm-dd",
		    autoclose: true,
		    todayHighlight: true
		});
		
		$('#transition_provider_name').change(function(){
			var value = this.value;
			if (value == 'Other') {
				$('#other_container').show('fast');
				$('#customerOrder\\.transition_provider_name').val('');
			} else {
				$('#other_container').hide('fast');
				$('#customerOrder\\.transition_provider_name').val(value);
			}
		}).trigger('change');
		
		if (_transition_provider_name == 'Other') {
			$('#other_container').show('fast');
		}
		
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
		
		$('input[name="connection_date"]').iCheck('check');
		
	}
	
	//flushApplication();
	
	function flushBroadbandOptions() {
		var o = { 
			ctx: ctx 
			, select_customer_type: select_customer_type
			, sale_id: sale_id
			, prepay_months: prepay_months
			, isContract: isContract
			, plan: plan
		};
		$('#broadband-options').html(tmpl('broadband_options_tmpl', o));
		
		$('input[name="order_broadband_type"],input[name="am"]').iCheck({ checkboxClass: 'icheckbox_square-green', radioClass: 'iradio_square-green' });
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
				if (select_customer_type == 'personal') {
					if (isContract) {
						if (prepay_months == 1 || prepay_months == 3 || prepay_months == 6) {
							if (sale_id == '20023') {
								price.service_price = 0;
								price.save_service_price = plan.plan_new_connection_fee;
							} else {
								price.service_price = 49;
								price.save_service_price = plan.plan_new_connection_fee - 49;
							}
						} else if (prepay_months == 12) {
							price.service_price = 0;
							price.save_service_price = plan.plan_new_connection_fee;
						}
					} else {
						if (prepay_months == 1) {
							price.service_price = parseInt(plan.plan_new_connection_fee);
							price.save_service_price = 0;
						} else if (prepay_months == 3 || prepay_months == 6) {
							price.service_price = plan.plan_new_connection_fee - parseInt((plan.plan_new_connection_fee/12)*prepay_months);
							price.save_service_price = parseInt((plan.plan_new_connection_fee/12)*prepay_months);
						} else if (prepay_months == 12) {
							price.service_price = 0;
							price.save_service_price = plan.plan_new_connection_fee;
						}
					}
				} else if (select_customer_type == 'business') {
					price.service_price = 0;
					price.save_service_price = plan.plan_new_connection_fee;
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
		
		flushPromotion();
		
		var o = {
			ctx: ctx
			, sale_id: sale_id
			, select_plan_id: select_plan_id
			, select_plan_type: select_plan_type
			, select_customer_type: select_customer_type
			, customer_address: customer_address
			, plan: plan
			, price: price
			, naked: naked
			, has_voip: has_voip
			, modem_name: modem_name
			, isContract: isContract
			, contract_name: contract_name
			, order_broadband_type: order_broadband_type
			, prepay_months: prepay_months
			, discount_desc: discount_desc
			, has_promotion_code: has_promotion_code
			, promotion_rates: promotion_rates
		}; //console.log(o);
		$('#order-modal').html(tmpl('order_modal_tmpl', o));
	
		$('#get-it-now').click(confirm);
	}
	
	/*
	 * GET IT NOW
	 */
	
	function confirm() {
		
		if (select_customer_type == 'personal') {
			var url = ctx + '/plans/order/confirm';
		} else if (select_customer_type == 'business') {
			var url = ctx + '/plans/order/confirm/business';
		}
		
		
		var customer = {
			address: customer_address
			, cellphone: $('#cellphone').val()
			, email: $('#email').val()
			, title: $('#title').val()
			, first_name: $('#first_name').val()
			, last_name: $('#last_name').val()
			, identity_type: $('#identity_type').val()
			, identity_number: $('#identity_number').val()
			, customerOrder: {
				order_broadband_type: $('input[name="order_broadband_type"]:checked').val()
				, prepay_months: prepay_months
				, contract: (contract_name == '' ? 'open term' : contract_name)
				, connection_date: connection_date + ', ' + $('input[name="am"]').val()
				, plan: plan
				, sale_id: sale_id
				, promotion: promotion
				, hardwares: [modem_selected]
				, hardware_id_selected: hardware_id_selected
			}
		};
			
		if (customer.customerOrder.order_broadband_type == 'transition') {
			customer.customerOrder._transition_provider_name = $('#transition_provider_name').val();
			customer.customerOrder.transition_provider_name = $('#customerOrder\\.transition_provider_name').val();
			customer.customerOrder.transition_account_holder_name = $('#customerOrder\\.transition_account_holder_name').val();
			customer.customerOrder.transition_account_number = $('#customerOrder\\.transition_account_number').val();
			customer.customerOrder.transition_porting_number = $('#customerOrder\\.transition_porting_number').val();
		}
		
		if (select_customer_type == 'personal') {
			customer.customerOrder.title = customer.title;
			customer.customerOrder.first_name = customer.first_name;
			customer.customerOrder.last_name = customer.last_name;
		} else if (select_customer_type == 'business') {
			customer.customerOrder.org_type = $('#customerOrder\\.org_type').val();
			customer.customerOrder.org_name = $('#customerOrder\\.org_name').val();
			customer.company_name = customer.customerOrder.org_name;
			customer.customerOrder.org_trading_name = $('#customerOrder\\.org_trading_name').val();
			customer.customerOrder.org_register_no = $('#customerOrder\\.org_register_no').val();
			customer.customerOrder.org_incoporate_date = $('#customerOrder\\.org_incoporate_date').val();
			customer.customerOrder.holder_name = $('#customerOrder\\.holder_name').val();
			customer.customerOrder.holder_job_title = $('#customerOrder\\.holder_job_title').val();
			customer.customerOrder.holder_phone = $('#customerOrder\\.holder_phone').val();
			customer.customerOrder.holder_email = $('#customerOrder\\.holder_email').val();
		}
		
		if (neworder) {
			customer.customerOrder.address = customer_address;
			customer.customerOrder.mobile = $('#customerOrder\\.mobile').val();
			customer.customerOrder.email = $('#customerOrder\\.email').val();
			customer.cellphone = customer.customerOrder.mobile;
			customer.email = customer.customerOrder.email;
			customer.customerOrder.customer_type = select_customer_type;
		} else {
			customer.customerOrder.address = customer_address;
			customer.customerOrder.mobile = $('#cellphone').val()
			customer.customerOrder.email = $('#email').val();;
			customer.customerOrder.customer_type = select_customer_type;
		}
	 	
		//console.log(customer);
		//console.log(JSON.stringify(customer));
		
		var l = Ladda.create(this); l.start();
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