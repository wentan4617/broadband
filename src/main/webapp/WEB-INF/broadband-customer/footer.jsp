<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<div class="navbar-inverse" style="padding-bottom: 50px">
	<div class="container" >
	    <footer style="margin-top: 10px">
	        <p class="pull-right hidden-xs hidden-sm" style="color: #fff">
				&copy; ${wedSession.company_name_ltd!=null && wedSession.company_name_ltd!='' ? wedSession.company_name_ltd : 'CyberPark Limited'} &middot; ${wedSession.website_year!=null && wedSession.website_year!='' ? wedSession.website_year : '2014'} 
			</p>
			<p class="text-center hidden-md hidden-lg" style="color: #fff">
				&copy; ${wedSession.company_name_ltd!=null && wedSession.company_name_ltd!='' ? wedSession.company_name_ltd : 'CyberPark Limited'} &middot; ${wedSession.website_year!=null && wedSession.website_year!='' ? wedSession.website_year : '2014'}
			</p>
			<p class="hidden-xs hidden-sm">
				<a href="${ctx }/about-us" rel="nofollow">About ${wedSession.company_name!=null && wedSession.company_name!='' ? wedSession.company_name : 'CyberPark'}</a>
				<span style="color: #fff">&middot;</span>
				<a href="${ctx }/term-and-conditions" rel="nofollow" >Terms &amp; Conditions</a>
				<span style="color: #fff">&middot;</span>
				<a href="${ctx }/about-us#contact" rel="nofollow">Contact Us: ${wedSession.company_hot_line_no!=null && wedSession.company_hot_line_no!='' ? wedSession.company_hot_line_no : '0800 229 237'}</a>
			</p>
			<p class="text-center hidden-md hidden-lg">
				<a href="${ctx }/about-us" rel="nofollow">About CyberPark</a>
				<span style="color: #fff">&middot;</span>
				<a href="${ctx }/term-and-conditions" rel="nofollow" >Terms &amp; Conditions</a>
				<span style="color: #fff">&middot;</span>
				<a href="${ctx }/about-us#contact" rel="nofollow">Contact Us: ${wedSession.company_hot_line_no!=null && wedSession.company_hot_line_no!='' ? wedSession.company_hot_line_no : '0800 229 237'}</a>
			</p>
	    </footer>
	    
	    <ul class="list-inline" style="margin: 0 auto; width: 320px">
			<li>
				<c:choose>
					<c:when test="${wsrSession.facebook_url!=null && wsrSession.facebook_url!=''}">
						<a target="_blank" href="${wsrSession.facebook_url}" >
							<c:choose>
								<c:when test="${wsrSession.facebook_lg_path!=null && wsrSession.facebook_lg_path!=''}">
									<img src="${ctx }/${wsrSession.facebook_lg_path}" alt="Wide Range Broadband" title="Wide Range Broadband"/>
								</c:when>
								<c:otherwise>
									<img src="${ctx }/public/bootstrap3/images/facebook-icon.png" alt="Wide Range Broadband" title="Wide Range Broadband"/>
								</c:otherwise>
							</c:choose>
						</a>
					</c:when>
					<c:otherwise>
						<a target="_blank" href="https://www.facebook.com/pages/Cyberpark/223261207861301" >
							<c:choose>
								<c:when test="${wsrSession.facebook_lg_path!=null && wsrSession.facebook_lg_path!=''}">
									<img src="${ctx }/${wsrSession.facebook_lg_path}" alt="Wide Range Broadband" title="Wide Range Broadband"/>
								</c:when>
								<c:otherwise>
									<img src="${ctx }/public/bootstrap3/images/facebook-icon.png" alt="Wide Range Broadband" title="Wide Range Broadband"/>
								</c:otherwise>
							</c:choose>
						</a>
					</c:otherwise>
				</c:choose>
			</li>
			<li>
				<c:choose>
					<c:when test="${wsrSession.googleplus_url!=null && wsrSession.googleplus_url!=''}">
						<a target="_blank" href="${wsrSession.googleplus_url}" rel="publisher">
							<c:choose>
								<c:when test="${wsrSession.googleplus_lg_path!=null && wsrSession.googleplus_lg_path!=''}">
									<img src="${ctx }/${wsrSession.googleplus_lg_path}" alt="Our Customers Are Now Enjoying Nation's Highest Broadband Speed" title="Our Customers Are Now Enjoying Nation's Highest Broadband Speed"/>
								</c:when>
								<c:otherwise>
									<img src="${ctx }/public/bootstrap3/images/google-plus-icon.png" alt="Our Customers Are Now Enjoying Nation's Highest Broadband Speed" title="Our Customers Are Now Enjoying Nation's Highest Broadband Speed"/>
								</c:otherwise>
							</c:choose>
						</a>
					</c:when>
					<c:otherwise>
						<a target="_blank" href="https://plus.google.com/113358993092406471657/posts" rel="publisher">
							<c:choose>
								<c:when test="${wsrSession.googleplus_lg_path!=null && wsrSession.googleplus_lg_path!=''}">
									<img src="${ctx }/${wsrSession.googleplus_lg_path}" alt="Our Customers Are Now Enjoying Nation's Highest Broadband Speed" title="Our Customers Are Now Enjoying Nation's Highest Broadband Speed"/>
								</c:when>
								<c:otherwise>
									<img src="${ctx }/public/bootstrap3/images/google-plus-icon.png" alt="Our Customers Are Now Enjoying Nation's Highest Broadband Speed" title="Our Customers Are Now Enjoying Nation's Highest Broadband Speed"/>
								</c:otherwise>
							</c:choose>
						</a>
					</c:otherwise>
				</c:choose>
			</li>
			<li>
				<c:choose>
					<c:when test="${wsrSession.twitter_url!=null && wsrSession.twitter_url!=''}">
						<a target="_blank" href="${wsrSession.twitter_url}">
							<c:choose>
								<c:when test="${wsrSession.twitter_lg_path!=null && wsrSession.twitter_lg_path!=''}">
									<img src="${ctx }/${wsrSession.twitter_lg_path}" alt="Join Us To Have An Unforgetable Internet Surfing Experience" title="Join Us To Have An Unforgetable Internet Surfing Experience"/>
								</c:when>
								<c:otherwise>
									<img src="${ctx }/public/bootstrap3/images/twitter-icon.png" alt="Join Us To Have An Unforgetable Internet Surfing Experience" title="Join Us To Have An Unforgetable Internet Surfing Experience"/>
								</c:otherwise>
							</c:choose>
						</a>
					</c:when>
					<c:otherwise>
						<a target="_blank" href="https://twitter.com/nzcyberpark">
							<c:choose>
								<c:when test="${wsrSession.twitter_lg_path!=null && wsrSession.twitter_lg_path!=''}">
									<img src="${ctx }/${wsrSession.twitter_lg_path}" alt="Join Us To Have An Unforgetable Internet Surfing Experience" title="Join Us To Have An Unforgetable Internet Surfing Experience"/>
								</c:when>
								<c:otherwise>
									<img src="${ctx }/public/bootstrap3/images/twitter-icon.png" alt="Join Us To Have An Unforgetable Internet Surfing Experience" title="Join Us To Have An Unforgetable Internet Surfing Experience"/>
								</c:otherwise>
							</c:choose>
						</a>
					</c:otherwise>
				</c:choose>
			</li>
			<li>
				<c:choose>
					<c:when test="${wsrSession.youtube_url!=null && wsrSession.youtube_url!=''}">
						<a target="_blank" href="${wsrSession.youtube_url}">
							<c:choose>
								<c:when test="${wsrSession.youtube_lg_path!=null && wsrSession.youtube_lg_path!=''}">
									<img src="${ctx }/${wsrSession.youtube_lg_path}" alt="Our Customer's Testimonials Is The Best Thing We've Got" title="Our Customer's Testimonials Is The Best Thing We've Got"/>
								</c:when>
								<c:otherwise>
									<img src="${ctx }/public/bootstrap3/images/youtube-icon.png" alt="Our Customer's Testimonials Is The Best Thing We've Got" title="Our Customer's Testimonials Is The Best Thing We've Got"/>
								</c:otherwise>
							</c:choose>
						</a>
					</c:when>
					<c:otherwise>
						<a target="_blank" href="http://www.youtube.com/channel/UCTmXMdGEUxYRwlxw3SjZePA">
							<c:choose>
								<c:when test="${wsrSession.youtube_lg_path!=null && wsrSession.youtube_lg_path!=''}">
									<img src="${ctx }/${wsrSession.youtube_lg_path}" alt="Our Customer's Testimonials Is The Best Thing We've Got" title="Our Customer's Testimonials Is The Best Thing We've Got"/>
								</c:when>
								<c:otherwise>
									<img src="${ctx }/public/bootstrap3/images/youtube-icon.png" alt="Our Customer's Testimonials Is The Best Thing We've Got" title="Our Customer's Testimonials Is The Best Thing We've Got"/>
								</c:otherwise>
							</c:choose>
						</a>
					</c:otherwise>
				</c:choose>
			</li>
		</ul>
		
		<c:choose>
			<c:when test="${wsrSession.two_dimensional_code_path!=null && wsrSession.two_dimensional_code_path!=''}">
				<img src="${ctx }/${wsrSession.two_dimensional_code_path}" class="img-responsive" width="160px" height="160px" style="margin: 20px auto" alt="Most Reliable NZ ISP Provider" title="Most Reliable NZ ISP Provider">
			</c:when>
			<c:otherwise>
				<img src="${ctx }/public/bootstrap3/images/cyberpark-2d-code.jpg" class="img-responsive" width="160px" height="160px" style="margin: 20px auto" alt="Most Reliable NZ ISP Provider" title="Most Reliable NZ ISP Provider">
			</c:otherwise>
		</c:choose>
	</div>
</div>
<a id="scrollUp" href="#top" title="" style="position: fixed; z-index: 2147483647"></a>
 