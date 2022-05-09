import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class SignupFrame extends JFrame{	//회원가입 기능
	
	//패널 버튼 라벨 컴포넌트 선언
	JPanel panel = new JPanel(new FlowLayout());
	JButton ok = new JButton("회원가입");
	JButton cancel = new JButton("돌아가기"); 
	
	JTextField typeId = new JTextField(); 
	JPasswordField typePassword = new JPasswordField();
	JTextField typeName = new JTextField();
	JTextField typePn = new JTextField();
	JTextField typeNickname = new JTextField();
	
	JLabel id = new JLabel("I   D");
	JLabel password = new JLabel("Password"); 
	JLabel name = new JLabel("N a m e");		
	JLabel pn = new JLabel("Phone number");		
	JLabel nickname = new JLabel("Nick name");
	JLabel label = new JLabel("정보를 입력해 주세요");
	Operator op;
	MyConnector mc;
	ErrorFrame er;
	public SignupFrame(Operator _op) {

		setTitle("회원가입");
		
		op = _op;
		mc = op.myconnector;

		
		// 버튼 텍스트필드 크기 조정
		id.setPreferredSize(new Dimension(100, 30));
		typeId.setPreferredSize(new Dimension(300, 30));
		password.setPreferredSize(new Dimension(100, 30));
		typePassword.setPreferredSize(new Dimension(300, 30));
		name.setPreferredSize(new Dimension(100, 30));
		typeName.setPreferredSize(new Dimension(300, 30));
		pn.setPreferredSize(new Dimension(100, 30));
		typePn.setPreferredSize(new Dimension(300, 30));
		nickname.setPreferredSize(new Dimension(100, 30));
		typeNickname.setPreferredSize(new Dimension(300, 30));
		ok.setPreferredSize(new Dimension(200, 30));
		cancel.setPreferredSize(new Dimension(200, 30));
		
		//버튼에 액션리스터 연결
		MyActionListener ml = new MyActionListener();
		
		
		//패널에 버튼 텍스트필드, 라벨 추가
		panel.add(id);
		panel.add(typeId);
		panel.add(password);
		panel.add(typePassword);
		panel.add(name);
		panel.add(typeName);
		panel.add(pn);
		panel.add(typePn);
		panel.add(nickname);
		panel.add(typeNickname);
		panel.add(ok);
		ok.addActionListener(ml);
		panel.add(cancel);
		cancel.addActionListener(ml);
		panel.add(label);
		
		setContentPane(panel);  //컨텐츠 팬을 패널로 설정
		
		setResizable(false);		//크기조정 불가하게 만듬
		setSize(450,270);
		
		//화면 중앙에 배치
		Dimension frameSize = this.getSize();   //프레임 사이즈를 가져오기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	    
	    setVisible(true);
	
	}
	//액션리스너
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			if(b.getText().equals("회원가입")) {//회원가입 버튼
				//입력받은 정보들을 서버로 보냄
				String pw = "";
				for(int i = 0 ; i <typePassword.getPassword().length;i++) {
					pw = pw + typePassword.getPassword()[i];
				}
				String id = typeId.getText();
				String name = typeName.getText();
				String pn = typePn.getText();
				String nickname = typeNickname.getText();
				if(mc.sendsignup(id,pw,name,pn,nickname)) {	//결과가 true면 안내창을 띄우고 화면 종료
					er = new ErrorFrame("회원가입 되었습니다.");
					dispose();
				}else {										//false인경우 안내창을 띄우고 텍스트필드들을 초기화
					er = new ErrorFrame("중복된 회원이 있습니다.");
					typeId.setText("");
					typePassword.setText("");
					typeName.setText("");
					typePn.setText("");
					typeNickname.setText("");
				}
				
				
			}else {//돌아가기 버튼
				dispose();
			}
		}
	}
}