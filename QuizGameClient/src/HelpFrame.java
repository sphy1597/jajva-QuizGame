import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.net.*;
import java.util.*;




public class HelpFrame extends JFrame{ // 채팅 기능 메인화면
	
	DefaultListModel<String> users = new DefaultListModel<>(); // JList로 접속중인 유저를 관리하기 위해 사용되는 리스트모델
	
	JTextField wf = new JTextField("채팅을 입력받는 부분입니다. ");			// 채팅내용을 입력하는 텍스트필드				
	JList userlist = new JList(users);			// 접속중인 유저를 보여주기 위한 JList
	JTextArea ta = new JTextArea("유저들의 채팅의 내용을 보여주는 부분입니다. ");				// 채팅의 내용이 보여지는 텍스트 에어리어
	
	
	JLabel anslabel = new JLabel("정답이 보여지는 부분입니다.");	 	//정답 라벨 설명
	JLabel hintlabel = new JLabel("제한시간 절반이 지나면 힌트가 보여줍니다.");			//힌트 라벨 설명
	//게임 진행 설명
	JTextArea QuestionArea = new JTextArea(" 문제가 출제 되는 부분입니다."+"\n"+" 초성이나 글자를 보여줍니다. "+"\n"+"정답을 작성할 때는 띄어쓰기를 하지 말고 작성해주세요");

	JButton exit = new JButton("돌아가기");		//로비로 돌아가는 버튼	
	
	public HelpFrame() {		// 게임에 대한 설명을 알려주는 인터페이스
		setTitle("도움말");
		
		MyActionListener ml = new MyActionListener();		//돌아가기 버튼 액션리스너
		
         // Gameroom과 동일하게 만들어진 인터페이스
		JPanel mainPanel = new JPanel(new GridLayout(1,2));
		JPanel gamePanel = new JPanel(new BorderLayout());
		JPanel chatPanel = new JPanel(new BorderLayout());
		JPanel helppanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(); 		// 텍스트필드, 텍스트에어리어가 들어가는 패널
		JPanel p2 = new JPanel();		// 라벨 리스트 버튼이 들어가는 패널
		
		//메인 패널에 추가
		mainPanel.add(gamePanel);
		mainPanel.add(chatPanel);
		
		//헬프패널에 돌아가기 버튼 추가
		helppanel.add(mainPanel, BorderLayout.CENTER);
		helppanel.add(exit, BorderLayout.SOUTH);
		exit.addActionListener(ml);
		setContentPane(helppanel);
		
		//채팅 패널에 추가
		chatPanel.add(p1, BorderLayout.CENTER);
		chatPanel.add(p2, BorderLayout.EAST);
		
		// 채팅관련 부분
		// 텍스트필드, 텍스트 에어리어
		p1.setLayout(new BorderLayout());		//p1을 보더레이아웃으로 설정
		p1.add(wf,BorderLayout.SOUTH);			//wf를 p1 보더레이아웃의 남쪽에 배치
		wf.setPreferredSize(new Dimension(300,30));
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
		
		JButton sendbtn = new JButton("보내기");		//보내기 버튼 생성
		sendbtn.setPreferredSize(new Dimension(80, 30));	
		//sendbtn.addActionListener(m1);				// 보내기 버튼 액션리스너 연결
		p2.add(sendbtn,BorderLayout.SOUTH);			//p2 남쪽에 배치
		
		//  -문제 출제 부분-
		//폰트설정
		Font font1 = new Font("맑은고딕", Font.PLAIN,25);
		Color backColor = new Color(254,245,237);
		//정답 라벨 추가 및 폰트, 색 설정
		gamePanel.add(anslabel, BorderLayout.NORTH);
		anslabel.setOpaque(true);
		anslabel.setBackground(backColor);
		anslabel.setFont(font1);
		//문제출제 부분, 힌트 라벨 폰트설정
		gamePanel.add(QuestionArea, BorderLayout.CENTER);
		gamePanel.add(hintlabel, BorderLayout.SOUTH);		 
		hintlabel.setOpaque(true);
		hintlabel.setBackground(backColor);
		hintlabel.setFont(font1);
	
		//배경색 설정
		gamePanel.setBackground(backColor);
		
		setSize(1300,720);		
	
		setVisible(true);
	
	}
	//돌아가기 액션리스너
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			if(b.getText().equals("돌아가기")) { //돌아가기 버튼을 누르면 창이 꺼짐
				dispose();
			}
			
		}
	}
}