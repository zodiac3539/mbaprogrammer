<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="usertest.BulletinVO" %>
<%@page import="usertest.BulletinDAO" %>
<%
    String categoryseq_str = request.getParameter("categoryseq");
    if(categoryseq_str == null)
    {
    	out.println("Hiccup! Something is wrong");
    	return;
    }

    long categoryseq = Long.parseLong(categoryseq_str);
    BulletinDAO dao = new BulletinDAO();
    BulletinVO vo = dao.getDataBySeq(categoryseq);
    String bcategory = vo.getBcategory();
    String subject = vo.getSubject();
    String content = vo.getContent();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
  <title>MBA Programmer - Gregory Choi</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet"
      href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/styles/default.min.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js"></script>
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>hljs.initHighlightingOnLoad();</script>
  <script>
  //Customized Script in here

  function update() {
	  <%
	  out.println("var categoryseq = " + vo.getCategoryseq());
	  %>
	  location.href='./update.jsp?categoryseq=' + categoryseq;
  }
  </script>
</head>
  <body>
  <div id="menu"></div><BR>
  <div id="body">
<%
String userid = (String) session.getAttribute("userid");
if(userid == null)
{
    userid = "";
}
%>
<BR><BR>
<div id="insert"><B>[View bulletin board data]</B></div><BR>
<form name="store" action="./UpdateBulServlet">
    <input type="hidden" name="command" value="insert"/>
    <table border="0" cellspacing="1" cellpadding="2" bgcolor="#000000" width="900">
<tr>
    <td bgcolor="#F0F8FF">
    Number:
    </td>
</tr>
<tr>
    <td bgcolor ="#FFFFFF">
    <%=categoryseq %>
    </td>
</tr>
<tr>
    <td bgcolor="#F0F8FF">
    Bulletin Category:
    </td>
</tr>
<tr>
    <td bgcolor ="#FFFFFF">
    <%=bcategory %>
    </td>
</tr>
<tr>
    <td bgcolor="#F0F8FF">
    Subject:
    </td>
</tr>
<tr>
    <td bgcolor ="#FFFFFF">
	<%=subject %>
    </td>
</tr>
<tr>
    <td bgcolor="#F0F8FF">
    Content:
    </td>
</tr>
<tr>
    <td bgcolor ="#FFFFFF">
	<%=content %>
    </td>
</table>
<BR/><BR/>
<%
if (userid.equals("grechoi")) {
%>
<input type="button" value="Update" onclick="update()"/>
<% } %>
</form>
<BR><BR>
  <script src="../js/tools.js"></script>
  </div>
</body>
</html>