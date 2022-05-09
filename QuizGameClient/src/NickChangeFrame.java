import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class NickChangeFrame extends JFrame{
	
	JPanel panel = new JPanel(new FlowLayout()); // ���̾ƿ� ���� 
	JButton change = new JButton("�����ϱ�"); // ü������ư ���� 
	JButton exit = new JButton("���ư���"); // �����ư ����
	JTextField typename = new JTextField(); // �̸� ������  ����	
	JLabel name = new JLabel("NickName"); // �� type id

	Operator op;
	ErrorFrame er;
	NickChangeFrame(Operator _op){
	
		op = _op;
		
		
		setTitle("NickName Change ");
		//�׼Ǹ����� ����
		MyActionListener ml = new MyActionListener();
		
		name.setPreferredSize(new Dimension(70, 30));			//id �� ũ�� ����
		typename.setPreferredSize(new Dimension(280, 30));	// id�Է��ϴ� �ؽ�Ʈ�ʵ� ũ�� ���� 
		change.setPreferredSize(new Dimension(123, 30));			// �Է� ��ư ũ������
		exit.setPreferredSize(new Dimension(123, 30));		// �ʱ�ȭ ��ư ũ�� ����
		panel.add(name); //  ID �߰� 					
		panel.add(typename); // �Էµ� ID �߰� 
		panel.add(change);	//enter��ư �߰�
		panel.add(exit);	// cancel �Է� ��ư �߰�
		setContentPane(panel); 
		
		//�׼Ǹ������߰�
		change.addActionListener(ml);
		exit.addActionListener(ml);
		
		// ���� ����
		Color pcolor = new Color(235,187,250);
		panel.setBackground(pcolor);
		
		// ȭ�� ������ ����, �����Ұ� �߾ӿ� ��ġ
		setResizable(false);
		setSize(400,120);
		Dimension frameSize = this.getSize();   //������ ����� ��������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		setVisible(true);
		
	}
	//�׼Ǹ�����
	class MyActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e ) {
			JButton b = (JButton)e.getSource();
			
			//�����ϱ� ��ư�� ������
			if(b.getText().equals("�����ϱ�")) {
				op.myconnector.sendnick(typename.getText());	//������ �г�����  ����
				
			}else {
				dispose();
			}
	
		}
	}
	
	// ���氡���̸� �г����� �ٲ���
	void nickchange(String newnick) {
		op.lobbyf.nick = newnick;
		op.lobbyf.nickname.setText(newnick);
		op.myconnector.mynick = newnick;
		er = new ErrorFrame("�г����� ����Ǿ����ϴ�.");
		dispose();
	}
	
	// ����Ұ����̸� �ȳ� �������̽��� ������
	void failchange() {
		er = new ErrorFrame("�ߺ��� �г����� �ֽ��ϴ�.");
	}
}
