package com.kosta.khn.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import com.kosta.khn.vo.BoardVo;

public interface BoardDao {
	public List<BoardVo> getBoardList(String keyField, String keyWord, int start, int end); 
	public BoardVo getBoard(int no); // 게시물 상세 조회
	public int insert(BoardVo vo);   // 게시물 등록
	public int delete(int no);       // 게시물 삭제
	public int update(BoardVo vo);   // 게시물 수정
	public void boardCnt(BoardVo vo); // 조회수 증가
//	public List<BoardVo> getSearch(String keyWord);
	public int getTotalCount(String keyField, String keyWord); //총 게시물 수
	public void replyBoard(BoardVo vo); // 게시물 답변
	public void replyUpBoard(int ref, int pos); // 게시물 답변에 위치값 증가
}

