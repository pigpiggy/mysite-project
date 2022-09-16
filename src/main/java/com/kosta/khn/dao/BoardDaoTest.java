package com.kosta.khn.dao;

import java.util.ArrayList;
import java.util.List;
import com.kosta.khn.vo.BoardVo;

public class BoardDaoTest {

	public static void main(String[] args) {
		BoardDao dao = new BoardDaoImpl();
		BoardVo vo = new BoardVo();
		int count = 0;
		//vo.setNo(0);
		//추가
		vo.setTitle("테스트2");
		vo.setContent("1234");
		vo.setUserNo(1);
		vo.setPos(0);
		vo.setRef(0);
		vo.setDepth(0);
		vo.setFilename("ff");
		vo.setFilesize((long) 20);
		vo.setFilename_("gg");
		vo.setFilesize_((long) 30);
	
		count = dao.insert(vo);
		System.out.println(count + "건 insert 완료");
	 
	  //삭제

	  count = dao.delete(39);
	  System.out.println(count + "건 delete 완료");
      
	  //수정	
		vo.setNo(55);
		vo.setTitle("김해나");
		vo.setContent("3333");
		count = dao.update(vo);
		System.out.println(count + "건 update 완료");
		
		dao.getBoardList("", "", 0, 10);
		System.out.println(dao.getBoardList("", "", 0, 10));
		
	  //검색

		List<BoardVo> list = new ArrayList<BoardVo>();
		list = dao.getBoardList("name", "정", 0, 5);
		System.out.println(list.toString());
	  
	
		
	}
	
}
