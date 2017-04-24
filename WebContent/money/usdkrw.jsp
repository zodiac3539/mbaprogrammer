<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="usertest.ScrapDAO" %>
<%@ page import="usertest.ScrapVO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%
    ScrapDAO dao = new ScrapDAO();
	List<ScrapVO> ret = dao.getRecentFive();
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>MBA Programmer - Gregory Choi</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script>var base = "${pageContext.request.contextPath}";</script>
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);
      google.charts.setOnLoadCallback(drawChart2);

      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Time', 'USD-KRW'],
		  <%
		  double current = 0;
		  Iterator<ScrapVO> it = ret.iterator();
		  while(it.hasNext()) {
			  ScrapVO vo = it.next();
			  out.print("[");
			  out.print("'"+vo.getDtime()+"',");
			  out.print("" + vo.getUsdkrw());
			  if(it.hasNext()) {
				  out.println("],");  
			  } else {
				  current = vo.getUsdkrw();
				  out.println("]");
			  }
			  
		  }
		  %>
        ]);

        var options = {
          title: 'USD-KRW',
          curveType: 'function',
          legend: { position: 'bottom' }
        };

        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));
        

        chart.draw(data, options);
        

     }

      function drawChart2() {
          var data2 = google.visualization.arrayToDataTable([
            ['Time', 'USD-EUR'],
  		  <%
  		  double currenteur = 0;
  		  Iterator<ScrapVO> it2 = ret.iterator();
  		  while(it2.hasNext()) {
  			  ScrapVO vo = it2.next();
  			  out.print("[");
  			  out.print("'"+vo.getDtime()+"',");
  			  out.print("" + vo.getUsdeur());
  			  if(it2.hasNext()) {
  				  out.println("],");  
  			  } else {
  				  currenteur = vo.getUsdeur();
  				  out.println("]");
  			  }
  			  
  		  }
  		  %>
          ]);
          var options2 = {
                  title: 'USD-EUR',
                  curveType: 'function',
                  legend: { position: 'bottom' }
                };
          
          var chart2 = new google.visualization.LineChart(document.getElementById('curve_chart2'));
          chart2.draw(data2, options2);
      }
      
      $( function() {
	      $("#progressbar").progressbar({
		      value: 1});
      });

      var time = 0;
	  function progress() {
          time = time + 1;
		  temp = (time/30) * 100;
		  if(temp >= 100) {
			  location.reload();
		  }
		  $("#progressbar").progressbar({
		      value: temp});
		  setTimeout("progress()", 1000);
      }

      setTimeout("progress()", 1000);
    </script>
</head>
  <body>
  <div id="menu"></div><BR>
  <div id="body">
    <div id="output" style="font-size: 20pt;">Current USD-KRW : <%=current%></div><BR>
    <BR>
    <div id="curve_chart" style="width: 800px; height: 400px"></div><BR>
    <BR>
    <div id="output" style="font-size: 20pt;">Current USD-EUR : <%=currenteur%></div><BR>
    <BR>
    <div id="curve_chart2" style="width: 800px; height: 400px"></div><BR>
    <BR>
    <div id="progressbar" style="width: 900px;"></div>
    <BR>
  <script src="../js/tools.js"></script>
  </div>
  </body>
</html>