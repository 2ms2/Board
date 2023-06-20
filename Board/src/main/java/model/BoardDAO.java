package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	// 데이터 베이스 연결
	public void getCon() {
		// Exception 날 수 있기 때문에 미리 설정
		try {
			Context initctx = new InitialContext();
			Context envctx = (Context) initctx.lookup("java:comp/env"); // 다운캐스팅
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool"); // 다운캐스팅
			con = ds.getConnection(); // 커넥션 연결해주는 메소드
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int getAllCount() {
		int count = 0;
		
		getCon();
		
		try {
			String sql = "SELECT count(*) "
							+ "FROM board";
			
			pstmt = con.prepareStatement(sql);
			
			// 쿼리 실행 후 결과 리턴
			rs = pstmt.executeQuery();
			
			// 행이 1개(단일행)이기 때문에 while문을 안써도 된다.
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return count;
	}
	
	// 모든 화면에 보여질 데이터를 10개씩 추출해서 리턴
	public Vector<BoardBean> getAllBoard(int startRow , int endRow) {
		Vector<BoardBean> v = new Vector<BoardBean>();
		
		getCon();
		
		try {
			String sql = "select * "
					 	  + "from (" 
						  	+ "select A.*, Rownum Rnum "
						    + "from ( "
						    	+ "select * "
						    	+ "from board "
						    	+ "order by ref desc, re_step asc "
						    	+ ") A "
						    + ") "
						  + "where Rnum >= ? and Rnum <= ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			
			// 반복문을 통해 데이터를 저장
			while(rs.next()){
				 // Boardbean클래스를 이용하여 데이터를 패키징 해준다
				 BoardBean bean = new BoardBean();
				 bean.setNum(rs.getInt(1));
				 bean.setSubject(rs.getString(2));
				 bean.setWriter(rs.getString(3));
				 bean.setReg_date(rs.getDate(4).toString());
				 bean.setReadcount(rs.getInt(5));
				 bean.setEmail(rs.getString(6));
				 bean.setPassword(rs.getString(7));
				 bean.setContent(rs.getString(8));
				 bean.setRef(rs.getInt(9));
				 bean.setRe_step(rs.getInt(10));
				 bean.setRe_level(rs.getInt(11));
				 
				 // 데이터를 저장 
				 v.add(bean);
			 }
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return v;
	}
	
	// 하나의 게시글을 저장
	public void insertBoard(BoardBean bean) {
		getCon();
		
		int ref = 0;
		int re_step = 1;
		int re_level = 1;
		
		// setNum, setReg_date, setReadcount 자동으로 받을거라
		// 데이터베이스에서 ref값 먼저 가져오자
		try {
			// 쿼리작성
			String refsql ="SELECT max(ref) "
							 + "FROM board";
			
			pstmt = con.prepareStatement(refsql);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				 ref = rs.getInt(1) + 1; // 가장 큰 값에 1을 더해준다
				
			}
			
			// 데이터 삽입
			String sql = "INSERT INTO board "
						  + "VALUES(board_seq.nextval, ?, ?, sysdate, 0, ?, ?, ?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getSubject());
			pstmt.setString(2, bean.getWriter());
			pstmt.setString(3, bean.getEmail());
			pstmt.setString(4, bean.getPassword());
			pstmt.setString(5, bean.getContent());
			pstmt.setInt(6, ref);
			pstmt.setInt(7, re_step);
			pstmt.setInt(8, re_level);
		
			pstmt.executeUpdate();
			
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 하나의 게시글을 읽어드리고, 게시글을 클릭했을 때 조회수 1씩 증가
	public BoardBean getOneBoard(int num) {
		getCon();
		
		BoardBean bean = null;
		
		try {
			// 하나의 게시글을 읽었을 때 조회수 증가
			String countsql = "UPDATE board "
								  + "SET readcount = readcount+1 "
								  + "WHERE num = ?";
			
			pstmt = con.prepareStatement(countsql);
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
			
			// 게시글 정보
			String sql = "SELECT * "
						  + "FROM board "
						  + "WHERE num = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				bean = new BoardBean();
				bean.setNum(rs.getInt(1));
				bean.setSubject(rs.getString(2));
				bean.setWriter(rs.getString(3));
				bean.setReg_date(rs.getDate(4).toString());
				bean.setReadcount(rs.getInt(5));
				bean.setEmail(rs.getString(6));
				bean.setPassword(rs.getString(7));
				bean.setContent(rs.getString(8));
				bean.setRef(rs.getInt(9));
				bean.setRe_step(rs.getInt(10));
				bean.setRe_level(rs.getInt(11));
			}
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	// 답변글 저장
	public void reInsertBoard(BoardBean bean) {
		getCon();
		
		// 부모글 그룹과 글 레벨, 글 스텝을 읽어드린다
		int ref = bean.getRef();
		int re_step = bean.getRe_step();
		int re_level = bean.getRe_level();
	
		try {
			// 최신글이 위로 올라가야된다
			// 부모글 보다 큰 re_level의 값을 전부 1씩 증가시켜준다.
			String levelsql ="UPDATE board "
						 		+ "SET re_level = re_level+1 "
						 		+ "WHERE ref = ? and re_level > ?";
			
			pstmt = con.prepareStatement(levelsql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, re_level);

			pstmt.executeUpdate();
			
			String sql = "INSERT INTO board "
						  + "VALUES(board_seq.nextval, ?, ?, sysdate, 0, ?, ?, ?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bean.getSubject());
			pstmt.setString(2, bean.getWriter());
			pstmt.setString(3, bean.getEmail());
			pstmt.setString(4, bean.getPassword());
			pstmt.setString(5, bean.getContent());
			pstmt.setInt(6, ref);
			pstmt.setInt(7, re_step+1); // 답글이기 때문에 기존 부모글 스텝보다 1 증가
			pstmt.setInt(8, re_level+1); // 답글이기 때문에 기존 부모글 레벨보다 1 증가
			
			pstmt.executeUpdate();

			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// 하나의 게시글을 읽어드린다. 조회수 증가 없음
	public BoardBean getOneUpdateBoard(int num) {
		getCon();
		
		BoardBean bean = null;
		
		try {
			String sql = "SELECT * "
						  + "FROM board "
						  + "WHERE num = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				bean = new BoardBean();
				bean.setNum(rs.getInt(1));
				bean.setSubject(rs.getString(2));
				bean.setWriter(rs.getString(3));
				bean.setReg_date(rs.getDate(4).toString());
				bean.setReadcount(rs.getInt(5));
				bean.setEmail(rs.getString(6));
				bean.setPassword(rs.getString(7));
				bean.setContent(rs.getString(8));
				bean.setRef(rs.getInt(9));
				bean.setRe_step(rs.getInt(10));
				bean.setRe_level(rs.getInt(11));
				
			}
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	// 하나의 게시글을 수정
	public void updateBoard(int num, String subject, String content) {
		getCon();
		
		try {
			String sql = "UPDATE board "
						  + "SET subject = ?, content = ? "
						  + "WHERE num = ?";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1,  subject);
			pstmt.setString(2,  content);
			pstmt.setInt(3,  num);

			pstmt.executeUpdate();
			
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}

	// 하나의 게시글을 삭제
	public void deleteBoard(int num) {
		getCon();
		
		try {
			String sql = "DELETE FROM board "
						  + "WHERE num = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
			
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
}// BoardDAO
