import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class LoginFrame extends JFrame{
	
	//패널 생성
	JPanel panel1 = new JPanel(new BorderLayout());
	JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
	JPanel panel3 = new JPanel(new GridLayout(4,1,1,5));
	
	// 로그이능ㄹ 위한 버튼과 라벨, 텍스트필드 선언
	JButton login = new JButton("로그인");
	JButton cancel = new JButton("지우기");
	JTextField typeId = new JTextField();
	JPasswordField typePassword = new JPasswordField();
	JLabel id = new JLabel("I    D");
	JLabel password = new JLabel("Passwrod");
	
	// 회원가입 ID찾기 PW찾기 기능을 위한 버튼 추가
	JButton signup = new JButton("회원가입");
	JButton findid = new JButton("아이디찾기");
	JButton findpw = new JButton("비밀번호찾기");
	//종료버튼
	JButton exit = new JButton("종료");
	
	Operator op;
	ErrorFrame er;
	SignupFrame sf;
	IdfindFrame iff;
	PwfindFrame pf;
	
	public LoginFrame(Operator _op) {
		
		op = _op;
		//액션리스너 추가
		MyActionListener ml = new MyActionListener();
		
		
		setTitle("LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//프레임이 종료되면 프로그램도 종료
		
		//라벨, 텍스트필드의 크기 조정
		id.setPreferredSize(new Dimension(70, 30));			//id 라벨 크기 조정
		typeId.setPreferredSize(new Dimension(300, 30));	// id입력하는 텍스트필드 크기 조정 
		password.setPreferredSize(new Dimension(70, 30));	// 비밀번호 라벨 크기 조정
		typePassword.setPreferredSize(new Dimension(300, 30)); // password입력받는 텍스트필드 크기 조정
		login.setPreferredSize(new Dimension(123, 30));			// 입력 버튼 크기조정
		cancel.setPreferredSize(new Dimension(123, 30));		// 초기화 버튼 크기 조정
		
		//회원가입 , ID찾기, PW찾기 종료 버튼들 크기조정
		signup.setPreferredSize(new Dimension(123, 30));
		findid.setPreferredSize(new Dimension(123, 30));
		findpw.setPreferredSize(new Dimension(123, 30));
		exit.setPreferredSize(new Dimension(123, 30));
		//패널1에 패널2 3 추가
		panel1.add(panel2,BorderLayout.CENTER);//로그인 관련
		panel1.add(panel3,BorderLayout.EAST); // 회원가입 관련
		
		//패널2에 로그인관련 컴포넌트추가 및 액션리스너 추가
		panel2.add(id);
		panel2.add(typeId);
		panel2.add(password);
		panel2.add(typePassword);
		panel2.add(login);
		login.addActionListener(ml);
		panel2.add(cancel);
		cancel.addActionListener(ml);
		
		//패널3에 회원가입 ID찾기 PW찾기 종료 버튼 추가 및 액션리스너 추가
		panel3.add(signup);
		signup.addActionListener(ml);
		panel3.add(findid);
		findid.addActionListener(ml);
		panel3.add(findpw);
		findpw.addActionListener(ml);
		panel3.add(exit);
		exit.addActionListener(ml);
		
		//색깔 설정
		Color p2Color = new Color(232,255,255);
		panel2.setBackground(p2Color);
		
		//색깔
		Color p3Color = new Color(232,255,255);
		panel3.setBackground(p3Color);
		
		setContentPane(panel1);
		
		setResizable(false);
		setSize(550, 160);
		
		//화면중앙에 배치
		Dimension frameSize = this.getSize();   //프레임 사이즈를 가져오기
 	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		setVisible(true);
		
	}
	// 액션리스너
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			//로그인 버튼
			if(b.getText().equals("로그인")) {
				
				String pw = "";													//Password에서 입력값 읽어오기
				for(int i=0; i<typePassword.getPassword().length; i++) {
					pw =  pw+ typePassword.getPassword()[i];
				}
				if(op.myconnector.sendLoginInfor(typeId.getText(), pw)) {		//서버를 통해 로그인 가능여부 체크
					//트루면 로비 프레임 시작 및 닉네임 설정, 로그인 화면 종료
					op.lobbyf.setVisible(true);
					op.lobbyf.nickname.setText(op.myconnector.mynick);
					op.lobbyf.id.setText(typeId.getText());
					dispose();
				}else {	// false면 로그인 실패 안내
					System.out.println("로그인 실패 ");
					er = new ErrorFrame("아이디와 비밀번호를 확인해주세요");
				}
				
			}else if(b.getText().equals("지우기")) {	//지우기 버튼
				//텍스트필드 초기화
				typeId.setText("");
				typePassword.setText("");
			}else if(b.getText().equals("회원가입")) {	//회원가입 버튼
				//회원가입 화면 띄움
				sf = new SignupFrame(op);
			}else if(b.getText().equals("아이디찾기")) {//아이디 찾기 버튼
				//아이디 찾기 화면 띄움
				iff = new IdfindFrame(op);
			}else if(b.getText().equals("비밀번호찾기")) { //비밀번호 찾기 버튼
				//비밀번호 찾기 화면 띄움
				pf = new PwfindFrame(op);
			}else if(b.getText().equals("종료")) { //종료버튼
				System.exit(0);	// 프로그램 종료
			}
			
		}
	}
}
