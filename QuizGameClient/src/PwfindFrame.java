import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class PwfindFrame extends JFrame{
	
	//컴포넌트들 선언
	JPanel panel = new JPanel(new FlowLayout()); 
	JButton find = new JButton("찾기"); 
	JButton exit = new JButton("돌아가기"); 
	JTextField typeid = new JTextField(); 
	JTextField typepn = new JTextField(); 
	JLabel id = new JLabel("I     D"); 
	JLabel pn = new JLabel("연락처"); 
	Operator op;
	ErrorFrame er;
	
	PwfindFrame(Operator _op){
		
		setTitle("FIND Password");
		//액션리스너 선언
		MyActionListener ml = new MyActionListener();
		op = _op;
		
		//컴포넌트들 크기 설정
		id.setPreferredSize(new Dimension(70, 30));			
		typeid.setPreferredSize(new Dimension(280, 30));	
		pn.setPreferredSize(new Dimension(70, 30));
		typepn.setPreferredSize(new Dimension(280, 30)); 
		find.setPreferredSize(new Dimension(123, 30));			
		exit.setPreferredSize(new Dimension(123, 30));	
		//패널에 컴포넌트 추가 및 컨텐츠팬 설정
		panel.add(id); 				
		panel.add(typeid); 
		panel.add(pn); 
		panel.add(typepn);
		panel.add(find);	
		panel.add(exit);	
		setContentPane(panel); 
		
		//액션리스너추가
		find.addActionListener(ml);
		exit.addActionListener(ml);
		
		//배경색 설정
		Color pcolor = new Color(255,217,250);
		panel.setBackground(pcolor);
		
		
		//화면 크기 조정불가, 크기 설정, 중앙에 배치
		setResizable(false);
		setSize(400,150);
		Dimension frameSize = this.getSize();   //프레임 사이즈를 가져오기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		setVisible(true);
		
	}
	//액션리스너
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			if(b.getText().equals("찾기")) {	//찾기 버튼을 누르면 입력된 정보를 서버로 보냄
				String result = op.myconnector.sendPwfind(typeid.getText(),typepn.getText());
				if(result.equals("ERROR!")) {	//에러인경우 안내 인터페이스 보여줌
					er = new ErrorFrame("일치하는 회원정보가 없습니다.");
					typeid.setText("");
					typepn.setText("");
				}else {	//아닌경우 받은 정보를 출력
					er = new ErrorFrame("PW : " + result);
					dispose();
				}
				
			}else {	//종료버튼 
				dispose();
			}
		}
	}
	

}