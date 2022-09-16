package com.kosta.khn.vo;

public class BoardVo {

	private int no;
	private String title;
	private String content;
	private int hit;
	private String regDate;
	private int userNo;
	private String userName;
	private int pos;
	private int ref;
	private int depth;
	private String filename;
	private Long filesize;
	private String filename_;
	private Long filesize_;
	private String pass;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Long getFilesize() {
		return filesize;
	}
	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}
	
	public String getFilename_() {
		return filename_;
	}
	public void setFilename_(String filename_) {
		this.filename_ = filename_;
	}
	public Long getFilesize_() {
		return filesize_;
	}
	public void setFilesize_(Long filesize_) {
		this.filesize_ = filesize_;
	}
	
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo, String userName) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.userName = userName;
	}
	public BoardVo(int no, String title, int hit, String regDate, String userName) {
		this.no = no;
		this.title = title;
		this.hit = hit;
		this.regDate = regDate;
		this.userName = userName;
	}

	
	public BoardVo(int no, String title, int hit, String regDate, int userNo, String userName) {
		super();
		this.no = no;
		this.title = title;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.userName = userName;
	}
	public BoardVo(int no, String title, String content) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
	}
	public BoardVo(String title, String content, int userNo, String filename, Long filesize) {
		super();
		this.title = title;
		this.content = content;
		this.userNo = userNo;
		this.filename = filename;
		this.filesize = filesize;
		
	}
	
	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo, String userName, int pos,
			int ref, int depth, String filename, Long filesize) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.userName = userName;
		this.pos = pos;
		this.ref = ref;
		this.depth = depth;
		this.filename = filename;
		this.filesize = filesize;
	}
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", regDate="
				+ regDate + ", userNo=" + userNo + ", userName=" + userName + ", pos=" + pos + ", ref=" + ref
				+ ", depth=" + depth + ", fileName=" + filename + ", filesize=" + filesize + "]";
	}
	public BoardVo() {
		super();
	}
	public BoardVo(String title, String content, int userNo) {
		this.title = title;
		this.content = content;
		this.userNo = userNo;

	}
	public BoardVo(String title, String content, int userNo, String fileName, long fileSize) {
		this.title = title;
		this.content = content;
		this.userNo = userNo;
		this.filename = fileName;
		this.filesize = fileSize;
	
	}
	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo, String userName,
			String filename, Long filesize, String filename_, long filesize_, int ref, int pos, int depth, String pass) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		this.userName = userName;
		this.filename = filename;
		this.filesize = filesize;
		this.filename_ = filename_;
		this.filesize_ = filesize_;
		this.ref = ref;
		this.pos = pos;
		this.depth = depth;
		this.pass = pass;

	}
	
	public BoardVo(String title, String content, int userNo, String filename, long filesize, String filename_, long filesize_) {
		this.title = title;
		this.content = content;
		this.userNo = userNo;
		this.filename = filename;
		this.filesize = filesize;
		this.filename_ = filename_;
		this.filesize_ = filesize_;
	}
	
	
}
