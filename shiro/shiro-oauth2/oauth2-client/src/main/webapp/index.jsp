<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<% request.setCharacterEncoding("utf-8");%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
    <h1>系统主页V1.0</h1>

    <p><a href="/hello">点我需要登录哦</a>
        <span style="color: red">${msg}</span>
        <shiro:authenticated>
            <span style="color: green">现在可以点我进去了</span>
        </shiro:authenticated>
    </p>

    <p>模拟通过oauth2向第三方服务请求资源</p>

    <p><a href="${pageContext.request.contextPath}/oauth-client/getCode">去 oauth2 server端授权</a></p>


</body>
</html>