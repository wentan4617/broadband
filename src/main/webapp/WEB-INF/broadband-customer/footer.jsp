<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div class="navbar-inverse" style="padding-bottom:50px;">
	<div class="container" >
	    <footer style="margin-top: 10px;">
	        <p class="pull-right hidden-xs hidden-sm" style="color:#fff">
				&copy; CyberPark Limited &middot; 2014 
			</p>
			<p class="text-center hidden-md hidden-lg" style="color:#fff">
				&copy; CyberPark Limited &middot; 2014
			</p>
			<p class="hidden-xs hidden-sm">
				<a href="${ctx }/about-us" rel="nofollow">About CyberPark</a>
				&middot;
				<a href="${ctx }/term-and-conditions" rel="nofollow" >Terms & Conditions</a>
				&middot;
				<a href="${ctx }/about-us#contact" rel="nofollow">Contact Us: 0800 229 237</a>
			</p>
			<p class="text-center hidden-md hidden-lg">
				<a href="${ctx }/about-us" rel="nofollow">About CyberPark</a>
				&middot;
				<a href="${ctx }/term-and-conditions" rel="nofollow" >Terms & Conditions</a>
				&middot;
				<a href="${ctx }/about-us#contact" rel="nofollow">Contact Us: 0800 229 237</a>
			</p>
	    </footer>
	    
	    <ul class="list-inline" style="margin:0 auto; width: 240px;">
			<li>
				<a title="Facebook" target="_blank" href="https://www.facebook.com/pages/Cyberpark/223261207861301" >
					<img alt="facebook" src="${ctx }/public/bootstrap3/images/facebook-icon.png" />
				</a>
			</li>
			<li>
				<a title="Google Plus" target="_blank" href="https://plus.google.com/113358993092406471657/posts" rel="publisher">
					<img alt="google-plus" src="${ctx }/public/bootstrap3/images/google-plus-icon.png" />
				</a>
			</li>
			<li>
				<a title="Twitter" target="_blank" href="https://twitter.com/CyberparkNZ">
					<img alt="twitter" src="${ctx }/public/bootstrap3/images/twitter-icon.png" />
				</a>
			</li>
		</ul>
		
		<img alt="" src="${ctx }/public/bootstrap3/images/cyberpark-2d-code.jpg" class="img-responsive" width="160px" height="160px" style="margin:20px auto;">
	</div>
</div>
<a id="scrollUp" href="#top" title="" style="position: fixed; z-index: 2147483647;"></a>
 