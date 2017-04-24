package grechoi.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONML;

import usertest.CategoryVO;
import usertest.MemorizeDAO;
import usertest.MemorizeVO;

public class UpdateMemServlet extends HttpServlet{
	 
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
		request.setCharacterEncoding( "UTF-8" );
		HttpSession session = request.getSession();
		
		MemorizeDAO memorize = new MemorizeDAO();

		long categoryseq = (long) session.getAttribute("categoryseq");
		String userid = (String) session.getAttribute("userid");
		
		if(command.equals("insert")) {
			MemorizeVO vo = new MemorizeVO();
			
			vo.setQuestion(request.getParameter("question"));
			vo.setAnswer(request.getParameter("answer"));
			vo.setUserid(userid);
			vo.setCategoryseq(categoryseq);

			memorize.insertMemorize(vo);
			response.sendRedirect("./ListMemServlet?categoryseq=" + categoryseq);
		} else if(command.equals("cleanup")) {
			//Update
			memorize.cleanUpOldCategory(categoryseq);
			
			response.sendRedirect("./ListMemServlet?categoryseq=" + categoryseq + "&msg=cleanup");				

		} else if(command.equals("update")) {
			//Update
			String seq_str = request.getParameter("seq");
			long seq = Long.parseLong(seq_str);
			String answer = request.getParameter("answer");
			String question = request.getParameter("question");
			MemorizeVO vo = new MemorizeVO();
			
			vo.setSeq(seq);
			vo.setCategoryseq(categoryseq);
			vo.setQuestion(question);
			vo.setAnswer(answer);
			memorize.updateMemorize(vo);
			//memorize.deleteMemorize(seq);
			String page = request.getParameter("page");
			response.sendRedirect("./ListMemServlet?categoryseq=" + categoryseq + "&page=" + page + "&msg=updated");
			
		} else if(command.equals("getdata")) {
			//Update
			//System.out.println("getdata has been invoked.");
			String seq_str = request.getParameter("seq");
			long seq = Long.parseLong(seq_str);
			MemorizeVO memorizeVO = memorize.getDataBySeq(seq);
			
			if( !memorizeVO.getUserid().equals(userid) ) {
				response.setContentType("text/json");
				// Actual logic goes here.
				PrintWriter out = response.getWriter();
				out.println("I warned you not to hack my system... This attemp will be logged. Wait for prosecution.");
				return;
			}
			
			StringBuffer strb = new StringBuffer();
			strb.append("{ \"results\" : { ");
			strb.append(" \"seq\" : " + memorizeVO.getSeq() + ", ");
			
			String question = memorizeVO.getQuestion();
			question = StringEscapeUtils.escapeJson(question);
			String answer = memorizeVO.getAnswer();
			answer = StringEscapeUtils.escapeJson(answer);
			
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Type", "application/json; charset=UTF-8");
			strb.append(" \"question\" : \"" + question + "\", ");			
			strb.append(" \"answer\" : \"" + answer + "\"  ");			
			strb.append(" } }");
			
			PrintWriter out = response.getWriter();
			out.println(strb.toString());	
			System.out.println(strb.toString());
			
		} else if(command.equals("delete")) {
			String seq_str = request.getParameter("seq");
			long seq = Long.parseLong(seq_str);
			System.out.println("Before Delete " + seq);
			memorize.deleteMemorize(seq);
			response.sendRedirect("./ListMemServlet?categoryseq=" + categoryseq);

		} else if(command.equals("like")) {
			System.out.println("Like has been invoked.");
			String seq_str = request.getParameter("seq");
			long seq = Long.parseLong(seq_str);
			String like = request.getParameter("like");
			MemorizeVO memorizeVO = null;
			if (like.equals("1")) {
				memorizeVO = memorize.plusOneCorrect(seq);
			} else {
				memorizeVO = memorize.plusOneWrong(seq);
			}
			
			StringBuffer strb = new StringBuffer();
			strb.append("{ \"results\" : { ");
			strb.append(" \"correct\" : " + memorizeVO.getCorrect() + ", ");
			strb.append(" \"wrong\" : " + memorizeVO.getWrong() + "");			
			strb.append(" } }");
			
			System.out.println(strb.toString());
			response.setContentType("application/json");
			// Actual logic goes here.
			PrintWriter out = response.getWriter();
			out.println(strb.toString());	
		}
		
	}
	
	 private String convertToWhatever(String value) {
	        if(value.indexOf("&#")==-1)
	            return value;

	        String newString ="";
	        value = value.replaceAll("&#","");
	        String[] characters = value.split(";");
	        for(int i=0; i<characters.length; i++){
	            if(characters[i].startsWith(" "))
	                newString +=" ";

	            if(characters[i].trim().length()!=4)
	                newString += characters[i].trim();
	            else
	                newString +=(char)Integer.valueOf(characters[i].trim()).intValue()+"";
	        }

	        return newString;
	    }
}