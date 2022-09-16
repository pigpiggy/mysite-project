<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.kosta.khn.dao.BoardDaoImpl"%>
<%@ page import="com.kosta.khn.dao.BoardDao"%>
<%@ page import="com.kosta.khn.vo.BoardVo"%>
<%@ page import="com.kosta.khn.vo.UserVo"%>
<%@page import="java.util.*"%>


<%   
     request.setCharacterEncoding("UTF-8");
     List<BoardVo> list = null;
     BoardDao dao = new BoardDaoImpl();

     int totalRecord = 0; //전체레코드수
     int numPerPage = 7; // 페이지당 레코드 수 
     int pagePerBlock = 5; //블럭당 페이지수 
     
     int totalPage = 0; //전체 페이지 수
     int totalBlock = 0;  //전체 블럭수 

     int nowPage = 1; // 현재페이지
     int nowBlock = 1;  //현재블럭
     
     int start = 0; //디비의 select 시작번호
     int end = 10; //시작번호로 부터 가져올 select 갯수 (rownum서브쿼리?)
     
     int listSize = 0; //현재 읽어온 게시물의 수 (맨앞이나 맨뒤는 꽉 안차니까)

     //검색용
   String keyWord = "", keyField = "";
   if (request.getParameter("kwd") != null) {
      keyWord = request.getParameter("kwd");
      keyField = request.getParameter("keyField");
   }
   
   //페이지 갯수 계산
   if (request.getParameter("nowPage") != null) {
      nowPage = Integer.parseInt(request.getParameter("nowPage"));
   }
    start = (nowPage * numPerPage)-numPerPage;
    end = numPerPage;
    
   totalRecord = dao.getTotalCount(keyField, keyWord);         //전체 게시물 건수
   totalPage = (int)Math.ceil((double)totalRecord / numPerPage);   //전체페이지수
   nowBlock = (int)Math.ceil((double)nowPage/pagePerBlock);       //현재블럭 계산
     
   totalBlock = (int)Math.ceil((double)totalPage / pagePerBlock);  //전체블럭계산
   
   System.out.println("keyField2:" + keyField);
   System.out.println("keyWord2:" + keyWord);

%>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
   function list() {
      document.listFrm.action = "list.jsp";
      document.listFrm.submit();
   }
   
   function pageing(page) {
      document.readFrm.nowPage.value = page;
      document.readFrm.submit();
   }
   
   function block(value){
       document.readFrm.nowPage.value=<%=pagePerBlock%>*(value-1)+1;
       document.readFrm.submit();
   } 
   
   function read(num){
      document.readFrm.num.value=num;
      document.readFrm.action="read.jsp";
      document.readFrm.submit();
   }
   
   function check() {
        if (document.searchFrm.keyWord.value == "") {
         alert("검색어를 입력하세요.");
         document.searchFrm.keyWord.focus();
         return;
        }
     document.searchFrm.submit();
    }
</script>


<title>Mysite</title>
</head>
<body>
   <div id="container">
      
      <c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
      <c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
      
      <div id="content">
         <div id="board">
            <form id="search_form" name = "search_form" action="/mysite/board" method="post">
               <input type="hidden" name="a" value="list"/>
               <select name="keyField" size="1" >
                   <option value="name">글쓴이</option>
                   <option value="title">제 목</option>
                   <option value="content">내 용</option>
                   <option value="reg_date">작성일</option>
                   
                  </select>
               <input type="text" id="kwd" name="kwd" value="" >
               <input type="hidden" name="nowPage" value="1">
               <input type="submit" value="찾기">
            </form>
            
            
            <%
              list = dao.getBoardList(keyField, keyWord, start, end);
            	
