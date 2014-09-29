<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div class="navbar navbar-darkgreen navbar-static-top">
	<div class="container">
		<div class="row">
			<div class="col-sm-2">
				<a class="navbar-brand" href="${ctx}/broadband-user/index">
				</a>
			</div>
			<div class="col-sm-2">
				<strong>
					<a class="navbar-brand" href='${ctx }/broadband-user/sale/online/ordering/view/1/${userSession.user_role == "sales" ? userSession.id : 0 }' style="color:orange; text-decoration:underline;"><small>Order List</small></a>
				</strong>
			</div>
			<div class="col-sm-5">
				<p class="app-form text-center">
					<small>Application Form</small>
				</p>
			</div>
			<div class="col-sm-3">
				<p class="navbar-text pull-right">
					<a href="javascript:void(0);" class="navbar-link" style="margin-right:10px;">
						<span class="glyphicon glyphicon-user" style="margin-right:10px;"></span>
						${userSession.user_name}
					</a>
					<a href="${ctx}/broadband-user/signout" >
						<span class="glyphicon glyphicon-log-out" style="margin-right:10px;"></span>
					</a>
				</p>
			</div>
		</div>
	</div>
</div>