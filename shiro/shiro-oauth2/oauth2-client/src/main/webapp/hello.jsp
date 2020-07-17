<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
Hi JSP. 现在时间是  ${now} <br>

<p>有admin权限才能看到里面内容：<shiro:hasRole name="admin"><span>admin私密空间</span></shiro:hasRole>
</p>
<p>只要有user权限就能看到里面内容：<shiro:hasAnyRoles name="user,admin"><span>admin，user私密空间</span></shiro:hasAnyRoles>
</p>