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
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  //Customized Script in here
  function save() {
	  var bcategory = document.store.bcategory.value;
	  var subject = document.store.subject.value;
	  var content = document.store.content.value;
  
      if(bcategory == '') {
    	  alert('Category is empty!');
    	  return;
      }
      
      if(subject == '') {
    	  alert('Subject is empty!');
    	  return;
      }
      
      if(content == '') {
    	  alert('Content is empty!');
    	  return;
      }
      
      document.store.submit();

  }
  
  function wordcounts() {
	  var obj = document.store.content.value;
	  if(obj == null) {
		  return;
	  }
	  
	  document.store.wordcount.value = obj.length;
  }
  </script>
</head>
  <body>
  <div id="menu"></div><BR>
  <div id="body">
<%
String userid = (String) session.getAttribute("userid");
if(userid == null) {
	out.println("You need to log in again </div></body></html>");
	return;
}
String username = (String) session.getAttribute("username");
out.println("Welcome " + username);
out.println("&nbsp;&nbsp;");
%>
<BR><BR>
<div id="insert"><B>[Update existing data]</B></div><BR>
<form name="store" action="./UpdateBulServlet">
    <input type="hidden" name="command" value="update"/>
    <input type="hidden" name="categoryseq" value="<%=vo.getCategoryseq() %>"/>
    
    <table border="0" cellspacing="1" cellpadding="2" bgcolor="#000000">
<tr>
    <td bgcolor="#FFFFFF">
    Bulletin Category: <BR/>
    <input type="text" name="bcategory" value="<%=vo.getBcategory() %>"/>
    </td>
</tr>
<tr>
    <td bgcolor="#FFFFFF">
    Subject: <BR/>
    <textarea cols="80" rows="3" name="subject"><%=vo.getSubject() %></textarea>
    </td>
</tr>
<tr>
    <td bgcolor="#FFFFFF">
    Content: <BR/>
    <textarea cols="80" rows="15" name="content" onkeyup="javascript:wordcounts()"><%=vo.getContent() %></textarea>
    </td>
</tr>
<tr>
    <td bgcolor="#FFFFFF">
	<input type="text" name="wordcount" value="0"/>
    </td>
</tr>
</table>
<BR/><BR/>
<input type="button" value="Save" onclick="save()"/>
</form>
<BR><BR>
	<script type="text/javascript" language="javascript">  
    var versionUpdate = (new Date()).getTime();  
    var script = document.createElement("script");  
    script.type = "text/javascript";  
    script.src = "../js/tools.js?v=" + versionUpdate;  
    document.body.appendChild(script);  
	</script> 
  </div>
</body>
</html>