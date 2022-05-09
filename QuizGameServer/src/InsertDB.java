import java.sql.*;
import java.util.*;

public class InsertDB {

	void signup(String _id, String _pw, String _name, String _pn,String _nickname) {	//회원가입과 닉네임 재설정 위해 유저정보를 저장하는 기능
		//DB연결
		Connection con = null;
		Statement stmt = null;
		String url ="jdbc:mysql://localhost:3306/quizgame?serverTimezone=Asia/Seoul";
		String user = "root";
		String passwd = "1111";
		
		//드라이브설정
		try {//try1
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(java.lang.ClassNotFoundException e) {
			System.out.println("Server > SelectDB> try1");
			System.err.print("ClassNotFoundException: "); 
			System.err.println("드라이버 로딩 오류: " + e.getMessage());
		}
		
		//내용을 추가하는 기능
		String insertString = "INSERT INTO user VALUES ";		// user테이블에 저장
		String recordString = insertString + "('" + _id + "','" + _pw + "','" + _name + "','" + _pn + "','" + _nickname + "')"; // "('내용')"; 와 같은 형태로 받은 값을 문자열로 만듬
		try {// DB에 데이터를 추가
			con = DriverManager.getConnection(url, user, passwd);	
			stmt = con.createStatement();
			stmt.executeUpdate(recordString);	//완성된 문자열을 통해 DB에 값을 추가
			
		}catch(SQLException ex) {
			System.out.println("Error > IDB > signup");
			
		}finally {//con과 stmt종료
    		try {
			if (stmt != null) stmt.close();
            		if (con != null) con.close();
           	} catch (Exception e) {
           			
           	}
       	}
	}//signup()
	
	
}
