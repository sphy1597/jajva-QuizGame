import java.io.*;
import java.net.*;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ConnectedClient extends Thread {
	
	//소켓 및 스트림 선언
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
	
	String mynick;	// 닉네임 저장
	String mymode;	// 모드 저장
	
	//태그 설정
	final String loginTag = "LOG:";	//로그인
	final String idfindTag = "IDF:";//아이디찾기
	final String signupTag = "SG:";//회원가입
	final String pwfindTag = "PWF:";//비밀번호찾기
	final String modeTag = "M:";//모드
	final String lobbyTag = "LOB:";//로비에 접속자수 + MODE1 2 3 4 5
	final String exitTag = "EX:";//나가기
	final String changeTag = "CH:"; //닉네임 변경 태그
	final String chatTag = "C:"; // 채팅
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
	
	
	public void run() { //스레드 시작
	
		try {
			System.out.println("Server > " + this.socket.getInetAddress()+ "에서 연결되었습니다.");
			//스트림 설정
			outStream = this.socket.getOutputStream();
			dataOutStream = new DataOutputStream(outStream);
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			
			while(true) {
				// 메세지가 들어오길 기다림
				msg = dataInStream.readUTF();
				StringTokenizer stk = new StringTokenizer(msg, "//");
				//StringTokenizer을 통해 태그와 내용 구분
				String tag = stk.nextToken();
				
				if(tag.equals(loginTag)) { //로그인
					String id = stk.nextToken();
					String pass = stk.nextToken();
					String logincheck = SDB.checklogin(id, pass);
					mymode = "LOBBY";	//모드를 로비로 설정
					if(logincheck.equals("FAIL")) {	
						//실패인 경우 로그인 실패를 보내줌
						dataOutStream.writeUTF("LOGIN_FAIL//fail");
						
					}else {	
						//성공인경우 ok와 함께 닉네임을 보내줌
						dataOutStream.writeUTF("LOGIN_OK//"+logincheck);
						mynick = logincheck;//닉네임 저장
						//플레이어 객체를 생성해 리스트에 추가
						myplayer = new Player(logincheck,socket);
						server.LobbyClients.add(myplayer);
						
						//각 게임에 참여중인 참여자수를 보내줌
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
					
					
				}else if(tag.equals(signupTag)) { //회원가입
					String id = stk.nextToken();
					String pw = stk.nextToken();
					String name = stk.nextToken();
					String pn = stk.nextToken();
					String nickname = stk.nextToken();
					
					//회원정보를 확인해서 ok와 fail을 보내줌
					if(SDB.signupcheck(id, pw, name, pn, nickname)) {
						IDB.signup(id, pw, name, pn, nickname);
						dataOutStream.writeUTF("SIGNUP_OK");
					}else {
						dataOutStream.writeUTF("SIGNUP_FAIL");
					}
				
				}else if(tag.equals(idfindTag)) { //아이디 찾기
					String name = stk.nextToken();
					String pn = stk.nextToken();
					String idfind = SDB.Idfind(name, pn);
					
					// DB에 확인한 결과가 에러면 에러를 아니면 찾은 아이디를 보내줌
					if(idfind.equals("ERROR!")) {
						dataOutStream.writeUTF("ERROR!");
					}else {
						dataOutStream.writeUTF(idfind);
					}
					
				}else if(tag.equals(pwfindTag)) { //비밀번호 찾기
					String id = stk.nextToken();
					String pn = stk.nextToken();
					String pwfind = SDB.Pwfind(id, pn);
					// DB에 확인한 결과가 에러면 에러를 아니면 비밀번호를 보내줌
					if(pwfind.equals("ERROR!")) {
						dataOutStream.writeUTF("ERROR!");
					}else {
						dataOutStream.writeUTF(pwfind);
					}
				}else if(tag.equals(changeTag)) {	//닉네임 변경
					String nick = stk.nextToken();
					
					//DB에 확인한 결과가 true면 체인지태그로 닉네임을 보내줌
					if(SDB.nickcheck(nick)) {
						dataOutStream.writeUTF(changeTag + "//" + nick);
						SDB.changenick(nick, mynick);	//DB에 닉네임 변경
						// 리스트에서 찾아서 닉네임 변경
						for(int i = 0;i<server.LobbyClients.size();i++) {
							if(server.LobbyClients.get(i).nick.equals(mynick)) {
								server.LobbyClients.get(i).nick = nick;
							}
						}
						mynick = nick;	//저장하고 있는 닉네임 변경
						
					
					}else {	//false면 에러를 보내줌
						dataOutStream.writeUTF(changeTag + "//ERROR!");
					}
					
				}else if(tag.equals(modeTag)) { //모드선택
					String mode = stk.nextToken();
					mymode = mode;			//1 2 3 4 5 모드저장
					
					// 퀴즈에 대한 안내 전송
					dataOutStream.writeUTF(quizTag + "//" + " 띄어쓰기를 하지 않고 정답을 입력해주세요 ");
					
					if(mode.equals("1")) {	// 1번 방에 들어간 경우
						if(server.Mode1.size()<=0) {		//첫번째로 방에 입장
							server.QM1 = new QuizMake(server, "1");	//quizmake 스레드 생성후 시작
							server.QM1.start();
							dataOutStream.writeUTF(quizTag + "//" + "5초 뒤 게임이 시작됩니다.  " + "\n" + " 띄어쓰기를 하지 않고 정답을 입력해주세요 ");
							SDB.makequizlist("1");	//퀴즈리스트를 1번 모드로 만듬
						}
						//모드1리스트에 추가
						server.Mode1.add(myplayer);
						//로비리스트에서 삭제
						int index = server.LobbyClients.indexOf(myplayer);
						server.LobbyClients.remove(index);
						//로비에 있는 유저들에게 내가 참여한 게임의 참여자수를 증가시킴
						for(int i = 0;i<server.LobbyClients.size();i++) { //로비에 있는 유저에게 접속자 수 증가시켜 보내주기
							try {
								outStream = server.LobbyClients.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								String pnum = ""+server.Mode1.size();
								dataOutStream.writeUTF(lobbyTag + "//MODE1//" + pnum);
							}catch(Exception ex) {
								System.out.println("server > connectedclient > modetag1 > 1");
							}
						}
						//게임룸에 있던 유저들에게 들어온 나의 아이디를 보내줌
						for(int i = 0; i<server.Mode1.size();i++) {	//게임룸에 있는 유저에게 새로 들어온 유저의 닉네임을 보내줌
							try {
								outStream = server.Mode1.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(listTag + "//" + mynick+"_0");
							}catch(Exception e) {
								System.out.println("server > connectedClient > modetag1 > 2");
							}
						}
						
						//나에게 원래 있던 유저들의 닉네임을 보내줌
						for(int i = 0;i<server.Mode1.size();i++) {	//새로 들어온 유저에게 기존에 게임룸에 있던 유저의 닉네임을 보내줌
							if(!mynick.equals(server.Mode1.get(i).nick)) {
								dataOutStream.writeUTF(listTag + "//" + server.Mode1.get(i).nick+"_"+server.Mode1.get(i).score);
							}
						}						
						
						//2번 방에 들어간 경우 1번과 동일
					}else if(mode.equals("2")) {
						if(server.Mode2.size()<=0) {
							server.QM2 = new QuizMake(server, "2");
							server.QM2.start();
							dataOutStream.writeUTF(quizTag + "//" + "5초 뒤 게임이 시작됩니다.");
							SDB.makequizlist("2");
						}
						server.Mode2.add(myplayer);
						int index = server.LobbyClients.indexOf(myplayer);
						server.LobbyClients.remove(index);
						for(int i = 0;i<server.LobbyClients.size();i++) { //로비에 있는 유저에게 접속자 수 증가시켜 보내주기
							try {
								outStream = server.LobbyClients.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								String pnum = ""+server.Mode2.size();
								dataOutStream.writeUTF(lobbyTag + "//MODE2//" + pnum);
							}catch(Exception ex) {
								System.out.println("server > connectedclient > modetag2 > 1");
							}
						}
						for(int i = 0; i<server.Mode2.size();i++) {	//게임룸에 있는 유저에게 새로 들어온 유저의 닉네임을 보내줌
							try {
								outStream = server.Mode2.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(listTag + "//" + mynick+"_0");
							}catch(Exception e) {
								System.out.println("server > connectedClient > modetag2 > 2");
							}
						}
						for(int i = 0;i<server.Mode2.size();i++) {	//새로 들어온 유저에게 기존에 게임룸에 있던 유저의 닉네임을 보내줌
							if(!mynick.equals(server.Mode2.get(i).nick)) {
								dataOutStream.writeUTF(listTag + "//" + server.Mode2.get(i).nick+"_"+server.Mode2.get(i).score);
							}
						}						
						
						//3번방에 들어간 경우 1번과 동일
					}else if(mode.equals("3")) {	
						if(server.Mode3.size()<=0) {
							server.QM3 = new QuizMake(server, "3");
							server.QM3.start();
							dataOutStream.writeUTF(quizTag + "//" + "5초 뒤 게임이 시작됩니다.");
							SDB.makequizlist("3");
						}
						server.Mode3.add(myplayer);
						int index = server.LobbyClients.indexOf(myplayer);
						server.LobbyClients.remove(index);
						for(int i = 0;i<server.LobbyClients.size();i++) { //로비에 있는 유저에게 접속자 수 증가시켜 보내주기
							try {
								outStream = server.LobbyClients.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								String pnum = ""+server.Mode3.size();
								dataOutStream.writeUTF(lobbyTag + "//MODE3//" + pnum);
							}catch(Exception ex) {
								System.out.println("server > connectedclient > modetag3 > 1");
							}
						}
						for(int i = 0; i<server.Mode3.size();i++) {	//게임룸에 있는 유저에게 새로 들어온 유저의 닉네임을 보내줌
							try {
								outStream = server.Mode3.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(listTag + "//" + mynick+"_0");
							}catch(Exception e) {
								System.out.println("server > connectedClient > modetag3 > 2");
							}
						}
						for(int i = 0;i<server.Mode3.size();i++) {	//새로 들어온 유저에게 기존에 게임룸에 있던 유저의 닉네임을 보내줌
							if(!mynick.equals(server.Mode3.get(i).nick)) {
								dataOutStream.writeUTF(listTag + "//" + server.Mode3.get(i).nick+"_"+server.Mode3.get(i).score);
							}
						}						
						
						//4번방에 들어간경우 1번과 동일
					}else if(mode.equals("4")) {	// 1번 방에 들어간 경우
						if(server.Mode4.size()<=0) {
							server.QM4 = new QuizMake(server, "4");
							server.QM4.start();
							dataOutStream.writeUTF(quizTag + "//" + "5초 뒤 게임이 시작됩니다.");
							SDB.makequizlist("4");
						}
						server.Mode4.add(myplayer);
						int index = server.LobbyClients.indexOf(myplayer);
						server.LobbyClients.remove(index);
						for(int i = 0;i<server.LobbyClients.size();i++) { //로비에 있는 유저에게 접속자 수 증가시켜 보내주기
							try {
								outStream = server.LobbyClients.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								String pnum = ""+server.Mode4.size();
								dataOutStream.writeUTF(lobbyTag + "//MODE4//" + pnum);
							}catch(Exception ex) {
								System.out.println("server > connectedclient > modetag4 > 1");
							}
						}
						for(int i = 0; i<server.Mode4.size();i++) {	//게임룸에 있는 유저에게 새로 들어온 유저의 닉네임을 보내줌
							try {
								outStream = server.Mode4.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(listTag + "//" + mynick+"_0");
							}catch(Exception e) {
								System.out.println("server > connectedClient > modetag4 > 2");
							}
						}
						for(int i = 0;i<server.Mode4.size();i++) {	//새로 들어온 유저에게 기존에 게임룸에 있던 유저의 닉네임을 보내줌
							if(!mynick.equals(server.Mode4.get(i).nick)) {
								dataOutStream.writeUTF(listTag + "//" + server.Mode4.get(i).nick+"_"+server.Mode4.get(i).score);
							}
						}						
						
						//5번방에 들어간 경우 1번과 동일
					}else if(mode.equals("5")) {	// 1번 방에 들어간 경우
						if(server.Mode5.size()<=0) {
							server.QM5 = new QuizMake(server, "5");
							server.QM5.start();
							dataOutStream.writeUTF(quizTag + "//" + "5초 뒤 게임이 시작됩니다.");
							SDB.makequizlist("5");
						}
						server.Mode5.add(myplayer);
						int index = server.LobbyClients.indexOf(myplayer);
						server.LobbyClients.remove(index);
						for(int i = 0;i<server.LobbyClients.size();i++) { //로비에 있는 유저에게 접속자 수 증가시켜 보내주기
							try {
								outStream = server.LobbyClients.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								String pnum = ""+server.Mode5.size();
								dataOutStream.writeUTF(lobbyTag + "//MODE5//" + pnum);
							}catch(Exception ex) {
								System.out.println("server > connectedclient > modetag5 > 1");
							}
						}
						for(int i = 0; i<server.Mode5.size();i++) {	//게임룸에 있는 유저에게 새로 들어온 유저의 닉네임을 보내줌
							try {
								outStream = server.Mode5.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(listTag + "//" + mynick+"_0");
							}catch(Exception e) {
								System.out.println("server > connectedClient > modetag5 > 2");
							}
						}
						for(int i = 0;i<server.Mode5.size();i++) {	//새로 들어온 유저에게 기존에 게임룸에 있던 유저의 닉네임을 보내줌
							if(!mynick.equals(server.Mode5.get(i).nick)) {
								dataOutStream.writeUTF(listTag + "//" + server.Mode5.get(i).nick+"_"+server.Mode5.get(i).score);
							}
						}						
						
					}//모드선택
					
				}else if(tag.equals(chatTag)) { //채팅
					
					String msg = stk.nextToken();
					String unick = stk.nextToken();
					
					// 내가 1번방에 있으면 채팅을 1번방에 뿌려줌
					if(mymode.equals("1")) { //채팅 1
						for(int i=0;i<server.Mode1.size();i++) {
							try {
								outStream = server.Mode1.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(chatTag+"//"+unick+" : "+msg);							
							}catch(Exception ex) {
								System.out.println("server > connectedclient > chat > 1" );
							}
						}
						
						//서버에 리스트안에 나의 인덱스 찾기
						int myindex=0;	
						for(int i=0;i<server.Mode1.size();i++) {
							if(server.Mode1.get(i).nick.equals(unick)) {
								myindex=i;
							}
						}
			
						//정답을 맞췄을 때 처리 기능
						if(msg.equals(server.QM1.nans)) {
							
							//내 스코어에 1더하면서 다른 클라이언트에 기존의 점수와 추가된 점수를 한번에 보내주기 위해서 > string으로 보내진 점수를 클라이언트에서 바꿀수 없기때문에
							int nowscore = server.Mode1.get(myindex).score;
							server.Mode1.get(myindex).score++;
							int plusscore = server.Mode1.get(myindex).score;
							
							//1번모드에 참여중인 유저들에게 정답과 닉네임을 보내줌
							for(int i = 0 ; i < server.Mode1.size() ; i++ ) {
								try {
									outStream = server.Mode1.get(i).socket.getOutputStream();
									dataOutStream = new DataOutputStream(outStream);
									dataOutStream.writeUTF(ansTag+"//"+unick+" : "+msg);
									// 맞춘 사람의 스코어를 새로 보내줌
									dataOutStream.writeUTF(cuserTag+"//"+unick+"//"+nowscore+"//"+plusscore);
		
								}catch(Exception e) {
									System.out.println(e.getMessage());
									System.out.println("server > connectedclient > chat > nans > 1");
								}
							}
							//맞춘 문제를 문제리스트에서 삭제
							for(int i=0;i<server.SDB.quizlist1.size();i++){
								if(server.SDB.quizlist1.get(i).Answer.equals(msg)) {
									server.SDB.quizlist1.remove(i);
								}
							}
							//문제출제 스레드를 종료시킨뒤 다시 실행 
							server.QM1.interrupt();
							server.QM1 = new QuizMake(server, "1");
							server.QM1.start();
						}
						
						
						// 2번방에 있을 떄 채팅 1번과 동일
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
						
						//서버에 리스트안에 나의 인덱스 찾기
						int myindex=0;	
						for(int i=0;i<server.Mode2.size();i++) {
							if(server.Mode2.get(i).nick.equals(unick)) {
								myindex=i;
							}
						}
			
						//정답을 맞췄을 때 처리 기능
						if(msg.equals(server.QM2.nans)) {
							
							//내 스코어에 1더하면서 다른 클라이언트에 기존의 점수와 추가된 점수를 한번에 보내주기 위해서 > string으로 보내진 점수를 클라이언트에서 바꿀수 없기때문에
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
							
						// 3번방에 있을경우 채팅 1번과 동일
					}else if(mymode.equals("3")) { //채팅 1
						for(int i=0;i<server.Mode3.size();i++) {
							try {
								outStream = server.Mode3.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(chatTag+"//"+unick+" : "+msg);							
							}catch(Exception ex) {
								System.out.println("server > connectedclient > chat > 3 " );
							}
						}
						
						//서버에 리스트안에 나의 인덱스 찾기
						int myindex=0;	
						for(int i=0;i<server.Mode3.size();i++) {
							if(server.Mode3.get(i).nick.equals(unick)) {
								myindex=i;
							}
						}
			
						//정답을 맞췄을 때 처리 기능
						if(msg.equals(server.QM3.nans)) {
							
							//내 스코어에 1더하면서 다른 클라이언트에 기존의 점수와 추가된 점수를 한번에 보내주기 위해서 > string으로 보내진 점수를 클라이언트에서 바꿀수 없기때문에
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
						// 4번 모드에 있을 때 채팅처리 1번과 동일
					}else if(mymode.equals("4")) { //채팅 1
						for(int i=0;i<server.Mode4.size();i++) {
							try {
								outStream = server.Mode4.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(chatTag+"//"+unick+" : "+msg);							
							}catch(Exception ex) {
								System.out.println("server > connectedclient > chat > 4 " );
							}
						}
						
						//서버에 리스트안에 나의 인덱스 찾기
						int myindex=0;	
						for(int i=0;i<server.Mode4.size();i++) {
							if(server.Mode4.get(i).nick.equals(unick)) {
								myindex=i;
							}
						}
			
						//정답을 맞췄을 때 처리 기능
						if(msg.equals(server.QM4.nans)) {
							
							//내 스코어에 1더하면서 다른 클라이언트에 기존의 점수와 추가된 점수를 한번에 보내주기 위해서 > string으로 보내진 점수를 클라이언트에서 바꿀수 없기때문에
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
						// 5번방에 있을 경우 채팅 처리 1번과 동일
					}else if(mymode.equals("5")) { //채팅 5
						for(int i=0;i<server.Mode5.size();i++) {
							try {
								outStream = server.Mode5.get(i).socket.getOutputStream();
								dataOutStream = new DataOutputStream(outStream);
								dataOutStream.writeUTF(chatTag+"//"+unick+" : "+msg);							
							}catch(Exception ex) {
								System.out.println("server > connectedclient > chat > 5 " );
							}
						}
						
						//서버에 리스트안에 나의 인덱스 찾기
						int myindex=0;	
						for(int i=0;i<server.Mode5.size();i++) {
							if(server.Mode5.get(i).nick.equals(unick)) {
								myindex=i;
							}
						}
			
						//정답을 맞췄을 때 처리 기능
						if(msg.equals(server.QM5.nans)) {
							
							//내 스코어에 1더하면서 다른 클라이언트에 기존의 점수와 추가된 점수를 한번에 보내주기 위해서 > string으로 보내진 점수를 클라이언트에서 바꿀수 없기때문에
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

					}//채팅
					
				}
			}//while
			
			
		}catch(IOException e) {
			
			System.out.println("server > connectedclient > 예외");
			
			// 게임을 종료했을 때
			
			// 내가 로비에 있었을 때 
			if(mymode.equals("LOBBY")) {	// 로비리스트에서 제거
				int index = server.LobbyClients.indexOf(myplayer);
				server.LobbyClients.remove(index);
				
			}
			// 1번방에 있었을 때
			if(mymode.equals("1")) {
				// 모드1번 리스트에서 나의 인덱스를 찾아서
				int index = server.Mode1.indexOf(myplayer);
				
				//모드1번의 유저들에게 나가기태그로 닉네임과 스코어를 보내줌
				for(int i=0;i<server.Mode1.size();i++) {
					try {
						outStream = server.Mode1.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						dataOutStream.writeUTF(exitTag + "//" + mynick+"_"+server.Mode1.get(index).score);
					}catch(Exception ex) {
						System.out.println("server > connectedclient > exit mode1-1" );
					}
				}
				//모드1번 리스트에서 제거
				server.Mode1.remove(index);
				// 아무도 없으면 스레드 종료
				if(server.Mode1.size()<=0) {
					server.QM1.interrupt();
				}
				//로비의 유저들에게 해당모드의 인원을 줄여줌
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
				
				//스레드 종료
				server.c.interrupt();
				
				//2번방에서 종료한 경우 1번과 동일
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
				
				//3번방에서 종료한 경우 1번과 동일
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
				
				//4번방에서 종료한 경우 1번과 동일
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
				
				//5번방에서 종료한 경우 1번과 동일
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
