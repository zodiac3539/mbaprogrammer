package usertest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class BulletinVO {
    //categoryseq BIGINT primary key,
    //bcategory VARCHAR(255),
    //subject VARCHAR(255),
    //content NVARCHAR(4000),
    //userid VARCHAR(200)

    private long categoryseq;
	private String bcategory;
    private String subject;
    private String content;
    private String userid;
    private String whenwritten;

	public String getWhen() {
		String ret = "";
		long dates = Long.parseLong(this.whenwritten);
		Calendar now = new GregorianCalendar();
		now.setTimeInMillis(dates);
		SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
		
		ret = format1.format(now.getTime()).toString();
		return ret;
	}

    public long getCategoryseq() {
		return categoryseq;
	}
	public void setCategoryseq(long categoryseq) {
		this.categoryseq = categoryseq;
	}

	public String getBcategory() {
		return bcategory;
	}
	public void setBcategory(String bcategory) {
		this.bcategory = bcategory;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getWhenwritten() {
		return whenwritten;
	}
	public void setWhenwritten(String whenwritten) {
		this.whenwritten = whenwritten;
	}
	
}
