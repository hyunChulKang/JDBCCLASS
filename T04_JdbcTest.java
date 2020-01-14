package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import kr.or.ddit.util.DBUtil;

public class T04_JdbcTest {
/*
 * LPROD 테이블에 새로운 데이터를 추가하기
 * 
 * lprod_gu와 lprod_name은 직접 입력받아 처리하고 
 * 
 * lprod_id는 현재의 lprod_id중 제일큰 값보다 1증가된 값으로 한다.
 * (기타 사항 : lprod_gu도 중복되는지 검사한다.)
 * 
 */
	public static void main(String[] args) {
		Connection conn =null;
		PreparedStatement pstmt =null;
		Statement stmt =null;
		ResultSet rs =null;
		Scanner sc =new Scanner(System.in);
		String a="";
		String b="";
		try {

				conn = DBUtil.getConnetion();
			
				stmt= conn.createStatement();
			String sql ="select max(lprod_id) from lprod";
			
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
			a=rs.getString(1);
			b=a+1;
			}
			int count=0;
			stmt=conn.createStatement();
			do {
				String sql1= "select * from lprod";
				rs=stmt.executeQuery(sql1);
				rs.getString(1);
				rs.getString(2);
				rs.getString(3);
			}while(count>0);
			System.out.println("lprod_gu입력");
			String n1= sc.nextLine();

			System.out.println("lprod_nm입력");
			String n2= sc.nextLine();
			
			String sql2 ="insert into lprod (LPROD_ID,LPROD_GU,LPROD_NM) value(?,?,?)";
			
			pstmt =conn.prepareStatement(sql2);
			pstmt.setString(1, b);
			pstmt.setString(2, n1);
			pstmt.setString(3, n2);
			
			System.out.println(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		

	}
		
}

