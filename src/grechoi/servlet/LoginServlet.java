package grechoi.servlet;

// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import usertest.UsermgmtDAO;
import usertest.UsermgmtVO;

public class LoginServlet extends HttpServlet{
	 
	  /**
	 * 
	 */
	private static final long serialVersionUID = -5956276989259780818L;
	private String message;

	public void init() throws ServletException {
		message="test";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doServlet(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doServlet(request, response);
	}

	public void doServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");
		if(userpw == null) userpw = "";
		
		UsermgmtDAO usermgmt = new UsermgmtDAO();
		UsermgmtVO userVO = usermgmt.getUserwithID(userid);
		
		if( userpw.equals(userVO.getUserpw()) ) {
			HttpSession session = request.getSession();
			session.setAttribute("userid", userid);
			session.setAttribute("userlevel", userVO.getUserlevel());
			session.setAttribute("username", userVO.getUsername());
			
			response.sendRedirect("ListCategoryServlet");
		} else {
			// Set response content type
			response.setContentType("text/html");
			// Actual logic goes here.
			PrintWriter out = response.getWriter();
			out.println("<h1>Wrong ID and password</h1>");			
		}
		
	}
	
	public void destroy()
	{
	      // do nothing.
	}
}