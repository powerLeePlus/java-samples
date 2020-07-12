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

    <shiro:authenticated>
        <p>认证之后（登录）展示内容</p>

        <h2>当前用户：<shiro:principal property="username"/> <%--相当于((User)Subject.getPrincipals()).getUsername()--%>
        </h2>


        <p><a href="${pageContext.request.contextPath}/user/logout">退出登录</a></p>

        <ul>
                <%-- 授权方式3：标签 --%>
            <shiro:hasAnyRoles name="user,admin">
                <li><a href="#">用户管理(admin,user)</a>
                    <ul>
                        <li><a href="/users.jsp">用户列表(user)</a></li>
                        <li><a href="/user/list">用户列表(admin)</a></li>
                        <shiro:hasPermission name="user:add:*">
                            <li><a href="#">添加</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:delete:*">
                            <li><a href="#">删除</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:update:*">
                            <li><a href="#">修改</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:find:*">
                            <li><a href="#">查询</a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasAnyRoles>
            <shiro:hasRole name="admin">
                <li><a href="#">商品管理(admin)</a></li>
                <shiro:hasPermission name="order:find:*">
                    <li><a href="#">订单管理(admin)</a></li>
                </shiro:hasPermission>
                <li><a href="#">物流管理(admin)</a></li>
            </shiro:hasRole>
        </ul>

    </shiro:authenticated>
    <shiro:notAuthenticated>
        <p>无需认证（无需登录）展示内容</p>

        <p><a href="${pageContext.request.contextPath}/register.jsp">注册</a></p>

        <p><a href="${pageContext.request.contextPath}/login.jsp">登录</a></p>
    </shiro:notAuthenticated>


</body>
</html>