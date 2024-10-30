<%-- 
    Document   : notification
    Created on : Oct 28, 2024, 9:19:27 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (request.getAttribute("success") != null) {
%>
<script>
    Swal.fire({
        title: "Good job!",
        text: "<%=request.getAttribute("success") %>",
        icon: "success"
    });
</script>
<%
    }
%>

<%
    if (request.getAttribute("error") != null) {
%>
<script> 
    Swal.fire({
        title: "error!",
        text: "<%= request.getAttribute("Error") %>",
        icon: "error"
    });
</script>
<%
    }
%>