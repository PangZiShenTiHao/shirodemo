<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>list</title>
</head>
<body>
     <h1>list page</h1>

     欢迎：<shiro:principal></shiro:principal>
     <shiro:hasRole name="admin">
         <br><br>
         <a href="admin.jsp"> admin </a>

     </shiro:hasRole>

     <shiro:hasRole name="user">
         <br><br>
         <a href="user.jsp"> user </a>
     </shiro:hasRole>
     <br><br>

     <a href="shiro/testShiroAnnotation">Test ShiroAnnotation</a>
     <br><br>
     <a href="shiro/logout"> 登出 </a>

</body>
</html>
