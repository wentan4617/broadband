<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />
<style>
	.progress-lower {
		background:rgba(255,50,90,0.3);
	}
	.progress-low {
		background:rgba(255,90,120,0.3);
	}
	.progress-mid {
		background:rgba(255,130,150,0.3);
	}
	.progress-high {
		background:rgba(255,170,180,0.3);
	}
	.progress-higher {
		background:rgba(255,210,210,0.3);
	}
</style>
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						Sales Commission View
					</h4>
				</div>
				<jsp:include page="sales-commission-query.html" />
				<hr>
				<div id="sales-commission-view-page"></div>
			</div>
		</div>
	</div>
</div>

<script type="text/html" id="sales_commission_view_page_tmpl">
<jsp:include page="sales-commission-view-page.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript">
(function($){
	
	var isReload = false;
	
	function doPage(pageNo, customerType, yearMonth, sale_id) {
	
		$.get('${ctx}/broadband-user/sale/sales-commission/view/' + pageNo + '/' + customerType + '/' + yearMonth + '/' + sale_id, function(page){
			page.ctx = '${ctx}';
	   		var $div = $('#sales-commission-view-page');
	   		$div.html(tmpl('sales_commission_view_page_tmpl', page));
	   		$div.find('tfoot a').click(function(){
				doPage($(this).attr('data-pageNo'), customerType, yearMonth, sale_id);
			});
	   		
	   		if(isReload==false){
		   		
		   		var users = page.params.users;
		   		var usersHTML = '';
		   		usersHTML+='<option value="0">All</option>';
		   		for(var usersIndex=0; usersIndex<users.length; usersIndex++){
		   			usersHTML+='<option value="'+users[usersIndex].id+'">'+users[usersIndex].user_name+'</option>'
		   		}
		   		$('select[data-id="sale_id"]').html(usersHTML);
		   		
		   		isReload = true;
	   		}

	   		$('a[data-val="all"] span').html('${allSum}');
	   		$('a[data-val="personal"] span').html('${personalSum}');
	   		$('a[data-val="business"] span').html('${businessSum}');
	   		
	   	}, 'json');
	}

	
	$('a[data-id="customer_type"]').click(function(){
		
		var customer_type = 'all';
		
		var field = $(this).attr('data-id');
		$('a[data-id="customer_type"]').removeClass('active');
		$(this).addClass('active');
		if($(this).attr('data-val')!='all'){
			customer_type = $(this).attr('data-val');
		}
		
		var sale_id = $('select[data-id="sale_id"]').val();
		
		var yearMonth = $('#month_input').val()=='' ? '0' : $('#month_input').val();
		
		doPage(1, customer_type, yearMonth, sale_id);
		
	});

	
	$('select[data-id="sale_id"]').change(function(){
		
		var customer_type = 'all';
		var sale_id = $(this).val();
		
		$('a[data-id="customer_type"]').each(function(){
			if($(this).hasClass('active')){
				customer_type = $(this).attr('data-val');
			}
		});
		
		var yearMonth = $('#month_input').val()=='' ? '0' : $('#month_input').val();
		
		doPage(1, customer_type, yearMonth, sale_id);
		
	});
	
	
	$('#month_input_datepicker').datepicker({
	    format: "yyyy-mm",
	    autoclose: true,
	    minViewMode: 1
	});
	$('#month_input_datepicker').datepicker().on('changeDate', function(ev){
		
		var customer_type = 'all';
		var sale_id = $('select[data-id="sale_id"]').val();
		
		$('a[data-id="customer_type"]').each(function(){
			if($(this).hasClass('active')){
				customer_type = $(this).attr('data-val');
			}
		});
		
		doPage(1, customer_type, $('#month_input').val(), sale_id);
		
	});
	
	
	doPage(1, 'all', '0', 0);
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />