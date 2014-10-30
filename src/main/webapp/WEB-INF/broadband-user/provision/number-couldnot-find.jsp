<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style type="text/css">
thead th {text-align:center;}
tbody td {text-align:center;}
</style>

<div class="container">
	<div class="row">
		<div class="col-md-12">
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
							Provision Exceptions (${statusType=='unmatched' ? "Can't find in CyberPark System" : "Disconnected in CyberPark System" })
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">
					<div class="panel-body">
						<div class="btn-group btn-group">
							<a href="${ctx}/broadband-user/provision/number-couldnot-find/${billingType}/unmatched" class="btn btn-default ${unmatchedActive }">
								Unmatched&nbsp;<span class="badge">${allUnmatchedSize}</span>
							</a>
							<a href="${ctx}/broadband-user/provision/number-couldnot-find/${billingType}/disconnected" class="btn btn-default ${disconnectedActive }">
								Disconnected&nbsp;<span class="badge">${allDisconnectedSize}</span>
							</a>
						</div>
						<br/><br/>
						<div class="btn-group btn-group">
							<a href="${ctx}/broadband-user/provision/number-couldnot-find/chorus/${statusType}" class="btn btn-default ${chorusActive }">
								Chorus&nbsp;<span class="badge">${chorusUnmatchedSize}${chorusDisconnectedSize}</span>
							</a>
							<a href="${ctx}/broadband-user/provision/number-couldnot-find/nca/${statusType}" class="btn btn-default ${ncaActive }">
								NCA&nbsp;<span class="badge">${ncaUnmatchedSize}${ncaDisconnectedSize}</span>
							</a>
							<a href="${ctx}/broadband-user/provision/number-couldnot-find/vos/${statusType}" class="btn btn-default ${vosActive }">
								VOS&nbsp;<span class="badge">${vosUnmatchedSize}${vosDisconnectedSize}</span>
							</a>
							<a href="${ctx}/broadband-user/provision/number-couldnot-find/asid/${statusType}" class="btn btn-default ${asidActive }">
								ASID&nbsp;<span class="badge">${asidUnmatchedSize}${asidDisconnectedSize}</span>
							</a>
						</div>
					</div>
				</div>
			</div>
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
					</h4>
					<div class="panel-body">
						<!-- Unmatched -->
						<c:forEach var="chorusUnmatched" items="${chorusUnmatched }">
							&nbsp;&nbsp;&nbsp;&nbsp;${chorusUnmatched.clear_service_id}&nbsp;(Billing Date: ${chorusUnmatched.statement_date_str})
						</c:forEach>
						<c:forEach var="ncaUnmatched" items="${ncaUnmatched }">
							&nbsp;&nbsp;&nbsp;&nbsp;${ncaUnmatched.original_number}&nbsp;(Billing Date: ${ncaUnmatched.date_str})
						</c:forEach>
						<c:forEach var="vosUnmatched" items="${vosUnmatched }">
							&nbsp;&nbsp;&nbsp;&nbsp;${vosUnmatched.ori_number}&nbsp;(Billing Date: ${vosUnmatched.call_start_str})
						</c:forEach>
						<c:forEach var="asidUnmatched" items="${asidUnmatched }">
							&nbsp;&nbsp;&nbsp;&nbsp;${asidUnmatched.clear_service_id}&nbsp;(Billing Date: ${asidUnmatched.statement_date_str})
						</c:forEach>
						
						<!-- Disconnected -->
						<c:forEach var="chorusDisconnected" items="${chorusDisconnected }">
							&nbsp;&nbsp;&nbsp;&nbsp;${chorusDisconnected.clear_service_id}&nbsp;(Billing Date: ${chorusDisconnected.statement_date_str})
						</c:forEach>
						<c:forEach var="ncaDisconnected" items="${ncaDisconnected }">
							&nbsp;&nbsp;&nbsp;&nbsp;${ncaDisconnected.original_number}&nbsp;(Billing Date: ${ncaDisconnected.date_str})
						</c:forEach>
						<c:forEach var="vosDisconnected" items="${vosDisconnected }">
							&nbsp;&nbsp;&nbsp;&nbsp;${vosDisconnected.ori_number}&nbsp;(Billing Date: ${vosDisconnected.call_start_str})
						</c:forEach>
						<c:forEach var="asidDisconnected" items="${asidDisconnected }">
							&nbsp;&nbsp;&nbsp;&nbsp;${asidDisconnected.clear_service_id}&nbsp;(Billing Date: ${asidDisconnected.statement_date_str})
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
(function($) {
	$('button[data-toggle="tooltip"]').tooltip();
	
	$('#checkbox_cis_top').click(function() {
		var b = $(this).prop("checked");
		if (b) {
			$('input[name="checkbox_cis"]').prop("checked", true);
		} else {
			$('input[name="checkbox_cis"]').prop("checked", false);
		}
	});
	
	$('#year_input_datepicker').datepicker({
	    format: "yyyy",
	    autoclose: true,
	    minViewMode: 2
	});
	
	$('#month_input_datepicker').datepicker({
	    format: "yyyy-mm",
	    autoclose: true,
	    minViewMode: 1
	});
	
	$('#year_input_datepicker').datepicker().on('changeDate', function(ev){
		window.location.href = $('#year_input').val();
	});
	
	$('#month_input_datepicker').datepicker().on('changeDate', function(ev){
		window.location.href = $('#month_input').val();
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />