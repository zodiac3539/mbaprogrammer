package grechoi.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import usertest.BulletinDAO;
import usertest.BulletinVO;

public class BulletingListServlet extends HttpServlet {
	 
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
		HttpSession session = request.getSession();
		
		String userid = (String) session.getAttribute("userid");
		//String cat = request.getParameter("categoryseq");
		//if(cat == null) cat = "1";
		
		String page_str = request.getParameter("page");
		if(page_str == null) page_str = "1";
		
		int page = Integer.parseInt(page_str);
				
		BulletinDAO dao = new BulletinDAO();
		int total_cnt = dao.getTotalCount();
		List<BulletinVO> list = dao.getBulletinList(page);
		
		request.setAttribute("list", list);
		request.setAttribute("total_cnt", total_cnt);
		request.setAttribute("page", page);
		
		String nextJSP = "/bulletin/list.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}
}
