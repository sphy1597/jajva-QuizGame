import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeletDB {
	
	void Deletuser(String _nick) {//DB에서 유저정보를 삭제하는 기능
		//DB연결
		Connection con = null;
		Statement stmt = null;
		String url ="jdbc:mysql://localhost:3306/quizgame?serverTimezone=Asia/Seoul";
		String user = "root";
		String passwd = "1111";
		
		try {//try1 드라이브 설정
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(java.lang.ClassNotFoundException e) {
			System.out.println("Server > SelectDB> try1");
			System.err.print("ClassNotFoundException: "); 
			System.err.println("드라이버 로딩 오류: " + e.getMessage());
		}
		
		try {// DB에서 삭제
			con = DriverManager.getConnection(url, user, passwd);	//DB연결
			stmt = con.createStatement();							//DB에 보낼 객체
			System.out.println("deletDb 받아온 값 : " + _nick);
			StringBuilder sb = new StringBuilder();					// 삭제할 문자열을 만들기위해 사용한 stringbuilder
			String sql = sb.append("delete from user where nickname = '")	//students 테이블에서 삭제
	                .append(_nick+"'")											//삭제할 학번
	                .append(";")
	                .toString();
			System.out.println("deletDB 완성된 값 : " + sql);
			stmt.executeUpdate(sql);								//DB에서 삭제
			
		}catch(SQLException ex) {
			System.out.println("error > Server > deletdb");
		}finally {//사용한 don과 stmt를 종료시킴
    		try {
			if (stmt != null) stmt.close();
            		if (con != null) con.close();
           	} catch (Exception e) {
           			
           	}
			
       	}
	}

}