//              list = (List)request.getAttribute("list");
              listSize = list.size();//브라우저 화면에 보여질 게시물 번호
              if (list.isEmpty()) {
               out.println("등록된 게시물이 없습니다.");
              } else {
            %>
            
            <table class="tbl-ex">
               <tr>
                  <th>번호</th>
                  <th>제목</th>
                  <th>글쓴이</th>
                  <th>조회수</th>
                  <th>작성일</th>
                  <th>&nbsp;</th>
               </tr>
               <%
                    for (int i = 0;i<numPerPage; i++) {
                     if (i == listSize) break;
                     BoardVo vo = list.get(i);
                     int no = vo.getNo();
                     String title = vo.getTitle();
                     String name = vo.getUserName();
                     String regdate = vo.getRegDate();
                     int userno = vo.getUserNo();
                     int pos = vo.getPos();
                     int ref = vo.getRef();
                     int depth = vo.getDepth();
                     int hit = vo.getHit();
               %>
               <tr>
                  <td align="center">
                     <%=no%>
                  </td>
                  <td>
                  <%
                       if(depth>0){
                        for(int j=0;j<depth;j++){
                           out.println("&nbsp;&nbsp;");
                           }
                        }
                  %>
                    <a href="/mysite/board?a=read&no=<%=no%>&nowPage=<%=nowPage%>"><%=title%></a>
                  </td>
                  <td align="center"><%=name%></td>
                  <td align="center"><%=hit%></td>
                  <td align="center"><%=regdate%></td>
                  <td>
                  <% 
                        UserVo authUser = (UserVo)session.getAttribute("authUser");
                        if(authUser != null){
                           int nom = authUser.getNo();
                           if(nom == userno){%>
                              <a href="/mysite/board?a=delete&no=<%=no %>" class="del">삭제</a>   
                     <%      }
                        }
                        %>
                  </td>
                  </tr>
               <%}//for%>
                           
            </table><%
             
              }
            %>
            
            <div class="pager">
               <tr>
         <td colspan="2"><br /><br /></td>
      </tr>
      <tr>
         <td>
         <!-- 페이징 및 블럭 처리 Start--> 
               <% 
                 int pageStart = (nowBlock -1)*pagePerBlock + 1 ; //하단 페이지 시작번호
               int pageEnd = ((pageStart + pagePerBlock ) <= totalPage) ?  (pageStart + pagePerBlock): totalPage+1; 
                 if(totalPage !=0){
                  if (nowPage > 1) {%>
                     <% if(keyWord == null || keyWord == ""){%>
                     <a href="/mysite/board?a=list&nowPage=<%=nowPage-1%>">prev</a>
                        <% }else {%>
                        <a href="/mysite/board?a=list&nowPage=<%=nowPage-1%>&keyField=<%=keyField%>&kwd=<%=keyWord%>">prev</a>
                        <%}}%>  
                  <% for ( ; pageStart < pageEnd; pageStart++){%>
                     <%if(keyWord == null || keyWord == ""){%>
                        <a href="/mysite/board?a=list&nowPage=<%=pageStart%>">
                       <%if(pageStart==nowPage) {%>
                       <font color="blue">
                       <%}%>
                     [<%=pageStart %>]
                       <%if(pageStart==nowPage) {%></font><%}%></a>
                        <%}else{%>
                       <a href="/mysite/board?a=list&nowPage=<%=pageStart %>&keyField=<%=keyField%>&kwd=<%=keyWord%>"> 
                       <%if(pageStart==nowPage) {%>
                       <font color="blue">
                       <%}%>
                     [<%=pageStart %>]
                       <%if(pageStart==nowPage) {%></font><%}%></a>
                       <%}}%>  
                   <%if (totalPage > nowPage ) {%>
                   <%if(keyWord == null || keyWord == ""){%>
                   <a href="/mysite/board?a=list&nowPage=<%=nowPage+1%>">next</a>
                      <% }else{%>
                   <a href="/mysite/board?a=list&nowPage=<%=nowPage+1%>&keyField=<%=keyField%>&kwd=<%=keyWord%>">next</a>
                      <% }%>
                      <% }%>  
                               <%}%>

             <!-- 페이징 및 블럭 처리 End-->
            
         </tr>
            </div>            
            <c:if test="${authUser != null }">
               <div class="bottom">
                  <a href="/mysite/board?a=writeform" id="new-book">글쓰기</a>
               </div>
            </c:if>            
         </div>
      </div>
      
      <c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
      
   </div><!-- /container -->
</body>
</html>      
      