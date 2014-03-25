<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>Test JSON</title>
</head>
<body>
	<form id="testJsonForm" >
		id(Integer): <input type="text" name="id"/><br/>
		username(String): <input type="text" name="username"/><br/>
		price(Double): <input type="text" name="price"/><br/>
		date(Date): <input type="text" name="date"/><br/>
		age(int): <input type="text" name="age"/><br/>
		<button id="testjsonBtn" >Test</button>
	</form>
	
	<script type="text/javascript" src="${ctx}/public/bootstrap3/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript">
	(function($){
		
		$('#testjsonBtn').click(function(e){
			e.preventDefault();
			
			/*
			var data = {
				'login_name': $('input[name="login_name"]').val()
				, 'password': $('input[name="password"]').val()
			}; 
			*/
			
			var data = {
				id: $('input[name="id"]').val()
				, username: $('input[name="username"]').val()
				, price: $('input[name="price"]').val()
				, date: $('input[name="date"]').val() // spring默认接受的时间格式为yyyy/mm/dd,不能为'',其他格式报错400
				, age: $('input[name="age"]').val() //基本类型不能为''，不然springmvc参数转换错误400
			}; 
			
			console.log(data);
			
			$.post('${ctx}/test/json', data, function(user){
				console.log(user);
			}, 'json');
			
		});
	})(jQuery);
	
	</script>
</body>
</html>