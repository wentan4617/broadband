<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>CyberPark</title>
    <link rel="shortcut icon" type="image/ico"  href="${ctx}/public/bootstrap3/images/icon.png"/>
    <link href="${ctx}/public/bootstrap3/css/bootstrap.min.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="${ctx}/public/bootstrap3/css/customer.css" rel="stylesheet" type="text/css"  />
    <link href="${ctx}/public/bootstrap3/css/datepicker.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/public/bootstrap3/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/public/bootstrap3/css/skins/all.css" rel="stylesheet" type="text/css" />
    <link>
    <!--[if lt IE 9]>
	<script src="${ctx}/public/bootstrap3/js/html5shiv.js"></script>
	<script src="${ctx}/public/bootstrap3/js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<jsp:include page="nav.jsp" />