<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />


<div class="container">
	<div class="page-header">
		<h1>
			Customer Information Review and Payment
		</h1>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">Please review your application information to payment</div>
		<div class="panel-body">
			
			<form class="form-horizontal" action="${ctx }/order/submit" method="post">
				<div class="row">
					<div class="col-md-10"><h4>Your Basic Information</h4></div>
					<div class="col-md-2">
						<a href="${ctx }/order/${orderPlan.id}" class="btn btn-success ">Back to edit</a>
					</div>
				</div>
				<hr/>
				<div class="form-group">
					<label for="login_name" class="control-label col-md-3">Your login name</label>
					<div class="col-md-3">
						<p class="form-control-static">${customer.login_name }</p>
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="control-label col-md-3">Password</label>
					<div class="col-md-3">
						<p class="form-control-static">${customer.password }</p>
					</div>
				</div>
				<div class="form-group">
					<label for="first_name" class="control-label col-md-3">First name</label>
					<div class="col-md-3">
						<p class="form-control-static">${customer.first_name }</p>
					</div>
				</div>
				<div class="form-group">
					<label for="last_name" class="control-label col-md-3">Last name</label>
					<div class="col-md-3">
						<p class="form-control-static">${customer.last_name }</p>
					</div>
				</div>
				<div class="form-group">
					<label for="email" class="control-label col-md-3">Your Email</label>
					<div class="col-md-3">
						<p class="form-control-static">${customer.email }</p>
					</div>
				</div>
				<div class="form-group">
					<label for="cellphone" class="control-label col-md-3">Your Phone</label>
					<div class="col-md-3">
						<p class="form-control-static">${customer.cellphone }</p>
					</div>
				</div>
				<div class="form-group">
					<label for="address" class="control-label col-md-3">Your Address</label>
					<div class="col-md-3">
						<p class="form-control-static">${customer.address }</p>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-3 col-md-offset-3">
						
						
					</div>
				</div>
				
				
				<!-- plan show -->
				<hr/>
				<h4>Please payment your plan</h4>
				<hr/>
				<div class="media">
				  	<a class="pull-left" href="#">
				    	<img class="media-object" data-src="holder.js/120x120" alt="...">
				  	</a>
			  		<div class="media-body">
			    		<h4 class="media-heading text-info">Your current purchase of plan</h4>
			    		<p class="text-danger">
			    			Currently, our pre-paid products and services, all pre-paid fees charged for three months, thank you for your cooperation.
			    		</p>
			    		<p>&nbsp;</p>
				    	<div class="row">
				    		<div class="col-md-4">
				    			<h4>${orderPlan.plan_name }</h4>
				    		</div>
				    		<div class="col-md-3">
				    			<h4>$  <strong class="text-success"> ${orderPlan.plan_price} </strong> * 3 months</h4>
				    		</div>
				    		<div class="col-md-3">
				    			<h4>Total Amount: $ <strong class="text-success"> ${orderPlan.plan_price * 3} </strong> </h4>
				    		</div>
				    		<div class="col-md-2">
								<button type="submit" class="btn btn-success">Payment</button>
							</div>
				    	</div>
			  		</div>
				</div>
			</form>
		</div>
	</div>
</div>


<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/holder.js"></script>
<jsp:include page="footer-end.jsp" />