<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div class="navbar navbar-inverse navbar-static-top" id="navhead">
	<div class="container">
	
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="icon-bar" ></span> 
				<span class="icon-bar" ></span> 
				<span class="icon-bar" ></span>
			</button>
			<a class="navbar-brand" href="${ctx }/home" rel="${nofollow}">
				<c:choose>
					<c:when test="${wsrSession.logo_path!=null && wsrSession.logo_path!=''}">
						<span style="display:inline-block; height:46px; width:111px; background:url('${ctx}/${wsrSession.logo_path }') no-repeat; margin-top:-12px; background-size:contain;"></span>
					</c:when>
					<c:otherwise>
						<span class="logo"></span>
					</c:otherwise>
				</c:choose>
			</a>
		</div>
		
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<%-- <li><a href="${ctx }/plans/plan-topup/personal" rel="nofollow">Top Up Plan</a></li> --%>
				<li><a href="${ctx }/plans/broadband" rel="nofollow">Personal Plan</a></li>
				<li><a href="${ctx }/plans/plan-term/business" rel="nofollow">Business Plan</a></li>
				<li>
					<a rel="nofollow" href="#" class="dropdown-toggle" data-toggle="dropdown" >Information<b class="caret"></b></a>
					<ul class="dropdown-menu">
			            <li><a href="${ctx }/about-us">About CyberPark</a></li>
		             	<li><a href="${ctx }/voucher">Voucher Checking</a></li>
			            <li><a href="${ctx }/phone-calling-rates">Phone Calling Rates</a></li>
			            <li><a href="${ctx }/voip-calling-rates">VoIP Calling Rates</a></li>
			            <li><a href="${ctx }/wifi-solution" >Wi-Fi Solution</a></li>
			            <li><a href="${ctx }/e-commerce" >E-Commerce</a></li>
		             	<li><a href="${ctx }/term-and-conditions">Terms &amp; Conditions</a></li>
		          	</ul>
				</li>
				<li><a href="${ctx }/about-us#contact" rel="nofollow"><span style="color: white">${wedSession.company_hot_line_no!=null && wedSession.company_hot_line_no!='' ? wedSession.company_hot_line_no : '0800 229 237'}</span></a></li>
				<li>
					<a rel="nofollow" href="#" class="dropdown-toggle" data-toggle="dropdown" >Share <b class="caret"></b></a>
					<ul class="dropdown-menu">
			            <li>
			            	<a class="fb-share-button" data-href="https://www.facebook.com/pages/Cyberpark/223261207861301" data-type="button_count"></a>
			            </li>
			            <li class="follow-us-on">
							<a href="https://twitter.com/share" class="twitter-share-button" data-url="https://twitter.com/CyberparkNZ" data-text="CyberPark provides broadband, phone, and other internet services to savvy New Zealand homes and businesses. CyberPark Provides All-In-One ISP Solutions Suit for Business And Personal Customers.">Tweet</a>
							<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+'://platform.twitter.com/widgets.js';fjs.parentNode.insertBefore(js,fjs);}}(document, 'script', 'twitter-wjs');</script>
			            </li>
			            <li class="follow-us-on">
			            	<a class="g-follow" data-annotation="bubble" data-height="20" data-href="https://plus.google.com/113358993092406471657" data-rel="publisher"></a>
			            </li>
		          	</ul>
				</li>
				<c:if test="${fn:length(customerReg.address) > 0 }">
				<li>
					<a href="${ctx }/plans/address-check" style="padding-top: 10px; padding-bottom: 5px" rel="nofollow">
						<span class="glyphicon glyphicon-map-marker" style="font-size: 24px"></span>
					</a>
				</li>
				</c:if>
			</ul>
			
			<p class="navbar-text navbar-right" >
				<c:if test="${customerSession != null }">
					<a href="${ctx }/customer/home" class="navbar-link" >
						<span class="glyphicon glyphicon-user"></span> 
						${customerSession.cellphone }
					</a> 
					<a href="${ctx}/signout" rel="nofollow" data-toggle="tooltip" data-placement="bottom" data-original-title="Sign out">
						<span class="glyphicon glyphicon-log-out"></span>
					</a>
				</c:if>
				<c:if test="${customerSession == null }">
					<a href="${ctx }/sign-in" class="navbar-link" ><span class="glyphicon glyphicon-log-in"></span> Sign in</a>
				</c:if>
			</p>
			
		</div>
	</div>
</div>
<div id="fb-root"></div>

<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/zh_CN/sdk.js#xfbml=1&version=v2.0";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<script type="text/javascript">
window.___gcfg = {lang: 'en-GB'};
(function() {
   var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
   po.src = 'https://apis.google.com/js/platform.js';
   var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
})();
</script>