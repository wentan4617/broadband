<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>jQuery File Upload Example</title>
<link href="${ctx}/public/bootstrap3/css/bootstrap.min.css" rel="stylesheet" type="text/css" media="screen" />
<style type="text/css">
.bar {
    height: 18px;
    background: green;
}
</style>
</head>
<body>
	<input id="fileupload" type="file" name="files[]" data-url="${ctx }/test/upload/server" multiple />
	<div id="progress">
		<div class="bar" style="width: 0%;"></div>
	</div>
	<script src="${ctx }/public/bootstrap3/js/jquery-1.11.1.min.js"></script>
	<script src="${ctx }/public/bootstrap3/js/vendor/jquery.ui.widget.js"></script>
	<script src="${ctx }/public/bootstrap3/js/jquery.iframe-transport.js"></script>
	<script src="${ctx }/public/bootstrap3/js/jquery.fileupload.js"></script>
	<script>
		$(function() {
			$('#fileupload').fileupload({
				dataType: 'json'
			 	, add: function (e, data) {
		            data.context = $('<button/>').text('Upload')
		                .appendTo(document.body)
		                .click(function () {
		                    data.context = $('<p/>').text('Uploading...').replaceAll($(this));
		                    data.submit();
		                });
		        }
				, done: function (e, data) {
		            data.context.text('Upload finished.');
		        }
				, progressall: function (e, data) {
			        var progress = parseInt(data.loaded/data.total * 100, 10);
			        $('#progress .bar').css('width', progress + '%');
			    }
			});
		});
	</script>
</body>
</html>