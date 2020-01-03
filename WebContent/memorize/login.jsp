<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String userid = (String) session.getAttribute("userid");
if(userid != null) {
%>
<HTML>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<body>
<script>
location.href='./ListCategoryServlet';
</script>
</body>
</HTML>
<%	
	return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>MBA Programmer - Gregory Choi</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
function login() {
	document.itheeng.submit();
}
</script>
</head>
<body>
  <div id="menu"></div><BR>
  <div id="body">
<BR/>
<form name="itheeng" action="./LoginServlet">
Login to Flash Card system<BR>
<%
String msg = request.getParameter("msg");
if(msg != null) {
	if(msg.equals("signupcomplete")) out.println("<font color=green>Sign up has been completed</font>");
}
%>
<BR>
<Table border="1" cellpadding="2" cellspacing="1" bgcolor="gray" width="400">
<TR>
<TD bgcolor=#FFFFFF>User ID</TD>
<TD bgcolor=#FFFFFF><Input type="text" name="userid"></TD>
</TR>
<TR>
<TD bgcolor=#FFFFFF>User Password</TD>
<TD bgcolor=#FFFFFF><Input type="password" name="userpw"></TD>
</TR>
</Table>
<BR>
<BR>
<input type="button" onclick="login();" value="Login">
<BR><BR>
<a href="./signup.jsp">Haven't you signed up yet? Here. Create your own account</a>

</form>

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