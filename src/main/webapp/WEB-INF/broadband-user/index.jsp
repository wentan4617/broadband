<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />
<jsp:include page="alert.jsp" />

<div class="container">
    <div class="row">
        <div class="col-md-3">
            <div class="thumbnail">
                <img data-src="holder.js/300x100" alt="...">
                <div class="caption">
                    <h3>Plan</h3>
                    <p>Create a plan products, plan on-line sales</p>
                    <ul class="list-unstyled">
                    	<li>
                    		<a href="${ctx }/broadband-user/plan/view/1">View Plan</a>
                    	</li>
                    	<li>
                    		<a href="${ctx }/broadband-user/plan/create">Create Plan</a>
                    	</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="thumbnail">
                <img data-src="holder.js/300x100" alt="...">
                <div class="caption">
                    <h3>CRM</h3>
                    <p>Simple customer relationship management module</p>
                    <ul class="list-unstyled">
                    	<li>
                    		<a href="${ctx }/broadband-user/crm/customer/view/1">View Customer</a>
                    	</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="thumbnail">
                <img data-src="holder.js/300x100" alt="...">
                <div class="caption">
                    <h3>Billing</h3>
                    <p>...</p>

                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="thumbnail">

                <img data-src="holder.js/300x100" alt="...">
                <div class="caption">
                    <h3>Provision</h3>
                    <p>Review all customer purchase orders and payment orders.</p>
                    <ul class="list-unstyled">
                    	<li>
                    		<a href="${ctx }/broadband-user/provision/customer/view/1">Provision Customer Order</a>
                    	</li>
                    </ul>

                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript"
    src="${ctx}/public/bootstrap3/js/holder.js"></script>
<jsp:include page="footer-end.jsp" />