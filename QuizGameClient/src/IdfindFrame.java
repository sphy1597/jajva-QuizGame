import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class IdfindFrame extends JFrame{
	
	JPanel panel = new JPanel(new FlowLayout()); // 레이아웃 선언 
	JButton find = new JButton("찾기"); // Button find 선언 
	JButton exit = new JButton("돌아가기"); // Button 돌아가기 선언
	JTextField typename = new JTextField(); // 연락처 받은곳  선언
	JTextField typepn = new JTextField(); // 연락처 받은곳  선언
	JLabel name = new JLabel("n a m e"); // 이름 라벨
	JLabel pn = new JLabel("연락처"); // 연락처 라벨
	Operator op;
	ErrorFrame er;
	IdfindFrame(Operator _op){
	
		op = _op;
		
		
		setTitle("FIND ID");
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//프레임이 종료되면 프로그램도 종료
		
		MyActionListener ml = new MyActionListener();
		
		// 컴포넌트들의 크기 조정 
		name.setPreferredSize(new Dimension(70, 30));			
		typename.setPreferredSize(new Dimension(280, 30));	
		pn.setPreferredSize(new Dimension(70, 30));	
		typepn.setPreferredSize(new Dimension(280, 30)); 
		find.setPreferredSize(new Dimension(123, 30));		
		exit.setPreferredSize(new Dimension(123, 30));		
		//컴포넌트들 패널에 추가
		panel.add(name); //  이름	라벨
		panel.add(typename); // 이름 입력받는 textfield 추가 
		panel.add(pn); // PASSWORD 추가 
		panel.add(typepn); // 입력된 PASSWORD 추가 
		panel.add(find);	//enter버튼 추가
		panel.add(exit);	// cancel 입력 버튼 추가
		
		setContentPane(panel); // 패널로 컨텐츠팬 설정
		
		//맥션리스너 추가
		find.addActionListener(ml);
		exit.addActionListener(ml);
		
		//폰트설정
		Color pcolor = new Color(255,217,250);
		//배경색 설정
		panel.setBackground(pcolor);
		
		
		setResizable(false);	//크기조정 불가
		setSize(400,150);		//크기조절
		// 화면중앙에 배치
		Dimension frameSize = this.getSize();   //프레임 사이즈를 가져오기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		setVisible(true);
		
	}
	//액션리스너
	class MyActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e ) {
			JButton b = (JButton)e.getSource();
			
			//찾기 버튼을 누르면 회원정보를 보냄
			if(b.getText().equals("찾기")) {
				String result = op.myconnector.sendIdfind(typename.getText(), typepn.getText());
				if(result.equals("ERROR!")) {					//일치하는 정보가 없으면 안내문구 보여줌
					er = new ErrorFrame("일치하는 회원정보가 없습니다.");
					typename.setText("");
					typepn.setText("");
					
				}else {			// 일치하는 정보가 있으면 아이디를 보여줌
					er = new ErrorFrame("I D : "+result);
					dispose();
				}
				
			}else {	//돌아가기 버튼을 누르면 인터페이스 종료
				dispose();
			}
	
		}
	}
}
