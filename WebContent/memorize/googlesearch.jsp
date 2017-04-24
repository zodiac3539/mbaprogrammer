<%@page import="java.net.URLEncoder" %>
<%
request.setCharacterEncoding("UTF-8");
String searchword = request.getParameter("searchword");
if(searchword == null) searchword = "itheeng";
searchword = URLEncoder.encode(searchword, "UTF-8");
out.println(searchword);

%>

<HTML>
<BODY>
<script>
//0ahUKEwigtv_Z1KTTAhUo6YMKHR6AAT4Q_AUIBigB
location.href="https://www.google.com/search?q=<%=searchword %>&source=lnms&tbm=isch&sa=X&ved=0ahUKEwjvyqat2KTTAhWC64MKHXx4DxoQ_AUIBigB&biw=1280&bih=918";
//                                                                                          ved=0ahUKEwjvyqat2KTTAhWC64MKHXx4DxoQ_AUIBigB&biw=1280&bih=918
</script>
</BODY>
</HTML>
