<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-heading"><h4 class="panel-title">${panelheading }</h4></div>
				<div class="panel-body">
					<form class="form-horizontal">
						<div class="form-group">
							<label for="hardware_name" class="control-label col-md-4">Hardware Name</label>
							<div class="col-md-3">
								<input type="text" value="${hardware.hardware_name}" id="hardware_name" class="form-control" placeholder="Hardware Name" />
							</div>
						</div>
						<div class="form-group">
							<label for="hardware_type" class="control-label col-md-4">Hardware Type</label>
							<div class="col-md-3">
								<select id="hardware_type" class="form-control">
									<option
										<c:if test="${hardware.hardware_type == ''}">
											selected="selected"
										</c:if>
									>none</option>
									<c:forEach var="type" items="router">
										<option value="${type}"
											<c:if test="${type == hardware.hardware_type}">
												selected="selected"
											</c:if>
										>${type}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="hardware_status" class="control-label col-md-4">Hardware Status</label>
							<div class="col-md-3">
								<select id="hardware_status" class="form-control">
									<option
										<c:if test="${hardware.hardware_status == ''}">
											selected="selected"
										</c:if>
									>none</option>
									<c:forEach var="status" items="active,selling,disable">
										<option value="${status}"
											<c:if test="${status == hardware.hardware_status}">
												selected="selected"
											</c:if>
										>${status}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="hardware_price" class="control-label col-md-4">Hardware Price</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<input type="text" value="${hardware.hardware_price}" id="hardware_price" class="form-control" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="hardware_cost" class="control-label col-md-4">Hardware Cost</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">$</span>
									<input type="text" value="${hardware.hardware_cost}" id="hardware_cost" class="form-control" />
								</div>
							</div>
						</div>
						<hr/>
						<div class="form-group">
							<div class="col-md-3 col-md-offset-4">
								<button type="button" class="btn btn-success" id="save-btn">Save</button>
							</div>
						</div>
						<hr/>
						<div class="form-group">
							<!-- <label for="hardware_desc" class="control-label col-md-4">Hardware Desc</label> -->
							<div class="col-md-12">
								<textarea id="hardware_desc" class="form-control" rows="24">${hardware.hardware_desc}</textarea>
							</div>
							<%-- <p class="help-block">
								<form:errors path="hardware_desc" cssErrorClass="error"/>
							</p> --%>
						</div>
					</form>
					
					<form:form modelAttribute="hardware" method="post" action="${ctx}/broadband-user/plan/hardware/pic/edit" class="form-horizontal" enctype="multipart/form-data">
						<form:hidden path="id"/>
						<hr/>
						<div class="page-header" style="margin:0;">
							<h3 class="text-success"><strong>Hardware Pictures</strong></h3>
						</div>
						<div class="form-group" style="margin:15px 0 0 0;">
						    <label for="img1" class="col-sm-4 control-label">Picture 1:</label>
						    <div class="col-sm-8">
								<input type="file" name="imgs" class="form-control" placeholder="Picture 1 Path" />
						    </div>
						</div>
						<div class="form-group" style="margin:15px 0 0 0;">
						    <label for="img2" class="col-sm-4 control-label">Picture 2:</label>
						    <div class="col-sm-8">
								<input type="file" name="imgs" class="form-control" placeholder="Picture 2 Path" />
						    </div>
						</div>
						<div class="form-group" style="margin:15px 0 0 0;">
						    <label for="img3" class="col-sm-4 control-label">Picture 3:</label>
						    <div class="col-sm-8">
								<input type="file" name="imgs" class="form-control" placeholder="Picture 3 Path" />
						    </div>
						</div>
						<hr/>
						<div class="form-group">
							<div class="col-md-3 col-md-offset-4">
								<button type="submit" class="btn btn-success">Save Pictures</button>
							</div>
						</div>
						<div class="form-group" style="margin:15px 0 0 0;">
							<div class="col-md-4">
							    <a href="#" class="thumbnail">
							      <img data-src="holder.js/300x200" alt="..."
								      <c:if test="${hardware.img1==null || hardware.img1==''}">
								      	src="data:image/png;base64,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"
								      </c:if>
								      <c:if test="${hardware.img1!=null && hardware.img1!=''}">
								      	src="${ctx}/public/upload/${hardware.img1}"
								      </c:if>
							      >
							    </a>
							</div>
							<div class="col-md-4">
							    <a href="#" class="thumbnail">
							      <img data-src="holder.js/300x200" alt="..."
								      <c:if test="${hardware.img2==null || hardware.img2==''}">
								      	src="data:image/png;base64,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"
								      </c:if>
								      <c:if test="${hardware.img2!=null && hardware.img2!=''}">
								      	src="${ctx}/public/upload/${hardware.img2}"
								      </c:if>
							      >
							    </a>
							</div>
							<div class="col-md-4">
							    <a href="#" class="thumbnail">
							      <img data-src="holder.js/300x200" alt="..."
								      <c:if test="${hardware.img3==null || hardware.img3==''}">
								      	src="data:image/png;base64,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"
								      </c:if>
								      <c:if test="${hardware.img3!=null && hardware.img3!=''}">
								      	src="${ctx}/public/upload/${hardware.img3}"
								      </c:if>
							      >
							    </a>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
(function($){
	
	$('#save-btn').on("click", function(){
		var $btn = $(this);
		$btn.button('loading');
		var plan = {
			id: '${hardware.id}'
			, hardware_name: $('#hardware_name').val()
			, hardware_type: $('#hardware_type option:selected').val()
			, hardware_status: $('#hardware_status option:selected').val()
			, hardware_price: $('#hardware_price').val()
			, hardware_cost: $('#hardware_cost').val()
			, hardware_desc: $('#hardware_desc').val()
		};
		$.post('${ctx}${action}', plan, function(json){
			if (json.hasErrors) {
				$.jsonValidation(json, 'right');
			} else {
				window.location.href='${ctx}' + json.url;
			}
		}, 'json').always(function () {
			$btn.button('reset');
	    });
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />