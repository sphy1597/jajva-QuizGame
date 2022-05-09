import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;


public class MsgListener extends Thread implements  Serializable{
	
	//������� �� �޼����� �ޱ� ���� inStream ����
	Socket socket = null;
	InputStream inStream = null;
	DataInputStream dataInStream = null;
	Operator op;
	
	
	//�±� ����
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
			// �ν�Ʈ�� ���� ����
			inStream = this.socket.getInputStream();
			dataInStream = new DataInputStream(inStream);
			
			while(true) {
				//���� �޼����� Stringtokenizer�� �±׿� ���� ����
				String msg = dataInStream.readUTF();
				StringTokenizer stk = new StringTokenizer(msg , "//");
				
				//�±�
				String tag = stk.nextToken();
				
				
				if(tag.equals(chatTag)) {	//�±װ� ä���±��� ���
					// ������ ���� �ִ� �ؽ�Ʈ�� ���ؼ� ���
					String chat = stk.nextToken();
					String oldtext = op.gf.ta.getText();
					op.gf.ta.setText(oldtext + "\n" +chat);
				}else if(tag.equals(lobbyTag)) {	// lobbyTag �ΰ��
					// ��带 Ȯ���ϰ� �ش� ��忡 �°� �����ڼ��� ��������
					String mode = stk.nextToken();
					String pnum = stk.nextToken();

					if(mode.equals("MODE1")) {
						op.lobbyf.user1.setText(pnum + "�� ������");
					}else if(mode.equals("MODE2")) {
						op.lobbyf.user2.setText(pnum + "�� ������");
					}else if(mode.equals("MODE3")) {
						op.lobbyf.user3.setText(pnum + "�� ������");
					}else if(mode.equals("MODE4")) {
						op.lobbyf.user4.setText(pnum + "�� ������");
					}else{
						op.lobbyf.user5.setText(pnum + "�� ������");
					}
					
				}else if(tag.equals(changeTag)) {	// changeTag �ΰ��
					// �г��Ӻ��� ��û ����� ���� ERROR!�ΰ�� ���и� �ƴѰ��� �г����� �ٲ���
					String result = stk.nextToken();
					
					if(result.equals("ERROR!")) {
						op.lobbyf.nc.failchange();
					}else {
						op.lobbyf.nc.nickchange(result);
					}
				}else if(tag.equals(listTag)) {	//����Ʈ �±��ΰ��
					// ���� ������ �̸����� ���ӷ� ����Ʈ�� �߰�
					String newuser = stk.nextToken();
					op.gf.users.addElement(newuser);
					
				}else if(tag.equals(exitTag)) {	//�����±�
					// ���� ������ �ش��ϴ� ����� ���ӷ� ����Ʈ���� ����
					String exituser = stk.nextToken();
					op.gf.users.removeElement(exituser);
					
				}else if(tag.equals(quizTag)) {	//�����±�
					// �ش� ��� ��� �����ϴ� �ؽ�Ʈ���� ���					
					String quiz = stk.nextToken();
					System.out.println(quiz);
					op.gf.QuestionArea.setText(quiz);
					
				}else if(tag.equals(hintTag)) {	//��Ʈ�±�
					// ��Ʈ�� ������ ��Ʈ�� ��µǴ� �󺧿� ���					
					String hint = stk.nextToken();
					op.gf.hintlabel.setText("��Ʈ : " + hint);
				}else if(tag.equals(ansTag)) {	//�����±�
					// ������ ������ ��µǴ� �󺧿� ���
					String answer = stk.nextToken();
					op.gf.anslabel.setText("���� : " + answer);
				}else if(tag.equals(cuserTag)) {	//������ �±�
					// �г��� // �������� // ������ �������� ���� ���� ����
					String nick = stk.nextToken();
					String oldscore = stk.nextToken();
					String newscore = stk.nextToken();				
					// �г��Ӱ� ���������� ���� ����Ʈ�� �ε����� ã�� �������� ��ü
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
	
