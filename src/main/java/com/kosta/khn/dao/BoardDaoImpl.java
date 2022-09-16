package com.kosta.khn.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.kosta.khn.vo.BoardVo;

public class BoardDaoImpl implements BoardDao {

	private Connection getConnection() throws SQLException {

		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(dburl, "webdb", "1234");
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC 드라이버 로드 실패!");
		}
		return conn;
	}

	// String savefolder = request.getServletContext().getRealPath("fileupload");
	private static final String ENCTYPE = "UTF-8";
	private static int MAXSIZE = 5 * 1024 * 1024;

	public List<BoardVo> getBoardList(String keyField, String keyWord, int start, int end) {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		List<BoardVo> list = new ArrayList<BoardVo>();

		try {
			conn = getConnection();

			if (keyWord.equals("null") || keyWord.equals("")) {
				query = "SELECT * \r\n" + "FROM (SELECT ROWNUM AS RNUM, A.*\r\n"
						+ "		FROM ( SELECT b.NO, b.TITLE, u.name, b.HIT, TO_CHAR(b.REG_DATE, 'YY-MM-DD HH24:MI') AS reg_date, b.CONTENT, b.DEPTH, b.pos, b.REF, b.USER_no, b.filename, b.filesize, b.filename_, b.filesize_\r\n"
						+ "				FROM BOARD b, USERS u \r\n" + "				WHERE b.user_no = u.NO\r\n"
						+ "				order by b.ref desc, pos asc) A\r\n"
						+ "						WHERE ROWNUM <= ?+?) WHERE RNUM > ?";

				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
				pstmt.setInt(3, start);
			} else {
				query = "SELECT * " + " FROM(" + "   SELECT ROWNUM AS RNUM, A.*"
						+ "   FROM ( select b.no, b.title, b.content, b.hit, to_char(b.reg_date,'YYYY-MM-DD HH24:MI') as reg_date, b.user_no, b.depth,  b.pos, b.ref,b.filename, b.filesize, b.filename_,b.filesize_, u.name from board b, users u where b.user_no = u.no and "
						+ keyField + " like ?  order by b.ref desc, pos asc ) A" + "   WHERE ROWNUM <= ?+?" + "   )"
						+ " WHERE RNUM > ?";

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + keyWord + "%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
				pstmt.setInt(4, start);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getInt("no"));
				vo.setTitle(rs.getString("title"));
				vo.setUserName(rs.getString("name"));
				vo.setRegDate(rs.getString("reg_date"));
				vo.setContent(rs.getString("content"));
				vo.setDepth(rs.getInt("depth"));
				vo.setUserNo(rs.getInt("user_no"));
				vo.setHit(rs.getInt("hit"));
				vo.setPos(rs.getInt("pos"));
				vo.setRef(rs.getInt("ref"));
				vo.setFilename(rs.getString("filename"));
				vo.setFilesize(rs.getLong("filesize"));
				vo.setFilename_(rs.getString("filename_"));
				vo.setFilesize_(rs.getLong("filesize_"));

				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return list;

	}

	public BoardVo getBoard(int no) {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVo boardVo = null;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "select b.no, b.title, b.content, b.hit, b.reg_date, b.user_no, u.name, b.filename, b.filesize, b.filename_, b.filesize_, b.ref, b.pos, b.depth, b.pass \r\n"
					+ "from board b, users u where b.user_no = u.no and b.no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				String userName = rs.getString("name");
				String filename = rs.getString("filename");
				Long filesize = rs.getLong("filesize");
				String filename_ = rs.getString("filename_");
				Long filesize_ = rs.getLong("filesize_");
				int ref=rs.getInt("ref");
				int pos=rs.getInt("pos");
				int depth=rs.getInt("depth");
				String pass = rs.getString("pass");
				boardVo = new BoardVo(no, title, content, hit, regDate, userNo, userName, filename, filesize, filename_,
						filesize_, ref, pos, depth, pass);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		System.out.println(boardVo);
		return boardVo;

	}

	public int insert(BoardVo vo) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		ResultSet rs = null;
		int ref = 0;
	       int pos = 0;// 새글이기에
	        int depth = 0;//
		
		try {
			conn = getConnection();

	        System.out.println("vo.userNo : ["+vo.getUserNo()+"]");
	        System.out.println("vo.title : ["+vo.getTitle()+"]");
	        System.out.println("vo.content : ["+vo.getContent()+"]");
	        System.out.println("vo.filename : [" + vo.getFilename() + "]");
			System.out.println("vo.filesize : [" + vo.getFilesize() + "]");
			System.out.println("vo.filename2 : [" + vo.getFilename_() + "]");
			System.out.println("vo.filesize2 : [" + vo.getFilesize_() + "]");
	        
	        String refsql = "select max(ref) from board";
	          pstmt = conn.prepareStatement(refsql);
	          // 쿼리 실행후 결과를 리턴
	          rs = pstmt.executeQuery();
	          if (rs.next()) {
	              ref = rs.getInt(1) + 1; // ref가장 큰 값에 1을 더해줌
	              // 최신글은 글번호가 가장 크기 때문에 원래 있던 글에서 1을 더해줌

	          }
			
	
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "insert into Board(NO, title, content, hit, reg_date, user_no, DEPTH, pos, REF, filename, filesize, filename_, filesize_)";
			query += "values(seq_Board_no.nextval, ?, ?, 0, sysdate, ?, ?, ?, ?, ?, ?, ?, ?) ";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getUserNo());
			pstmt.setInt(4, depth);
			pstmt.setInt(5, pos);
			pstmt.setInt(6, ref);
			pstmt.setString(7, vo.getFilename());
			pstmt.setLong(8, vo.getFilesize());
			pstmt.setString(9, vo.getFilename_());
			pstmt.setLong(10, vo.getFilesize_());
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}

	public int delete(int no) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "delete from board where no = ?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 삭제");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}

	public int update(BoardVo vo) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update board set title = ?, content = ? where no = ? ";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNo());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

		return count;
	}

	public void boardCnt(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "UPDATE BOARD \r\n" + "SET hit=hit+1\r\n" + "WHERE NO = ?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, vo.getNo());

			count = pstmt.executeUpdate();
			System.out.println(count + "건 증가");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}

	}

