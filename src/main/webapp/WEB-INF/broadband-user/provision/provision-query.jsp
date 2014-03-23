<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div class="panel-group" id="accordion">
	<div class="panel panel-default">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-toggle="collapse"
					data-parent="#accordion" href="#collapseOne"> 
					Provision Customer Order Query</a>
			</h4>
		</div>
		<div id="collapseOne" class="panel-collapse collapse in">
			<div class="panel-body">
				<div class="btn-group btn-group">
					<a href="${ctx }/broadband-user/provision/customer/view/1/pending" data-name="queryBtn" class="btn btn-default ${pendingActive }">
						Order Pending&nbsp;<span class="badge">${pendingSum }</span>
					</a>
					<a href="${ctx }/broadband-user/provision/customer/view/1/paid" data-name="queryBtn" class="btn btn-default ${paidActive }">
						Order Paid&nbsp;<span class="badge">${paidSum }</span>
					</a>
					<a href="${ctx }/broadband-user/provision/customer/view/1/ordering" data-name="queryBtn" class="btn btn-default ${orderingActive }">
						Order Ordering&nbsp;<span class="badge">${orderingSum }</span>
					</a>
					<a href="${ctx }/broadband-user/provision/customer/view/1/using" data-name="queryBtn" class="btn btn-default ${usingActive }">
						Order Using&nbsp;<span class="badge">${usingSum }</span>
					</a>
					<a href="${ctx }/broadband-user/provision/customer/view/1/cancel" data-name="queryBtn" class="btn btn-default ${cancelActive }">
						Order Cancel&nbsp;<span class="badge">${cancelSum }</span>
					</a>
					<a href="${ctx }/broadband-user/provision/customer/view/1/discard" data-name="queryBtn" class="btn btn-default ${discardActive }">
						Order Discard&nbsp;<span class="badge">${discardSum }</span>
					</a>
				</div>
			</div>
		</div>
	</div>

</div>