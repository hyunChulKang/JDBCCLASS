package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.DBUtil2;

public class boardEx {
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Scanner scan;
	int pk;
	public boardEx(){
		scan= new Scanner(System.in);
	}
	public static void main(String[] args) {
		boardEx be =new boardEx();
		be.start();
	}
	
	
	public void start() {
		System.out.println();
		System.out.println("================게시판=====================");
		
	do {
		System.out.println("1.글작성\t2.글목록\t3.글수정\t4.글삭제\t5.글검색\t0.게시판종료");
		pk=Integer.parseInt(scan.nextLine());
		switch(pk) {

		case 1 :
			write();
			break;
		case 2 : 
			selectview();
			break;
		case 3 : 
			repair();
			break;
		case 4 : 
			delete();
			break;
		case 5 : 
			findout();
			break;
		case 0 :
			System.out.println("게시판종료");
			break;
		default:
			System.out.println("다시입력해주세요.");
		}
	}while(pk !=0);
	
		}
	private void findout() {
		System.out.println();
		System.out.println("검색할 키워드를 입력하세요.");
		String keyword =scan.nextLine();
		try {
			
			conn =DBUtil2.getConnetion();
			String sql = "select * from jdbc_board where board_content like '%'||? "
							+ " OR board_content like ?||'%' OR board_content like '%'||?||'%'"
							+ " OR board_title like ?||'%' OR board_title like '%'||? "
							+ " OR board_title like '%'||?||'%' ";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			pstmt.setString(3, keyword);
			pstmt.setString(4, keyword);
			pstmt.setString(5, keyword);
			pstmt.setString(6, keyword);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				String no =rs.getString("board_no");
				String title =rs.getString("board_title");
				String writer =rs.getString("board_writer");
				String date=rs.getString("board_date");
				String content=rs.getString("board_content");
			System.out.println("-----------------------------------------------------------");
			System.out.println("\t순번\t제목\t작성자\t작성날짜\t내용");
			System.out.println("\t" + no + "\t" + title + "\t" + writer + "\t" + date + "\t" + content);
			
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	private void delete() {
		System.out.println();
		System.out.println("삭제할 작성글의 번호를 입력해주세요");
		String no = scan.nextLine();
		try {
			conn =DBUtil2.getConnetion();
			String sql ="delete from jdbc_board where board_no = ?";
			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1, no);
			
			int cnt =pstmt.executeUpdate();
			if(cnt >0) {
				System.out.println("글삭제가 완료되었습니다.");
			}else {
				System.out.println("글삭제에 실패했습니다.");
			}
		}catch(SQLException e) {
			System.out.println("글삭제에 실패했습니다.");
			e.printStackTrace();
		}finally {
			disConnect();
		}
	}
	private void repair() {
		boolean check= true;
		String no="";
		do {
			System.out.println();
			System.out.println("==========================작성글 수정===========================");
			System.out.println("작성자 번호 >>");
			no =scan.nextLine();
			check=getSelect(no);
			if(check ==false) {
				System.out.println(no + "번 작성글이 없습니다.");
				System.out.println("다시 입력하세요.");
			}
		}while(check ==false);
		System.out.println("수정할 제목 내용을 입력하세요");
		System.out.println("제목 >>");
		String title =scan.nextLine();
		
		System.out.println("수정할 내용");
		String content = scan.nextLine();
		
		try {
			conn= DBUtil2.getConnetion();
			String sql ="update jdbc_board set board_title = ?, board_content= ? where board_no = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, no);
			int cnt =pstmt.executeUpdate();
			if(cnt >0) {
				System.out.println("정상적으로 글이 수정되었습니다.");
			}else {
				System.out.println("글 수정에 실패했습니다.");
			}
		}catch(SQLException e) {
			System.out.println("글 수정에 실패했습니다.");
			e.printStackTrace();
		}finally {
			disConnect();
		}
	}
	private void selectview() {
		System.out.println();
		System.out.println("===========================글 목록=============================");
		System.out.println("순번\t제목\t작성자\t작성날짜\t\t\t내용");
		try {
			conn=DBUtil2.getConnetion();
			stmt=conn.createStatement();
			String sql ="select * from jdbc_board ORDER BY board_no desc";
		rs =stmt.executeQuery(sql);
		while(rs.next()){
			String no =rs.getString("board_no");
			String title =rs.getString("board_title");
			String writer =rs.getString("board_writer");
			String date=rs.getString("board_date");
			String content=rs.getString("board_content");
		System.out.println("-----------------------------------------------------------");
		System.out.println( no + "\t" + title + "\t" + writer + "\t" + date + "\t" + content);
		System.out.println();
		}
		} catch (SQLException e) {
			System.out.println("selectview에서 조회실패");
			e.printStackTrace();
		}finally {
			disConnect();
		}
		
	}
	private void write() {
		System.out.println();
		System.out.println("작성자 이름 >>");
		String writer =scan.nextLine();
		System.out.println("제목 >> ");
		String title =scan.nextLine();
		System.out.println("글 내용 >> ");
		String content =scan.nextLine();
		
		try {
			conn =DBUtil2.getConnetion();
			String sql ="insert into jdbc_board (board_no, board_title, board_writer, board_date, board_content)"
						+" values (board_seq.nextval, ?, ?, sysdate,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, writer);
			pstmt.setString(3, content);
			pstmt.executeUpdate();
			System.out.println();
			System.out.println("글 작성완료");
			System.out.println();
		}catch(SQLException e) {
			System.out.println("write에서 작성실패");
			e.printStackTrace();
		}finally {
			disConnect();
		}
	
	}
	public boolean getSelect(String no) {
		boolean check = false;
		try {
			conn=DBUtil2.getConnetion();
			String sql ="select count(*) cnt from jdbc_board where board_no = ? ";
			pstmt =conn.prepareStatement(sql);
			pstmt.setString(1, no);
			rs = pstmt.executeQuery();
			int cnt=0;
			if(rs.next()) {
				cnt= rs.getInt("cnt");
			}
			if(cnt>0) {
				check =true;
			}
		}catch(SQLException e) {
			System.out.println("조회실패");
			e.printStackTrace();
			check=false;
			
		}finally {
			disConnect();
		}
		return check;
	}
	public void disConnect() {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(stmt!=null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}