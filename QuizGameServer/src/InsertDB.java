import java.sql.*;
import java.util.*;

public class InsertDB {

	void signup(String _id, String _pw, String _name, String _pn,String _nickname) {	//ȸ�����԰� �г��� �缳�� ���� ���������� �����ϴ� ���
		//DB����
		Connection con = null;
		Statement stmt = null;
		String url ="jdbc:mysql://localhost:3306/quizgame?serverTimezone=Asia/Seoul";
		String user = "root";
		String passwd = "1111";
		
		//����̺꼳��
		try {//try1
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(java.lang.ClassNotFoundException e) {
			System.out.println("Server > SelectDB> try1");
			System.err.print("ClassNotFoundException: "); 
			System.err.println("����̹� �ε� ����: " + e.getMessage());
		}
		
		//������ �߰��ϴ� ���
		String insertString = "INSERT INTO user VALUES ";		// user���̺� ����
		String recordString = insertString + "('" + _id + "','" + _pw + "','" + _name + "','" + _pn + "','" + _nickname + "')"; // "('����')"; �� ���� ���·� ���� ���� ���ڿ��� ����
		try {// DB�� �����͸� �߰�
			con = DriverManager.getConnection(url, user, passwd);	
			stmt = con.createStatement();
			stmt.executeUpdate(recordString);	//�ϼ��� ���ڿ��� ���� DB�� ���� �߰�
			
		}catch(SQLException ex) {
			System.out.println("Error > IDB > signup");
			
		}finally {//con�� stmt����
    		try {
			if (stmt != null) stmt.close();
            		if (con != null) con.close();
           	} catch (Exception e) {
           			
           	}
       	}
	}//signup()
	
	
}
