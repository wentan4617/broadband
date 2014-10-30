<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div class="list-group">
	<a href="${ctx }/customer/home" class="list-group-item ${home }">Home</a>
	<a href="${ctx }/customer/orders" class="list-group-item ${orders }">View Orders</a>
	<a href="${ctx }/customer/new-order" class="list-group-item ${neworder }">New Order</a>
	<a href="${ctx }/customer/change-password" class="list-group-item ${change_password }">Change Password</a>
</div>