//	public List<BoardVo> getSearch(String keyWord) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		List<BoardVo> list = new ArrayList<BoardVo>();
//
//		try {
//			conn = getConnection();
//
//			// 3. SQL문 준비 / 바인딩 / 실행
//			String query = "SELECT b.NO, b.TITLE, u.NAME, b.HIT, TO_CHAR(b.REG_DATE, 'YY-MM-DD HH24:MI') AS regDate, b.CONTENT  \r\n"
//					+ "FROM BOARD b, USERS u \r\n"
//					+ "WHERE b.USER_NO = u.NO AND (u.NAME LIKE ? OR b.REG_DATE LIKE ? OR b.TITLE LIKE ? OR b.CONTENT LIKE ? )";
//
//			pstmt = conn.prepareStatement(query);
//			pstmt.setString(1, '%' + keyWord + '%');
//			pstmt.setString(2, '%' + keyWord + '%');
//			pstmt.setString(3, '%' + keyWord + '%');
//			pstmt.setString(4, '%' + keyWord + '%');
//			rs = pstmt.executeQuery();
//
//			// 4.결과처리
//			while (rs.next()) {
//				int no = rs.getInt("no");
//				String title = rs.getString("title");
//				int hit = rs.getInt("hit");
//				String regDate = rs.getString("regDate");
//				String userName = rs.getString("name");
//
//				BoardVo vo = new BoardVo(no, title, hit, regDate, userName);
//				list.add(vo);
//			}
//
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			// 5. 자원정리
//			try {
//				if (pstmt != null) {
//					pstmt.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException e) {
//				System.out.println("error:" + e);
//			}
//
//		}
//
//		return list;
//
//	}

	@Override
	public int getTotalCount(String keyField, String keyWord) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		int totalCount = 0;
		System.out.println("keyField" + keyField);
		System.out.println("keyWord" + keyWord);
		try {
			conn = getConnection();
			if (keyWord.equals("null") || keyWord.equals("")) {
				query = "select count(no) from board";
				pstmt = conn.prepareStatement(query);
			} else {
				query = "SELECT count(*) \r\n" + "FROM BOARD b, USERS u  \r\n" + "WHERE b.USER_NO = u.NO AND "
						+ keyField + " LIKE ? ";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + keyWord + "%");
			}
			rs = pstmt.executeQuery();

			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		return totalCount;
	}

	// 게시물 답변
	public void replyBoard(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = "INSERT INTO BOARD (NO, TITLE, CONTENT, HIT, REG_DATE, USER_NO, DEPTH, POS, REF, PASS)\r\n";
			sql+="values(seq_board_no.nextval,?,?,0,sysdate,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getUserNo());
			pstmt.setInt(4, vo.getDepth()+1);
			pstmt.setInt(5, vo.getPos()+1);
			pstmt.setInt(6, vo.getRef());
			pstmt.setString(7, vo.getPass());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
	}

	// 답변에 위치값 증가
	public void replyUpBoard(int ref, int pos) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = getConnection();
			sql = "update Board set pos = pos + 1 where ref = ? and pos > ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, pos);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
	}
}
