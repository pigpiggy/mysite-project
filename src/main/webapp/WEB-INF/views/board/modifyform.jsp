<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.kosta.khn.dao.BoardDaoImpl"%>
<%@ page import="com.kosta.khn.dao.BoardDao"%>
<%@ page import="com.kosta.khn.vo.BoardVo"%>
<%@ page import="com.kosta.khn.vo.UserVo"%>
<%@page import="java.util.*"%>

<%--<%   
//수정했을 때 현재 페이지
int nowPage = Integer.parseInt(request.getParameter("nowPage"));
String pass = request.getParameter("pass"); //데이터베이스에 입력되는 pass
System.out.println("pass:" + pass);
--%>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
<title>Mysite</title>
</head>
<script>
   function check(){
      var password = document.getElementById("pass"); //사용자가 jsp페이지에서 입력하는 비번
      if(${param.pass}!=password.value){
         alert("비밀번호가 틀렸습니다. 다시 입력해 주세요.");
         password.focus();
         return false;
      }
   }
</script>
<body>
	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="board">
				<form class="board-form" method="post" action="/mysite/board">
					<input type="hidden" name="a" value="modify" />
					<input type="hidden" name="no" value="${boardVo.no}" />
				
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글수정</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value="${boardVo.title}"></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="content">${boardVo.content}</textarea>
							</td>
						</tr>
						<c:if test="${boardVo.pos > 0 }">
						<tr>
							<td class="label">비밀번호</td>
							<td><input type="password" id="pass" name="pass" placeholder="숫자를 입력해주세요."></td>
						</tr>
						</c:if>
					</table>
				
					<div class="bottom">
						<a href="/mysite/board?a=list&nowPage=${param.nowPage }">취소</a>
					
						<input type="submit" value="수정" onClick="return check();" onKeyup="onlyNumber(this)">
					</div>
				</form>				
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div><!-- /container -->
</body>
</html>		
		
