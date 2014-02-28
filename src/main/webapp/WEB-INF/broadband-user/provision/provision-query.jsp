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
					data-parent="#accordion" href="#collapseOne"> Provision
					Customer Order Query</a>
			</h4>
		</div>
		<div id="collapseOne" class="panel-collapse collapse in">
			<div class="panel-body">
				<div class="btn-group btn-group-lg">
					<button type="button" class="btn btn-default">
						Paid Customers View &nbsp;<span class="badge">42</span>
					</button>
					<button type="button" class="btn btn-default">
						Ordering Customers View&nbsp;<span class="badge">42</span>
					</button>
					<button type="button" class="btn btn-default">
						Using Customers View&nbsp;<span class="badge">42</span>
					</button>
				</div>
			</div>
		</div>
	</div>

</div>