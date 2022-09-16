<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<% pageContext.setAttribute( "newLine", "\n" ); %>
<%@ page import="com.kosta.khn.dao.BoardDaoImpl"%>
<%@ page import="com.kosta.khn.dao.BoardDao"%>
<%@ page import="com.kosta.khn.vo.BoardVo"%>
<%--<%
	int no = Integer.parseInt(request.getParameter("no"));
	BoardDao dao = new BoardDaoImpl();
	BoardVo vo = dao.getBoard(no);
	String filename = vo.getFilename();
	Long filesize = vo.getFilesize();
	String filename_ = vo.getFilename_();
	Long filesize_ = vo.getFilesize_();
	String nowPage = (String)request.getAttribute("nowPage");
	session.setAttribute("vo", vo);
	System.out.println("vo:" + vo);
--%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
<title>Mysite</title>
</head>
<body>
	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${boardVo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(boardVo.content, newLine, "<br>")}
							</div>
						</td>
					</tr>
					<tr> 
     				<td align="center" bgcolor="#DDDDDD">첨부파일1</td>
     				<td bgcolor="" colspan="3">
     				<%--<% if( filename !=null && !filename.equals("")) {
                        <a href="/mysite/board?a=download&filename=${boardVo.filename }">${boardVo.filename }</a>
                     &nbsp;&nbsp;<font color="blue">(${boardVo.filesize }KBytes)</font>  
                        <%} else{%> 등록된 파일이 없습니다.<%}%>--%>
                    <c:choose>
                    	<c:when test ="${not empty boardVo.filename}">
                    	<a href="/mysite/board?a=download&filename=${boardVo.filename }">${boardVo.filename }</a>
                    	 &nbsp;&nbsp;<font color="blue">(${boardVo.filesize }KBytes)</font> 
                    	</c:when>
                    	<c:otherwise>
                    	<p> 등록된 파일이 없습니다. </p>
                    	</c:otherwise>
                    	</c:choose>
                    </td>
				    </tr>
					<tr> 
     				<td align="center" bgcolor="#DDDDDD">첨부파일2</td>
     				<td bgcolor="" colspan="3">
     				<%--<% if( filename_ !=null && !filename_.equals("")) {
                        <a href="/mysite/board?a=download&filename=${boardVo.filename_ }">${boardVo.filename_ }</a>
                     &nbsp;&nbsp;<font color="blue">(${boardVo.filesize_ }KBytes)</font>  
                        <%} else{%> 등록된 파일이 없습니다.<%}%>--%>
                         <c:choose>
                    	<c:when test ="${not empty boardVo.filename_}">
                    	<a href="/mysite/board?a=download&filename=${boardVo.filename_ }">${boardVo.filename_ }</a>
                    	 &nbsp;&nbsp;<font color="blue">(${boardVo.filesize_ }KBytes)</font> 
                    	</c:when>
                    	<c:otherwise>
                    	<p> 등록된 파일이 없습니다. </p>
                    	</c:otherwise>
                    	</c:choose>
                    </td>
     				</td>
   					</tr>
						
				</table>
				<div class="bottom">
					<a href="/mysite/board">글목록</a>
					
					<c:if test="${authUser.no != null }">
						<a href="/mysite/board?a=reply&nowPage=${param.nowPage }&ref=${boardVo.ref}&depth=${boardVo.depth}&pos=${boardVo.pos}&userNo=${boardVo.userNo}">답 변</a>
					</c:if>
					<c:if test="${authUser.no == boardVo.userNo }">
						<a href="/mysite/board?a=modifyform&no=${boardVo.no }&nowPage=${param.nowPage }&pass=${boardVo.pass}">글수정</a>
					</c:if>
				</div>
				
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
		
	</div><!-- /container -->
</body>
</html>		
		
