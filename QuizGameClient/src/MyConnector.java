import java.io.*;
import java.net.*;
import java.util.*;
import java.util.StringTokenizer;

public class MyConnector {
	//���� �� �ν�Ʈ�� �ƿ���Ʈ�� ����
	Socket mySocket = null;
	OutputStream outStream = null;
	DataOutputStream dataOutStream = null;
	InputStream inStream = null;
	DataInputStream dataInStream = null;
	
	//�±� ����
	final String loginTag = "LOG:";
	final String idfindTag = "IDF:";
	final String pwfindTag = "PWF:";
	final String signupTag = "SG:";
	final String modeTag = "M:";
	final String changeTag = "CH:";
	final String chatTag = "C:";
	//���� �г����� ����
	String mynick = null;
	
	ErrorFrame er;
	MsgListener msgl;
	Operator op;
	
	MyConnector(Operator _op){
		op = _op;
		try {
			//������ �����ϱ� ���� ���� 	ip = localhost ��Ʈ�� 60000
			mySocket = new Socket("localhost", 60000);
			System.out.println("Client LOG > ������ ����Ǿ����ϴ�.");
			//�ν�Ʈ�� �ƿ���Ʈ�� ����
			outStream = mySocket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = mySocket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			Thread.sleep(100);
			
			
		}catch(Exception e) {
			System.out.println("client > MyConnector > MyConnector");
		}
	}
	
	//�α����� ���� �α��� ������ ������ ����
	boolean sendLoginInfor(String id, String password) {
		boolean flag = false;
		String msg = null;
		try {// �α����±�//���̵�//��й�ȣ �����·� ����
			dataOutStream.writeUTF(loginTag+"//"+id+"//"+password);
			//������� ��ٸ�
			msg = dataInStream.readUTF();
			//������� �±׿� ������ �и���
			StringTokenizer stk = new StringTokenizer(msg, "//");
			String result = stk.nextToken();
			String nick = stk.nextToken();
			
			// ������ �����̸� �г����� �����ϰ� msgListener �����ϰ� true��ȯ
			if(result.equals("LOGIN_OK")) {
				mynick = nick;
				op.msgl = new MsgListener(mySocket, op);
				op.msgl.start();
				flag = true;
			}else {//�ƴϸ� ���� false��ȯ
				flag = false;
			}
			
		}catch(Exception e) {
			System.out.println("error > Client > MyConnector > sendLoginInfor");
		}
		return flag;
	}//sendLoginInfor()
	
	// IDã�⸦ ���� ������ ������ ����
	String sendIdfind(String name, String pn) {
		String id = null;
		String msg = null;
		try {
			// ���̵�ã���±�//�̸�//����ó ���·� ����
			dataOutStream.writeUTF(idfindTag+"//"+name+"//"+pn);
			//����� ��ٸ�
			msg = dataInStream.readUTF();
			//����� ERROR!�̸� ������ ������
			if(msg.equals("ERROR!")) {
				id = "ERROR!";
			}else {//������ �ƴϸ� ���̵� ������
				id = msg;
			}
			
		}catch(Exception e) {
			System.out.println("error > client > Myconnector > sendidfindInfor");
		}
		return id;
	}
	
	// ��й�ȣ ã������ ������ ������ ����
	String sendPwfind(String id, String pn) {
		String pw = null;
		String msg = null;
		try {
			// ��й�ȣã���±�//���̵�//����ó �� ���·� ����
			dataOutStream.writeUTF(pwfindTag + "//" + id + "//" + pn);
			msg = dataInStream.readUTF();
			
			// ������ �ƴϸ� ��й�ȣ��, ������ ������ ����
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
	
	// ȸ�������� ���� ������ ����
	boolean sendsignup(String id,String pw,String name, String pn,String nickname) {
		String msg = null;
		boolean flag = false;
		
		try {//		ȸ�������±�//���̵�//��й�ȣ//�̸�//����ó//�г����� ���·� ����
			dataOutStream.writeUTF(signupTag+"//"+id+"//"+pw+"//"+name +"//" +pn+"//"+nickname);
			msg = dataInStream.readUTF();
			//����� �����̸� Ʈ�縦 ���� �ƴϸ� false�� ����
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
	
	// ������ ���� ������ ��尡 ������� ����
	void sendmode(String mode) {		// 1 2 3 4 5
		try {// ����±�//���� ����
			dataOutStream.writeUTF(modeTag+"//"+mode);	// 1 2 3 4 5
		}catch(Exception e) {
			System.out.println("error > client > Myconnector > sendmode");
		}
	}
	
	//�г��Ӻ����� ���� �г����� ����
	void sendnick(String _nick) {
		try {
			//	�����±�//�г������� ����
			dataOutStream.writeUTF(changeTag+"//" + _nick);
			
		}catch(Exception e) {
			System.out.println("error > client >  myconnectpr > sendnick");
		}
	}
	
	// ä�� ������ ����
	void sendmsg(String msg) {
		//�޼��� ������ �ԷµǾ��� ���� ����
		if(!msg.equals("")) {
			//	ä���±�//����//�г������� ����
			try {
				dataOutStream.writeUTF(chatTag+"//"+msg+"//"+mynick);
			}catch(Exception e) {
				System.out.println("error > client >  myconnectpr > sendmsg");
			}
		}
		
		
	}
}
