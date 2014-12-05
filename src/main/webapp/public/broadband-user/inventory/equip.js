(function($){
	
	// SWITCH QUERY BY STATUS GROUP BUTTONS
	$('button[data-name="query_equip_by_status_btn"]').click(function(){
		$('button[data-name="query_equip_by_status_btn"]').removeClass('active');
		$(this).addClass('active');
		$.getEquipPage(1, $(this).attr('data-status'));
	});
	
	
	
		
	// ADD/EDIT EQUIPMENT
	$('a[data-name="add_equip_btn"]').click(function(){
		$.get(ctx+'/broadband-user/inventory/equip/pattern/get', function(patterns){
			var patternOptions = "<option></option>";
			for(var ps=0; ps<patterns.length; ps++){
				patternOptions+='<option data-equip-name="'+patterns[ps].equip_name+'" data-equip-type="'+patterns[ps].equip_type+'" data-equip-purpose="'+patterns[ps].equip_purpose+'" >'+patterns[ps].equip_name+',&nbsp;'+patterns[ps].equip_type+',&nbsp;'+patterns[ps].equip_purpose+'</option>';
			}
			$('select[data-name="equip_pattern_selector"]').html(patternOptions);
		}, 'json');
		$('#addEquipmentModal').modal('show');
	});
	$('select[data-name="equip_pattern_selector"]').change(function(){
		$('input[data-name="equip_name"]').val($(this).find('option:selected').attr('data-equip-name'));
		$('select[data-name="equip_type"]').find('option[value='+$(this).find('option:selected').attr('data-equip-type')+']').attr('selected',true);
		$('select[data-name="equip_purpose"]').find('option[value='+$(this).find('option:selected').attr('data-equip-purpose')+']').attr('selected',true);
	});
	$('a[data-name="addEquipmentModalBtn"]').click(function(){
		var equip_name = $('input[data-name="equip_name"]').val();
		var equip_type = $('select[data-name="equip_type"]').val();
		var equip_purpose = $('select[data-name="equip_purpose"]').val();
		var equip_sn = $('input[data-name="equip_sn"]').val();
		var data = {
			'equip_name':equip_name,
			'equip_type':equip_type,
			'equip_purpose':equip_purpose,
			'equip_sn':equip_sn
		};
		$.get(ctx+'/broadband-user/inventory/equip/create', data, function(json){
			$.jsonValidation(json, 'right');
		}, 'json');
	});
	$('#addEquipmentModal').on('hidden.bs.modal', function(){
		var equip_status;
		$('button[data-name="query_equip_by_status_btn"]').each(function(){
			if($(this).hasClass('active')){
				equip_status = $(this).attr('data-status');
			}
		});
		$.getEquipPage(1, equip_status);
		$.getEquipPatternPage(1);
	});

	
	// ADD EQUIP PATTERN
	$('a[data-name="add_equip_pattern_btn"]').click(function(){
		$('#addEquipPatternModal').modal('show');
	});
	$('a[data-name="addEquipPatternModalBtn"]').click(function(){
		var equip_pattern_name = $('input[data-name="equip_pattern_name"]').val();
		var equip_pattern_type = $('select[data-name="equip_pattern_type"]').val();
		var equip_pattern_purpose = $('select[data-name="equip_pattern_purpose"]').val();
		var data = {
			'equip_name':equip_pattern_name,
			'equip_type':equip_pattern_type,
			'equip_purpose':equip_pattern_purpose
		};
		$.get(ctx+'/broadband-user/inventory/equip/pattern/create', data, function(json){
			$.jsonValidation(json, 'right');
		}, 'json');
	});
	$('#addEquipPatternModal').on('hidden.bs.modal', function(){
		var equip_status;
		$('button[data-name="query_equip_by_status_btn"]').each(function(){
			if($(this).hasClass('active')){
				equip_status = $(this).attr('data-status');
			}
		});
		$.getEquipPage(1, equip_status);
		$.getEquipPatternPage(1);
	});
	
	// EQUIP PAGE
	$.getEquipPage = function(pageNo, equip_status) {
		$.get(ctx+'/broadband-user/inventory/equip/view/' + pageNo + '/' + equip_status, function(page){
			page.ctx = ctx;
			page.user_role = user_role;
	   		var $div = $('#equip_view');
	   		$div.html(tmpl('equip_tmpl', page));
	   		$div.find('tfoot a').click(function(){
	   			$.getEquipPage($(this).attr('data-pageNo'), equip_status);
			});
	   		
	   		$('a[data-name="remove_equip_btn"]').click(function(){
	   			$('a[data-name="removeEquipmentModalBtn"]').attr('id', this.id);
	   			$('#removeEquipmentModal').modal('show');
	   		});
	   		$('a[data-name="removeEquipmentModalBtn"]').click(function(){
	   			var data = {
	   				'id':this.id
	   			};
	   			$.post(ctx+'/broadband-user/inventory/equip/remove', data, function(json){
	   				$.jsonValidation(json, 'right');
	   			}, 'json');
	   		});
	   		$('#removeEquipmentModal').on('hidden.bs.modal', function(){
	   			$.getEquipPage(1, equip_status);
	   		});
	   		
	   	}, 'json');
	}
	$.getEquipPage(1, 'all');
	
	// EQUIP PATTERN PAGE
	$.getEquipPatternPage = function(pageNo) {
		$.get(ctx+'/broadband-user/inventory/equip/pattern/view/' + pageNo, function(page){
			page.ctx = ctx;
			page.user_role = user_role;
	   		var $div = $('#equip_pattern_view');
	   		$div.html(tmpl('equip_pattern_tmpl', page));
	   		$div.find('tfoot a').click(function(){
	   			$.getEquipPatternPage($(this).attr('data-pageNo'));
			});
	   		
	   		$('a[data-name="remove_equip_pattern_btn"]').click(function(){
	   			$('a[data-name="removeEquipmentPatternModalBtn"]').attr('id', this.id);
	   			$('#removeEquipmentPatternModal').modal('show');
	   		});
	   		$('a[data-name="removeEquipmentPatternModalBtn"]').click(function(){
	   			var data = {
	   				'id':this.id
	   			};
	   			$.post(ctx+'/broadband-user/inventory/equip/pattern/remove', data, function(json){
	   				$.jsonValidation(json, 'right');
	   			}, 'json');
	   		});
	   		$('#removeEquipmentPatternModal').on('hidden.bs.modal', function(){
	   			$.getEquipPatternPage(1);
	   		});
	   		
	   	}, 'json');
	}
	$.getEquipPatternPage(1);
	
})(jQuery);