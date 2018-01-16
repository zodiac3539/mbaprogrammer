<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="usertest.BulletinVO" %>
<%
    boolean isFirst = false;
    boolean isLast = false;
    
    int current_page = (Integer)request.getAttribute("page");
    int total_cnt = (Integer) request.getAttribute("total_cnt");
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
  <title>MBA Programmer - Gregory Choi</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
      //Customized Script in here
  </script>
</head>
<body>
  <div id="menu"></div><BR>
  <div id="body">
  <%
//Paging
if(current_page == 1) {
	isFirst = true;
}

int total_page = (int)Math.ceil( (double)((double)total_cnt / (double)20) );
if(current_page == total_page) {
	isLast = true;
}
int nextpg = current_page + 1;
int prevpg = current_page - 1;

if(total_cnt == 0) {
	isFirst = true;
	isLast = true;
}

if(!isFirst) {
	out.println("<a id=prev1 href=\"./BulletinServlet?page="+prevpg+"\"><img src=\"../img/previous.png\" width=25 heigh=25></a>&nbsp;&nbsp;");

}

if(!isLast) {
	out.println("<a id=next1 href=\"./BulletinServlet?&page="+nextpg+"\"><img src=\"../img/next.png\" width=25 heigh=25></a>");
}
out.println("<BR>Current Page: " + current_page);
%>
<BR><BR>
<B>[List of your flash cards]</B>
<%
Iterator<BulletinVO> it = ((List<BulletinVO>)request.getAttribute("list")).iterator();

//if(!it.hasNext()) {
//	out.println("Currently, there is no data.<BR/>");
//}
//int i=0;
%>
<BR>
<table border="0" cellspacing="1" cellpadding="2" bgcolor="gray" width="600">

<tr>
    <td bgcolor="#FFFFFF">Num</td>
    <td bgcolor="#FFFFFF">Category</td>
    <td bgcolor="#FFFFFF">Subject</td>
    <td bgcolor="#FFFFFF">Writer</td>
</tr>
<!-- Iteration Start -->
<%
if(!it.hasNext()) {
	out.println("<tr><td bgcolor=#FFFFFF colspan=4>");
    out.println("Currently, there is no data.<BR/>");
    out.println("</td></tr>");
}
int i=0;
%>
<!-- Iteration Ends -->
</table>
  
  <BR><BR>
  <%
      String userid = (String) session.getAttribute("userid");
  	  if (userid == null) userid = "";
  	  
      if(userid.equals("grechoi")) {
   %>
          <BR>
          <a href = "./write.jsp">Write a post</a>
          <BR> 
   <%
      }
   %>
  <script src="../js/tools.js"></script>
  </div>

</body>
</html>