


function init() {
	
console.log('init!');
var bases = '..';

	    str = '';
	    str = str + '<Table width=100% bgcolor=#000099 class=mainmenu><tr><td>';
	    str = str + '<a id=\"menu01\" class=\"customlink\">Home</a>';
	    str = str + '&nbsp;&nbsp;&nbsp;&nbsp;';
	    str = str + '<a id=\"menu02\" class=\"customlink\">Foreign Exchange</a>';
	    str = str + '&nbsp;&nbsp;&nbsp;&nbsp;';
	    str = str + '<a id=\"menu03\" class=\"customlink\">Finance R Repository</a>';
	    str = str + '&nbsp;&nbsp;&nbsp;&nbsp;';
	    str = str + '<a id=\"menu04\" class=\"customlink\">Calculator</a>';
	    str = str + '&nbsp;&nbsp;&nbsp;&nbsp;';	    
	    str = str + '<a id=\"menu05\" class=\"customlink\">Code Archives</a>';
	    str = str + '&nbsp;&nbsp;&nbsp;&nbsp;';	    
	    str = str + '<a id=\"menu06\" class=\"customlink\">Flash Card</a>';
	    str = str + '</td></tr></table>';
	    
	    str = str + '<BR>';
	    str = str + '<script async src=\"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\"></script>';
	    str = str + '<ins class=\"adsbygoogle\"';
	    str = str + '    style=\"display:block\"';
	    str = str + '    data-ad-client=\"ca-pub-7620137999744720\"';
	    str = str + '    data-ad-slot=\"5778750897\"';
	    str = str + '    data-ad-format=\"auto\"></ins>';
	    str = str + '';
	    
	    $("#menu").html( str );
	    (adsbygoogle = window.adsbygoogle || []).push({});

	    var cssId = 'myCss';  // you could encode the css path itself to generate id..
	    if (!document.getElementById(cssId))
	    {
	        var head  = document.getElementsByTagName('head')[0];
	        var link  = document.createElement('link');
	        link.id   = cssId;
	        link.rel  = 'stylesheet';
	        link.type = 'text/css';
	        link.href = '../css/mycss.css';
	        link.media = 'all';
	        head.appendChild(link);
	    }
	    
	    
	    //Home
	    for(i=1;i<=6;i++) {
	    	var str_temp = '#menu0' + i;
	    	console.log(str_temp);
	    	
	    	$(str_temp).mouseover (
		    		function() {
		    			$(this).css('cursor', 'pointer');
		    			$(this).effect("highlight", {color: '#ffff66'}, 500);
		    		}
		    )

		    
	    	$(str_temp).click (
		    		function() {
		    		    var dict = {};
		    		    dict["#menu01"] = "../welcome/welcome.jsp";
		    		    dict["#menu02"] = "../money/usdkrw.jsp";

		    		    dict["#menu03"] = "../financer/index.html";
		    		    dict["#menu04"] = "../calculator/justcalculator.html";
		    		    dict["#menu05"] = "http://zodiac3539.cafe24.com/portfolio.html";
		    		    dict["#menu06"] = "../memorize/login.jsp";
		    		    
		    		    decision = $(this).attr('id');
		    			location.href = dict[ '#'+ decision];
		    		}
		    )		    
	    }	    	
	 
}
$(document).ready(function(){
   init();
});

