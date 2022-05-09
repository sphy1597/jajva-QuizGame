import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.Timer;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class QuizMake extends Thread{
	//���ϰ� ��Ʈ�� ����
	Socket socket = null;
	OutputStream outStream = null;
	DataOutputStream dataOutStream = null;
	InputStream inStream = null;
	DataInputStream dataInStream = null;
	
	
	Server server;
	String MODE;
	//�±׼���
	final String quizTag = "Q:";
	final String ansTag = "ANS:";
	final String hintTag = "HI:";
	final String imageTag = "IMAGE:";
	
	String nans;	//���繮���� ����
	String nhint;	//���� ��Ʈ����
	
	QuizMake(Server _server,String _MODE){ //MODE >> 1 2 3 4 5
		server = _server;
		MODE = _MODE;		
		nans = null;
		nhint = null;
		
	}
	
	public void run() {	// ���������� ���� ������
		try {
			// ������ ������ ��ȣ ����
			Random random = new Random();			
	
			//1�� ����� ���
			if(MODE.equals("1")) {
				while(true) {
					sleep(5000);
					int qindex = random.nextInt(server.SDB.quizlist1.size());	//���� ��ȣ�� �̿��� �ε����� ����
					sendQuestion(qindex, MODE);									//�ش� �ε����� ���� ������ �������� ����
					sleep(5000);
					sendHint(qindex,MODE);										//�ش� �ε����� ���� ��Ʈ�� �������� ����
					sleep(5000);			
					sendAnswer(qindex, MODE);									// ������ �����鿡�� ����
					server.SDB.quizlist1.remove(qindex);						// ��������Ʈ���� �ش� ������ ����	
				}
				
				//2�� ����� ��� 1���� ����
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
				//3�� ����� ��� 1���� ����
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
				
				//4�� ����� ��� 1���� ����
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
				//5�� ����� ��� 1���� ����
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
	
	//������ �����鿡�� ������ ���
	void sendQuestion(int index, String MODE) {
		
		//1�� ����ΰ��
		if(MODE.equals("1")) {
			try {
				//1�� ��忡 �������� �����鿡�� ����
				for(int i =0; i<server.Mode1.size();i++) {
					outStream = server.Mode1.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					//���ο� ������ �����ϰ� ��Ʈ�� ������ ��ĭ���� ����
					dataOutStream.writeUTF(quizTag + "//" + "���� : "+server.SDB.quizlist1.get(index).Question);
					dataOutStream.writeUTF(hintTag + "//"+ " ");
					dataOutStream.writeUTF(ansTag + "//" + " ");
					//���� ������ ������ ����
					nans = server.SDB.quizlist1.get(index).Answer;
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendquestion > 1 ");
			}
			
			
			//2�� ����ΰ�� 1���� ����
		}else if(MODE.equals("2")) {
			try {
				for(int i =0; i<server.Mode2.size();i++) {
					outStream = server.Mode2.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(quizTag + "//" + "���� : "+server.SDB.quizlist2.get(index).Question);
					dataOutStream.writeUTF(hintTag + "//"+ " ");
					dataOutStream.writeUTF(ansTag + "//" + " ");
					nans = server.SDB.quizlist2.get(index).Answer;
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendquestion > 2 ");
			}
			//3�� ����ΰ�� 1���� ����
		}else if(MODE.equals("3")) {
			try {
				for(int i =0; i<server.Mode3.size();i++) {
					outStream = server.Mode3.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(quizTag + "//" + "���� : "+server.SDB.quizlist3.get(index).Question);
					dataOutStream.writeUTF(hintTag + "//"+ " ");
					dataOutStream.writeUTF(ansTag + "//" + " ");
					nans = server.SDB.quizlist3.get(index).Answer;
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendquestion > 3 ");
			}
			//4�� ����ΰ�� 1���� ����
		}else if(MODE.equals("4")) {
			try {
				for(int i =0; i<server.Mode4.size();i++) {
					outStream = server.Mode4.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(quizTag + "//" + "���� : "+server.SDB.quizlist4.get(index).Question);
					dataOutStream.writeUTF(hintTag + "//"+ " ");
					dataOutStream.writeUTF(ansTag + "//" + " ");
					nans = server.SDB.quizlist4.get(index).Answer;
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendquestion > 4 ");
			}
			//5�� ����ΰ�� 1���� ����
		}else if(MODE.equals("5")) {
			try {
				for(int i =0; i<server.Mode5.size();i++) {
					outStream = server.Mode5.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					dataOutStream.writeUTF(quizTag + "//" + "���� : "+server.SDB.quizlist5.get(index).Question);
					dataOutStream.writeUTF(hintTag + "//"+ " ");
					dataOutStream.writeUTF(ansTag + "//" + " ");
					nans = server.SDB.quizlist5.get(index).Answer;
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendquestion > 5 ");
			}
		}
		
	}// sendQues
	
	//��Ʈ�� �����ϴ� ���
	void sendHint(int index, String MODE) {
		
		
		// 1~5�� ��� ��� ����
		//1�� ����� ���
		if(MODE.equals("1")) {
			try {
				//1����忡 �������� ��� �������� ����
				for(int i =0; i<server.Mode1.size();i++) {
					outStream = server.Mode1.get(i).socket.getOutputStream();
					dataOutStream = new DataOutputStream(outStream);
					// ��Ʈ�±׷� ��Ʈ�� ���� 
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
	
	
	// ������ ������ ���
	void sendAnswer(int index, String MODE) {
		
		// 1~5�� ��� ��� ����
		// 1������� ���
		if(MODE.equals("1")) {
			try {
				//1����忡 ������ ��� �������� ����
				for(int i = 0 ; i < server.Mode1.size() ; i++ ) {
						outStream = server.Mode1.get(i).socket.getOutputStream();
						dataOutStream = new DataOutputStream(outStream);
						//�����±׿� ������ ����
						dataOutStream.writeUTF(ansTag+"//"+server.SDB.quizlist1.get(index).Answer);
				}
			}catch(Exception e) {
				System.out.println("server > quizmake > sendquestion > 1 ");
			}
			nans="";	//���� ������ �ʱ�ȭ > ��������� ������ �Է��ϸ� ����ó�� �Ǵ� ������ ��������
			
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
