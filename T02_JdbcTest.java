package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.DBUtil;

public class T02_JdbcTest {
	public static void main(String[] args) {
		/*
		 * 	문제1) 사용자로부터 lprod_id값을 입력받아 입력한 값보다
		 * 		  lprod_id가 큰 자료들을 출력하시오.
		 * 
		 * 	문제2) lprod_id값을 2개 입력받아 두 값 중 작은 값부터 큰값 사이의
		 * 		  자료를 출력하시오
		 * 
		 */
					

		Connection conn =null;
		Statement stmt =null;
		ResultSet rs =null;	//쿼리문이 select일 경우에 필요함.
		Scanner sc =new Scanner(System.in);
		int num =Integer.parseInt(sc.nextLine());
try {
	conn=DBUtil.getConnetion();
	
	stmt =conn.createStatement();
	
	String sql ="select * from lprod";
	
	rs= stmt.executeQuery(sql);
	
	System.out.println("실행한 쿼리문 : " + sql);
	System.out.println("====쿼리실행결과====");
	
	
	while(rs.next()) {
		rs.getInt(1);
		rs.getString(2);
		rs.getString(3);
		System.out.println("------------------------------------------------");
	}
	
	System.out.println("출력 끝....");
	
	
} catch (SQLException e) {
}finally {
	//6. 종료( 사용했던 자원을 모둔 반납한다.
	if(rs != null)
		try {
			rs.close();
		} catch (SQLException e) {
		}
	if(stmt != null)
		try {
			stmt.close();
		} catch (SQLException e ) {
		}
	if(conn != null) {
		try {
			conn.close();
		} catch (SQLException e) {
		}
	}
	
}

	}
}
