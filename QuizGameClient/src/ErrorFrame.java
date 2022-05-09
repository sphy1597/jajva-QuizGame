import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ErrorFrame extends JFrame{
	JLabel text = new JLabel(" ");	//안내문구가 나오는 라벨
	JPanel p1 = new JPanel(new BorderLayout());		// 메인패널
	JButton exit = new JButton("확인");				//화면을 종료하는 버튼
	
	ErrorFrame(String msg){ // 로그인 성공 실패와 같은 문구를 알려주는 인터페이스
		setTitle("안내");
		MyActionListener ml = new MyActionListener();	//버튼 액션리스너
		p1.add(text, BorderLayout.CENTER);				//센터에 안내라벨 추가
		p1.add(exit, BorderLayout.SOUTH);				//남쪽에 버튼추가
		exit.addActionListener(ml);						//액션리스너 추가
		setContentPane(p1);								//컨텐츠팬 설정
		text.setText(msg);								//받아온 메세지를 안내 문구로 설정
		text.setHorizontalAlignment(JLabel.CENTER);		//라벨을 중앙으로 정렬
		
		//로그인 창을 화면 중앙에 배치시키기...
  	    Dimension frameSize = this.getSize();   //프레임 사이즈를 가져오기
 	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		
		setSize(300,120);
		setVisible(true);
	}
	
	class MyActionListener implements ActionListener{ //확인 버튼 액션리스너
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			if(b.getText().equals("확인")) {	//버튼이 눌렸을 때
				dispose();					//화면 종료
			}
		}
	}
	
	
}