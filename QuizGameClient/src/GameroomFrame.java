import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.net.*;
import java.util.*;




public class GameroomFrame extends JFrame{ // 채팅 기능 메인화면
	
	DefaultListModel<String> users = new DefaultListModel<>(); // JList로 접속중인 유저를 관리하기 위해 사용되는 리스트모델
	
	JTextField wf = new JTextField();			// 채팅내용을 입력하는 텍스트필드				
	JList userlist = new JList(users);			// 접속중인 유저를 보여주기 위한 JList
	JTextArea ta = new JTextArea();				// 채팅의 내용이 보여지는 텍스트 에어리어
	
	JButton sendbtn = new JButton("보내기");		//보내기 버튼 생성
	
	JLabel anslabel = new JLabel(" 정답 :  ");	// 정답을 출력하는 라벨
	JLabel hintlabel = new JLabel(" 힌트 : "); 	// 힌트를 출력하는 라벨 
	JTextArea QuestionArea = new JTextArea(" "); //문제가 출제되는 부분
	
	
	Operator op;
	
	public GameroomFrame(Operator _op) { 	// 퀴즈게임이 진행 되는 인터페이스
		
		op = _op;
		
		MyActionListener m1 = new MyActionListener();	//액션리스너 객체 생성
		
		
		setTitle(" ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//프레임이 종료되면 프로그램도 종료
         
		JPanel mainPanel = new JPanel(new GridLayout(1,2));  //게임이 진행되는 패널과 채팅이 진행되는 패널
		JPanel gamePanel = new JPanel(new BorderLayout());  // 게임 패널
		JPanel chatPanel = new JPanel(new BorderLayout());  // 채팅 패널
		
		JPanel p1 = new JPanel(); 		// 텍스트필드, 텍스트에어리어가 들어가는 패널
		JPanel p2 = new JPanel();		// 라벨 리스트 버튼이 들어가는 패널
		
		mainPanel.add(gamePanel);      // 메인패널에 게임패널 추가
		mainPanel.add(chatPanel);      // 메인패널에 채팅패널 추가
		
		setContentPane(mainPanel);    // 컨텐츠팬을 메인패널로 설정
		
		chatPanel.add(p1, BorderLayout.CENTER);  // 채팅패널에 패널1을 보더 센터에 추가 ( textarea )
		chatPanel.add(p2, BorderLayout.EAST);    // 채팅패널에 패널2 동쪽에 추가
		
		// 채팅관련 부분
		// 텍스트필드, 텍스트 에어리어
		p1.setLayout(new BorderLayout());				//p1을 보더레이아웃으로 설정
		p1.add(wf,BorderLayout.SOUTH);					//wf를 p1 보더레이아웃의 남쪽에 배치
		wf.setPreferredSize(new Dimension(300,30));  	//wf의 크기를 설정
		p1.add(new JScrollPane(ta), BorderLayout.CENTER);	// p1의 센터에 스크롤을 단 텍스트에어리어를 추가
		ta.setEditable(false);
		
		// 접속자수 p2
		p2.setLayout(new BorderLayout());			//p2를 보더레이아웃으로 설정
		JLabel ul = new JLabel(" 접속자 ");			// 라벨을 추가하고 접속자로 텍스트 설정
		p2.add(ul,BorderLayout.NORTH);				// 라벨을 북쪽에 배치
		ul.setHorizontalAlignment(JLabel.CENTER);				
		ul.setPreferredSize(new Dimension(80, 30));				
		userlist.setFixedCellWidth(80);				//리스트 크기 조정
		
		p2.add(new JScrollPane(userlist), BorderLayout.CENTER);	// 스크롤을 단 리스트를 p2 센터에 배치
		
		sendbtn.setPreferredSize(new Dimension(80, 30));	
		sendbtn.addActionListener(m1);				// 보내기 버튼 액션리스너 연결
		p2.add(sendbtn,BorderLayout.SOUTH);			//p2 남쪽에 배치
		
		// 폰트설정
		Font font1 = new Font("맑은고딕", Font.PLAIN,25);
		Color backColor = new Color(254,245,237);
		Font font2 = new Font("맑은고딕", Font.PLAIN,30);
		Color areaColor = new Color(255,255,255);
		
		//문제가 출제되는 텍스트 에어리어
		QuestionArea.setLineWrap(true);    //자동 줄바꿈
		QuestionArea.setFont(font2);
	
		//정답이 출력되는 부분 배경, 폰트 설정
		gamePanel.add(anslabel, BorderLayout.NORTH);
		anslabel.setOpaque(true);
		anslabel.setBackground(backColor);
		anslabel.setFont(font1);
		
		//문제가 출제되는 부분 폰트, 배경 설정
		gamePanel.add(QuestionArea, BorderLayout.CENTER);
		gamePanel.add(hintlabel, BorderLayout.SOUTH);		 
		hintlabel.setOpaque(true);
		hintlabel.setBackground(backColor);
		hintlabel.setFont(font1);
		
		//패널의 배경색을 설정
		gamePanel.setBackground(backColor);
		
		//윈도우 크기 설정
		setSize(1300,700);		
	
	}
	//액션리스너 
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			if(b.getText().equals("보내기")) { //보내기 버튼을 누르면 채팅을 보냄
				String msg = wf.getText();
				op.myconnector.sendmsg(msg);
				wf.setText("");
			}
			
		}
	}
}