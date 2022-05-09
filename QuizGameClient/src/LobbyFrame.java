import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class LobbyFrame extends JFrame{
	
	JPanel panel1 = new JPanel(new BorderLayout());
	//p2 센터
	JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	//p3은 왼쪽
	JPanel panel3 = new JPanel(new BorderLayout());
	
	JPanel p4 = new JPanel(new GridLayout(3,1,5,5));
	JPanel p5 = new JPanel(new GridLayout(2,1));
	//p2에
	
	//각 방의 설명을 보여주는 라벨과 참여하는 버튼
	JLabel roomnum1 = new JLabel("1");						// 방번호
	JLabel roomname1 = new JLabel("초성보고 영화 제목 맞추기");		// 방이름
	JLabel user1 = new JLabel(" 0명 참여중");					// 참여자 수
	JButton join1 = new JButton("1번 참가");					// 참가버튼
	//1과 동일
	JLabel roomnum2 = new JLabel("2");
	JLabel roomname2 = new JLabel("가사보고 노래 제목 맞추기");
	JLabel user2 = new JLabel(" 0명 참여중");
	JButton join2 = new JButton("2번 참가");
	//1과 동일
	JLabel roomnum3 = new JLabel("3");
	JLabel roomname3 = new JLabel("초성보고 노래 제목 맞추기");
	JLabel user3 = new JLabel(" 0명 참여중");
	JButton join3 = new JButton("3번 참가");
	//1과 동일
	JLabel roomnum4 = new JLabel("4");
	JLabel roomname4 = new JLabel("나라별 수도 맞추기");
	JLabel user4 = new JLabel(" 0명 참여중");
	JButton join4 = new JButton("4번 참가");
	//1과 동일
	JLabel roomnum5 = new JLabel("5");
	JLabel roomname5 = new JLabel("사자성어 맞추기");
	JLabel user5 = new JLabel(" 0명 참여중");
	JButton join5 = new JButton("5번 참가");
	
	//좌측 상단에 아이디와 닉네임을 출력하는 라벨
	JLabel id = new JLabel("I D");
	JLabel nickname = new JLabel(" nickname ");
	//좌측 하단에 버튼 
	JButton changename = new JButton("닉네임 설정");		// 닉네임 재설정 버튼
	JButton help = new JButton("도움말");					// 도움말 인터페이스를 띄우는 버튼
	JButton exit = new JButton("종료");					// 종료 버튼
	String nick;	//나의 닉네임을 저장
	String myid;	//나의 아이디를 저장
	
	
	Operator op;
	ErrorFrame er;
	NickChangeFrame nc;
	
	public LobbyFrame(Operator _op) {
		
		//액션리스너 추가
		MyActionListener ml = new MyActionListener();
		
		setTitle("LOGIN");	//타이틀 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//프레임이 종료되면 프로그램도 종료
		
		op = _op;
		
		//닉네임과 아이디 설정
		nickname.setText(nick);
		id.setText(myid);
		
		//폰트 선언
		Font font1 = new Font("맑은고딕", Font.PLAIN,30);
		Font font2 = new Font("맑은고딕", Font.PLAIN,25);
		Color p2Color = new Color(255,196,225);
		Color labelColor = new Color(251,255,226);
		Color btnColor = new Color(255,235,204);
		Color idcolor = new Color(245,245,245);
		
		//p2
		//방번호 방이름 참여자수 버튼의 크기 및 폰트 설정
		roomnum1.setPreferredSize(new Dimension(80, 100));
		roomnum1.setHorizontalAlignment(JLabel.CENTER);				
		roomnum1.setFont(font1);
		roomnum1.setOpaque(true);
		roomnum1.setBackground(labelColor);
		roomnum1.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		
		roomname1.setPreferredSize(new Dimension(400, 100));	
		roomname1.setHorizontalAlignment(JLabel.CENTER);				
		roomname1.setFont(font1);
		roomname1.setBorder(BorderFactory.createLineBorder(Color.BLACK));	
		roomname1.setOpaque(true);
		roomname1.setBackground(labelColor);
		
		user1.setPreferredSize(new Dimension(200, 100));	
		user1.setHorizontalAlignment(JLabel.CENTER);			
		user1.setFont(font1);
		user1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		user1.setOpaque(true);
		user1.setBackground(labelColor);
		
		join1.setPreferredSize(new Dimension(150, 100)); 
		join1.setFont(font2);
		join1.setBackground(btnColor);
		
		//방번호 방이름 참여자수 버튼의 크기 및 폰트 설정
		roomnum2.setPreferredSize(new Dimension(80, 100));
		roomnum2.setHorizontalAlignment(JLabel.CENTER);				
		roomnum2.setFont(font1);
		roomnum2.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomnum2.setOpaque(true);
		roomnum2.setBackground(labelColor);
		
		roomname2.setPreferredSize(new Dimension(400, 100));	
		roomname2.setHorizontalAlignment(JLabel.CENTER);				
		roomname2.setFont(font1);
		roomname2.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomname2.setOpaque(true);
		roomname2.setBackground(labelColor);
		
		user2.setPreferredSize(new Dimension(200, 100));	
		user2.setHorizontalAlignment(JLabel.CENTER);			
		user2.setFont(font1);
		user2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		user2.setOpaque(true);
		user2.setBackground(labelColor);
		
		join2.setPreferredSize(new Dimension(150, 100)); 
		join2.setFont(font2);
		join2.setBackground(btnColor);
		
		//방번호 방이름 참여자수 버튼의 크기 및 폰트 설정
		roomnum3.setPreferredSize(new Dimension(80, 100));
		roomnum3.setHorizontalAlignment(JLabel.CENTER);				
		roomnum3.setFont(font1);
		roomnum3.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomnum3.setOpaque(true);
		roomnum3.setBackground(labelColor);
		
		roomname3.setPreferredSize(new Dimension(400, 100));	
		roomname3.setHorizontalAlignment(JLabel.CENTER);				
		roomname3.setFont(font1);
		roomname3.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomname3.setOpaque(true);
		roomname3.setBackground(labelColor);
		
		user3.setPreferredSize(new Dimension(200, 100));	
		user3.setHorizontalAlignment(JLabel.CENTER);			
		user3.setFont(font1);
		user3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		user3.setOpaque(true);
		user3.setBackground(labelColor);
		
		join3.setPreferredSize(new Dimension(150, 100)); 
		join3.setFont(font2);
		join3.setBackground(btnColor);
		
		//방번호 방이름 참여자수 버튼의 크기 및 폰트 설정
		roomnum4.setPreferredSize(new Dimension(80, 100));
		roomnum4.setHorizontalAlignment(JLabel.CENTER);				
		roomnum4.setFont(font1);
		roomnum4.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomnum4.setOpaque(true);
		roomnum4.setBackground(labelColor);
		
		roomname4.setPreferredSize(new Dimension(400, 100));	
		roomname4.setHorizontalAlignment(JLabel.CENTER);				
		roomname4.setFont(font1);
		roomname4.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomname4.setOpaque(true);
		roomname4.setBackground(labelColor);
		
		user4.setPreferredSize(new Dimension(200, 100));	
		user4.setHorizontalAlignment(JLabel.CENTER);			
		user4.setFont(font1);
		user4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		user4.setOpaque(true);
		user4.setBackground(labelColor);
		
		join4.setPreferredSize(new Dimension(150, 100)); 
		join4.setFont(font2);
		join4.setBackground(btnColor);
		
		//방번호 방이름 참여자수 버튼의 크기 및 폰트 설정
		roomnum5.setPreferredSize(new Dimension(80, 100));
		roomnum5.setHorizontalAlignment(JLabel.CENTER);				
		roomnum5.setFont(font1);
		roomnum5.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomnum5.setOpaque(true);
		roomnum5.setBackground(labelColor);
		
		roomname5.setPreferredSize(new Dimension(400, 100));	
		roomname5.setHorizontalAlignment(JLabel.CENTER);				
		roomname5.setFont(font1);
		roomname5.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomname5.setOpaque(true);
		roomname5.setBackground(labelColor);
		
		user5.setPreferredSize(new Dimension(200, 100));	
		user5.setHorizontalAlignment(JLabel.CENTER);			
		user5.setFont(font1);
		user5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		user5.setOpaque(true);
		user5.setBackground(labelColor);
		
		join5.setPreferredSize(new Dimension(150, 100)); 
		join5.setFont(font2);
		join5.setBackground(btnColor);
		
		//p3 
		//아이디와 닉네임 보여주는 라벨의 크기와 배경, 폰트 설정
		id.setPreferredSize(new Dimension(160, 50));			
		id.setHorizontalAlignment(JLabel.CENTER);				
		id.setFont(font1);
		id.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		id.setOpaque(true);
		id.setBackground(idcolor);
		
		nickname.setPreferredSize(new Dimension(160, 50));		
		nickname.setHorizontalAlignment(JLabel.CENTER);				
		nickname.setFont(font1);
		nickname.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		nickname.setOpaque(true);
		nickname.setBackground(idcolor);
		
		//p3 버튼
		//닉네임 재설정, 도움말, 종료 버튼의 크기 설정
		changename.setPreferredSize(new Dimension(160, 50));
		help.setPreferredSize(new Dimension(160, 50));
		exit.setPreferredSize(new Dimension(160, 50));
	
		//p1에 추가
		panel1.add(panel2,BorderLayout.CENTER);
		panel1.add(panel3,BorderLayout.WEST);
		
		//p2에 추가
		panel2.add(roomnum1);
		panel2.add(roomname1);
		panel2.add(user1);
		panel2.add(join1);
		
		panel2.add(roomnum2);
		panel2.add(roomname2);
		panel2.add(user2);
		panel2.add(join2);
		
		panel2.add(roomnum3);
		panel2.add(roomname3);
		panel2.add(user3);
		panel2.add(join3);

		panel2.add(roomnum4);
		panel2.add(roomname4);
		panel2.add(user4);
		panel2.add(join4);

		panel2.add(roomnum5);
		panel2.add(roomname5);
		panel2.add(user5);
		panel2.add(join5);
		
		
		//참가 버튼 액션리스너 추가
		join1.addActionListener(ml);
		join2.addActionListener(ml);
		join3.addActionListener(ml);
		join4.addActionListener(ml);
		join5.addActionListener(ml);
		//닉네임 재설정 도움말 종료 액션리스너 추가
		changename.addActionListener(ml);
		help.addActionListener(ml);
		exit.addActionListener(ml);
		//p3에 추가
		//좌측의 컴포넌트들 추가
		panel3.add(p5, BorderLayout.NORTH);
		p5.add(id);
		p5.add(nickname);
		panel3.add(p4, BorderLayout.SOUTH);
		p4.add(changename);
		p4.add(help);
		p4.add(exit);

		panel2.setBackground(p2Color);
		
		//배경색 설정
		Color p3Color = new Color(237,210,243);
		panel3.setBackground(p3Color);
		p4.setBackground(p3Color);
		
		setContentPane(panel1); //컨텐츠팬 설정
	

		setSize(1040, 570);
		//중앙에 배치
		Dimension frameSize = this.getSize();   //프레임 사이즈를 가져오기
 	   	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

	}
	//액션 리스너
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			//1~5번방 참가 버튼
			if(b.getText().equals("1번 참가")) {
				op.myconnector.sendmode("1");	// 모드를 1번을 서버로 전송
				op.gf.setVisible(true);			// 게임룸 보여짐
				op.gf.setTitle(roomname1.getText());	//게임룸 타이틀을 해당 게임 주제로 설정
				dispose( );						// 로비화면 종료
			}else if(b.getText().equals("2번 참가")){
				op.myconnector.sendmode("2");
				op.gf.setVisible(true);
				op.gf.setTitle(roomname2.getText());
				dispose( );
			}else if(b.getText().equals("3번 참가")){
				op.myconnector.sendmode("3");
				op.gf.setVisible(true);
				op.gf.setTitle(roomname3.getText());
				dispose( );
			}else if(b.getText().equals("4번 참가")){
				op.myconnector.sendmode("4");
				op.gf.setVisible(true);
				op.gf.setTitle(roomname4.getText());
				dispose( );
			}else if(b.getText().equals("5번 참가")){
				op.myconnector.sendmode("5");
				op.gf.setVisible(true);
				op.gf.setTitle(roomname5.getText());
				dispose( );
				
				//도움말 버튼
			}else if(b.getText().equals("도움말")){
				HelpFrame hf = new HelpFrame();		//도움말 인터페이스를 띄움
				//닉네임 설정 버튼
			}else if(b.getText().equals("닉네임 설정")){ 
				nc = new NickChangeFrame(op);		//닉네임 재설정 인터페이스를 띄움
				
			}else { //종료버튼 
				dispose(); //로비 화면 종료
			}
		}
		
	}

}
