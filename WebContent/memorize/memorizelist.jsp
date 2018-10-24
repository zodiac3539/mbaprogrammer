<%@page import="java.util.List" %>
<%@page import="java.util.Iterator" %>
<%@page import="usertest.MemorizeVO" %>
<%
    boolean isFirst = false;
    boolean isLast = false;
    
    int current_page = (Integer)request.getAttribute("page");
    int total_cnt = (Integer) request.getAttribute("total_cnt");
    long categoryseq = (Long)session.getAttribute("categoryseq");
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>MBA Programmer - Gregory Choi (Flash Card)</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <style>
  .outer {
      position: absolute;
      left: 100px;
      top: 150px;
      width: 1200px;
      height: 280px;
      margin: 20px auto;
      border: 2px solid gray;
      background-color: gray;
  }
  </style>
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
    function save() {
        var question = document.store.question.value;
        var answer = document.store.answer.value;

        if(question.length < 1) {
            alert('Question cannot be null');
            return;
        }

        if(answer.length < 1) {
            alert('Question cannot be null');
            return;
        }
		document.store.command.value="insert";
        document.store.submit();
    }

    function cleanup() {
        res = confirm('These flash cards will be deleted.\r\n- Older than one week\r\n-Correct > Wrong\r\nDo you want to proceed?');

        if( res == true ) {
    		document.store.command.value="cleanup";
            document.store.submit();
        }

    }

    function showanswer(num, seq) {
        var tempstr = '#ans_' + num;
        $(tempstr).slideDown();
        tempstr = '#memorize_' + seq;
        $(tempstr).slideDown();
    }

    function memorize(seq) {
        tempstr = '#memorize_' + seq;
        $(tempstr).fadeOut();

        $.getJSON( "./UpdateMemServlet", { command: "like", seq: seq, like: "1" } )
        .done(function( data ) {
            correct = parseInt(data.results.correct);
            wrong = parseInt(data.results.wrong);
        	ret = oxprint(correct, wrong);
        	var temp = '#oxoutput_' + seq;
        	$(temp).fadeOut();
        	$(temp).html(ret);
        	$(temp).fadeIn();
        });
        
        
    }

    function notmemorize(seq) {
    	tempstr = '#memorize_' + seq;
        $(tempstr).fadeOut();

        $.getJSON( "./UpdateMemServlet", { command: "like", seq: seq, like: "2" } )
        .done(function( data ) {
            correct = parseInt(data.results.correct);
            wrong = parseInt(data.results.wrong);
        	ret = oxprint(correct, wrong);
        	var temp = '#oxoutput_' + seq;
        	$(temp).fadeOut();
        	$(temp).html(ret);
        	$(temp).fadeIn();
        });
    }

    function oxprint(num1, num2) {
        ret = '';
    	if(num2 > 0) {
        	ret = ret + '<font color=red size=0.5pt>';
        	for(i=0;i<num2;i++) {
        		ret = ret + 'X';
            }
        	ret = ret + '</font>';
        }
        
        if(num1 > 0) {
        	ret = ret + '<font color=green size=0.5pt>';
        	for(i=0;i<num1;i++) {
        		ret = ret + 'O';
            }
        	ret = ret +  '</font>';
        }
        return ret;
        
    }

    function preupdate(seq) {
    	var doc = document.documentElement;
    	var left = (window.pageXOffset || doc.scrollLeft) - (doc.clientLeft || 0);
    	var top = (window.pageYOffset || doc.scrollTop)  - (doc.clientTop || 0);

    	top = top + 120;
    	top_str = top + 'px';
    	//console.log(top_str);
    	
        $.getJSON( "./UpdateMemServlet", { command: "getdata", seq: seq} )
        .done(function( data ) {
            console.log(data);
            
        	document.updateform.seq.value = data.results.seq;
        	var tmp1 = decodeHtml(data.results.question);
        	//console.log(tmp1);
        	document.updateform.question.value = tmp1;
        	var tmp2 = decodeHtml(data.results.answer);
        	console.log(tmp2);
        	
        	document.updateform.answer.value = tmp2;
        	
        });
        
        $('#updatepanel').css('top', top_str);
        $('#updatepanel').fadeIn();
    }

    function update() {
        var question = document.updateform.question.value;
        var answer = document.updateform.answer.value;

        if(question.length < 1) {
            alert('Question cannot be null');
            return;
        }

        if(answer.length < 1) {
            alert('Question cannot be null');
            return;
        }
		document.updateform.command.value="update";
        document.updateform.submit();
    }

    function cancel_update() {
    	$('#updatepanel').fadeOut();
    }


    function decodeHtml(html) {
        var txt = document.createElement("textarea");
    	txt.innerHTML = html;
    	return txt.value;
    	
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
<a href="./ListCategoryServlet"><img src="../img/backtothec.png"></a>
<a href="./LogoutServlet"><img src="../img/signout.png"></a>
<a href="javascript:cleanup();"><img src="../img/cleanup.png"/></a>
<BR><BR>
<div id="insert"><B>Insert new data</B></div><BR>
<form name="store" action="./UpdateMemServlet">
    <input type="hidden" name="command" value="insert"/>
    <input type="hidden" name="like" value="1"/>
    <input type="hidden" name="seq" value="0"/><BR/><BR/>
    <table border="0" cellspacing="1" cellpadding="2" bgcolor="#000000">
<tr>
    <td bgcolor="#FFFFFF">
    Question: <BR/>
    <textarea cols="120" rows="3" name="question"></textarea>
    </td>
</tr>
<tr>
    <td bgcolor="#FFFFFF">
    Answer: <BR/>
    <textarea cols="120" rows="6" name="answer"></textarea>
    </td>
</table>
<BR/><BR/>
<input type="button" value="Save" onclick="save()"/>
</form>
<BR><BR>

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
	out.println("<a id=prev1 href=\"./ListMemServlet?categoryseq="+categoryseq+"&page="+prevpg+"\"><img src=\"../img/previous.png\" width=25 heigh=25></a>&nbsp;&nbsp;");

}

