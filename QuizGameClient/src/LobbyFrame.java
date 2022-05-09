import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class LobbyFrame extends JFrame{
	
	JPanel panel1 = new JPanel(new BorderLayout());
	//p2 ����
	JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	//p3�� ����
	JPanel panel3 = new JPanel(new BorderLayout());
	
	JPanel p4 = new JPanel(new GridLayout(3,1,5,5));
	JPanel p5 = new JPanel(new GridLayout(2,1));
	//p2��
	
	//�� ���� ������ �����ִ� �󺧰� �����ϴ� ��ư
	JLabel roomnum1 = new JLabel("1");						// ���ȣ
	JLabel roomname1 = new JLabel("�ʼ����� ��ȭ ���� ���߱�");		// ���̸�
	JLabel user1 = new JLabel(" 0�� ������");					// ������ ��
	JButton join1 = new JButton("1�� ����");					// ������ư
	//1�� ����
	JLabel roomnum2 = new JLabel("2");
	JLabel roomname2 = new JLabel("���纸�� �뷡 ���� ���߱�");
	JLabel user2 = new JLabel(" 0�� ������");
	JButton join2 = new JButton("2�� ����");
	//1�� ����
	JLabel roomnum3 = new JLabel("3");
	JLabel roomname3 = new JLabel("�ʼ����� �뷡 ���� ���߱�");
	JLabel user3 = new JLabel(" 0�� ������");
	JButton join3 = new JButton("3�� ����");
	//1�� ����
	JLabel roomnum4 = new JLabel("4");
	JLabel roomname4 = new JLabel("���� ���� ���߱�");
	JLabel user4 = new JLabel(" 0�� ������");
	JButton join4 = new JButton("4�� ����");
	//1�� ����
	JLabel roomnum5 = new JLabel("5");
	JLabel roomname5 = new JLabel("���ڼ��� ���߱�");
	JLabel user5 = new JLabel(" 0�� ������");
	JButton join5 = new JButton("5�� ����");
	
	//���� ��ܿ� ���̵�� �г����� ����ϴ� ��
	JLabel id = new JLabel("I D");
	JLabel nickname = new JLabel(" nickname ");
	//���� �ϴܿ� ��ư 
	JButton changename = new JButton("�г��� ����");		// �г��� �缳�� ��ư
	JButton help = new JButton("����");					// ���� �������̽��� ���� ��ư
	JButton exit = new JButton("����");					// ���� ��ư
	String nick;	//���� �г����� ����
	String myid;	//���� ���̵� ����
	
	
	Operator op;
	ErrorFrame er;
	NickChangeFrame nc;
	
	public LobbyFrame(Operator _op) {
		
		//�׼Ǹ����� �߰�
		MyActionListener ml = new MyActionListener();
		
		setTitle("LOGIN");	//Ÿ��Ʋ 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//�������� ����Ǹ� ���α׷��� ����
		
		op = _op;
		
		//�г��Ӱ� ���̵� ����
		nickname.setText(nick);
		id.setText(myid);
		
		//��Ʈ ����
		Font font1 = new Font("�������", Font.PLAIN,30);
		Font font2 = new Font("�������", Font.PLAIN,25);
		Color p2Color = new Color(255,196,225);
		Color labelColor = new Color(251,255,226);
		Color btnColor = new Color(255,235,204);
		Color idcolor = new Color(245,245,245);
		
		//p2
		//���ȣ ���̸� �����ڼ� ��ư�� ũ�� �� ��Ʈ ����
		roomnum1.setPreferredSize(new Dimension(80, 100));
		roomnum1.setHorizontalAlignment(JLabel.CENTER);				
		roomnum1.setFont(font1);
		roomnum1.setOpaque(true);
		roomnum1.setBackground(labelColor);
		roomnum1.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		
		roomname1.setPreferredSize(new Dimension(400, 100));	
		roomname1.setHorizontalAlignment(JLabel.CENTER);				
		roomname1.setFont(font1);
		roomname1.setBorder(BorderFactory.createLineBorder(Color.BLACK));	
		roomname1.setOpaque(true);
		roomname1.setBackground(labelColor);
		
		user1.setPreferredSize(new Dimension(200, 100));	
		user1.setHorizontalAlignment(JLabel.CENTER);			
		user1.setFont(font1);
		user1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		user1.setOpaque(true);
		user1.setBackground(labelColor);
		
		join1.setPreferredSize(new Dimension(150, 100)); 
		join1.setFont(font2);
		join1.setBackground(btnColor);
		
		//���ȣ ���̸� �����ڼ� ��ư�� ũ�� �� ��Ʈ ����
		roomnum2.setPreferredSize(new Dimension(80, 100));
		roomnum2.setHorizontalAlignment(JLabel.CENTER);				
		roomnum2.setFont(font1);
		roomnum2.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomnum2.setOpaque(true);
		roomnum2.setBackground(labelColor);
		
		roomname2.setPreferredSize(new Dimension(400, 100));	
		roomname2.setHorizontalAlignment(JLabel.CENTER);				
		roomname2.setFont(font1);
		roomname2.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomname2.setOpaque(true);
		roomname2.setBackground(labelColor);
		
		user2.setPreferredSize(new Dimension(200, 100));	
		user2.setHorizontalAlignment(JLabel.CENTER);			
		user2.setFont(font1);
		user2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		user2.setOpaque(true);
		user2.setBackground(labelColor);
		
		join2.setPreferredSize(new Dimension(150, 100)); 
		join2.setFont(font2);
		join2.setBackground(btnColor);
		
		//���ȣ ���̸� �����ڼ� ��ư�� ũ�� �� ��Ʈ ����
		roomnum3.setPreferredSize(new Dimension(80, 100));
		roomnum3.setHorizontalAlignment(JLabel.CENTER);				
		roomnum3.setFont(font1);
		roomnum3.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomnum3.setOpaque(true);
		roomnum3.setBackground(labelColor);
		
		roomname3.setPreferredSize(new Dimension(400, 100));	
		roomname3.setHorizontalAlignment(JLabel.CENTER);				
		roomname3.setFont(font1);
		roomname3.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomname3.setOpaque(true);
		roomname3.setBackground(labelColor);
		
		user3.setPreferredSize(new Dimension(200, 100));	
		user3.setHorizontalAlignment(JLabel.CENTER);			
		user3.setFont(font1);
		user3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		user3.setOpaque(true);
		user3.setBackground(labelColor);
		
		join3.setPreferredSize(new Dimension(150, 100)); 
		join3.setFont(font2);
		join3.setBackground(btnColor);
		
		//���ȣ ���̸� �����ڼ� ��ư�� ũ�� �� ��Ʈ ����
		roomnum4.setPreferredSize(new Dimension(80, 100));
		roomnum4.setHorizontalAlignment(JLabel.CENTER);				
		roomnum4.setFont(font1);
		roomnum4.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomnum4.setOpaque(true);
		roomnum4.setBackground(labelColor);
		
		roomname4.setPreferredSize(new Dimension(400, 100));	
		roomname4.setHorizontalAlignment(JLabel.CENTER);				
		roomname4.setFont(font1);
		roomname4.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomname4.setOpaque(true);
		roomname4.setBackground(labelColor);
		
		user4.setPreferredSize(new Dimension(200, 100));	
		user4.setHorizontalAlignment(JLabel.CENTER);			
		user4.setFont(font1);
		user4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		user4.setOpaque(true);
		user4.setBackground(labelColor);
		
		join4.setPreferredSize(new Dimension(150, 100)); 
		join4.setFont(font2);
		join4.setBackground(btnColor);
		
		//���ȣ ���̸� �����ڼ� ��ư�� ũ�� �� ��Ʈ ����
		roomnum5.setPreferredSize(new Dimension(80, 100));
		roomnum5.setHorizontalAlignment(JLabel.CENTER);				
		roomnum5.setFont(font1);
		roomnum5.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomnum5.setOpaque(true);
		roomnum5.setBackground(labelColor);
		
		roomname5.setPreferredSize(new Dimension(400, 100));	
		roomname5.setHorizontalAlignment(JLabel.CENTER);				
		roomname5.setFont(font1);
		roomname5.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
		roomname5.setOpaque(true);
		roomname5.setBackground(labelColor);
		
		user5.setPreferredSize(new Dimension(200, 100));	
		user5.setHorizontalAlignment(JLabel.CENTER);			
		user5.setFont(font1);
		user5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		user5.setOpaque(true);
		user5.setBackground(labelColor);
		
		join5.setPreferredSize(new Dimension(150, 100)); 
		join5.setFont(font2);
		join5.setBackground(btnColor);
		
		//p3 
		//���̵�� �г��� �����ִ� ���� ũ��� ���, ��Ʈ ����
		id.setPreferredSize(new Dimension(160, 50));			
		id.setHorizontalAlignment(JLabel.CENTER);				
		id.setFont(font1);
		id.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		id.setOpaque(true);
		id.setBackground(idcolor);
		
		nickname.setPreferredSize(new Dimension(160, 50));		
		nickname.setHorizontalAlignment(JLabel.CENTER);				
		nickname.setFont(font1);
		nickname.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		nickname.setOpaque(true);
		nickname.setBackground(idcolor);
		
		//p3 ��ư
		//�г��� �缳��, ����, ���� ��ư�� ũ�� ����
		changename.setPreferredSize(new Dimension(160, 50));
		help.setPreferredSize(new Dimension(160, 50));
		exit.setPreferredSize(new Dimension(160, 50));
	
		//p1�� �߰�
		panel1.add(panel2,BorderLayout.CENTER);
		panel1.add(panel3,BorderLayout.WEST);
		
		//p2�� �߰�
		panel2.add(roomnum1);
		panel2.add(roomname1);
		panel2.add(user1);
		panel2.add(join1);
		
		panel2.add(roomnum2);
		panel2.add(roomname2);
		panel2.add(user2);
		panel2.add(join2);
		
		panel2.add(roomnum3);
		panel2.add(roomname3);
		panel2.add(user3);
		panel2.add(join3);

		panel2.add(roomnum4);
		panel2.add(roomname4);
		panel2.add(user4);
		panel2.add(join4);

		panel2.add(roomnum5);
		panel2.add(roomname5);
		panel2.add(user5);
		panel2.add(join5);
		
		
		//���� ��ư �׼Ǹ����� �߰�
		join1.addActionListener(ml);
		join2.addActionListener(ml);
		join3.addActionListener(ml);
		join4.addActionListener(ml);
		join5.addActionListener(ml);
		//�г��� �缳�� ���� ���� �׼Ǹ����� �߰�
		changename.addActionListener(ml);
		help.addActionListener(ml);
		exit.addActionListener(ml);
		//p3�� �߰�
		//������ ������Ʈ�� �߰�
		panel3.add(p5, BorderLayout.NORTH);
		p5.add(id);
		p5.add(nickname);
		panel3.add(p4, BorderLayout.SOUTH);
		p4.add(changename);
		p4.add(help);
		p4.add(exit);

		panel2.setBackground(p2Color);
		
		//���� ����
		Color p3Color = new Color(237,210,243);
		panel3.setBackground(p3Color);
		p4.setBackground(p3Color);
		
		setContentPane(panel1); //�������� ����
	

		setSize(1040, 570);
		//�߾ӿ� ��ġ
		Dimension frameSize = this.getSize();   //������ ����� ��������
 	   	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

	}
	//�׼� ������
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			//1~5���� ���� ��ư
			if(b.getText().equals("1�� ����")) {
				op.myconnector.sendmode("1");	// ��带 1���� ������ ����
				op.gf.setVisible(true);			// ���ӷ� ������
				op.gf.setTitle(roomname1.getText());	//���ӷ� Ÿ��Ʋ�� �ش� ���� ������ ����
				dispose( );						// �κ�ȭ�� ����
			}else if(b.getText().equals("2�� ����")){
				op.myconnector.sendmode("2");
				op.gf.setVisible(true);
				op.gf.setTitle(roomname2.getText());
				dispose( );
			}else if(b.getText().equals("3�� ����")){
				op.myconnector.sendmode("3");
				op.gf.setVisible(true);
				op.gf.setTitle(roomname3.getText());
				dispose( );
			}else if(b.getText().equals("4�� ����")){
				op.myconnector.sendmode("4");
				op.gf.setVisible(true);
				op.gf.setTitle(roomname4.getText());
				dispose( );
			}else if(b.getText().equals("5�� ����")){
				op.myconnector.sendmode("5");
				op.gf.setVisible(true);
				op.gf.setTitle(roomname5.getText());
				dispose( );
				
				//���� ��ư
			}else if(b.getText().equals("����")){
				HelpFrame hf = new HelpFrame();		//���� �������̽��� ���
				//�г��� ���� ��ư
			}else if(b.getText().equals("�г��� ����")){ 
				nc = new NickChangeFrame(op);		//�г��� �缳�� �������̽��� ���
				
			}else { //�����ư 
				dispose(); //�κ� ȭ�� ����
			}
		}
		
	}

}
