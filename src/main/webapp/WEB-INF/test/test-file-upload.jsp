<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>jQuery File Upload Example</title>
</head>
<body>
	<input id="fileupload" type="file" name="files[]" data-url="${ctx }/test/upload/server" multiple />
	<script src="${ctx }/public/bootstrap3/js/jquery-1.11.1.min.js"></script>
	<script src="${ctx }/public/bootstrap3/js/vendor/jquery.ui.widget.js"></script>
	<script src="${ctx }/public/bootstrap3/js/jquery.iframe-transport.js"></script>
	<script src="${ctx }/public/bootstrap3/js/jquery.fileupload.js"></script>
	<script>
		$(function() {
			$('#fileupload').fileupload({
				dataType : 'json',
				done : function(e, data) {
					console.log(data);
					$.each(data.result.files, function(index, file) {
						$('<p/>').text(file.name).appendTo(document.body);
					});
				}
			});
		});
	</script>
</body>
</html>