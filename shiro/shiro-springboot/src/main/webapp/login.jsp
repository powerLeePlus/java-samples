<%@ page contentType="text/html;charset=utf-8"%>
<% request.setCharacterEncoding("utf-8");%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/user/login" method="post">
    用户名:<input type="text" name="username" > <br/>
    密码  : <input type="text" name="password"> <br>
    记住我: <input type="checkbox" name="rememberMe"> <br>
    <input type="submit" value="登录">
</form>

</body>
</html>