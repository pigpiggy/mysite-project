<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.kosta.khn.dao.BoardDaoImpl"%>
<%@ page import="com.kosta.khn.dao.BoardDao"%>
<%@ page import="com.kosta.khn.vo.BoardVo"%>
<%@ page import="com.kosta.khn.vo.UserVo"%>
<%@page import="java.util.*"%>

<%
		  BoardVo vo = new BoardVo();
		  String title = vo.getTitle();
		  String content = vo.getContent(); 
		  vo = (BoardVo)session.getAttribute("vo");
		  String nowPage = (String)request.getAttribute("nowPage");
		  System.out.println("replynowPage:"+nowPage);
		  String ref = (String)request.getAttribute("ref");
		  String pos = (String)request.getAttribute("pos");
		  String depth = (String)request.getAttribute("depth");
		  System.out.println("vo:" + vo);
		  UserVo authUser = (UserVo) session.getAttribute("authUser");
		  int userNo =  authUser.getNo();
%>
<html>
<head>
<title>Reply</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="">
<script>    
function inNumber(){ //숫자만 입력받기 
    if(event.keyCode<48 || event.keyCode>57){
       event.returnValue=false;
    }
}

</script>
<div align="center">
<br><br>
 <table width="600" cellpadding="3" >
  <tr>
   <td bgcolor="#c4c4c4" height="21" align="center">답변하기</td>
  </tr>
</table>
<form method="post" action="/mysite/board">
<input type="hidden" name="a" value="replywrite">
   <table>
    <tr>
     <td>제 목</td>
     <td>
	  <input name="title" size="50" value="┖ 답변 : <%=vo.getTitle() %>" maxlength="50"></td> 
    </tr>
	<tr>
     <td>내 용</td>
     <td>
	   <textarea name="content" rows="12" cols="50">
      	========답변 글을 쓰세요.=======
     <%=vo.getContent() %>
     </textarea>
      </td>
    </tr>
    <tr>
     <td>비밀 번호</td> 
	  <td width="450">
	  <input type="password" name="pass" size="60" placeholder="숫자만 가능합니다." onkeypress="inNumber();">
	  </td> 
    </tr>
    <tr>
     <td colspan="2" height="5"><hr/></td>
    </tr>
	<tr> 
     <td colspan="2" align="center">
	 <input type="submit" value="답변등록" onKeyup="onlyNumber(this)">
     <input type="reset" value="다시쓰기">
     <input type="button" value="뒤로" onClick="history.back()"></td>
    </tr> 
   </table>
 <input type="hidden" name="nowPage" value="<%=nowPage%>">
 <input type="hidden" name="ref" value="<%=ref%>">
 <input type="hidden" name="pos" value="<%=pos%>">
 <input type="hidden" name="depth" value="<%=depth%>">
 <input type="hidden" name="userNo" value="<%=userNo%>">
 
</form> 
</div>
</body>
</html>