<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="description" content="${seoSession.description}">
    <meta name="keywords" content="${seoSession.keywords}">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>${seoSession.title}</title>
    <link rel="shortcut icon" type="image/ico" href="${ctx}/public/bootstrap3/images/icon.png"/>
    <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/public/bootstrap3/css/bootstrap.min.css"  />
    <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/public/bootstrap3/css/datepicker3.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/public/bootstrap3/css/bootstrap-select.min.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/public/bootstrap3/css/skins/all.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/public/bootstrap3/css/ladda-themeless.min.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/public/bootstrap3/css/bootstrap-nav-wizard.css?ver=20149131504" />
    <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/public/bootstrap3/css/customer.css?ver=20149131504"/>
    <link rel="apple-touch-icon" href="${ctx}/public/bootstrap3/images/apple-touch-icon-iphone.png" />
	<link rel="apple-touch-icon" sizes="57x57" href="${ctx}/public/bootstrap3/images/apple-touch-icon-small.png" />
	<link rel="apple-touch-icon" sizes="72x72" href="${ctx}/public/bootstrap3/images/apple-touch-icon-middle.png" />
	<link rel="apple-touch-icon" sizes="114x114" href="${ctx}/public/bootstrap3/images/apple-touch-icon-large.png" />
	<link rel="apple-touch-icon" sizes="144x144" href="${ctx}/public/bootstrap3/images/apple-touch-icon-large.png" />
    <!--[if lt IE 9]>
   	<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<jsp:include page="nav.jsp" />