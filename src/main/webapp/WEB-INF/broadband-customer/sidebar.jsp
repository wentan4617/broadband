<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div class="list-group">
	<a href="${ctx }/customer/home" class="list-group-item ${home }">Home</a>
	<a href="${ctx }/customer/data" class="list-group-item ${data }">View Data Usage</a>
	<a href="${ctx }/customer/billing/1" class="list-group-item ${bills }">View Billing</a>
	<a href="${ctx }/customer/discard-billing/1"  class="list-group-item ${discard_bills }">Discard Billing</a>
	<a href="${ctx }/customer/payment" class="list-group-item ${payment }">Make Payment</a>
</div>