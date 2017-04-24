package grechoi.servlet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import usertest.UsermgmtDAO;
import usertest.UsermgmtVO;


public class SignupServlet extends HttpServlet {
	 
	  /**
	 * 
	 */
	private static final long serialVersionUID = -5956276989259780818L;
	private String USER_AGENT = "CHROME";

	public void init() throws ServletException {
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doServlet(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doServlet(request, response);
	}

	public void doServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsermgmtDAO usermgmtDAO = new UsermgmtDAO();
		
		String command = request.getParameter("command");
		if(command == null) command = "";
		
		if(command.equals("insert")) {
			//https://www.google.com/recaptcha/api/siteverify
			try {				
				String userid = request.getParameter("userid");
				String userpw = request.getParameter("userpw");
				String username = request.getParameter("username");
				
				UsermgmtVO vo = new UsermgmtVO();
				vo.setUserid(userid);
				vo.setUserpw(userpw);
				vo.setUsername(username);
				
				usermgmtDAO.createNewUser(vo);
				response.sendRedirect("login.jsp?msg=signupcomplete");
				//UserID
			} catch(Exception ex) {
				System.out.println("Error while connecting Google Captcha.");
				ex.printStackTrace();
			}			
		} else if (command.equals("checkid")) {
			//Duplicate ID check
			String userid = request.getParameter("userid");
			if(userid == null) return;
			String input = request.getParameter("g-recaptcha-response");
			String ret = "";
			try {
				ret = sendPost(input);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			
			if(ret == null) return;
			
			JSONObject json = new JSONObject(ret);
			
			boolean bot_success = (boolean) json.get("success");
			
			boolean id_success = false;
			UsermgmtVO usermgmtVO = usermgmtDAO.getUserwithID(userid);
			if(usermgmtVO.getUserid().equals("No ID")) id_success = true;
			
			StringBuffer strb = new StringBuffer();
			strb.append("{ \"results\" : { ");
			strb.append(" \"id_success\" : " + id_success + ", ");
			strb.append(" \"bot_success\" : " + bot_success + "");			
			strb.append(" } }");
			
			System.out.println(strb.toString());
			response.setContentType("text/json");
			// Actual logic goes here.
			PrintWriter out = response.getWriter();
			out.println(strb.toString());	
			
		}
		
	}

	
	private String sendPost(String responsive) throws Exception {

		String url = "https://www.google.com/recaptcha/api/siteverify";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "secret=6LdkZR0UAAAAAJgP64TiwKc93ogw_UOy7OOyRLLn&response=" + responsive;

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		if(responseCode != 200) {
			System.out.println("There's problem with the connection to Google Captcha!");
			return null;
		}
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);
		//System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
		//print result

	}
}

