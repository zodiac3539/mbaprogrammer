package grechoi.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import usertest.BulletinDAO;
import usertest.BulletinVO;

public class BulletinUpdateServlet extends HttpServlet {	 
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
		if(userid == null) userid = "";
		
		if(!userid.equals("grechoi")) {
			return;
		}
		String command = request.getParameter("command");
		if(command == null) command = "";
		
		BulletinDAO dao = new BulletinDAO();
		
		if(command.equals("insert")) {
			String bcategory = request.getParameter("bcategory");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
		
			BulletinVO vo = new BulletinVO();
			
			vo.setBcategory(bcategory);
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setUserid(userid);

			dao.insertBulletin(vo);
			response.sendRedirect("./BulletinServlet");

		} else if(command.equals("update")) {
			long categoryseq = Long.parseLong( request.getParameter("categoryseq") );
			String bcategory = request.getParameter("bcategory");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
		
			BulletinVO vo = new BulletinVO();

			vo.setCategoryseq( categoryseq );
			vo.setBcategory(bcategory);
			vo.setSubject(subject);
			vo.setContent(content);
			vo.setUserid(userid);

			dao.updateBulletin(vo);
			response.sendRedirect("./view.jsp?categoryseq=" + categoryseq);
		
		}
		//String cat = request.getParameter("categoryseq");
		//if(cat == null) cat = "1";
	}
}
