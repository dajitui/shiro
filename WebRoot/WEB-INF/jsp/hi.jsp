<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
  </head>
  
  <body>
    <button onclick="a()">登录</button>
有权限访问
  </body>
  <script type="text/javascript">
  	function a(){
  		$.get({
  			url:"/u/login?username=ys&password=123456",
  			success:function (result){
  				window.location.href=result;
  			}
  		});
  	}
  </script>
</html>