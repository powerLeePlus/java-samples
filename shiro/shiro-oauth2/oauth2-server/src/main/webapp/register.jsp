<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/7/12 0012
  Time: 下午 12:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
</head>
<body>
<h1>用户注册</h1>
<form action="${pageContext.request.contextPath}/user/register" method="post">
    用户名:<input type="text" name="username" > <br/>
    密码  : <input type="text" name="password"> <br>
    <input type="submit" value="立即注册">
</form>
</body>
</html>
