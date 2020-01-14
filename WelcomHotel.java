package kr.or.ddit.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class WelcomHotel {
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private Scanner scan;
	public WelcomHotel() {
		scan = new Scanner(System.in);
	}
	public static void main(String[] args) {
		try {
			new WelcomHotel().ConnectionServer();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			new WelcomHotel().hotelStart();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection ConnectionServer() throws SQLException {
		Connection conn =null;
		String url ="jdbc:oracle:thin:@localhost:1521/xe";
		String userId ="kavin";
		String password ="java";
		conn = DriverManager.getConnection(url,userId,password);
		return conn;
	}
	
	public void hotelMenu() {
		System.out.println("\t\t\t__________________________________________________________________");
		System.out.print("\t\t\t| 1. 체크인\t | ");
		System.out.print("2. 체크아웃\t | ");
		System.out.print("3. 객실 상태\t | ");
		System.out.print("4. 업무 종료\t | ");
		System.out.println();
	}
	
	public void roomEmpty() throws SQLException {
		Connection conn = ConnectionServer();
		PreparedStatement pstmt =null;
		Statement stmt =null;
		ResultSet rs =null;
		String name=null;
		
		stmt =conn.createStatement();
		
		String sql =" select guest_name from welcomhotel";
		rs =stmt.executeQuery(sql);
		
		while(rs.next()) {
			name=rs.getString("guest_name");
		}
		if(name == null) {
			for(int i=101; i<131; i++) {
					try {
						String sql2 ="insert into welcomhotel (guest_name, customer_age, customer_tel, room_status, room_num) values(?,?,?,?,?)";
						pstmt =conn.prepareStatement(sql2);
						pstmt.setString(1,"...");
						pstmt.setString(2,"없음");
						pstmt.setString(3,"없음");
						pstmt.setString(4,"공실");
						pstmt.setInt(5,i);
						pstmt.executeUpdate();
					} catch (SQLException e) {
					}
			}
		}	
	}
	public void  hotelStart() throws SQLException {
		System.out.println();
		System.out.println("\t\t\t호텔에 오신걸 환영합니다.");
		roomEmpty();
		System.out.println();
		while(true) {
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("\t\t\t 호텔 메인 화면");
			hotelMenu();
			int menu =Integer.parseInt(scan.nextLine());
			
			switch(menu) {
			
			case 1 : checkIn();
				break;
			case 2 : checkOut();
				break;
			case 3 : roomList();
				break;
			case 4 :
				System.out.println("프로그램을 종료합니다...");
				return;
			default :
				System.out.println(" 잘못 입력했습니다. 다시입력해주세요");
			}
		
		}
	}

	private void roomList() throws SQLException {
		
		Connection conn =ConnectionServer();
		Statement stmt =null;
		ResultSet rs =null;
		String name="";
		String status="";
		int roomNum;
		int cnt=0;
		try {
			stmt =conn.createStatement();
			
			String sql =" select * from welcomhotel";
			rs =stmt.executeQuery(sql);
			
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒현재 입실상황▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
		System.out.println("객실\t객실상태\t예약자\t객실\t객실상태\t예약자\t객실\t객실상태\t예약자\t객실\t객실상태\t예약자\t객실\t객실상태\t예약자\t");
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
		while(rs.next()) {
				cnt++;
				name =rs.getString("guest_name");
				status =rs.getString("room_status");
				roomNum =rs.getInt("room_num");
			if(cnt%5==0) {
				System.out.println();
			}
			System.out.print(roomNum +"호  => "+status+"\t" + name+"\t");
		}
		System.out.println();
		System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
		
		} catch (SQLException e) {
		}
	
	}

	private void checkOut() throws SQLException {
		Connection conn =ConnectionServer();
		Statement stmt =null;
		ResultSet rs =null;
		PreparedStatement pstmt =null;
		int roomNum;
		String status="";
		String getname="";
		
		int yesNo;
		
		boolean in_out = true;
		while(in_out) {
			System.out.println();
			System.out.println("체크아웃 하실 객실 번호를 입력해주세요.");
			System.out.print("객실번호 >> ");
			int roomNumber = Integer.parseInt(scan.nextLine());
			
			System.out.println();
			System.out.print("이름 >> ");
			String name = scan.nextLine();
			
			
			 try {
					stmt =conn.createStatement();
					
					String sql =" select guest_name, room_num, room_status from welcomhotel";
					rs =stmt.executeQuery(sql);
					
					while(rs.next()) {
						status =rs.getString("room_status");
						roomNum=rs.getInt("room_num");
						getname=rs.getString("guest_name");
							if( roomNumber == roomNum && status.equals("공실")) {							//<---조회 검사.
								System.out.println(roomNumber + "객실은 현재 공실 상태입니다.");
								return;
							}else if(roomNum == roomNumber && getname.equals(name)){
								
							String sql2 ="UPDATE welcomhotel SET guest_name = ? , customer_age = ?,customer_tel = ? , room_status  = ?  WHERE room_num  = ?";
								pstmt =conn.prepareStatement(sql2);
								
								pstmt.setString(1,"...");
								pstmt.setString(2,"없음");
								pstmt.setString(3,"없음");
								pstmt.setString(4,"공실");
								pstmt.setInt(5,roomNum);
								pstmt.executeUpdate();
								
								System.out.println("체크아웃이 완료 되었습니다.");
								System.out.println("이용해주셔서 감사합니다.");
								return;
							}else if( roomNumber == roomNum != getname.equals(name)) {
								System.out.println("객실과 고객이름이 불일치합니다.");
								System.out.println("체크아웃을 계속하시겠습니까?");
								System.out.println(" 1.네\r 2.아니요");
								while(in_out) {
								yesNo =Integer.parseInt(scan.nextLine());
									if(yesNo == 1) {
										in_out=false;
										
									}else if (yesNo ==2) {
										return;
									}else {
										System.out.println("다시입력해주세요.");
										in_out=true;
									}
								}
									
							}
			
					}
			 } catch (SQLException e) {
			 }
			
		}
	}

	private void checkIn() throws SQLException {
		Connection conn =ConnectionServer();
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		Statement stmt =null;
		int yesNo;
		boolean in_out =true;
		int roomNum;
		int inputNum;
		String status;
		
		while(in_out) {
			
			
			System.out.println();
			roomList();
			System.out.println();
			System.out.println("▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒");
			System.out.println();
			System.out.println();
			System.out.println("\t\t\t\t\t101호~130호사이의 객실번호를 입력해주세요");
			 inputNum =Integer.parseInt(scan.nextLine());
			 try {
					stmt =conn.createStatement();
					
					String sql =" select room_num, room_status from welcomhotel";
					rs =stmt.executeQuery(sql);
					
					while(rs.next()) {
						status =rs.getString("room_status");
						roomNum=rs.getInt("room_num");
						if( inputNum == roomNum && !status.equals("공실")) {							//<---조회 검사.
							System.out.println(inputNum + "다른손님이 사용중입니다.");
							return;
						}
					}
				} catch (SQLException e) {
				}
			 
			 System.out.println("\t\t\t\t\t"+inputNum +"호 로 예약 해드릴까요?");
			 System.out.println("\t\t\t\t\t 1.여기로할꼐요\r\t\t\t\t\t 2.다시 고를래요\r\t\t\t\t\t 3.예약안해요");
			 yesNo = Integer.parseInt(scan.nextLine());
			 if(yesNo == 1) {
				 System.out.println("\t\t\t\t\t예약을 위해 간단한 인적사항을 받겠습니다.");
				 status = "예약";
				 in_out=false;
					System.out.println();
					System.out.println();
					System.out.print("\t\t\t\t\t이름을 입력해주세요");
					String name = scan.nextLine();
					
					System.out.println();
					System.out.print("\t\t\t\t\t나이를 입력해주세요");
					String age = scan.nextLine();
					
					System.out.println();
					System.out.print("\t\t\t\t\t전화번호를 입력해주세요");
					String tel = scan.nextLine();
					String sql ="UPDATE welcomhotel SET guest_name = ? , customer_age = ?,customer_tel = ?, room_status  = ?  WHERE room_num  = ?";
					try {
					pstmt =conn.prepareStatement(sql);
					
					pstmt.setString(1,name);
					pstmt.setString(2,age);
					pstmt.setString(3,tel);
					pstmt.setString(4, status);
					pstmt.setInt(5,inputNum);
					pstmt.executeUpdate();
					
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					System.out.println();
					System.out.println("\t\t\t\t\t"+name + "님 예약완료 !!");
					return;
			 }else if(yesNo == 2){
				 
			 }else if(yesNo == 3) {
				 return;
			 }else {
				 System.out.println("잘못 입력하셨습니다.");
				 System.out.println();
			 }
		
		}
	}

}

class Customer {
	private String name;
	private String age;
	private String tel;
	private String status;
	private int roomNumber;
	
	public Customer(String name, String age, String tel, String statue, int roomNumber) {
		super();
		this.name = name;
		this.age = age;
		this.tel = tel;
		this.status = statue;
		this.roomNumber = roomNumber;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}


	
}