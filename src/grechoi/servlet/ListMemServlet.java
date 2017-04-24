package grechoi.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import usertest.MemorizeDAO;
import usertest.MemorizeVO;

public class ListMemServlet extends HttpServlet{
	 
	  /**
	 * 
	 */
	private static final long serialVersionUID = -5956276989259780818L;

	public void init() throws ServletException {
		//message="test";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doServlet(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doServlet(request, response);
	}

	public void doServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String userid = (String) session.getAttribute("userid");
		String cat = request.getParameter("categoryseq");
		if(cat == null) cat = "1";
		
		String page_str = request.getParameter("page");
		if(page_str == null) page_str = "1";
		
		int page = Integer.parseInt(page_str);
		
		long categoryseq = Long.parseLong( request.getParameter("categoryseq") );		
		
		MemorizeDAO dao = new MemorizeDAO();
		int total_cnt = dao.getTotalCount(userid, categoryseq);
		List<MemorizeVO> list = dao.getMemorizeList(userid, categoryseq, page);
		
		request.setAttribute("list", list);
		session.setAttribute("categoryseq", categoryseq);
		request.setAttribute("total_cnt", total_cnt);
		request.setAttribute("page", page);
		
		String nextJSP = "/memorize/memorizelist.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}
}