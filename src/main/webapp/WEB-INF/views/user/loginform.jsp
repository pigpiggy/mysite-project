<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="/mysite/assets/css/user.css" rel="stylesheet" type="text/css">
	<title>Insert title here</title>
</head>
<body>

	<div id="container">
		
	<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
	<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="wrapper">
			<div id="content">
				<div id="user">
					
					<form id="login-form" name="loginform" method="get" action="/mysite/user">
						<input type="hidden" name="a" value="login" /> 
						
						<label class="block-label" for="email">이메일</label> 
						<input id="email" name="email" type="text" value=""> 

						<label class="block-label">패스워드</label> 
						<input name="password" type="password" value="">
						
						<c:if test="${param.result eq 'fail'}">
						<p> 로그인에 실패하였습니다. </p>
						<p> 다시 입력해주세요. </p>
						</c:if>
						<input type="submit" value="로그인">
					</form>
					
				</div><!-- /user -->
			</div><!-- /content -->
		</div><!-- /wrapper -->
		
	<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>

		<!-- /footer -->
		
	</div> <!-- /container -->


</body>
</html>