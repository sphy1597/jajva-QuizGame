import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.net.*;
import java.util.*;




public class GameroomFrame extends JFrame{ // ä�� ��� ����ȭ��
	
	DefaultListModel<String> users = new DefaultListModel<>(); // JList�� �������� ������ �����ϱ� ���� ���Ǵ� ����Ʈ��
	
	JTextField wf = new JTextField();			// ä�ó����� �Է��ϴ� �ؽ�Ʈ�ʵ�				
	JList userlist = new JList(users);			// �������� ������ �����ֱ� ���� JList
	JTextArea ta = new JTextArea();				// ä���� ������ �������� �ؽ�Ʈ �����
	
	JButton sendbtn = new JButton("������");		//������ ��ư ����
	
	JLabel anslabel = new JLabel(" ���� :  ");	// ������ ����ϴ� ��
	JLabel hintlabel = new JLabel(" ��Ʈ : "); 	// ��Ʈ�� ����ϴ� �� 
	JTextArea QuestionArea = new JTextArea(" "); //������ �����Ǵ� �κ�
	
	
	Operator op;
	
	public GameroomFrame(Operator _op) { 	// ��������� ���� �Ǵ� �������̽�
		
		op = _op;
		
		MyActionListener m1 = new MyActionListener();	//�׼Ǹ����� ��ü ����
		
		
		setTitle(" ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//�������� ����Ǹ� ���α׷��� ����
         
		JPanel mainPanel = new JPanel(new GridLayout(1,2));  //������ ����Ǵ� �гΰ� ä���� ����Ǵ� �г�
		JPanel gamePanel = new JPanel(new BorderLayout());  // ���� �г�
		JPanel chatPanel = new JPanel(new BorderLayout());  // ä�� �г�
		
		JPanel p1 = new JPanel(); 		// �ؽ�Ʈ�ʵ�, �ؽ�Ʈ���� ���� �г�
		JPanel p2 = new JPanel();		// �� ����Ʈ ��ư�� ���� �г�
		
		mainPanel.add(gamePanel);      // �����гο� �����г� �߰�
		mainPanel.add(chatPanel);      // �����гο� ä���г� �߰�
		
		setContentPane(mainPanel);    // ���������� �����гη� ����
		
		chatPanel.add(p1, BorderLayout.CENTER);  // ä���гο� �г�1�� ���� ���Ϳ� �߰� ( textarea )
		chatPanel.add(p2, BorderLayout.EAST);    // ä���гο� �г�2 ���ʿ� �߰�
		
		// ä�ð��� �κ�
		// �ؽ�Ʈ�ʵ�, �ؽ�Ʈ �����
		p1.setLayout(new BorderLayout());				//p1�� �������̾ƿ����� ����
		p1.add(wf,BorderLayout.SOUTH);					//wf�� p1 �������̾ƿ��� ���ʿ� ��ġ
		wf.setPreferredSize(new Dimension(300,30));  	//wf�� ũ�⸦ ����
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
		
		sendbtn.setPreferredSize(new Dimension(80, 30));	
		sendbtn.addActionListener(m1);				// ������ ��ư �׼Ǹ����� ����
		p2.add(sendbtn,BorderLayout.SOUTH);			//p2 ���ʿ� ��ġ
		
		// ��Ʈ����
		Font font1 = new Font("�������", Font.PLAIN,25);
		Color backColor = new Color(254,245,237);
		Font font2 = new Font("�������", Font.PLAIN,30);
		Color areaColor = new Color(255,255,255);
		
		//������ �����Ǵ� �ؽ�Ʈ �����
		QuestionArea.setLineWrap(true);    //�ڵ� �ٹٲ�
		QuestionArea.setFont(font2);
	
		//������ ��µǴ� �κ� ���, ��Ʈ ����
		gamePanel.add(anslabel, BorderLayout.NORTH);
		anslabel.setOpaque(true);
		anslabel.setBackground(backColor);
		anslabel.setFont(font1);
		
		//������ �����Ǵ� �κ� ��Ʈ, ��� ����
		gamePanel.add(QuestionArea, BorderLayout.CENTER);
		gamePanel.add(hintlabel, BorderLayout.SOUTH);		 
		hintlabel.setOpaque(true);
		hintlabel.setBackground(backColor);
		hintlabel.setFont(font1);
		
		//�г��� ������ ����
		gamePanel.setBackground(backColor);
		
		//������ ũ�� ����
		setSize(1300,700);		
	
	}
	//�׼Ǹ����� 
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			if(b.getText().equals("������")) { //������ ��ư�� ������ ä���� ����
				String msg = wf.getText();
				op.myconnector.sendmsg(msg);
				wf.setText("");
			}
			
		}
	}
}