import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class LoginFrame extends JFrame{
	
	//�г� ����
	JPanel panel1 = new JPanel(new BorderLayout());
	JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
	JPanel panel3 = new JPanel(new GridLayout(4,1,1,5));
	
	// �α��̴ɤ� ���� ��ư�� ��, �ؽ�Ʈ�ʵ� ����
	JButton login = new JButton("�α���");
	JButton cancel = new JButton("�����");
	JTextField typeId = new JTextField();
	JPasswordField typePassword = new JPasswordField();
	JLabel id = new JLabel("I    D");
	JLabel password = new JLabel("Passwrod");
	
	// ȸ������ IDã�� PWã�� ����� ���� ��ư �߰�
	JButton signup = new JButton("ȸ������");
	JButton findid = new JButton("���̵�ã��");
	JButton findpw = new JButton("��й�ȣã��");
	//�����ư
	JButton exit = new JButton("����");
	
	Operator op;
	ErrorFrame er;
	SignupFrame sf;
	IdfindFrame iff;
	PwfindFrame pf;
	
	public LoginFrame(Operator _op) {
		
		op = _op;
		//�׼Ǹ����� �߰�
		MyActionListener ml = new MyActionListener();
		
		
		setTitle("LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//�������� ����Ǹ� ���α׷��� ����
		
		//��, �ؽ�Ʈ�ʵ��� ũ�� ����
		id.setPreferredSize(new Dimension(70, 30));			//id �� ũ�� ����
		typeId.setPreferredSize(new Dimension(300, 30));	// id�Է��ϴ� �ؽ�Ʈ�ʵ� ũ�� ���� 
		password.setPreferredSize(new Dimension(70, 30));	// ��й�ȣ �� ũ�� ����
		typePassword.setPreferredSize(new Dimension(300, 30)); // password�Է¹޴� �ؽ�Ʈ�ʵ� ũ�� ����
		login.setPreferredSize(new Dimension(123, 30));			// �Է� ��ư ũ������
		cancel.setPreferredSize(new Dimension(123, 30));		// �ʱ�ȭ ��ư ũ�� ����
		
		//ȸ������ , IDã��, PWã�� ���� ��ư�� ũ������
		signup.setPreferredSize(new Dimension(123, 30));
		findid.setPreferredSize(new Dimension(123, 30));
		findpw.setPreferredSize(new Dimension(123, 30));
		exit.setPreferredSize(new Dimension(123, 30));
		//�г�1�� �г�2 3 �߰�
		panel1.add(panel2,BorderLayout.CENTER);//�α��� ����
		panel1.add(panel3,BorderLayout.EAST); // ȸ������ ����
		
		//�г�2�� �α��ΰ��� ������Ʈ�߰� �� �׼Ǹ����� �߰�
		panel2.add(id);
		panel2.add(typeId);
		panel2.add(password);
		panel2.add(typePassword);
		panel2.add(login);
		login.addActionListener(ml);
		panel2.add(cancel);
		cancel.addActionListener(ml);
		
		//�г�3�� ȸ������ IDã�� PWã�� ���� ��ư �߰� �� �׼Ǹ����� �߰�
		panel3.add(signup);
		signup.addActionListener(ml);
		panel3.add(findid);
		findid.addActionListener(ml);
		panel3.add(findpw);
		findpw.addActionListener(ml);
		panel3.add(exit);
		exit.addActionListener(ml);
		
		//���� ����
		Color p2Color = new Color(232,255,255);
		panel2.setBackground(p2Color);
		
		//����
		Color p3Color = new Color(232,255,255);
		panel3.setBackground(p3Color);
		
		setContentPane(panel1);
		
		setResizable(false);
		setSize(550, 160);
		
		//ȭ���߾ӿ� ��ġ
		Dimension frameSize = this.getSize();   //������ ����� ��������
 	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		setVisible(true);
		
	}
	// �׼Ǹ�����
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			//�α��� ��ư
			if(b.getText().equals("�α���")) {
				
				String pw = "";													//Password���� �Է°� �о����
				for(int i=0; i<typePassword.getPassword().length; i++) {
					pw =  pw+ typePassword.getPassword()[i];
				}
				if(op.myconnector.sendLoginInfor(typeId.getText(), pw)) {		//������ ���� �α��� ���ɿ��� üũ
					//Ʈ��� �κ� ������ ���� �� �г��� ����, �α��� ȭ�� ����
					op.lobbyf.setVisible(true);
					op.lobbyf.nickname.setText(op.myconnector.mynick);
					op.lobbyf.id.setText(typeId.getText());
					dispose();
				}else {	// false�� �α��� ���� �ȳ�
					System.out.println("�α��� ���� ");
					er = new ErrorFrame("���̵�� ��й�ȣ�� Ȯ�����ּ���");
				}
				
			}else if(b.getText().equals("�����")) {	//����� ��ư
				//�ؽ�Ʈ�ʵ� �ʱ�ȭ
				typeId.setText("");
				typePassword.setText("");
			}else if(b.getText().equals("ȸ������")) {	//ȸ������ ��ư
				//ȸ������ ȭ�� ���
				sf = new SignupFrame(op);
			}else if(b.getText().equals("���̵�ã��")) {//���̵� ã�� ��ư
				//���̵� ã�� ȭ�� ���
				iff = new IdfindFrame(op);
			}else if(b.getText().equals("��й�ȣã��")) { //��й�ȣ ã�� ��ư
				//��й�ȣ ã�� ȭ�� ���
				pf = new PwfindFrame(op);
			}else if(b.getText().equals("����")) { //�����ư
				System.exit(0);	// ���α׷� ����
			}
			
		}
	}
}
