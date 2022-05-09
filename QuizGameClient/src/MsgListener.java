import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;


public class MsgListener extends Thread implements  Serializable{
	
	//소켓통신 및 메세지를 받기 위한 inStream 설정
	Socket socket = null;
	InputStream inStream = null;
	DataInputStream dataInStream = null;
	Operator op;
	
	
	//태그 정의
	final String chatTag = "C:";
	final String listTag = "L:";
	final String exitTag = "EX:";
	final String lobbyTag = "LOB:";
	final String changeTag = "CH:";
	final String quizTag = "Q:";
	final String ansTag = "ANS:";
	final String hintTag = "HI:";
	final String cuserTag = "CU:";
	final String imageTag = "IMAGE:";
	
	
	MsgListener(Socket _s, Operator _op){
		socket = _s;
		op = _op;
	}
	
	public void run() {
		try {
			// 인스트림 소켓 설정
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			
			while(true) {
				//들어온 메세지를 Stringtokenizer로 태그와 내용 구분
				String msg = dataInStream.readUTF();
				StringTokenizer stk = new StringTokenizer(msg , "//");
				
				//태그
				String tag = stk.nextToken();
				
				
				if(tag.equals(chatTag)) {	//태그가 채팅태그인 경우
					// 내용을 원래 있던 텍스트에 더해서 출력
					String chat = stk.nextToken();
					String oldtext = op.gf.ta.getText();
					op.gf.ta.setText(oldtext + "\n" +chat);
				}else if(tag.equals(lobbyTag)) {	// lobbyTag 인경우
					// 모드를 확인하고 해당 모드에 맞게 참여자수를 설정해줌
					String mode = stk.nextToken();
					String pnum = stk.nextToken();

					if(mode.equals("MODE1")) {
						op.lobbyf.user1.setText(pnum + "명 참여중");
					}else if(mode.equals("MODE2")) {
						op.lobbyf.user2.setText(pnum + "명 참여중");
					}else if(mode.equals("MODE3")) {
						op.lobbyf.user3.setText(pnum + "명 참여중");
					}else if(mode.equals("MODE4")) {
						op.lobbyf.user4.setText(pnum + "명 참여중");
					}else{
						op.lobbyf.user5.setText(pnum + "명 참여중");
					}
					
				}else if(tag.equals(changeTag)) {	// changeTag 인경우
					// 닉네임변경 요청 결과를 통해 ERROR!인경우 실패를 아닌경우는 닉네임을 바꿔줌
					String result = stk.nextToken();
					
					if(result.equals("ERROR!")) {
						op.lobbyf.nc.failchange();
					}else {
						op.lobbyf.nc.nickchange(result);
					}
				}else if(tag.equals(listTag)) {	//리스트 태그인경우
					// 들어온 유저의 이름으로 게임룸 리스트에 추가
					String newuser = stk.nextToken();
					op.gf.users.addElement(newuser);
					
				}else if(tag.equals(exitTag)) {	//종료태그
					// 들어온 유저에 해당하는 사람을 게임룸 리스트에서 제거
					String exituser = stk.nextToken();
					op.gf.users.removeElement(exituser);
					
				}else if(tag.equals(quizTag)) {	//퀴즈태그
					// 해당 퀴즈를 퀴즈를 출제하는 텍스트에어리어에 출력					
					String quiz = stk.nextToken();
					System.out.println(quiz);
					op.gf.QuestionArea.setText(quiz);
					
				}else if(tag.equals(hintTag)) {	//힌트태그
					// 힌트의 내용을 힌트가 출력되는 라벨에 출력					
					String hint = stk.nextToken();
					op.gf.hintlabel.setText("힌트 : " + hint);
				}else if(tag.equals(ansTag)) {	//정답태그
					// 정답을 정답이 출력되는 라벨에 출력
					String answer = stk.nextToken();
					op.gf.anslabel.setText("정답 : " + answer);
				}else if(tag.equals(cuserTag)) {	//정답자 태그
					// 닉네임 // 원래점수 // 새점수 형식으로 들어온 값을 통해
					String nick = stk.nextToken();
					String oldscore = stk.nextToken();
					String newscore = stk.nextToken();				
					// 닉네임과 원래점수를 통해 리스트의 인덱스를 찾고 새점수로 교체
					int index = op.gf.users.indexOf(nick+"_"+oldscore);
					op.gf.users.setElementAt(nick+"_"+newscore, index);
		
				}
	
			}
		}catch(Exception e) {
			System.out.println(e);
			System.out.println("client > msglistener > run");
		}
	}
}
	
