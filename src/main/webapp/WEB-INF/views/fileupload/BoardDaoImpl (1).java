package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

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

	public List<BoardVo> getList() {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVo> list = new ArrayList<BoardVo>();

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "SELECT b.no, b.title, b.hit, TO_CHAR(b.reg_date, 'YY-MM-DD') as regDate, b.user_no, u.name\r\n"
					+ "FROM  board b, users u \r\n" + "WHERE b.user_no = u.no \r\n" + "ORDER BY b.NO DESC";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("b.no");
				String title = rs.getString("b.title");
				int hit = rs.getInt("b.hit");
				String regDate = rs.getString("regDate");
				int userNo = rs.getInt("b.user_no");
				String userName = rs.getString("u.name");

				BoardVo vo = new BoardVo(no, title, hit, regDate, userNo, userName);
				list.add(vo);
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
			String query = "select b.no, b.title, b.content, b.hit, b.reg_date, b.user_no, u.name "
					+ "from board b, users u " + "where b.user_no = u.no " + "and b.no = ?";

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

				boardVo = new BoardVo(no, title, content, hit, regDate, userNo, userName);
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

		try {
			conn = getConnection();

			System.out.println("vo.userNo : [" + vo.getUserNo() + "]");
			System.out.println("vo.title : [" + vo.getTitle() + "]");
			System.out.println("vo.content : [" + vo.getContent() + "]");

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "insert into board values (seq_board_no.nextval, ?, ?, 0, sysdate, ?)";
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getUserNo());

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

	@Override
	public List<BoardVo> getSearch(String keyWord) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVo> list = new ArrayList<BoardVo>();

		try {
			conn = getConnection();

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "SELECT b.NO, b.TITLE, u.NAME ,b.HIT, TO_CHAR(b.REG_DATE, 'YY-MM-DD HH24:MI') AS regDate, b.CONTENT   \r\n"
					+ "FROM BOARD b, USERS u \r\n" + "WHERE b.USER_NO = u.NO \r\n"
					+ "AND (u.NAME LIKE ? OR b.TITLE LIKE ? OR b.CONTENT LIKE ?)";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, '%' + keyWord + '%');
			pstmt.setString(2, '%' + keyWord + '%');
			pstmt.setString(3, '%' + keyWord + '%');
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("regDate");
				int userNo = rs.getInt("user_no");
				String userName = rs.getString("name");

				BoardVo vo = new BoardVo(no, title, hit, regDate, userNo, userName);
				list.add(vo);
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

		return list;

	}

}
