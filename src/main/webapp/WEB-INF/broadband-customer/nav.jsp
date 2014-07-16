<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<style>
.follow-us-on{
	padding:3px 20px;
	display:block;
}
.ig-b- { display: inline-block; }
.ig-b- img { visibility: hidden; }
.ig-b-:hover { background-position: 0 -60px; } .ig-b-:active { background-position: 0 -120px; }
.ig-b-v-24 { width: 137px; height: 24px; background: url(//badges.instagram.com/static/images/ig-badge-view-sprite-24.png) no-repeat 0 0; }
@media only screen and (-webkit-min-device-pixel-ratio: 2), only screen and (min--moz-device-pixel-ratio: 2), only screen and (-o-min-device-pixel-ratio: 2 / 1), only screen and (min-device-pixel-ratio: 2), only screen and (min-resolution: 192dpi), only screen and (min-resolution: 2dppx) {
.ig-b-v-24 { background-image: url(//badges.instagram.com/static/images/ig-badge-view-sprite-24@2x.png); background-size: 160px 178px; } }
</style>


<div class="navbar navbar-default navbar-static-top" id="navhead">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse" style="background-color:#5cb85c;">
				<span class="icon-bar" style="background-color:#fff;"></span> 
				<span class="icon-bar" style="background-color:#fff;"></span> 
				<span class="icon-bar" style="background-color:#fff;"></span>
			</button>
			<a class="navbar-brand" href="${ctx }/home" rel="${nofollow}">
				<span class="logo"></span> 
			</a>
		</div>
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li>
					<a rel="nofollow" href="#" class="dropdown-toggle" data-toggle="dropdown" ><strong>Personal Plan</strong><b class="caret"></b></a>
					<ul class="dropdown-menu list-inline hidden-xs hidden-sm" style="width:610px;">
			            <li style="width:100%;">
			            	<div class="row">
			            		<div class="col-md-4" style="border-right: 2px dotted;">
			            			<p class="text-center text-success">
				            			<a href="${ctx }/plans/plan-no-term/personal" target="_blank" style="text-decoration: none;">
				            				<span class="glyphicon glyphicon-th-list" style="font-size:85px;display:block;margin-top: 10px;"></span>
				            				<span style="font-size:22px;display:block;font-weight: bold;margin-top: 10px;">No Term Plan</span>
										</a>
			            			</p>
								</div>
			            		<div class="col-md-4" style="border-right: 2px dotted;">
			            			<p class="text-center text-success">
				            			<a href="${ctx }/plans/plan-topup/personal" target="_blank" style="text-decoration: none;">
				            				<span class="glyphicon glyphicon-th-large " style="font-size:85px;display:block;margin-top: 10px;"></span>
				            				<span style="font-size:22px;display:block;font-weight: bold;margin-top: 10px;">Top Up Plan</span>
										</a>
			            			</p>
			            		</div>
			            		<div class="col-md-4">
			            			<p class="text-center text-success">
				            			<a href="${ctx }/plans/plan-term/personal" target="_blank" style="text-decoration: none;">
				            				<span class="glyphicon glyphicon-th" style="font-size:85px;display:block;margin-top: 10px;"></span>
				            				<span style="font-size:22px;display:block;font-weight: bold;margin-top: 10px;">Term Plan</span>
										</a>
			            			</p>
			            		</div>
			            	</div>
			            </li>
		          	</ul>
		          	<ul class="dropdown-menu hidden-md hidden-lg"><!--  visible-xs visible-sm -->
			            <li>
			            	<a href="${ctx }/plans/plan-no-term/personal" target="_blank">
	            				<span class="glyphicon glyphicon-th-list"></span> No Term Plan
							</a>	
			            </li>
			            <li>
			            	<a href="${ctx }/plans/plan-topup/personal" target="_blank" >
	            				<span class="glyphicon glyphicon-th-large "></span> Top Up Plan
							</a>
			            </li>
			            <li>
			            	<a href="${ctx }/plans/plan-term/personal" target="_blank" >
	            				<span class="glyphicon glyphicon-th"></span> Term Plan
							</a>
			            </li>
		          	</ul>
				</li>
				<li><a href="${ctx }/plans/plan-term/business" rel="nofollow"><strong>Business Plan</strong></a></li>
				<li>
					<a rel="nofollow" href="#" class="dropdown-toggle" data-toggle="dropdown" ><strong>Information</strong><b class="caret"></b></a>
					<ul class="dropdown-menu"><!--  visible-xs visible-sm -->
			            <li>
			            	<a href="${ctx }/about-us" >
	            				About CyberPark
							</a>	
			            </li>
		             	<li>
			            	<a href="${ctx }/voucher" >
	            				Voucher Checking
							</a>
			            </li>
			            <li>
			            	<a href="${ctx }/home-phone-calling-rates" >
	            				Business Calling Rates
							</a>
			            </li>
			            <li>
			            	<a href="${ctx }/wifi-solution" >
	            				Wifi Solution
							</a>
			            </li>
			            <li>
			            	<a href="${ctx }/e-commerce" >
	            				E-Commerce
							</a>
			            </li>
		             	<li>
			            	<a href="${ctx }/term-and-conditions" >
	            				Terms & Conditions
							</a>
			            </li>
		          	</ul>
				</li>
				<li><a href="${ctx }/about-us#contact" rel="nofollow"><strong>Contact Us: <span style="color:white;">0800 229 237</span></strong></a></li>
				<li>
					<a rel="nofollow" href="#" class="dropdown-toggle" data-toggle="dropdown" ><strong>Share&nbsp;<span class="glyphicon glyphicon-send"></span></strong><b class="caret"></b></a>
					<ul class="dropdown-menu"><!--  visible-xs visible-sm -->
		             	<li class="follow-us-on">
			            	<a title="Instagram" href="http://instagram.com/cyberparknz?ref=badge" class="ig-b- ig-b-v-24"><img src="//badges.instagram.com/static/images/ig-badge-view-24.png" alt="Instagram" /></a>
			            </li>
			            <li>
			            	<a title="Facebook" target="_blank" href="https://www.facebook.com/pages/Cyberpark/223261207861301">
			            		<img src="${ctx}/public/bootstrap3/images/follow-on-facebook.png" style="border-radius:5px 5px 5px 5px;"/>
			            	</a>
			            </li>
			            <li>
			            	<a title="Twitter" target="_blank" href="https://twitter.com/CyberparkNZ">
			            		<img src="${ctx}/public/bootstrap3/images/follow-on-twitter.png" style="border-radius:5px 5px 5px 5px;"/>
			            	</a>
			            </li>
			            <li>
			            	<a title="Google Plus" target="_blank" href="https://plus.google.com/113358993092406471657/posts">
			            		<img src="${ctx}/public/bootstrap3/images/follow-on-g+.png" style="border-radius:5px 5px 5px 5px;"/>
			            	</a>
			            </li>
		             	<li class="follow-us-on">
			            	<a data-pin-do="buttonFollow" href="http://www.pinterest.com/cyberparknz/">Pinterest</a>
			            </li>
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
			</ul>
			
			<c:if test="${customerSession != null }">
				<p class="navbar-text pull-right" >
					<a href="${ctx }/customer/home"  class="navbar-link" style="margin-right:10px;">
						<span class="glyphicon glyphicon-user" style="margin-right:10px;"></span>
						<c:choose>
							<c:when test="${customerSession.customer_type == 'personal' }">
								${customerSession.first_name } ${customerSession.last_name }
							</c:when>
							<c:when test="${customerSession.customer_type == 'business' }">
								${customerSession.company_name }
							</c:when>
						</c:choose>
					</a>
					<a href="${ctx}/signout" rel="nofollow" data-toggle="tooltip" data-placement="bottom" data-original-title="Sign out">
						<span class="glyphicon glyphicon-log-out" style="margin-right:10px;"></span>
					</a>
				</p>
			</c:if>
			<c:if test="${customerSession == null }">
	 			<a href="${ctx }/login" class="btn btn-success navbar-btn navbar-right hidden-xs hidden-sm">
	 				<span class="glyphicon glyphicon-log-in"></span> <strong>Login</strong>
	 			</a>
	 			<a href="${ctx }/login" class="btn btn-success btn-block btn-lg navbar-btn navbar-right hidden-md hidden-lg" >
	 				<span class="glyphicon glyphicon-log-in"></span> <strong>Login</strong>
	 			</a>
			</c:if>
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

<script type="text/javascript" async src="//assets.pinterest.com/js/pinit.js"></script>