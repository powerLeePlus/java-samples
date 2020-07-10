<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=utf-8"%>
<% request.setCharacterEncoding("utf-8");%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<h1>shiro 系统主页</h1>
<h5>当前用户：${currUser}</h5>
<a href="${pageContext.request.contextPath}/user/logout">退出登录</a>
<ul>
    <li><a href="/users.jsp">用户列表-all</a></li>
    <li><a href="/user/list">用户列表-admin</a></li>
    <li>商品管理</li>
    <%-- 授权方式3：标签 --%>
    <shiro:hasAnyRoles name="user,admin">
        <li>订单管理(只有user，admin才能看到)</li>
        <li>物流管理(只有user，admin才能看到)</li>
    </shiro:hasAnyRoles>
    <shiro:hasRole name="admin">
        <li>系统信息(只有admin才能看到)</li>
    </shiro:hasRole>
</ul>
</body>
</html>