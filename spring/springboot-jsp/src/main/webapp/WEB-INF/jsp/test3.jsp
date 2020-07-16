<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
这是演示springmvc `return redirect:String`  <br>
Hi JSP. 现在时间是（Model拿不到）&emsp;  ${now} <br>
Hi JSP. 现在时间是（url params能拿到）&emsp;
<%
    String now=(String)request.getParameter("now");
    out.print(now);
%>