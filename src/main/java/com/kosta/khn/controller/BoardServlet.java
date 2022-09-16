package com.kosta.khn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kosta.khn.dao.BoardDao;
import com.kosta.khn.dao.BoardDaoImpl;
import com.kosta.khn.util.WebUtil;
import com.kosta.khn.vo.BoardVo;
import com.kosta.khn.vo.UserVo;



@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String actionName = request.getParameter("a");
		System.out.println("board:" + actionName);
		
		if ("list".equals(actionName)) {
			// 리스트 가져오기
			  int numPerPage = 7; // 페이지당 레코드 수 
			  int nowPage = 1; // 현재페이지
			  
			  int start = 0; //디비의 select 시작번호
			  int end = 10; //시작번호로 부터 가져올 select 갯수 (rownum서브쿼리?)
			  
			  //검색용
			String keyWord = "", keyField = "";
			List<BoardVo> list = null;
			if (request.getParameter("kwd") != null) {
				keyField = request.getParameter("keyField");
				keyWord = request.getParameter("kwd");
			}
			
			BoardDao dao = new BoardDaoImpl();
			
			System.out.println("keyField" + keyField);
			System.out.println("keyWord" + keyWord);

			//페이지 갯수 계산
			if (request.getParameter("nowPage") != null) {
				nowPage = Integer.parseInt(request.getParameter("nowPage"));
			}
			
			start = (nowPage * numPerPage)-numPerPage;
			end = numPerPage;
			
			list = dao.getBoardList(keyField, keyWord, start, end);

			System.out.println("검색결과:"+list.toString());

			// 리스트 화면에 보내기
			request.setAttribute("list", list);

			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");

		} else if ("read".equals(actionName)) {
			// 게시물 가져오기
		
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao dao = new BoardDaoImpl();
			BoardVo boardVo = dao.getBoard(no);
			
			String nowPage = request.getParameter("nowPage");
			System.out.println("readnowPage" + nowPage);
			request.setAttribute("nowPage", nowPage);
			
			boardVo = dao.getBoard(no);
			dao.boardCnt(boardVo);
			HttpSession session = request.getSession();
	        session.setAttribute("vo", boardVo);	

			System.out.println(boardVo.toString());

			// 게시물 화면에 보내기
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		} else if ("modifyform".equals(actionName)) {
			// 게시물 가져오기
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao dao = new BoardDaoImpl();
			BoardVo boardVo = dao.getBoard(no);

			// 게시물 화면에 보내기
			request.setAttribute("boardVo", boardVo);
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyform.jsp");
		} else if ("modify".equals(actionName)) {
			// 게시물 가져오기
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));

			BoardVo vo = new BoardVo(no, title, content);
			BoardDao dao = new BoardDaoImpl();

			dao.update(vo);

			WebUtil.redirect(request, response, "/mysite/board?a=list");
		} else if ("writeform".equals(actionName)) {
			// 로그인 여부체크
			UserVo authUser = getAuthUser(request);
			if (authUser != null) { // 로그인했으면 작성페이지로
				WebUtil.forward(request, response, "/WEB-INF/views/board/writeform.jsp");
			} else { // 로그인 안했으면 리스트로
				WebUtil.redirect(request, response, "/mysite/board?a=list");
			}

		} else if ("delete".equals(actionName)) {
			int no = Integer.parseInt(request.getParameter("no"));

			BoardDao dao = new BoardDaoImpl();
			dao.delete(no);

			WebUtil.redirect(request, response, "/mysite/board?a=list");

		} 
//		else if ("search".equals(actionName)) {
//			String keyWord = request.getParameter("kwd");
//			BoardDao dao = new BoardDaoImpl();
//			List<BoardVo> list = dao.getSearch(keyWord);
//
//			System.out.println(list.toString());
//
//			// 리스트 화면에 보내기
//			request.setAttribute("list", list);
//			// WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
//
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
//			rd.forward(request, response);
//		} 
		else if ("download".equals(actionName)) {
			request.setCharacterEncoding("UTF-8");

//			BoardDao dao = new BoardDaoImpl();
//			WebUtil.redirect(request, response, "/mysite/board?a=list");

			String fileName = request.getParameter("filename");
			
			// 서버에 올라간 경로를 가져옴
			ServletContext context = getServletContext();
			String uploadFilePath = "C:\\Users\\KOSTA\\eclipse-workspace\\mysite\\src\\main\\webapp\\WEB-INF\\views\\fileupload";
			String filePath = uploadFilePath + File.separator + fileName;
			
			System.out.println(" LOG [업로드된 파일 경로] :: " + uploadFilePath);
			System.out.println(" LOG [파일 전체 경로] :: " + filePath);
			
			byte[] b = new byte[4096];
			FileInputStream fileInputStream = new FileInputStream(filePath);
			
			String mimeType = getServletContext().getMimeType(filePath);
			if(mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			
	        // 파일명 UTF-8로 인코딩(한글일 경우를 대비)
	        String sEncoding = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + sEncoding);
	        
	        // 파일 쓰기 OutputStream
	        ServletOutputStream servletOutStream = response.getOutputStream();
	        
	        int read;
	        while((read = fileInputStream.read(b,0,b.length))!= -1){
	            servletOutStream.write(b,0,read);            
	        }
	        
	        servletOutStream.flush();
	        servletOutStream.close();
	        fileInputStream.close();
		}else if ("reply".equals(actionName)) {
			 
			 String nowPage = request.getParameter("nowPage");
	         request.setAttribute("nowPage", nowPage);
	         String ref = request.getParameter("ref");
	         request.setAttribute("ref", ref);
	         String pos = request.getParameter("pos");
	         request.setAttribute("pos", pos);
	         String depth = request.getParameter("depth");
	         request.setAttribute("depth", depth);
	         String userNo = request.getParameter("userNo");
	         request.setAttribute("userNo", userNo);
	         System.out.println("ref:"+ref);

			WebUtil.forward(request, response, "/WEB-INF/views/board/reply.jsp");
			
		}else if ("replywrite".equals(actionName)) {
			// 게시물 가져오기
			BoardVo vo = new BoardVo();
			BoardDao dao = new BoardDaoImpl();
			request.setAttribute("vo", vo);
			vo.setTitle(request.getParameter("title"));
			vo.setUserNo(Integer.parseInt(request.getParameter("userNo")));
			vo.setContent(request.getParameter("content"));
			vo.setRef(Integer.parseInt(request.getParameter("ref")));
			vo.setPos(Integer.parseInt(request.getParameter("pos")));
			vo.setDepth(Integer.parseInt(request.getParameter("depth")));
			String pass = request.getParameter("pass");
			vo.setPass(pass);
			System.out.println("test");
			dao.replyUpBoard(vo.getRef(), vo.getPos());
			dao.replyBoard(vo);
			
			WebUtil.redirect(request, response, "/mysite/board?a=list");

		}else {
			WebUtil.redirect(request, response, "/mysite/board?a=list");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);

	}

	// 로그인 되어 있는 정보를 가져온다.
	protected UserVo getAuthUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		return authUser;
	}

	
}
