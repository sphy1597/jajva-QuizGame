import java.io.*;
import java.net.*;
import java.util.*;
import java.util.StringTokenizer;

public class MyConnector {
	//소켓 및 인스트림 아웃스트림 선언
	Socket mySocket = null;
	OutputStream outStream = null;
	DataOutputStream dataOutStream = null;
	InputStream inStream = null;
	DataInputStream dataInStream = null;
	
	//태그 선언
	final String loginTag = "LOG:";
	final String idfindTag = "IDF:";
	final String pwfindTag = "PWF:";
	final String signupTag = "SG:";
	final String modeTag = "M:";
	final String changeTag = "CH:";
	final String chatTag = "C:";
	//나의 닉네임을 저장
	String mynick = null;
	
	ErrorFrame er;
	MsgListener msgl;
	Operator op;
	
	MyConnector(Operator _op){
		op = _op;
		try {
			//서버와 연결하기 위한 설정 	ip = localhost 포트는 60000
			mySocket = new Socket("localhost", 60000);
			System.out.println("Client LOG > 서버로 연결되었습니다.");
			//인스트림 아웃스트림 설정
			outStream = mySocket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = mySocket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			Thread.sleep(100);
			
			
		}catch(Exception e) {
			System.out.println("client > MyConnector > MyConnector");
		}
	}
	
	//로그인을 위해 로그인 정보를 서버에 전송
	boolean sendLoginInfor(String id, String password) {
		boolean flag = false;
		String msg = null;
		try {// 로그인태그//아이디//비밀번호 의형태로 전송
			dataOutStream.writeUTF(loginTag+"//"+id+"//"+password);
			//결과값을 기다림
			msg = dataInStream.readUTF();
			//결과값을 태그와 내용을 분리해
			StringTokenizer stk = new StringTokenizer(msg, "//");
			String result = stk.nextToken();
			String nick = stk.nextToken();
			
			// 내용이 오케이면 닉네임을 설정하고 msgListener 시작하고 true반환
			if(result.equals("LOGIN_OK")) {
				mynick = nick;
				op.msgl = new MsgListener(mySocket, op);
				op.msgl.start();
				flag = true;
			}else {//아니면 실패 false반환
				flag = false;
			}
			
		}catch(Exception e) {
			System.out.println("error > Client > MyConnector > sendLoginInfor");
		}
		return flag;
	}//sendLoginInfor()
	
	// ID찾기를 위한 정보를 서버로 보냄
	String sendIdfind(String name, String pn) {
		String id = null;
		String msg = null;
		try {
			// 아이디찾기태그//이름//연락처 형태로 전송
			dataOutStream.writeUTF(idfindTag+"//"+name+"//"+pn);
			//결과를 기다림
			msg = dataInStream.readUTF();
			//결과가 ERROR!이면 에러를 보내줌
			if(msg.equals("ERROR!")) {
				id = "ERROR!";
			}else {//에러가 아니면 아이디를 보내줌
				id = msg;
			}
			
		}catch(Exception e) {
			System.out.println("error > client > Myconnector > sendidfindInfor");
		}
		return id;
	}
	
	// 비밀번호 찾기위한 정보를 서버로 보냄
	String sendPwfind(String id, String pn) {
		String pw = null;
		String msg = null;
		try {
			// 비밀번호찾기태그//아이디//연락처 의 형태로 보냄
			dataOutStream.writeUTF(pwfindTag + "//" + id + "//" + pn);
			msg = dataInStream.readUTF();
			
			// 에러가 아니면 비밀번호를, 에러면 에러를 리턴
			if(msg.equals("ERROR!")) {
				pw = "ERROR!";
			}else {
				pw = msg;
			}
		}catch(Exception e) {
			System.out.println("client > Myconnector > sendpwfind");
		}
		return pw;
	}
	
	// 회원가입을 위한 정보를 전송
	boolean sendsignup(String id,String pw,String name, String pn,String nickname) {
		String msg = null;
		boolean flag = false;
		
		try {//		회원가입태그//아이디//비밀번호//이름//연락처//닉네임의 형태로 보냄
			dataOutStream.writeUTF(signupTag+"//"+id+"//"+pw+"//"+name +"//" +pn+"//"+nickname);
			msg = dataInStream.readUTF();
			//결과가 오케이면 트루를 리턴 아니면 false를 리턴
			if(msg.equals("SIGNUP_OK")) {
				flag = true;
			}else {
				flag = false;
			}
			
		}catch(Exception e) {
			System.out.println("error > client > Myconnector > sendsignup");
		}
		return flag;
	}
	
	// 서버에 내가 선택한 모드가 어떤것인지 전송
	void sendmode(String mode) {		// 1 2 3 4 5
		try {// 모드태그//모드로 전송
			dataOutStream.writeUTF(modeTag+"//"+mode);	// 1 2 3 4 5
		}catch(Exception e) {
			System.out.println("error > client > Myconnector > sendmode");
		}
	}
	
	//닉네임변경을 위해 닉네임을 전송
	void sendnick(String _nick) {
		try {
			//	변경태그//닉네임으로 보냄
			dataOutStream.writeUTF(changeTag+"//" + _nick);
			
		}catch(Exception e) {
			System.out.println("error > client >  myconnectpr > sendnick");
		}
	}
	
	// 채팅 내용을 전송
	void sendmsg(String msg) {
		//메세지 내용이 입력되었을 때만 전송
		if(!msg.equals("")) {
			//	채팅태그//내용//닉네임으로 보냄
			try {
				dataOutStream.writeUTF(chatTag+"//"+msg+"//"+mynick);
			}catch(Exception e) {
				System.out.println("error > client >  myconnectpr > sendmsg");
			}
		}
		
		
	}
}
