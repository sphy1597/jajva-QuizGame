import java.io.*;
import java.net.*;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ConnectedClient extends Thread {
	
	//���� �� ��Ʈ�� ����
	Socket socket;
	OutputStream outStream;
	DataOutputStream dataOutStream;
	InputStream  inStream;
	DataInputStream dataInStream;
	
	String msg;
	
	Server server;
	SelectDB SDB;
	InsertDB IDB;
	Player myplayer;
	
	String mynick;	// �г��� ����
	String mymode;	// ��� ����
	
	//�±� ����
	final String loginTag = "LOG:";	//�α���
	final String idfindTag = "IDF:";//���̵�ã��
	final String signupTag = "SG:";//ȸ������
	final String pwfindTag = "PWF:";//��й�ȣã��
	final String modeTag = "M:";//���
	final String lobbyTag = "LOB:";//�κ� �����ڼ� + MODE1 2 3 4 5
	final String exitTag = "EX:";//������
	final String changeTag = "CH:"; //�г��� ���� �±�
	final String chatTag = "C:"; // ä��
	final String listTag = "L:";
	final String quizTag = "Q:";
	final String ansTag = "ANS:";
	final String hintTag = "HI:";
	final String cuserTag = "CU:";
	
	ConnectedClient(Socket _socket, Server _server){
		this.socket = _socket;
		this.server = _server;
		SDB = server.SDB;
		IDB = server.IDB;
	}
	
	
	public void run() { //������ ����
	
		try {
			System.out.println("Server > " + this.socket.getInetAddress()+ "���� ����Ǿ����ϴ�.");
			//��Ʈ�� ����
			outStream = this.socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			
			while(true) {
				// �޼����� ������ ��ٸ�
				msg = dataInStream.readUTF();
				StringTokenizer stk = new StringTokenizer(msg, "//");
				//StringTokenizer�� ���� �±׿� ���� ����
				String tag = stk.nextToken();
				
				if(tag.equals(loginTag)) { //�α���
					String id = stk.nextToken();
					String pass = stk.nextToken();
					String logincheck = SDB.checklogin(id, pass);
					mymode = "LOBBY";	//��带 �κ�� ����
					if(logincheck.equals("FAIL")) {	
						//������ ��� �α��� ���и� ������
						dataOutStream.writeUTF("LOGIN_FAIL//fail");
						
					}else {	
						//�����ΰ�� ok�� �Բ� �г����� ������
						dataOutStream.writeUTF("LOGIN_OK//"+logincheck);
						mynick = logincheck;//�г��� ����
						//�÷��̾� ��ü�� ������ ����Ʈ�� �߰�
						myplayer = new Player(logincheck,socket);
						server.LobbyClients.add(myplayer);
						
						//�� ���ӿ� �������� �����ڼ��� ������
						String pnum1 = ""+server.Mode1.size();
						String pnum2 = ""+server.Mode2.size();
						String pnum3 = ""+server.Mode3.size();
						String pnum4 = ""+server.Mode4.size();
						String pnum5 = ""+server.Mode5.size();
						
						dataOutStream.writeUTF(lobbyTag+"//MODE1//"+pnum1);
						dataOutStream.writeUTF(lobbyTag+"//MODE2//"+pnum2);
						dataOutStream.writeUTF(lobbyTag+"//MODE3//"+pnum3);
						dataOutStream.writeUTF(lobbyTag+"//MODE4//"+pnum4);
						dataOutStream.writeUTF(lobbyTag+"//MODE5//"+pnum5);
					}
					
					
				}else if(tag.equals(signupTag)) { //ȸ������
					String id = stk.nextToken();
					String pw = stk.nextToken();
					String name = stk.nextToken();
					String pn = stk.nextToken();
					String nickname = stk.nextToken();
					
					//ȸ�������� Ȯ���ؼ� ok�� fail�� ������
					if(SDB.signupcheck(id, pw, name, pn, nickname)) {
						IDB.signup(id, pw, name, pn, nickname);
						dataOutStream.writeUTF("SIGNUP_OK");
					}else {
						dataOutStream.writeUTF("SIGNUP_FAIL");
					}
				
				}else if(tag.equals(idfindTag)) { //���̵� ã��
					String name = stk.nextToken();
					String pn = stk.nextToken();
					String idfind = SDB.Idfind(name, pn);
					
					// DB�� Ȯ���� ����� ������ ������ �ƴϸ� ã�� ���̵� ������
					if(idfind.equals("ERROR!")) {
						dataOutStream.writeUTF("ERROR!");
					}else {
						dataOutStream.writeUTF(idfind);
					}
					
				}else if(tag.equals(pwfindTag)) { //��й�ȣ ã��
					String id = stk.nextToken();
					String pn = stk.nextToken();
					String pwfind = SDB.Pwfind(id, pn);
					// DB�� Ȯ���� ����� ������ ������ �ƴϸ� ��й�ȣ�� ������
					if(pwfind.equals("ERROR!")) {
						dataOutStream.writeUTF("ERROR!");
					}else {
						dataOutStream.writeUTF(pwfind);
					}
				}else if(tag.equals(changeTag)) {	//�г��� ����
					String nick = stk.nextToken();
					
					//DB�� Ȯ���� ����� true�� ü�����±׷� �г����� ������
					if(SDB.nickcheck(nick)) {
						dataOutStream.writeUTF(changeTag + "//" + nick);
						SDB.changenick(nick, mynick);	//DB�� �г��� ����
						// ����Ʈ���� ã�Ƽ� �г��� ����
						for(int i = 0;i<server.LobbyClients.size();i++) {
							if(server.LobbyClients.get(i).nick.equals(mynick)) {
								server.LobbyClients.get(i).nick = nick;
							}
						}
						mynick = nick;	//�����ϰ� �ִ� �г��� ����
						
					
					}else {	//false�� ������ ������
						dataOutStream.writeUTF(changeTag + "//ERROR!");
					}
					
				}else if(tag.equals(modeTag)) { //��弱��
					String mode = stk.nextToken();
					mymode = mode;			//1 2 3 4 5 �������
					
					// ��� ���� �ȳ� ����
					dataOutStream.writeUTF(quizTag + "//" + " ���⸦ ���� �ʰ� ������ �Է����ּ��� ");
					
					if(mode.equals("1")) {	// 1�� �濡 �� ���
						if(server.Mode1.size()<=0) {		//ù��°�� �濡 ����
							server.QM1 = new QuizMake(server, "1");	//quizmake ������ ������ ����
							server.QM1.start();
							dataOutStream.writeUTF(quizTag + "//" + "5�� �� ������ ���۵˴ϴ�.  " + "\n" + " ���⸦ ���� �ʰ� ������ �Է����ּ��� ");
							SDB.makequizlist("1");	//�����Ʈ�� 1�� ���� ����
						}
						//���1����Ʈ�� �߰�
						server.Mode1.add(myplayer);
						//�κ񸮽�Ʈ���� ����
						int index = server.LobbyClients.indexOf(myplayer);
						server.LobbyClients.remove(index);
						//�κ� �ִ� �����鿡�� ���� ������ ������ �����ڼ��� ������Ŵ
						for(int i = 0;i<server.LobbyClients.size();i++) { //�κ� �ִ� �������� ������ �� �������� �����ֱ�
							try {
								outStream = server.LobbyClients.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								String pnum = ""+server.Mode1.size();
								dataOutStream.writeUTF(lobbyTag + "//MODE1//" + pnum);
							}catch(Exception ex) {
								System.out.println("server > connectedclient > modetag1 > 1");
							}
						}
						//���ӷ뿡 �ִ� �����鿡�� ���� ���� ���̵� ������
						for(int i = 0; i<server.Mode1.size();i++) {	//���ӷ뿡 �ִ� �������� ���� ���� ������ �г����� ������
							try {
								outStream = server.Mode1.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(listTag + "//" + mynick+"_0");
							}catch(Exception e) {
								System.out.println("server > connectedClient > modetag1 > 2");
							}
						}
						
						//������ ���� �ִ� �������� �г����� ������
						for(int i = 0;i<server.Mode1.size();i++) {	//���� ���� �������� ������ ���ӷ뿡 �ִ� ������ �г����� ������
							if(!mynick.equals(server.Mode1.get(i).nick)) {
								dataOutStream.writeUTF(listTag + "//" + server.Mode1.get(i).nick+"_"+server.Mode1.get(i).score);
							}
						}						
						
						//2�� �濡 �� ��� 1���� ����
					}else if(mode.equals("2")) {
						if(server.Mode2.size()<=0) {
							server.QM2 = new QuizMake(server, "2");
							server.QM2.start();
							dataOutStream.writeUTF(quizTag + "//" + "5�� �� ������ ���۵˴ϴ�.");
							SDB.makequizlist("2");
						}
						server.Mode2.add(myplayer);
						int index = server.LobbyClients.indexOf(myplayer);
						server.LobbyClients.remove(index);
						for(int i = 0;i<server.LobbyClients.size();i++) { //�κ� �ִ� �������� ������ �� �������� �����ֱ�
							try {
								outStream = server.LobbyClients.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								String pnum = ""+server.Mode2.size();
								dataOutStream.writeUTF(lobbyTag + "//MODE2//" + pnum);
							}catch(Exception ex) {
								System.out.println("server > connectedclient > modetag2 > 1");
							}
						}
						for(int i = 0; i<server.Mode2.size();i++) {	//���ӷ뿡 �ִ� �������� ���� ���� ������ �г����� ������
							try {
								outStream = server.Mode2.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(listTag + "//" + mynick+"_0");
							}catch(Exception e) {
								System.out.println("server > connectedClient > modetag2 > 2");
							}
						}
						for(int i = 0;i<server.Mode2.size();i++) {	//���� ���� �������� ������ ���ӷ뿡 �ִ� ������ �г����� ������
							if(!mynick.equals(server.Mode2.get(i).nick)) {
								dataOutStream.writeUTF(listTag + "//" + server.Mode2.get(i).nick+"_"+server.Mode2.get(i).score);
							}
						}						
						
						//3���濡 �� ��� 1���� ����
					}else if(mode.equals("3")) {	
						if(server.Mode3.size()<=0) {
							server.QM3 = new QuizMake(server, "3");
							server.QM3.start();
							dataOutStream.writeUTF(quizTag + "//" + "5�� �� ������ ���۵˴ϴ�.");
							SDB.makequizlist("3");
						}
						server.Mode3.add(myplayer);
						int index = server.LobbyClients.indexOf(myplayer);
						server.LobbyClients.remove(index);
						for(int i = 0;i<server.LobbyClients.size();i++) { //�κ� �ִ� �������� ������ �� �������� �����ֱ�
							try {
								outStream = server.LobbyClients.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								String pnum = ""+server.Mode3.size();
								dataOutStream.writeUTF(lobbyTag + "//MODE3//" + pnum);
							}catch(Exception ex) {
								System.out.println("server > connectedclient > modetag3 > 1");
							}
						}
						for(int i = 0; i<server.Mode3.size();i++) {	//���ӷ뿡 �ִ� �������� ���� ���� ������ �г����� ������
							try {
								outStream = server.Mode3.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(listTag + "//" + mynick+"_0");
							}catch(Exception e) {
								System.out.println("server > connectedClient > modetag3 > 2");
							}
						}
						for(int i = 0;i<server.Mode3.size();i++) {	//���� ���� �������� ������ ���ӷ뿡 �ִ� ������ �г����� ������
							if(!mynick.equals(server.Mode3.get(i).nick)) {
								dataOutStream.writeUTF(listTag + "//" + server.Mode3.get(i).nick+"_"+server.Mode3.get(i).score);
							}
						}						
						
						//4���濡 ����� 1���� ����
					}else if(mode.equals("4")) {	// 1�� �濡 �� ���
						if(server.Mode4.size()<=0) {
							server.QM4 = new QuizMake(server, "4");
							server.QM4.start();
							dataOutStream.writeUTF(quizTag + "//" + "5�� �� ������ ���۵˴ϴ�.");
							SDB.makequizlist("4");
						}
						server.Mode4.add(myplayer);
						int index = server.LobbyClients.indexOf(myplayer);
						server.LobbyClients.remove(index);
						for(int i = 0;i<server.LobbyClients.size();i++) { //�κ� �ִ� �������� ������ �� �������� �����ֱ�
							try {
								outStream = server.LobbyClients.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								String pnum = ""+server.Mode4.size();
								dataOutStream.writeUTF(lobbyTag + "//MODE4//" + pnum);
							}catch(Exception ex) {
								System.out.println("server > connectedclient > modetag4 > 1");
							}
						}
						for(int i = 0; i<server.Mode4.size();i++) {	//���ӷ뿡 �ִ� �������� ���� ���� ������ �г����� ������
							try {
								outStream = server.Mode4.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(listTag + "//" + mynick+"_0");
							}catch(Exception e) {
								System.out.println("server > connectedClient > modetag4 > 2");
							}
						}
						for(int i = 0;i<server.Mode4.size();i++) {	//���� ���� �������� ������ ���ӷ뿡 �ִ� ������ �г����� ������
							if(!mynick.equals(server.Mode4.get(i).nick)) {
								dataOutStream.writeUTF(listTag + "//" + server.Mode4.get(i).nick+"_"+server.Mode4.get(i).score);
							}
						}						
						
						//5���濡 �� ��� 1���� ����
					}else if(mode.equals("5")) {	// 1�� �濡 �� ���
						if(server.Mode5.size()<=0) {
							server.QM5 = new QuizMake(server, "5");
							server.QM5.start();
							dataOutStream.writeUTF(quizTag + "//" + "5�� �� ������ ���۵˴ϴ�.");
							SDB.makequizlist("5");
						}
						server.Mode5.add(myplayer);
						int index = server.LobbyClients.indexOf(myplayer);
						server.LobbyClients.remove(index);
						for(int i = 0;i<server.LobbyClients.size();i++) { //�κ� �ִ� �������� ������ �� �������� �����ֱ�
							try {
								outStream = server.LobbyClients.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								String pnum = ""+server.Mode5.size();
								dataOutStream.writeUTF(lobbyTag + "//MODE5//" + pnum);
							}catch(Exception ex) {
								System.out.println("server > connectedclient > modetag5 > 1");
							}
						}
						for(int i = 0; i<server.Mode5.size();i++) {	//���ӷ뿡 �ִ� �������� ���� ���� ������ �г����� ������
							try {
								outStream = server.Mode5.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(listTag + "//" + mynick+"_0");
							}catch(Exception e) {
								System.out.println("server > connectedClient > modetag5 > 2");
							}
						}
						for(int i = 0;i<server.Mode5.size();i++) {	//���� ���� �������� ������ ���ӷ뿡 �ִ� ������ �г����� ������
							if(!mynick.equals(server.Mode5.get(i).nick)) {
								dataOutStream.writeUTF(listTag + "//" + server.Mode5.get(i).nick+"_"+server.Mode5.get(i).score);
							}
						}						
						
					}//��弱��
					
				}else if(tag.equals(chatTag)) { //ä��
					
					String msg = stk.nextToken();
					String unick = stk.nextToken();
					
					// ���� 1���濡 ������ ä���� 1���濡 �ѷ���
					if(mymode.equals("1")) { //ä�� 1
						for(int i=0;i<server.Mode1.size();i++) {
							try {
								outStream = server.Mode1.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(chatTag+"//"+unick+" : "+msg);							
							}catch(Exception ex) {
								System.out.println("server > connectedclient > chat > 1" );
							}
						}
						
						//������ ����Ʈ�ȿ� ���� �ε��� ã��
						int myindex=0;	
						for(int i=0;i<server.Mode1.size();i++) {
							if(server.Mode1.get(i).nick.equals(unick)) {
								myindex=i;
							}
						}
			
						//������ ������ �� ó�� ���
						if(msg.equals(server.QM1.nans)) {
							
							//�� ���ھ 1���ϸ鼭 �ٸ� Ŭ���̾�Ʈ�� ������ ������ �߰��� ������ �ѹ��� �����ֱ� ���ؼ� > string���� ������ ������ Ŭ���̾�Ʈ���� �ٲܼ� ���⶧����
							int nowscore = server.Mode1.get(myindex).score;
							server.Mode1.get(myindex).score++;
							int plusscore = server.Mode1.get(myindex).score;
							
							//1����忡 �������� �����鿡�� ����� �г����� ������
							for(int i = 0 ; i < server.Mode1.size() ; i++ ) {
								try {
									outStream = server.Mode1.get(i).socket.getOutputStream();
									dataOutStream = new DataOutputStream(outStream);
									dataOutStream.writeUTF(ansTag+"//"+unick+" : "+msg);
									// ���� ����� ���ھ ���� ������
									dataOutStream.writeUTF(cuserTag+"//"+unick+"//"+nowscore+"//"+plusscore);
		
								}catch(Exception e) {
									System.out.println(e.getMessage());
									System.out.println("server > connectedclient > chat > nans > 1");
								}
							}
							//���� ������ ��������Ʈ���� ����
							for(int i=0;i<server.SDB.quizlist1.size();i++){
								if(server.SDB.quizlist1.get(i).Answer.equals(msg)) {
									server.SDB.quizlist1.remove(i);
								}
							}
							//�������� �����带 �����Ų�� �ٽ� ���� 
							server.QM1.interrupt();
							server.QM1 = new QuizMake(server, "1");
							server.QM1.start();
						}
						
						
						// 2���濡 ���� �� ä�� 1���� ����
					}else if(mymode.equals("2")) {
						for(int i=0;i<server.Mode2.size();i++) {
							try {
								outStream = server.Mode2.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(chatTag+"//"+unick+" : "+msg);							
							}catch(Exception ex) {
								System.out.println("server > connectedclient > chat > 2 " );
							}
						}
						
						//������ ����Ʈ�ȿ� ���� �ε��� ã��
						int myindex=0;	
						for(int i=0;i<server.Mode2.size();i++) {
							if(server.Mode2.get(i).nick.equals(unick)) {
								myindex=i;
							}
						}
			
						//������ ������ �� ó�� ���
						if(msg.equals(server.QM2.nans)) {
							
							//�� ���ھ 1���ϸ鼭 �ٸ� Ŭ���̾�Ʈ�� ������ ������ �߰��� ������ �ѹ��� �����ֱ� ���ؼ� > string���� ������ ������ Ŭ���̾�Ʈ���� �ٲܼ� ���⶧����
							int nowscore = server.Mode2.get(myindex).score;
							System.out.println("nowscore : " + nowscore);
							server.Mode2.get(myindex).score++;
							int plusscore = server.Mode2.get(myindex).score;
							System.out.println("plusscore : " + plusscore);
							
							for(int i = 0 ; i < server.Mode2.size() ; i++ ) {
								try {
									outStream = server.Mode2.get(i).socket.getOutputStream();
									dataOutStream = new DataOutputStream(outStream);
									dataOutStream.writeUTF(ansTag+"//"+unick+" : "+msg);
										
									dataOutStream.writeUTF(cuserTag+"//"+unick+"//"+nowscore+"//"+plusscore);
		
								}catch(Exception e) {
									System.out.println(e.getMessage());
									System.out.println("server > connectedclient > chat > nans > 1");
								}
							}
							for(int i=0;i<server.SDB.quizlist2.size();i++){
								if(server.SDB.quizlist2.get(i).Answer.equals(msg)) {
									server.SDB.quizlist2.remove(i);
								}
							}
							server.QM2.interrupt();
							server.QM2 = new QuizMake(server, "2");
							server.QM2.start();
						}
							
						// 3���濡 ������� ä�� 1���� ����
					}else if(mymode.equals("3")) { //ä�� 1
						for(int i=0;i<server.Mode3.size();i++) {
							try {
								outStream = server.Mode3.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(chatTag+"//"+unick+" : "+msg);							
							}catch(Exception ex) {
								System.out.println("server > connectedclient > chat > 3 " );
							}
						}
						
						//������ ����Ʈ�ȿ� ���� �ε��� ã��
						int myindex=0;	
						for(int i=0;i<server.Mode3.size();i++) {
							if(server.Mode3.get(i).nick.equals(unick)) {
								myindex=i;
							}
						}
			
						//������ ������ �� ó�� ���
						if(msg.equals(server.QM3.nans)) {
							
							//�� ���ھ 1���ϸ鼭 �ٸ� Ŭ���̾�Ʈ�� ������ ������ �߰��� ������ �ѹ��� �����ֱ� ���ؼ� > string���� ������ ������ Ŭ���̾�Ʈ���� �ٲܼ� ���⶧����
							int nowscore = server.Mode3.get(myindex).score;
							server.Mode3.get(myindex).score++;
							int plusscore = server.Mode3.get(myindex).score;
							
							for(int i = 0 ; i < server.Mode3.size() ; i++ ) {
								try {
									outStream = server.Mode3.get(i).socket.getOutputStream();
									dataOutStream = new DataOutputStream(outStream);
									dataOutStream.writeUTF(ansTag+"//"+unick+" : "+msg);
										
									dataOutStream.writeUTF(cuserTag+"//"+unick+"//"+nowscore+"//"+plusscore);
		
								}catch(Exception e) {
									System.out.println(e.getMessage());
									System.out.println("server > connectedclient > chat > nans > 3");
								}
							}
							for(int i=0;i<server.SDB.quizlist3.size();i++){
								if(server.SDB.quizlist3.get(i).Answer.equals(msg)) {
									server.SDB.quizlist3.remove(i);
								}
							}
							server.QM3.interrupt();
							server.QM3 = new QuizMake(server, "3");
							server.QM3.start();
						}
						// 4�� ��忡 ���� �� ä��ó�� 1���� ����
					}else if(mymode.equals("4")) { //ä�� 1
						for(int i=0;i<server.Mode4.size();i++) {
							try {
								outStream = server.Mode4.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(chatTag+"//"+unick+" : "+msg);							
							}catch(Exception ex) {
								System.out.println("server > connectedclient > chat > 4 " );
							}
						}
						
						//������ ����Ʈ�ȿ� ���� �ε��� ã��
						int myindex=0;	
						for(int i=0;i<server.Mode4.size();i++) {
							if(server.Mode4.get(i).nick.equals(unick)) {
								myindex=i;
							}
						}
			
						//������ ������ �� ó�� ���
						if(msg.equals(server.QM4.nans)) {
							
							//�� ���ھ 1���ϸ鼭 �ٸ� Ŭ���̾�Ʈ�� ������ ������ �߰��� ������ �ѹ��� �����ֱ� ���ؼ� > string���� ������ ������ Ŭ���̾�Ʈ���� �ٲܼ� ���⶧����
							int nowscore = server.Mode4.get(myindex).score;
							System.out.println("nowscore : " + nowscore);
							server.Mode4.get(myindex).score++;
							int plusscore = server.Mode4.get(myindex).score;
							System.out.println("plusscore : " + plusscore);
							
							for(int i = 0 ; i < server.Mode4.size() ; i++ ) {
								try {
									outStream = server.Mode4.get(i).socket.getOutputStream();
									dataOutStream = new DataOutputStream(outStream);
									dataOutStream.writeUTF(ansTag+"//"+unick+" : "+msg);
										
									dataOutStream.writeUTF(cuserTag+"//"+unick+"//"+nowscore+"//"+plusscore);
		
								}catch(Exception e) {
									System.out.println(e.getMessage());
									System.out.println("server > connectedclient > chat > nans > 4");
								}
							}
							for(int i=0;i<server.SDB.quizlist4.size();i++){
								if(server.SDB.quizlist4.get(i).Answer.equals(msg)) {
									server.SDB.quizlist4.remove(i);
								}
							}
							server.QM4.interrupt();
							server.QM4 = new QuizMake(server, "4");
							server.QM4.start();
						}
						// 5���濡 ���� ��� ä�� ó�� 1���� ����
					}else if(mymode.equals("5")) { //ä�� 5
						for(int i=0;i<server.Mode5.size();i++) {
							try {
								outStream = server.Mode5.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(chatTag+"//"+unick+" : "+msg);							
							}catch(Exception ex) {
								System.out.println("server > connectedclient > chat > 5 " );
							}
						}
						
						//������ ����Ʈ�ȿ� ���� �ε��� ã��
						int myindex=0;	
						for(int i=0;i<server.Mode5.size();i++) {
							if(server.Mode5.get(i).nick.equals(unick)) {
								myindex=i;
							}
						}
			
						//������ ������ �� ó�� ���
						if(msg.equals(server.QM5.nans)) {
							
							//�� ���ھ 1���ϸ鼭 �ٸ� Ŭ���̾�Ʈ�� ������ ������ �߰��� ������ �ѹ��� �����ֱ� ���ؼ� > string���� ������ ������ Ŭ���̾�Ʈ���� �ٲܼ� ���⶧����
							int nowscore = server.Mode5.get(myindex).score;
							System.out.println("nowscore : " + nowscore);
							server.Mode5.get(myindex).score++;
							int plusscore = server.Mode5.get(myindex).score;
							System.out.println("plusscore : " + plusscore);
							
							for(int i = 0 ; i < server.Mode5.size() ; i++ ) {
								try {
									outStream = server.Mode5.get(i).socket.getOutputStream();
									dataOutStream = new DataOutputStream(outStream);
									dataOutStream.writeUTF(ansTag+"//"+unick+" : "+msg);
										
									dataOutStream.writeUTF(cuserTag+"//"+unick+"//"+nowscore+"//"+plusscore);
		
								}catch(Exception e) {
									System.out.println(e.getMessage());
									System.out.println("server > connectedclient > chat > nans > 5");
								}
							}
							for(int i=0;i<server.SDB.quizlist5.size();i++){
								if(server.SDB.quizlist5.get(i).Answer.equals(msg)) {
									server.SDB.quizlist5.remove(i);
								}
							}
							server.QM5.interrupt();
							server.QM5 = new QuizMake(server, "5");
							server.QM5.start();
						}

					}//ä��
					
				}
			}//while
			
			
		}catch(IOException e) {
			
			System.out.println("server > connectedclient > ����");
			
			// ������ �������� ��
			
			// ���� �κ� �־��� �� 
			if(mymode.equals("LOBBY")) {	// �κ񸮽�Ʈ���� ����
				int index = server.LobbyClients.indexOf(myplayer);
				server.LobbyClients.remove(index);
				
			}
			// 1���濡 �־��� ��
			if(mymode.equals("1")) {
				// ���1�� ����Ʈ���� ���� �ε����� ã�Ƽ�
				int index = server.Mode1.indexOf(myplayer);
				
				//���1���� �����鿡�� �������±׷� �г��Ӱ� ���ھ ������
				for(int i=0;i<server.Mode1.size();i++) {
					try {
						outStream = server.Mode1.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						dataOutStream.writeUTF(exitTag + "//" + mynick+"_"+server.Mode1.get(index).score);
					}catch(Exception ex) {
						System.out.println("server > connectedclient > exit mode1-1" );
					}
				}
				//���1�� ����Ʈ���� ����
				server.Mode1.remove(index);
				// �ƹ��� ������ ������ ����
				if(server.Mode1.size()<=0) {
					server.QM1.interrupt();
				}
				//�κ��� �����鿡�� �ش����� �ο��� �ٿ���
				for(int i = 0;i<server.LobbyClients.size();i++) {
					try {
						outStream = server.LobbyClients.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						String pnum = "" + server.Mode1.size();
						dataOutStream.writeUTF(lobbyTag + "//MODE1//" + pnum);
					}catch(Exception ex) {
						System.out.println("server > connectedclient > exit mode1-2" );
					}
				}
				
				//������ ����
				server.c.interrupt();
				
				//2���濡�� ������ ��� 1���� ����
			}else if(mymode.equals("2")) {
				
				int index = server.Mode2.indexOf(myplayer);
				for(int i=0;i<server.Mode2.size();i++) {
					try {
						outStream = server.Mode2.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						dataOutStream.writeUTF(exitTag + "//" + mynick+"_"+server.Mode2.get(index).score);
					}catch(Exception ex) {
						System.out.println("server > connectedclient > exit mode2-1" );
					}
				}
				server.Mode2.remove(index);
				if(server.Mode2.size()<=0) {
					server.QM2.interrupt();
				}
				
				for(int i = 0;i<server.LobbyClients.size();i++) {
					try {
						outStream = server.LobbyClients.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						String pnum = "" + server.Mode2.size();
						dataOutStream.writeUTF(lobbyTag + "//MODE2//" + pnum);
					}catch(Exception ex) {
						System.out.println("server > connectedclient > exit mode2-2" );
					}
				}
				
				server.c.interrupt();
				
				//3���濡�� ������ ��� 1���� ����
			}else if(mymode.equals("3")) {
				
				int index = server.Mode3.indexOf(myplayer);
				for(int i=0;i<server.Mode3.size();i++) {
					try {
						outStream = server.Mode3.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						dataOutStream.writeUTF(exitTag + "//" + mynick+"_"+server.Mode3.get(index).score);
					}catch(Exception ex) {
						System.out.println("server > connectedclient > exit mode3-1" );
					}
				}
				server.Mode3.remove(index);
				if(server.Mode3.size()<=0) {
					server.QM3.interrupt();
				}
				for(int i = 0;i<server.LobbyClients.size();i++) {
					try {
						outStream = server.LobbyClients.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						String pnum = "" + server.Mode3.size();
						dataOutStream.writeUTF(lobbyTag + "//MODE3//" + pnum);
					}catch(Exception ex) {
						System.out.println("server > connectedclient > exit mode3-2" );
					}
				}
				
			
				
				server.c.interrupt();
				
				//4���濡�� ������ ��� 1���� ����
			}else if(mymode.equals("4")) {
				
				int index = server.Mode4.indexOf(myplayer);
				for(int i=0;i<server.Mode4.size();i++) {
					try {
						outStream = server.Mode4.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						dataOutStream.writeUTF(exitTag + "//" + mynick+"_"+server.Mode4.get(index).score);
					}catch(Exception ex) {
						System.out.println("server > connectedclient > exit mode4-1" );
					}
				}
				server.Mode4.remove(index);
				if(server.Mode4.size()<=0) {
					server.QM4.interrupt();
				}
				
				for(int i = 0;i<server.LobbyClients.size();i++) {
					try {
						outStream = server.LobbyClients.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						String pnum = "" + server.Mode4.size();
						dataOutStream.writeUTF(lobbyTag + "//MODE4//" + pnum);
					}catch(Exception ex) {
						System.out.println("server > connectedclient > exit mode4-2" );
					}
				}
				

				server.c.interrupt();
				
				//5���濡�� ������ ��� 1���� ����
			}else if(mymode.equals("5")) {
				
				int index = server.Mode5.indexOf(myplayer);
				for(int i=0;i<server.Mode5.size();i++) {
					try {
						outStream = server.Mode5.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						dataOutStream.writeUTF(exitTag + "//" + mynick+"_"+server.Mode5.get(index).score);
					}catch(Exception ex) {
						System.out.println("server > connectedclient > exit mode5-1" );
					}
				}

				server.Mode5.remove(index);
				if(server.Mode5.size()<=0) {
					server.QM5.interrupt();
				}
				for(int i = 0;i<server.LobbyClients.size();i++) {
					try {
						outStream = server.LobbyClients.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						String pnum = "" + server.Mode5.size();
						dataOutStream.writeUTF(lobbyTag + "//MODE5//" + pnum);
					}catch(Exception ex) {
						System.out.println("server > connectedclient > exit mode5-2" );
					}
				}
				
				server.c.interrupt();
				
				
			}
			
			
		}
		
		
	}
	
	

}
