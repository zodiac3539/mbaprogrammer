package usertest;

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


    
}
