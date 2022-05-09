import java.sql.*;
import java.util.*;

public class SelectDB {
	
	//���������� �����ϱ� ���� ����Ʈ
	ArrayList<User> users = new ArrayList<User>();
	
	//�������� �����ϱ� ���� ����Ʈ
	ArrayList<Quiz> quizlist1 = new ArrayList<Quiz>();
	ArrayList<Quiz> quizlist2 = new ArrayList<Quiz>();
	ArrayList<Quiz> quizlist3 = new ArrayList<Quiz>();
	ArrayList<Quiz> quizlist4 = new ArrayList<Quiz>();
	ArrayList<Quiz> quizlist5 = new ArrayList<Quiz>();
	
	Server server;
	InsertDB IDB;
	
	SelectDB(Server _server){
		server = _server;
		IDB= server.IDB;
	}
	
	// �������� ����Ʈ�� ����� ���
	void makeusers(){
		// DB����
		Connection con = null;
		Statement stmt = null;
		String url ="jdbc:mysql://localhost:3306/quizgame?serverTimezone=Asia/Seoul";
		String user = "root";
		String passwd = "1111";
		
		//���Ӱ� ����Ʈ�� ��������� �ʱ�ȭ
		users.clear();
		
		//����̺꼳��
		try {//try1
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(java.lang.ClassNotFoundException e) {
			System.out.println("Server > SelectDB> try1");
			System.err.print("ClassNotFoundException: "); 
			System.err.println("����̹� �ε� ����: " + e.getMessage());
		}
		
		try {//try2
			con = DriverManager.getConnection(url,user,passwd);	// DB�� ����
			stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM user");
			
			//DB�� ����� ������ ���� �о� �鿩�� User��ü�� ����� ����Ʈ�� ����
			while(result.next()) {
				String id = result.getString("id");
				String password = result.getString("password");
				String name = result.getString("name");
				String pn = result.getString("phonenum");
				String nickname = result.getString("nickname");
				User userinfor = new User(id, password, name,pn,nickname);
				users.add(userinfor);
			}
			
			
		}catch(SQLException ex) {
			System.out.println("error > Server > SelectDB > try2");
		}finally {	// ����
    		try {
			if (stmt != null) stmt.close();
            		if (con != null) con.close();
           	} catch (Exception e) {
           			
           	}
		}
		
		
	}//selectDB()
	
	//�α��� �������� ���θ� üũ
	String checklogin(String _id, String _password) {
		String flag = "FAIL";
		
		//��������Ʈ�� ����
		makeusers();
		
		//��������Ʈ�� ��ġ�ϴ� ������ �ִ���Ȯ�� �ؼ� ������ �ش� �г����� ��ȯ
		for(int i = 0; i < users.size();i++){
			if(users.get(i).Id.equals(_id)&&users.get(i).Password.equals(_password)) {
				System.out.println(users.get(i).Id + " " + users.get(i).Password);
				flag = users.get(i).Nickname;
			}
		}
		
		
		//�̹� �κ�Ǵ� ���ӹ濡 ���������� Ȯ���ϴ� ��� 
		for(int i = 0; i < server.LobbyClients.size();i++) {
			if(server.LobbyClients.get(i).nick.equals(flag)) {
				flag = "FAIL";
			}
		}
		for(int i = 0; i < server.Mode1.size();i++) {
			if(server.Mode1.get(i).nick.equals(flag)) {
				flag = "FAIL";
			}
		}
		for(int i = 0; i < server.Mode2.size();i++) {
			if(server.Mode2.get(i).nick.equals(flag)) {
				flag = "FAIL";
			}
		}
		for(int i = 0; i < server.Mode3.size();i++) {
			if(server.Mode3.get(i).nick.equals(flag)) {
				flag = "FAIL";
			}
		}
		for(int i = 0; i < server.Mode4.size();i++) {
			if(server.Mode4.get(i).nick.equals(flag)) {
				flag = "FAIL";
			}
		}
		for(int i = 0; i < server.Mode5.size();i++) {
			if(server.Mode5.get(i).nick.equals(flag)) {
				flag = "FAIL";
			}
		}
		
		return flag;
	}//check Login
	

	// ȸ�������� ���� üũ
	boolean signupcheck(String _id, String _pw, String _name, String _pn,String _nn) {
		boolean flag = true;
		//��������Ʈ�� ����
		makeusers();
	
		//����Ʈ�� ��ġ�ϴ� ������ �ִ��� Ȯ��
		for(int i = 0; i < users.size();i++){
			if(users.get(i).Id.equals(_id)||users.get(i).Nickname.equals(_nn)||users.get(i).Name.equals(_name)||users.get(i).Pn.equals(_pn)) {
				flag = false;
			}
		}
		
		return flag;
	}
	
	// ���̵�ã�� ���
	String Idfind(String _name, String _pn) {
		String id = "ERROR!";
		//��������Ʈ�� ����
		makeusers();
		
		// ����Ʈ�� ��ġ�ϴ� ������ �ִ��� Ȯ�� ������ ���̵� ����
		for(int i = 0;i <users.size();i++) {
			if(users.get(i).Name.equals(_name) && users.get(i).Pn.equals(_pn)) {
				id = users.get(i).Id;
			}
		}
		
		return id;
	}
	
	//��й�ȣ ã�� ���
	String Pwfind(String _id, String _pn) {
		String pw = "ERROR!";
		//��������Ʈ ����
		makeusers();
		
		//����Ʈ�� ��ġ�ϴ� ������ �ִ��� Ȯ�� ������ ��й�ȣ�� ����
		for(int i = 0 ; i < users.size();i++) {
			if(users.get(i).Id.equals(_id) && users.get(i).Pn.equals(_pn)) {
				pw = users.get(i).Password;
			}
		}
		return pw;
	}
	
	//�г��� ������ ���� üũ
	boolean nickcheck(String _nick) {
		boolean flag = true;
		makeusers();
		
		for(int i = 0; i < users.size(); i ++) {
			if(users.get(i).Nickname.equals(_nick)) {
				flag = false;
			}
		}
		
		return flag;
	}
	
	//�г��� ���� ���
	void changenick(String _nick,String mynick) {
		User userinfor = null;
		makeusers();
		for(int i = 0 ; i < users.size() ; i ++) {
			if(users.get(i).Nickname.equals(mynick)) {
				// DB���� �г����� ��ġ�ϴ� ���� ������ ã���� �����ϰ� ���ο� �г������� �ٽ� �߰�
				server.DDB.Deletuser(mynick);
				server.IDB.signup(users.get(i).Id, users.get(i).Password,users.get(i).Name ,users.get(i).Pn, _nick);	
			}
		}
	}
	
	//���� ����Ʈ�� ����� ���
	void makequizlist(String mode) {
		//DB����
		Connection con = null;
		Statement stmt = null;
		String url ="jdbc:mysql://localhost:3306/quizgame?serverTimezone=Asia/Seoul";
		String user = "root";
		String passwd = "1111";
		ResultSet qresult;
		
		try {//try1 ����̺� ����
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(java.lang.ClassNotFoundException e) {
			System.err.println("����̹� �ε� ����: " + e.getMessage());
		}
		
		//�� ��忡 �´� DB�� ���̺��� ������ �������
		try {//try2
			con = DriverManager.getConnection(url,user,passwd);
			stmt = con.createStatement();
			if(mode.equals("1")) {
				quizlist1.clear();
				qresult = stmt.executeQuery("SELECT * FROM quiz1");
				
			}else if(mode.equals("2")){
				quizlist2.clear();
				qresult = stmt.executeQuery("SELECT * FROM quiz2");
				
			}else if(mode.equals("3")){
				quizlist3.clear();
				qresult = stmt.executeQuery("SELECT * FROM quiz3");
				
			}else if(mode.equals("4")){
				quizlist4.clear();
				qresult = stmt.executeQuery("SELECT * FROM quiz4");
				
			}else{
				quizlist5.clear();
				qresult = stmt.executeQuery("SELECT * FROM quiz5");
				
			}
			
			
			
			while(qresult.next()) {
				String qnum = qresult.getString("quiznum");
				String question = qresult.getString("question");
				String hint = qresult.getString("hint");
				String answer = qresult.getString("answer");
				Quiz quiz = new Quiz(qnum,question,hint,answer);
			
				//�� ��忡 �°� ���� ������ ����Ʈ�� �߰�
				if(mode.equals("1")) {
					quizlist1.add(quiz);
				}else if(mode.equals("2")) {
					quizlist2.add(quiz);
				}else if(mode.equals("3")) {
					quizlist3.add(quiz);
				}else if(mode.equals("4")) {
					quizlist4.add(quiz);
				}else{
					quizlist5.add(quiz);
				}
				
			}
			
			
		}catch(SQLException ex) {
			System.out.println("error > Server > SelectDB > try2");
		}finally {// ����
    		try {
			if (stmt != null) stmt.close();
            		if (con != null) con.close();
           	} catch (Exception e) {
           			
           	}
		}
		
		
	}
}
