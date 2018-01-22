package grechoi.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

public class FileListServlet  extends HttpServlet{
	 
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
		ServletContext servletContext = this.getServletConfig().getServletContext();
		
		String uploaddir = servletContext.getRealPath("/")
				+ File.separator + "upload"
				+ File.separator;
		
		File directory = new File(uploaddir);
		
		File[] files = directory.listFiles();

		Arrays.sort(files, new Comparator<File>() {
		    public int compare(File f1, File f2) {
		        return Long.compare(f2.lastModified(), f1.lastModified());
		    }
		});
		
		String filelist = "";
		//answer = StringEscapeUtils.escapeJson(answer);
		
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "application/json; charset=UTF-8");
		StringBuffer strb = new StringBuffer();
		strb.append(" { \"result\" : [ { \"filename\" : \"NULL\"");
		
		for(File f : files) {
			if(f.isFile()) {
				strb.append("}, { \"filename\" : ");
				strb.append( "\"" + f.getName() + "\" ");
			}
		}
		strb.append("} ] }");
		PrintWriter out = response.getWriter();
		out.println(strb.toString());
		out.flush();
		out.close();
		//System.out.println(strb.toString());
		
	}

}
