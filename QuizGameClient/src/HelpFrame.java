import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.net.*;
import java.util.*;




public class HelpFrame extends JFrame{ // ä�� ��� ����ȭ��
	
	DefaultListModel<String> users = new DefaultListModel<>(); // JList�� �������� ������ �����ϱ� ���� ���Ǵ� ����Ʈ��
	
	JTextField wf = new JTextField("ä���� �Է¹޴� �κ��Դϴ�. ");			// ä�ó����� �Է��ϴ� �ؽ�Ʈ�ʵ�				
	JList userlist = new JList(users);			// �������� ������ �����ֱ� ���� JList
	JTextArea ta = new JTextArea("�������� ä���� ������ �����ִ� �κ��Դϴ�. ");				// ä���� ������ �������� �ؽ�Ʈ �����
	
	
	JLabel anslabel = new JLabel("������ �������� �κ��Դϴ�.");	 	//���� �� ����
	JLabel hintlabel = new JLabel("���ѽð� ������ ������ ��Ʈ�� �����ݴϴ�.");			//��Ʈ �� ����
	//���� ���� ����
	JTextArea QuestionArea = new JTextArea(" ������ ���� �Ǵ� �κ��Դϴ�."+"\n"+" �ʼ��̳� ���ڸ� �����ݴϴ�. "+"\n"+"������ �ۼ��� ���� ���⸦ ���� ���� �ۼ����ּ���");

	JButton exit = new JButton("���ư���");		//�κ�� ���ư��� ��ư	
	
	public HelpFrame() {		// ���ӿ� ���� ������ �˷��ִ� �������̽�
		setTitle("����");
		
		MyActionListener ml = new MyActionListener();		//���ư��� ��ư �׼Ǹ�����
		
         // Gameroom�� �����ϰ� ������� �������̽�
		JPanel mainPanel = new JPanel(new GridLayout(1,2));
		JPanel gamePanel = new JPanel(new BorderLayout());
		JPanel chatPanel = new JPanel(new BorderLayout());
		JPanel helppanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(); 		// �ؽ�Ʈ�ʵ�, �ؽ�Ʈ���� ���� �г�
		JPanel p2 = new JPanel();		// �� ����Ʈ ��ư�� ���� �г�
		
		//���� �гο� �߰�
		mainPanel.add(gamePanel);
		mainPanel.add(chatPanel);
		
		//�����гο� ���ư��� ��ư �߰�
		helppanel.add(mainPanel, BorderLayout.CENTER);
		helppanel.add(exit, BorderLayout.SOUTH);
		exit.addActionListener(ml);
		setContentPane(helppanel);
		
		//ä�� �гο� �߰�
		chatPanel.add(p1, BorderLayout.CENTER);
		chatPanel.add(p2, BorderLayout.EAST);
		
		// ä�ð��� �κ�
		// �ؽ�Ʈ�ʵ�, �ؽ�Ʈ �����
		p1.setLayout(new BorderLayout());		//p1�� �������̾ƿ����� ����
		p1.add(wf,BorderLayout.SOUTH);			//wf�� p1 �������̾ƿ��� ���ʿ� ��ġ
		wf.setPreferredSize(new Dimension(300,30));
		p1.add(new JScrollPane(ta), BorderLayout.CENTER);	// p1�� ���Ϳ� ��ũ���� �� �ؽ�Ʈ���� �߰�
		ta.setEditable(false);
		
		// �����ڼ� p2
		p2.setLayout(new BorderLayout());			//p2�� �������̾ƿ����� ����
		JLabel ul = new JLabel(" ������ ");			// ���� �߰��ϰ� �����ڷ� �ؽ�Ʈ ����
		p2.add(ul,BorderLayout.NORTH);				// ���� ���ʿ� ��ġ
		ul.setHorizontalAlignment(JLabel.CENTER);				
		ul.setPreferredSize(new Dimension(80, 30));				
		userlist.setFixedCellWidth(80);				//����Ʈ ũ�� ����
		
		p2.add(new JScrollPane(userlist), BorderLayout.CENTER);	// ��ũ���� �� ����Ʈ�� p2 ���Ϳ� ��ġ
		
		JButton sendbtn = new JButton("������");		//������ ��ư ����
		sendbtn.setPreferredSize(new Dimension(80, 30));	
		//sendbtn.addActionListener(m1);				// ������ ��ư �׼Ǹ����� ����
		p2.add(sendbtn,BorderLayout.SOUTH);			//p2 ���ʿ� ��ġ
		
		//  -���� ���� �κ�-
		//��Ʈ����
		Font font1 = new Font("�������", Font.PLAIN,25);
		Color backColor = new Color(254,245,237);
		//���� �� �߰� �� ��Ʈ, �� ����
		gamePanel.add(anslabel, BorderLayout.NORTH);
		anslabel.setOpaque(true);
		anslabel.setBackground(backColor);
		anslabel.setFont(font1);
		//�������� �κ�, ��Ʈ �� ��Ʈ����
		gamePanel.add(QuestionArea, BorderLayout.CENTER);
		gamePanel.add(hintlabel, BorderLayout.SOUTH);		 
		hintlabel.setOpaque(true);
		hintlabel.setBackground(backColor);
		hintlabel.setFont(font1);
	
		//���� ����
		gamePanel.setBackground(backColor);
		
		setSize(1300,720);		
	
		setVisible(true);
	
	}
	//���ư��� �׼Ǹ�����
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			if(b.getText().equals("���ư���")) { //���ư��� ��ư�� ������ â�� ����
				dispose();
			}
			
		}
	}
}