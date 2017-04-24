<html>
  <head>
    <title>MBA Programmer Signup</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type="text/javascript">
      var onloadCallback = function() {
        grecaptcha.render('html_element', {
          'sitekey' : '6LdkZR0UAAAAALMcQdQ5QxgNJxuOxnYkS5lJNX8N'
        });
      };

      function signup() {
          var userid = document.signform.userid.value;
          var userpw1 = document.signform.userpw.value;
          var userpw2 = document.signform.userpw2.value;
          var username = document.signform.username.value;

          var error1 = false;
		  if(userid.length < 1) error1 = true;
		  if(userpw1.length < 1) error1 = true;
		  if(userpw2.length < 1) error1 = true;
		  if(username.length < 1) error1 = true;

		  if(error1 == true) {
              alert('All Field Should be Filled!');
              return;
		  }

		  if(userpw1 != userpw2) {
              alert('Password should be consistent!');
              return;
	      }

		  var captcha = document.signform['g-recaptcha-response'].value;
	      $.getJSON( "./SignupServlet", { "command": "checkid", "userid": userid, "g-recaptcha-response": captcha } )
	        .done(function( data ) {
	            id_success = data.results.id_success;
	            bot_success = data.results.bot_success;

	            if(id_success == false) {
                     alert('Somebody uses that ID already. Please choose different ID');
                     return;
			    }

	            if(bot_success == false) {
                    alert('Please tick off I am not a robot check box');
                    return;
			    }
			    document.signform.command.value = 'insert';
	            document.signform.submit();
				
	        });
          
      }
    </script>
  </head>
  <body>
  <div id="menu"></div><BR>
  <div id="body">
<BR/>  
    <form action="./SignupServlet" name="signform" method="POST">
<input type="hidden" name="command" value="insert"/>
    <Table border="0" cellspacing="1" cellpadding="2" bgcolor="gray">
        <tr>
        <td bgcolor=#FFFFFF>User ID</td>
        <td bgcolor=#FFFFFF><input type="text" id="userid" name="userid"></td>
        </tr>
        <tr>
        <td bgcolor=#FFFFFF>User Password</td>
        <td bgcolor=#FFFFFF><input type="password" id="password1" name="userpw"></td>
        </tr>
        <tr>
        <td bgcolor=#FFFFFF>User Password Confirm</td>
        <td bgcolor=#FFFFFF><input type="password" id="password2" name="userpw2"></td>
        </tr>
        <tr>
        <td bgcolor=#FFFFFF>User Name</td>
        <td bgcolor=#FFFFFF><input type="text" name="username" id="username"></td>
        </tr>
        <tr>
        <td bgcolor=#FFFFFF>Captcha</td>
        <td bgcolor=#FFFFFF><div id="html_element"></div></td>
        </tr>
    </Table>
      <BR>
      Terms and Agreements<BR>
      <textarea cols="80" rows="8" readonly>
0. The user must be aware that he or she rents the database from www.mbaprogrammer.com at no cost.     
      
1. The administration of MBAprogrammer.com has full control on this web site. The user can voice their opinion, but the last decision is up to the administrator.

2. MBAprogrammer.com is NOT responsible for any breach or lost data. It's user's responsibility to back up the data.

3. MBAprogrammer.com can delete the user account at the administrator's own will. MBAprogrammer.com shall not be legally responsible for the deleted account.

4. Do not hack into the system not excessively use of the system. Those activities are subject to the prosecution. MBAprogrammer.com has a zero-tolerance policy for those incidents.

5. The administrator may adopt additional restrictions, such as limiting storing the number of questions to protect his own infrastructure.

6. The user shall follow additional instructions from the administrator. Otherwise, the user account may be deleted or IP address can be blocked.

7. The administration can limit the user activities, if possible. www.mbaprogrammer.com is not responsible for when the user experiences those limitations.
      </textarea><BR>
      
      <br>
      <input type="button" value="I agree with the terms above and I want to sign up" onclick="javascript:signup();">
    </form>
    <script src="https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit"
        async defer>
    </script>
    
  <script src="../js/tools.js"></script>
  </div>
  </body>
</html>