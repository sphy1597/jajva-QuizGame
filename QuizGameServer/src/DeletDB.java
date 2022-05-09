import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeletDB {
	
	void Deletuser(String _nick) {//DB���� ���������� �����ϴ� ���
		//DB����
		Connection con = null;
		Statement stmt = null;
		String url ="jdbc:mysql://localhost:3306/quizgame?serverTimezone=Asia/Seoul";
		String user = "root";
		String passwd = "1111";
		
		try {//try1 ����̺� ����
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(java.lang.ClassNotFoundException e) {
			System.out.println("Server > SelectDB> try1");
			System.err.print("ClassNotFoundException: "); 
			System.err.println("����̹� �ε� ����: " + e.getMessage());
		}
		
		try {// DB���� ����
			con = DriverManager.getConnection(url, user, passwd);	//DB����
			stmt = con.createStatement();							//DB�� ���� ��ü
			System.out.println("deletDb �޾ƿ� �� : " + _nick);
			StringBuilder sb = new StringBuilder();					// ������ ���ڿ��� ��������� ����� stringbuilder
			String sql = sb.append("delete from user where nickname = '")	//students ���̺��� ����
	                .append(_nick+"'")											//������ �й�
	                .append(";")
	                .toString();
			System.out.println("deletDB �ϼ��� �� : " + sql);
			stmt.executeUpdate(sql);								//DB���� ����
			
		}catch(SQLException ex) {
			System.out.println("error > Server > deletdb");
		}finally {//����� don�� stmt�� �����Ŵ
    		try {
			if (stmt != null) stmt.close();
            		if (con != null) con.close();
           	} catch (Exception e) {
           			
           	}
			
       	}
	}

}