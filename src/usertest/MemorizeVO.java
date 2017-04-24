package usertest;

public class MemorizeVO {
	/*
    seq BIGINT primary key,
    categoryseq BIGINT,
    userid VARCHAR(200),
    question VARCHAR(255),
    answer NVARCHAR(4000),
    wheninserted BIGINT
    */
	
	private long seq;
	private long categoryseq;
	private String userid;
	private String question;
	private String answer;
	private long wheninserted;
	private int correct;
	private int wrong;
	
	
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public long getCategoryseq() {
		return categoryseq;
	}
	public void setCategoryseq(long categoryseq) {
		this.categoryseq = categoryseq;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public long getWheninserted() {
		return wheninserted;
	}
	public void setWheninserted(long wheninserted) {
		this.wheninserted = wheninserted;
	}
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int correct) {
		this.correct = correct;
	}
	public int getWrong() {
		return wrong;
	}
	public void setWrong(int wrong) {
		this.wrong = wrong;
	}
	
	
}
