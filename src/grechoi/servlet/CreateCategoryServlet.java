package grechoi.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import usertest.CategoryVO;
import usertest.MemorizeDAO;

public class CreateCategoryServlet extends HttpServlet{
	 
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
		String command = request.getParameter("command");
		if(command == null) command = "";
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String userid = (String)session.getAttribute("userid");
		
		if(userid == null) return;
		
		MemorizeDAO memorize = new MemorizeDAO();
		
		if(command.equals("create")) {
			CategoryVO categoryVO = new CategoryVO();
			
			categoryVO.setCategoryname(request.getParameter("categoryname"));
			categoryVO.setUserid(userid);
			
			memorize.insertCategory(categoryVO);
			
			response.sendRedirect("ListCategoryServlet");
		} else if(command.equals("delete")) {
            long categoryseq = 0;
            String cseq = request.getParameter("categoryseq");
            categoryseq = Long.parseLong(cseq);
			
			memorize.deleteCategory(categoryseq);
			
			response.sendRedirect("ListCategoryServlet");
		}
	}
}
