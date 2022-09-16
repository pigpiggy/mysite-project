package com.kosta.khn.controller;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kosta.khn.dao.BoardDao;
import com.kosta.khn.dao.BoardDaoImpl;
import com.kosta.khn.vo.BoardVo;
import com.kosta.khn.vo.UserVo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/fileupload")
public class FileUploadServlet extends HttpServlet {
      private static final long serialVersionUID = 1L;

      private static final String ENCTYPE = "UTF-8";

       private static final int BYTE       = 1024;
       private static final int KILOBYTE   = 1024;

       private static final int MEMORY_THRESHOLD   = BYTE * KILOBYTE * 3;  //  3MB  memory buffer
       private static final int MAX_FILE_SIZE      = BYTE * KILOBYTE * 40; //  40MB 가장 큰 파일 용량
       private static final int MAX_REQUEST_SIZE   = BYTE * KILOBYTE * 50; //  client에서 server로 전송 되는 request의 총 용량

       private static final String TEMP_STROAGE  = "/Users/pyuteo/eclipse-workspace/mysite/src/main/webapp/WEB-INF/views/fileupload";
       private static final String FILE_STROAGE  = "/Users/pyuteo/eclipse-workspace/mysite/src/main/webapp/WEB-INF/views/fileupload";
   
   
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      if( !ServletFileUpload.isMultipartContent(request) ) {
            //TODO
            // 클라이언트에서 서버로 전송하는 방식이 multipart type 이 아니다.
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);

        // server에 파일이 업로드 되는 동안 임시로 저장 될 경로
        factory.setRepository(new File(TEMP_STROAGE));

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);   // 가장 큰 파일 사이즈 설정
        upload.setSizeMax(MAX_REQUEST_SIZE);    // 총 request의 사이즈 설정

        // 서버에 파일 저장 경로
        String uploadPath =  FILE_STROAGE;

        // 서버에 저장소 만들기
        File uploadStorage = new File(uploadPath);
        if(!uploadStorage.isDirectory()) {
            uploadStorage.mkdir();
        }
        
        MultipartRequest multi=new MultipartRequest(request,FILE_STROAGE,5*1024*1024,"UTF-8",new DefaultFileRenamePolicy());
        String title = multi.getParameter("title");
        System.out.println(title);
        String content = multi.getParameter("content");
        System.out.println(content);
        
        Enumeration formNames = multi.getFileNames();
        
        String fileInput = "";
        String filename = "";
        String filename_ = "";
        File fileObj1 = null;
        File fileObj2 = null;
        long filesize = 0;
        long filesize_ = 0;
        
        while(formNames.hasMoreElements()) {
           if(filename=="") {
        	   fileInput = (String)formNames.nextElement();
        	   filename = multi.getFilesystemName(fileInput);
        	   fileObj1 = multi.getFile(fileInput);
        	   if(fileObj1!=null) {
        		   filesize = Long.parseLong(String.valueOf(fileObj1.length()));
                }
              
           } else {
        	   fileInput = (String)formNames.nextElement();
        	   filename_ = multi.getFilesystemName(fileInput);
        	   fileObj2 = multi.getFile(fileInput);
        	   if(fileObj2!=null) {
        		   filesize_ = Long.parseLong(String.valueOf(fileObj2.length()));
               }
           }
           
        }
        
        System.out.println("filename:" + filename + "filename2:" + filename_);
        
        
        UserVo authUser = getAuthUser(request);
        int userNo = authUser.getNo();
        BoardDao dao = new BoardDaoImpl();
        BoardVo vo = new BoardVo(title, content, userNo, filename, filesize, filename_, filesize_);
        dao.insert(vo);
        
        
        
        getServletContext().getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);
    }


   
   // 로그인 되어 있는 정보를 가져온다.
   protected UserVo getAuthUser(HttpServletRequest request) {
      HttpSession session = request.getSession();
      UserVo authUser = (UserVo) session.getAttribute("authUser");

      return authUser;
   }

}