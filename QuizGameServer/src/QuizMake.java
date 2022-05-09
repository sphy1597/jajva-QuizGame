import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.Timer;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class QuizMake extends Thread{
	//소켓과 스트림 선언
	Socket socket = null;
	OutputStream outStream = null;
	DataOutputStream dataOutStream = null;
	InputStream inStream = null;
	DataInputStream dataInStream = null;
	
	
	Server server;
	String MODE;
	//태그선언
	final String quizTag = "Q:";
	final String ansTag = "ANS:";
	final String hintTag = "HI:";
	final String imageTag = "IMAGE:";
	
	String nans;	//현재문제의 정답
	String nhint;	//현재 힌트정답
	
	QuizMake(Server _server,String _MODE){ //MODE >> 1 2 3 4 5
		server = _server;
		MODE = _MODE;		
		nans = null;
		nhint = null;
		
	}
	
	public void run() {	// 문제출제를 위한 스레드
		try {
			// 문제를 랜덤한 번호 출제
			Random random = new Random();			
	
			//1번 모드인 경우
			if(MODE.equals("1")) {
				while(true) {
					sleep(5000);
					int qindex = random.nextInt(server.SDB.quizlist1.size());	//랜덤 번호를 이용해 인덱스를 설정
					sendQuestion(qindex, MODE);									//해당 인덱스와 모드로 문제를 유저에게 전송
					sleep(5000);
					sendHint(qindex,MODE);										//해당 인덱스와 모드로 힌트를 유저에게 전송
					sleep(5000);			
					sendAnswer(qindex, MODE);									// 정답을 유저들에게 정송
					server.SDB.quizlist1.remove(qindex);						// 문제리스트에서 해당 문제를 삭제	
				}
				
				//2번 모드인 경우 1번과 동일
			}else if(MODE.equals("2")) {
				while(true) {
					sleep(5000);
					int qindex = random.nextInt(server.SDB.quizlist2.size());
					sendQuestion(qindex, MODE);
					sleep(5000);
					sendHint(qindex,MODE);
					sleep(5000);
					sendAnswer(qindex, MODE);
					server.SDB.quizlist2.remove(qindex);				
				}
				//3번 모드인 경우 1번과 동일
			}else if(MODE.equals("3")) {
				while(true) {
					sleep(5000);
					int qindex = random.nextInt(server.SDB.quizlist3.size());
					sendQuestion(qindex, MODE);
					sleep(5000);
					sendHint(qindex,MODE);
					sleep(5000);
					sendAnswer(qindex, MODE);
					server.SDB.quizlist3.remove(qindex);	
				}
				
				//4번 모드인 경우 1번과 동일
			}else if(MODE.equals("4")) {
				while(true) {
					sleep(5000);
					int qindex = random.nextInt(server.SDB.quizlist4.size());
					sendQuestion(qindex, MODE);
					sleep(5000);
					sendHint(qindex,MODE);
					sleep(5000);
					sendAnswer(qindex, MODE);
					server.SDB.quizlist4.remove(qindex);
				}
				//5번 모드인 경우 1번과 동일
			}else if(MODE.equals("5")) {
				while(true) {
					sleep(5000);
					int qindex = random.nextInt(server.SDB.quizlist5.size());
					sendQuestion(qindex, MODE);
					sleep(5000);
					sendHint(qindex,MODE);
					sleep(5000);
					sendAnswer(qindex, MODE);
					server.SDB.quizlist5.remove(qindex);

						
				}
			}
			
		}catch(Exception e) {
			System.out.println("server > quizmake > run()");
		}
		
	}
	
	//문제를 유저들에게 보내는 기능
	void sendQuestion(int index, String MODE) {
		
		//1번 모드인경우
		if(MODE.equals("1")) {
			try {
				//1번 모드에 참가중인 유저들에게 전송
				for(int i =0; i<server.Mode1.size();i++) {
					outStream = server.Mode1.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					//새로운 문제를 전송하고 힌트와 정답을 빈칸으로 전송
					dataOutStream.writeUTF(quizTag + "//" + "문제 : "+server.SDB.quizlist1.get(index).Question);
					dataOutStream.writeUTF(hintTag + "//"+ " ");
					dataOutStream.writeUTF(ansTag + "//" + " ");
					//현재 문제의 정답을 저장
					nans = server.SDB.quizlist1.get(index).Answer;
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendquestion > 1 ");
			}
			
			
			//2번 모드인경우 1번과 동일
		}else if(MODE.equals("2")) {
			try {
				for(int i =0; i<server.Mode2.size();i++) {
					outStream = server.Mode2.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(quizTag + "//" + "문제 : "+server.SDB.quizlist2.get(index).Question);
					dataOutStream.writeUTF(hintTag + "//"+ " ");
					dataOutStream.writeUTF(ansTag + "//" + " ");
					nans = server.SDB.quizlist2.get(index).Answer;
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendquestion > 2 ");
			}
			//3번 모드인경우 1번과 동일
		}else if(MODE.equals("3")) {
			try {
				for(int i =0; i<server.Mode3.size();i++) {
					outStream = server.Mode3.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(quizTag + "//" + "문제 : "+server.SDB.quizlist3.get(index).Question);
					dataOutStream.writeUTF(hintTag + "//"+ " ");
					dataOutStream.writeUTF(ansTag + "//" + " ");
					nans = server.SDB.quizlist3.get(index).Answer;
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendquestion > 3 ");
			}
			//4번 모드인경우 1번과 동일
		}else if(MODE.equals("4")) {
			try {
				for(int i =0; i<server.Mode4.size();i++) {
					outStream = server.Mode4.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(quizTag + "//" + "문제 : "+server.SDB.quizlist4.get(index).Question);
					dataOutStream.writeUTF(hintTag + "//"+ " ");
					dataOutStream.writeUTF(ansTag + "//" + " ");
					nans = server.SDB.quizlist4.get(index).Answer;
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendquestion > 4 ");
			}
			//5번 모드인경우 1번과 동일
		}else if(MODE.equals("5")) {
			try {
				for(int i =0; i<server.Mode5.size();i++) {
					outStream = server.Mode5.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(quizTag + "//" + "문제 : "+server.SDB.quizlist5.get(index).Question);
					dataOutStream.writeUTF(hintTag + "//"+ " ");
					dataOutStream.writeUTF(ansTag + "//" + " ");
					nans = server.SDB.quizlist5.get(index).Answer;
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendquestion > 5 ");
			}
		}
		
	}// sendQues
	
	//힌트를 정송하는 기능
	void sendHint(int index, String MODE) {
		
		
		// 1~5번 모드 모두 동일
		//1번 모드인 경우
		if(MODE.equals("1")) {
			try {
				//1번모드에 참가중인 모든 유저에게 전송
				for(int i =0; i<server.Mode1.size();i++) {
					outStream = server.Mode1.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					// 힌트태그로 힌트를 전송 
					dataOutStream.writeUTF(hintTag + "//"+server.SDB.quizlist1.get(index).Hint);
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendhint > 1 ");
			}
		}else if(MODE.equals("2")) {
			try {
				for(int i =0; i<server.Mode2.size();i++) {
					outStream = server.Mode2.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(hintTag + "//"+server.SDB.quizlist2.get(index).Hint);
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendhint > 2 ");
			}
		}else if(MODE.equals("3")) {
			try {
				for(int i =0; i<server.Mode3.size();i++) {
					outStream = server.Mode3.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(hintTag + "//"+server.SDB.quizlist3.get(index).Hint);
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendhint > 3 ");
			}
		}else if(MODE.equals("4")) {
			try {
				for(int i =0; i<server.Mode4.size();i++) {
					outStream = server.Mode4.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(hintTag + "//"+server.SDB.quizlist4.get(index).Hint);
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendhint > 4 ");
			}
		}else if(MODE.equals("5")) {
			try {
				for(int i =0; i<server.Mode5.size();i++) {
					outStream = server.Mode5.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(hintTag + "//"+server.SDB.quizlist5.get(index).Hint);
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendhint > 5 ");
			}
		}

	}
	
	
	// 정답을 보내는 기능
	void sendAnswer(int index, String MODE) {
		
		// 1~5번 모드 모두 동일
		// 1번모드인 경우
		if(MODE.equals("1")) {
			try {
				//1번모드에 참가한 모든 유저에게 전송
				for(int i = 0 ; i < server.Mode1.size() ; i++ ) {
						outStream = server.Mode1.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						//정답태그와 정답을 전송
						dataOutStream.writeUTF(ansTag+"//"+server.SDB.quizlist1.get(index).Answer);
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendquestion > 1 ");
			}
			nans="";	//현재 정답을 초기화 > 정답공개후 정답을 입력하면 정답처리 되는 문제를 막기위해
			
		}else if(MODE.equals("2")) {
			try {
				for(int i =0; i<server.Mode2.size();i++) {
					outStream = server.Mode2.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(ansTag + "//" + server.SDB.quizlist2.get(index).Answer);
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
				System.out.println("server > quizmake > sendquestion > 2 ");
			}
			
			nans="";
		}else if(MODE.equals("3")) {
			try {
				for(int i =0; i<server.Mode3.size();i++) {
					outStream = server.Mode3.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(ansTag + "//" + server.SDB.quizlist3.get(index).Answer);
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendquestion > 3 ");
			}
			
			
			nans="";
		}else if(MODE.equals("4")) {
			try {
				for(int i =0; i<server.Mode4.size();i++) {
					outStream = server.Mode4.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(ansTag + "//" + server.SDB.quizlist4.get(index).Answer);
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendquestion > 4 ");
			}
			
			nans="";
		}else if(MODE.equals("5")) {
			try {
				for(int i =0; i<server.Mode5.size();i++) {
					outStream = server.Mode5.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(ansTag + "//" + server.SDB.quizlist5.get(index).Answer);
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendquestion > 5 ");
			}
			
			nans="";
		}
		
	}

	
}
