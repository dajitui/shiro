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
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
  </head>
  
  <body>
  
  <form action="http://localhost:8080/shiro/u/denglu">
  	<input name="username"/>
  	<input name="password"/>
  	<button  type="submit">登录</button>
  </form>
  
    <button onclick="a()">登录</button>
欢迎登录
  </body>
  <script type="text/javascript">
  	function a(){
  		$.ajax({
  			url:"http://localhost:8080/shiro/u/denglu",
  			type:"get",
  			async : false,
  			data: {
            username:"ys",
            password:"123456"
        },
  			success:function(result){
  				window.location.href=result;
  			},
  			error:function(e){
  			alert("登录失败");
  			}
  		});
  	}
  </script>
  
</html>
