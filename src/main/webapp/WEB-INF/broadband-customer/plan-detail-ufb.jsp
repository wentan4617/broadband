<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="header.jsp" />

<div style="background:#eee;padding-bottom:20px;">
<div class="container">

	<!-- non-naked plans -->
	<div class="page-header">
		<h1>
			UFB Plus <small>Please choose what you need</small>
		</h1>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="thumbnail">
				<img src="${ctx }/public/bootstrap3/images/ufb_low.png" alt="">
				<div class="caption">
					<h3>UFB Low Data Plan</h3>
					<h4>($XX/month)</h4>
					<p>Benefits:</p>
					<ul>
						<li>1 PSTN Telephone Line</li>
						<li>Local call free (National wide)</li>
						<li><strong class="text-danger">80 GB DATA</strong></li>
						<li>Calling to Telecom mobile $0.12, non-Telecom mobile $0.19 40 special countries 4.5c/mins</li>
						<li>Free Router</li>
						<li>12 months plan </li>
					</ul>
					<p class="text-center">
						<a href="${ctx }/order/16" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="thumbnail">
				<img src="${ctx }/public/bootstrap3/images/ufb_mid.png" alt="">
				<div class="caption">
					<h3>UFB Middle Data Plan</h3>
					<h4>($XX/month)</h4>
					<p>Benefits:</p>
					<ul >
						<li>1 PSTN Telephone Line</li>
						<li>Local call free (National wide)</li>
						<li><strong class="text-danger">150 GB DATA</strong></li>
						<li>Calling to Telecom mobile $0.12, non-Telecom mobile $0.19 40 special countries 4.5c/mins</li>
						<li>Free Router</li>
						<li>12 months plan</li>
					</ul>
					<p class="text-center">
						<a href="${ctx }/order/17" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="thumbnail">
				<img src="${ctx }/public/bootstrap3/images/ufb_high.png" alt="">
				<div class="caption">
					<h3>UFB High Data Plan</h3>
					<h4>($XX/month)</h4>
					<p>Benefits:</p>
					<ul >
						<li>1 PSTN Telephone Line</li>
						<li>Local call free (National wide)</li>
						<li><strong class="text-danger">350 GB DATA</strong></li>
						<li>Calling to Telecom mobile $0.12, non-Telecom mobile $0.19 40 special countries 4.5c/mins</li>
						<li>Free Router</li>
						<li>12 months plan </li>
					</ul>
					<p class="text-center">
						<a href="${ctx }/order/18" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
	</div>
	
	<!-- naked plans -->
	<div class="page-header">
		<h1>
			UFB Naked <small>Please choose what you need</small>
		</h1>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="thumbnail">
				<img src="${ctx }/public/bootstrap3/images/ufbn_low.png" alt="">
				<div class="caption">
					<h3>UFB Naked Low Data Plan </h3>
					<h4>($XX/month)</h4>
					<h6>Can apply 1 VoIP number for free, VoIP gateway cost $89</h6>
					<p>Benefits:</p>
					<ul >
						<li><strong class="text-danger">80 GB DATA</strong></li>
						<li>Free Router</li>
						<li>12 months plan</li>
					</ul>
					<p class="text-center">
						<a href="${ctx }/order/19" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="thumbnail">
				<img src="${ctx }/public/bootstrap3/images/ufbn_low.png" alt="">
				<div class="caption">
					<h3>UFB Naked Middle Data Plan</h3>
					<h4>($XX/month)</h4>
					<h6>&nbsp;</h6>
					<p>Benefits:</p>
					<ul >
						<li><strong class="text-danger">150 GB DATA</strong></li>
						<li>Free Router</li>
						<li>12 months plan</li>
					</ul>
					<p class="text-center">
						<a href="${ctx }/order/20" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="thumbnail">
				<img src="${ctx }/public/bootstrap3/images/ufbn_low.png" alt="">
				<div class="caption">
					<h3>UFB Naked High Data Plan</h3>
					<h4>($XX/month)</h4>
					<h6>&nbsp;</h6>
					<p>Benefits:</p>
					<ul >
						<li><strong class="text-danger">350 GB DATA</strong></li>
						<li>Free Router</li>
						<li>12 months plan</li>
					</ul>
					<p class="text-center">
						<a href="${ctx }/order/21" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
	</div>
</div>
</div>

<jsp:include page="footer.jsp" />
<jsp:include page="script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/holder.js"></script>
<jsp:include page="footer-end.jsp" />