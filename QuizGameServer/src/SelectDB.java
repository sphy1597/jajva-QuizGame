import java.sql.*;
import java.util.*;

public class SelectDB {
	
	//유저정보를 관리하기 위한 리스트
	ArrayList<User> users = new ArrayList<User>();
	
	//문제들을 관리하기 위한 리스트
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
	
	// 유저정보 리스트를 만드는 기능
	void makeusers(){
		// DB접속
		Connection con = null;
		Statement stmt = null;
		String url ="jdbc:mysql://localhost:3306/quizgame?serverTimezone=Asia/Seoul";
		String user = "root";
		String passwd = "1111";
		
		//새롭게 리스트를 만들기위해 초기화
		users.clear();
		
		//드라이브설정
		try {//try1
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(java.lang.ClassNotFoundException e) {
			System.out.println("Server > SelectDB> try1");
			System.err.print("ClassNotFoundException: "); 
			System.err.println("드라이버 로딩 오류: " + e.getMessage());
		}
		
		try {//try2
			con = DriverManager.getConnection(url,user,passwd);	// DB에 연결
			stmt = con.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM user");
			
			//DB에 저장된 내용을 각가 읽어 들여서 User객체로 만들어 리스트에 저장
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
		}finally {	// 정리
    		try {
			if (stmt != null) stmt.close();
            		if (con != null) con.close();
           	} catch (Exception e) {
           			
           	}
		}
		
		
	}//selectDB()
	
	//로그인 성공가능 여부를 체크
	String checklogin(String _id, String _password) {
		String flag = "FAIL";
		
		//유저리스트를 만들어서
		makeusers();
		
		//유저리스트에 일치하는 정보가 있는지확인 해서 있으면 해당 닉네임을 반환
		for(int i = 0; i < users.size();i++){
			if(users.get(i).Id.equals(_id)&&users.get(i).Password.equals(_password)) {
				System.out.println(users.get(i).Id + " " + users.get(i).Password);
				flag = users.get(i).Nickname;
			}
		}
		
		
		//이미 로비또는 게임방에 참여중인지 확인하는 기능 
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
	

	// 회원가입을 위한 체크
	boolean signupcheck(String _id, String _pw, String _name, String _pn,String _nn) {
		boolean flag = true;
		//유저리스트를 만듬
		makeusers();
	
		//리스트에 일치하는 정보가 있는지 확인
		for(int i = 0; i < users.size();i++){
			if(users.get(i).Id.equals(_id)||users.get(i).Nickname.equals(_nn)||users.get(i).Name.equals(_name)||users.get(i).Pn.equals(_pn)) {
				flag = false;
			}
		}
		
		return flag;
	}
	
	// 아이디찾기 기능
	String Idfind(String _name, String _pn) {
		String id = "ERROR!";
		//유저리스트를 생성
		makeusers();
		
		// 리스트에 일치하는 정보가 있는지 확인 있으면 아이디를 리턴
		for(int i = 0;i <users.size();i++) {
			if(users.get(i).Name.equals(_name) && users.get(i).Pn.equals(_pn)) {
				id = users.get(i).Id;
			}
		}
		
		return id;
	}
	
	//비밀번호 찾기 기능
	String Pwfind(String _id, String _pn) {
		String pw = "ERROR!";
		//유저리스트 생성
		makeusers();
		
		//리스트에 일치하는 정보가 있는지 확인 있으면 비밀번호를 리턴
		for(int i = 0 ; i < users.size();i++) {
			if(users.get(i).Id.equals(_id) && users.get(i).Pn.equals(_pn)) {
				pw = users.get(i).Password;
			}
		}
		return pw;
	}
	
	//닉네임 변겨을 위한 체크
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
	
	//닉네임 변경 기능
	void changenick(String _nick,String mynick) {
		User userinfor = null;
		makeusers();
		for(int i = 0 ; i < users.size() ; i ++) {
			if(users.get(i).Nickname.equals(mynick)) {
				// DB에서 닉네임이 일치하는 유저 정보를 찾은뒤 삭제하고 새로운 닉네임으로 다시 추가
				server.DDB.Deletuser(mynick);
				server.IDB.signup(users.get(i).Id, users.get(i).Password,users.get(i).Name ,users.get(i).Pn, _nick);	
			}
		}
	}
	
	//퀴즈 리스트를 만드는 기능
	void makequizlist(String mode) {
		//DB연결
		Connection con = null;
		Statement stmt = null;
		String url ="jdbc:mysql://localhost:3306/quizgame?serverTimezone=Asia/Seoul";
		String user = "root";
		String passwd = "1111";
		ResultSet qresult;
		
		try {//try1 드라이브 설정
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(java.lang.ClassNotFoundException e) {
			System.err.println("드라이버 로딩 오류: " + e.getMessage());
		}
		
		//각 모드에 맞는 DB의 테이블에서 정보를 가지고옴
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
			
				//각 모드에 맞게 퀴즈 내용을 리스트에 추가
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
		}finally {// 종료
    		try {
			if (stmt != null) stmt.close();
            		if (con != null) con.close();
           	} catch (Exception e) {
           			
           	}
		}
		
		
	}
}
