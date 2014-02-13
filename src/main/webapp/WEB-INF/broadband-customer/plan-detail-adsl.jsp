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
			ADSL Plus <small>Please choose what you need</small>
		</h1>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="thumbnail">
				<div class="caption">
					<h3><strong class="text-success">ADSL BROADBAND</strong></h3>
					<hr/>
					<div class="well">
						The broadband standard in NZ. 
						Fast internet over your copper 
						phone line.
					</div>
					<h4><strong class="text-success">How much data do you need?</strong></h4>
					<div class="btn-group btn-group-justified">
						<a href="javascript:void(0);" class="btn btn-default">Left</a>
					  	<a href="javascript:void(0);" class="btn btn-default">Middle</a>
					  	<a href="javascript:void(0);" class="btn btn-default">Right</a>
					</div>
					<p class="text-center">
						<a href="${ctx }/order/4" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="thumbnail">
				<img src="${ctx }/public/bootstrap3/images/adsl_mid.png" alt="">
				<div class="caption">
					<h3>ADSL Middle Data Plan</h3>
					<h4>($XX/month)</h4>
					<p>Benefits:</p>
					<ul >
						<li>1 PSTN Telephone Line</li>
						<li>Local call free (National wide)</li>
						<li><strong class="text-danger">300 GB DATA</strong></li>
						<li>Calling to Telecom mobile $0.12, non-Telecom mobile $0.19 40 special countries 4.5c/mins</li>
						<li>Free Router</li>
						<li>12 months plan</li>
					</ul>
					<p class="text-center">
						<a href="${ctx }/order/5" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="thumbnail">
				<img src="${ctx }/public/bootstrap3/images/adsl_high.png" alt="">
				<div class="caption">
					<h3>ADSL High Data Plan</h3>
					<h4>($XX/month)</h4>
					<p>Benefits:</p>
					<ul >
						<li>1 PSTN Telephone Line</li>
						<li>Local call free (National wide)</li>
						<li><strong class="text-danger">Unlimited DATA</strong></li>
						<li>Calling to Telecom mobile $0.12, non-Telecom mobile $0.19 40 special countries 4.5c/mins</li>
						<li>Free Router</li>
						<li>12 months plan </li>
					</ul>
					<p class="text-center">
						<a href="${ctx }/order/6" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
	</div>
	
	<!-- naked plans -->
	<div class="page-header">
		<h1>
			ADSL Naked <small>Please choose what you need</small>
		</h1>
	</div>
	<div class="row">
		<div class="col-md-4">
			<div class="thumbnail">
				<img src="${ctx }/public/bootstrap3/images/adsln_low.png" alt="">
				<div class="caption">
					<h3>ADSL Naked Low Data Plan</h3>
					<h4>($XX/month)</h4>
					<h6>Can apply 1 VoIP number for free, VoIP gateway cost $89</h6>
					<p>Benefits:</p>
					<ul >
						<li><strong class="text-danger">100 GB DATA</strong></li>
						<li>Free Router</li>
						<li>12 months plan</li>
					</ul>
					<p class="text-center">
						<a href="${ctx }/order/10" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="thumbnail">
				<img src="${ctx }/public/bootstrap3/images/adsln_mid.png" alt="">
				<div class="caption">
					<h3>ADSL Naked Middle Data Plan</h3>
					<h4>($XX/month)</h4>
					<h6>&nbsp;</h6>
					<p>Benefits:</p>
					<ul >
						<li><strong class="text-danger">300 GB DATA</strong></li>
						<li>Free Router</li>
						<li>12 months plan</li>
					</ul>
					<p class="text-center">
						<a href="${ctx }/order/11" class="btn btn-success" role="button">Purchase</a> 
					</p>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="thumbnail">
				<img src="${ctx }/public/bootstrap3/images/adsln_high.png" alt="">
				<div class="caption">
					<h3>ADSL Naked High Data Plan</h3>
					<h4>($XX/month)</h4>
					<h6>&nbsp;</h6>
					<p>Benefits:</p>
					<ul >
						<li><strong class="text-danger">Unlimited DATA</strong></li>
						<li>Free Router</li>
						<li>12 months plan</li>
					</ul>
					<p class="text-center">
						<a href="${ctx }/order/12" class="btn btn-success" role="button">Purchase</a> 
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