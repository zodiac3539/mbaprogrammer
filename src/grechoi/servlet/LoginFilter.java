package grechoi.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	private ServletContext context;
	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

	public void destroy() {
		//we can close resources here
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// pass the request along the filter chain
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		//String url = request.getServletPath();
		
		String uri = request.getRequestURI();
		
		if(!uri.contains("login.jsp") && !uri.contains("LoginServlet") && !uri.contains("signup.jsp") && !uri.contains("SignupServlet") && !uri.contains("sample")) {
			//String url = request.getContextPath();
			String url = "";
			url = url + "/memorize/login.jsp";
			
			HttpSession session = request.getSession(false);
			if (null == session) {
				response.sendRedirect( url );
			}
			
			String userid = (String)session.getAttribute("userid");
			if(userid == null) {
				response.sendRedirect( url );			
			}

		} else {
			//System.out.println("Loginpage is accessed...");
		}
		
		chain.doFilter(req, res);
		
	}

}
