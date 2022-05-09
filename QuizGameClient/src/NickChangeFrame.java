import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class NickChangeFrame extends JFrame{
	
	JPanel panel = new JPanel(new FlowLayout()); // 레이아웃 선언 
	JButton change = new JButton("변경하기"); // 체인지버튼 선언 
	JButton exit = new JButton("돌아가기"); // 종료버튼 선언
	JTextField typename = new JTextField(); // 이름 받은곳  선언	
	JLabel name = new JLabel("NickName"); // 라벨 type id

	Operator op;
	ErrorFrame er;
	NickChangeFrame(Operator _op){
	
		op = _op;
		
		
		setTitle("NickName Change ");
		//액션리스너 설정
		MyActionListener ml = new MyActionListener();
		
		name.setPreferredSize(new Dimension(70, 30));			//id 라벨 크기 조정
		typename.setPreferredSize(new Dimension(280, 30));	// id입력하는 텍스트필드 크기 조정 
		change.setPreferredSize(new Dimension(123, 30));			// 입력 버튼 크기조정
		exit.setPreferredSize(new Dimension(123, 30));		// 초기화 버튼 크기 조정
		panel.add(name); //  ID 추가 					
		panel.add(typename); // 입력된 ID 추가 
		panel.add(change);	//enter버튼 추가
		panel.add(exit);	// cancel 입력 버튼 추가
		setContentPane(panel); 
		
		//액션리스너추가
		change.addActionListener(ml);
		exit.addActionListener(ml);
		
		// 배경색 설정
		Color pcolor = new Color(235,187,250);
		panel.setBackground(pcolor);
		
		// 화면 사이즈 설정, 조정불가 중앙에 배치
		setResizable(false);
		setSize(400,120);
		Dimension frameSize = this.getSize();   //프레임 사이즈를 가져오기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		setVisible(true);
		
	}
	//액션리스너
	class MyActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e ) {
			JButton b = (JButton)e.getSource();
			
			//변경하기 버튼을 누르면
			if(b.getText().equals("변경하기")) {
				op.myconnector.sendnick(typename.getText());	//서버로 닉네임을  보냄
				
			}else {
				dispose();
			}
	
		}
	}
	
	// 변경가능이면 닉네임을 바꿔줌
	void nickchange(String newnick) {
		op.lobbyf.nick = newnick;
		op.lobbyf.nickname.setText(newnick);
		op.myconnector.mynick = newnick;
		er = new ErrorFrame("닉네임이 변경되었습니다.");
		dispose();
	}
	
	// 변경불가능이면 안내 인터페이스를 보여줌
	void failchange() {
		er = new ErrorFrame("중복된 닉네임이 있습니다.");
	}
}