if(!isLast) {
	out.println("<a id=next1 href=\"./ListMemServlet?categoryseq="+categoryseq+"&page="+nextpg+"\"><img src=\"../img/next.png\" width=25 heigh=25></a>");
}
out.println("<BR>Current Page: " + current_page);
%>
<BR><BR>
<B>[List of your flash cards]</B>
<%
Iterator<MemorizeVO> it = ((List<MemorizeVO>)request.getAttribute("list")).iterator();

if(!it.hasNext()) {
	out.println("Currently, there is no data.<BR/>");
}
int i=0;
while(it.hasNext()) {
	MemorizeVO vo = it.next();
%>
<BR>
<table border="0" cellspacing="1" cellpadding="2" bgcolor="gray" width="1200">
<tr>
    <td bgcolor="#FFFFFF">
    <font color="gray">Question:</font> &nbsp;&nbsp;&nbsp;<a id="show<%=i%>" style="color:green; cursor:pointer" onclick="showanswer('<%=i%>', '<%=vo.getSeq() %>')">[Show]</a><BR/>
    <%
    String question = vo.getQuestion();
    question = question.replaceAll("\r\n", "<BR>");
    if(vo.getCorrect() > vo.getWrong()) {
    	out.println("<font color=gray>" + question + "</font>");	
    } else {
    	out.println(question);
    }
	    
    %>
    <div id="oxoutput_<%=vo.getSeq() %>">
    <script>document.write(oxprint(<%=vo.getCorrect() %>, <%=vo.getWrong() %>));</script>
    </div>
    </td>
</tr>
<tr>
    <td bgcolor="#FFFFFF" >
    <div id="ans_<%=i%>" style="display:none;">
    <font color="gray">Answer:</font> &nbsp;&nbsp;&nbsp;<a id="del<%=i%>" style="color:red;">[X]</a>&nbsp;&nbsp;&nbsp;
    <a id="update<%=i %>">[Update]</a>    
    <BR/>
    <%
    String answer = vo.getAnswer();
    answer = answer.replaceAll("\r\n", "<BR>");
	out.println(answer);    
    %>
    </div><BR/>
    <div id="memorize_<%=vo.getSeq() %>" style="display:none">
    <a href="javascript:memorize('<%=vo.getSeq() %>')">[I memorize this]</a>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="javascript:notmemorize('<%=vo.getSeq() %>')">[I don't memorize this]</a>
    </div>
    <BR>
    <a href="./googlesearch.jsp?searchword=<%=vo.getQuestion() %>" target="_BLANK"><img src="../img/google.png" width="10%" height="10%" border="0"></a>
    </td>
</tr>
</table>
<BR/>
<script>
var str_l = "#del<%=i %>";
var show = "#show<%=i %>";
var upt = "#update<%=i %>";
$(show).mouseover (
     function() {
           $(this).effect("highlight", {color: 'yellow'}, 300);
     }
)
$(str_l).mouseover (
		function() {
			$(this).css('cursor', 'pointer');
			$(this).effect("highlight", {color: 'red'}, 500);
		}
)
$(str_l).click (
		function() {
			res = confirm('Do you really want to delete?');
			if(res == true) {
                document.store.command.value = 'delete';
                document.store.seq.value = '<%=vo.getSeq()%>';
                document.store.submit();
			}
		}
)
$(upt).mouseover (
		function() {
			$(this).css('cursor', 'pointer');
			$(this).effect("highlight", {color: 'green'}, 500);
		}
)
$(upt).click (
		function() {
            preupdate('<%=vo.getSeq() %>');
		}
)


</script>
<%
    i = i + 1;
}
%>
<BR>
<%
if(!isFirst) {
	out.println("<a href=\"./ListMemServlet?categoryseq="+categoryseq+"&page="+prevpg+"\"><img src=\"../img/previous.png\" width=25 heigh=25></a>&nbsp;&nbsp;&nbsp;");
}
if(!isLast) {
	out.println("<a href=\"./ListMemServlet?categoryseq="+categoryseq+"&page="+nextpg+"\"><img src=\"../img/next.png\" width=25 heigh=25></a>");
}
%>

<div id="updatepanel" class="outer" style="display: none;">
<B>[Update]</B><BR>
<form name="updateform" action="./UpdateMemServlet">
    <input type="hidden" name="command" value="update"/>
    <input type="hidden" name="like" value="1"/>
    <input type="hidden" name="page" value="<%=current_page %>"/>
    <input type="hidden" name="seq" value="0"/><BR/><BR/>
    <table border="0" cellspacing="1" cellpadding="2" bgcolor="#000000">
<tr>
    <td bgcolor="#FFFFFF">
    Question: <BR/>
    <textarea cols="80" rows="3" name="question"></textarea>
    </td>
</tr>
<tr>
    <td bgcolor="#FFFFFF">
    Answer: <BR/>
    <textarea cols="80" rows="3" name="answer"></textarea>
    </td>
</table>
<BR/><BR/>
<input type="button" value="Update" onclick="update()"/>&nbsp;&nbsp;
<input type="button" value="Cancel" onclick="cancel_update()"/>
</form>
</div>

  <script src="../js/tools.js"></script>
  </div>
  </body>
</html>