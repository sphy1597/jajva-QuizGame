import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ErrorFrame extends JFrame{
	JLabel text = new JLabel(" ");	//�ȳ������� ������ ��
	JPanel p1 = new JPanel(new BorderLayout());		// �����г�
	JButton exit = new JButton("Ȯ��");				//ȭ���� �����ϴ� ��ư
	
	ErrorFrame(String msg){ // �α��� ���� ���п� ���� ������ �˷��ִ� �������̽�
		setTitle("�ȳ�");
		MyActionListener ml = new MyActionListener();	//��ư �׼Ǹ�����
		p1.add(text, BorderLayout.CENTER);				//���Ϳ� �ȳ��� �߰�
		p1.add(exit, BorderLayout.SOUTH);				//���ʿ� ��ư�߰�
		exit.addActionListener(ml);						//�׼Ǹ����� �߰�
		setContentPane(p1);								//�������� ����
		text.setText(msg);								//�޾ƿ� �޼����� �ȳ� ������ ����
		text.setHorizontalAlignment(JLabel.CENTER);		//���� �߾����� ����
		
		//�α��� â�� ȭ�� �߾ӿ� ��ġ��Ű��...
  	    Dimension frameSize = this.getSize();   //������ ����� ��������
 	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		
		setSize(300,120);
		setVisible(true);
	}
	
	class MyActionListener implements ActionListener{ //Ȯ�� ��ư �׼Ǹ�����
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			if(b.getText().equals("Ȯ��")) {	//��ư�� ������ ��
				dispose();					//ȭ�� ����
			}
		}
	}
	
	
}