<%-- 
    Document   : login
    Created on : Nov 11, 2024, 9:41:16 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="shared/header.jsp" />
<jsp:include page="shared/nav.jsp" />
<div>
    <form action="Login" method="POST">
        <h2>Login System</h2>
        <div>
            <label>User name</label>
            <input type="text" name="username" class="form-control" />
        </div>
        
        <div>
            <label>Password </label>
            <input type="password" name="password" required="" class="form-control">
        </div>
        
         <div class="mt-2">
            <button type="submit" class="btn btn-primary">Login</button>
        </div>
         <%
            if (request.getAttribute("error") != null) {
        %>
        <div class="text-danger mb-3">
            <%=request.getAttribute("error")%>
        </div>
        <%
            }
        %>
    </form>
</div>
<jsp:include page="shared/footer.jsp" />
