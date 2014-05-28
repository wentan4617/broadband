<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<jsp:include page="../header.jsp" />
<jsp:include page="../alert.jsp" />

<style>

</style>

<div class="container">
	<div class="row">
		<div class="col-md-12">
		
			<div class="panel panel-success">
				<div class="panel-heading">
					<h4 class="panel-title">
						Billing File Upload
					</h4>
				</div>
				<div class="panel-body">
					<form class="form-horizontal">
						<div class="form-group">
							<div class="col-md-2">
								<select id="select_year" class="form-control selectpicker" >
									<c:forEach var="year" begin="2014" end="2024" step="1">
										<option value="${year }" ${year==cur_year?'selected="selected"':'' }>${year }</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-7">
								<div class="btn-toolbar">
									<div class="btn-group">
										<a href="javascript:void(0);" class="btn btn-default">Jan.</a>
										<a href="javascript:void(0);" class="btn btn-default">Feb.</a>
										<a href="javascript:void(0);" class="btn btn-default">Mar.</a>
										<a href="javascript:void(0);" class="btn btn-default">Apr.</a>
										<a href="javascript:void(0);" class="btn btn-default">May</a>
										<a href="javascript:void(0);" class="btn btn-default">Jun.</a>
										<a href="javascript:void(0);" class="btn btn-default">Jul.</a>
										<a href="javascript:void(0);" class="btn btn-default">Aug.</a>
										<a href="javascript:void(0);" class="btn btn-default">Sept.</a>
										<a href="javascript:void(0);" class="btn btn-default">Oct.</a>
										<a href="javascript:void(0);" class="btn btn-default">Nov.</a>
										<a href="javascript:void(0);" class="btn btn-default">Dec.</a>
									</div>
								</div>
							</div>
							<div class="col-md-1">
								<span class="btn btn-success fileinput-button">
				                    <i class="glyphicon glyphicon-plus"></i>
				                    <span>Add files...</span>
				                    <input id="fileupload" type="file" name="files[]" multiple data-url="${ctx }/test/upload/server">
				                </span>
							</div>
						</div>
					</form>
				</div>
				
				
				<div id="billing-file-upload"></div>
				
			</div>
		</div>
	</div>
</div>

<script type="text/html" id="uploadTable_tmpl">
<jsp:include page="uploadTable.html" />
</script>

<jsp:include page="../footer.jsp" />
<jsp:include page="../script.jsp" />
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jTmpl.js"></script>
<script type="text/javascript" src="${ctx }/public/bootstrap3/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx }/public/bootstrap3/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx }/public/bootstrap3/js/jquery.fileupload.js"></script>

<script type="text/javascript">
(function($){
	
	$('.selectpicker').selectpicker();
	
	$('#fileupload').fileupload({
		dataType: 'json'
	 	, add: function (e, data) { console.log(data.files);
	 		$('#billing-file-upload').html(tmpl('uploadTable_tmpl', data));
        }
		, done: function (e, data) {
            data.context.text('Upload finished.');
        }
		, progressall: function (e, data) {
	        var progress = parseInt(data.loaded/data.total * 100, 10);
	        $('#progress .bar').css('width', progress + '%');
	    }
	});
	
})(jQuery);
</script>
<jsp:include page="../footer-end.jsp" />