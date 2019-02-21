<%
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Cache-Control", "no-store");
    response.addHeader("Cache-Control", "must-revalidate");
%>