<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="usertest.CategoryVO" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>MBA Programmer - Gregory Choi</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
    function delete_category(num, name) {
        res = confirm('Do you really want to delete ' + name + '?');
        if(res == true) {
            document.createcategory.command.value = 'delete';
            document.createcategory.categoryseq.value = num;            
        	document.createcategory.submit();
        	
        }
    }
    function save() {
        categoryname = document.createcategory.categoryname.value;
        if(categoryname.length == 0) {
            alert('Category Name cannot be null');
            return;
        }
        document.createcategory.submit();
    }
  </script>
</head>
  <body>
  <div id="menu"></div><BR>
  <div id="body">
  
<%
String username = (String) session.getAttribute("username");
out.println("Welcome " + username);
out.println("&nbsp;&nbsp;");
%>
<a href="./LogoutServlet">Log out</a>

<BR><BR>
Choose your Flash Card deck.<BR><BR>
<table border="0" bgcolor="gray" width="400" cellpadding="2" cellspacing="1">
<%
Iterator<CategoryVO> it = ((List<CategoryVO>)request.getAttribute("list")).iterator();
while(it.hasNext()) {
	CategoryVO vo = it.next();
	String link = "./ListMemServlet?categoryseq=" + vo.getCategoryseq();
    out.println("<TR><TD bgcolor=#FFFFFF height=40>");
	out.println("<a href=\""+link+"\">" + vo.getCategoryname() + "</a>&nbsp;&nbsp;");
    out.println("</TD><TD bgcolor=#FFFFFF align=center>");
%>

<img src="../img/delete.png" width="15" height="15" onclick="delete_category('<%=vo.getCategoryseq() %>', '<%=vo.getCategoryname()  %>')"/>
</TD></TR>
<%
}
%>

</table>

<BR>
<BR>
<div id="create"><b>[Create New Category]</b></div><BR>
<form name="createcategory" action="./CreateCategoryServlet">
<input type="hidden" name="command" value="create"/>
<input type="hidden" name="categoryseq" value="0"/>
Category Name: <input type="text" name="categoryname" value=""/><BR>
<a href="javascript:save();"/><img src="../img/create.png" width="30" height="30" border="0"/></a>
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