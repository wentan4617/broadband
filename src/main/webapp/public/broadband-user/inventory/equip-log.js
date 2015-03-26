(function($){
	
	// EQUIP LOG PAGE
	$.getEquipLogPage = function(pageNo) {
		$.get(ctx+'/broadband-user/inventory/equip/log/view/' + pageNo, function(json){
			json.ctx = ctx;
	   		var $div = $('#equip-log-view');
	   		$div.html(tmpl('equip_log_tmpl', json));
	   		$div.find('tfoot a').click(function(){
	   			$.getEquipLogPage($(this).attr('data-pageNo'));
			});
	   	}, 'json');
	}
	$.getEquipLogPage(1);
	
})(jQuery);