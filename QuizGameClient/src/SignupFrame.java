import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class SignupFrame extends JFrame{	//ȸ������ ���
	
	//�г� ��ư �� ������Ʈ ����
	JPanel panel = new JPanel(new FlowLayout());
	JButton ok = new JButton("ȸ������");
	JButton cancel = new JButton("���ư���"); 
	
	JTextField typeId = new JTextField(); 
	JPasswordField typePassword = new JPasswordField();
	JTextField typeName = new JTextField();
	JTextField typePn = new JTextField();
	JTextField typeNickname = new JTextField();
	
	JLabel id = new JLabel("I   D");
	JLabel password = new JLabel("Password"); 
	JLabel name = new JLabel("N a m e");		
	JLabel pn = new JLabel("Phone number");		
	JLabel nickname = new JLabel("Nick name");
	JLabel label = new JLabel("������ �Է��� �ּ���");
	Operator op;
	MyConnector mc;
	ErrorFrame er;
	public SignupFrame(Operator _op) {

		setTitle("ȸ������");
		
		op = _op;
		mc = op.myconnector;

		
		// ��ư �ؽ�Ʈ�ʵ� ũ�� ����
		id.setPreferredSize(new Dimension(100, 30));
		typeId.setPreferredSize(new Dimension(300, 30));
		password.setPreferredSize(new Dimension(100, 30));
		typePassword.setPreferredSize(new Dimension(300, 30));
		name.setPreferredSize(new Dimension(100, 30));
		typeName.setPreferredSize(new Dimension(300, 30));
		pn.setPreferredSize(new Dimension(100, 30));
		typePn.setPreferredSize(new Dimension(300, 30));
		nickname.setPreferredSize(new Dimension(100, 30));
		typeNickname.setPreferredSize(new Dimension(300, 30));
		ok.setPreferredSize(new Dimension(200, 30));
		cancel.setPreferredSize(new Dimension(200, 30));
		
		//��ư�� �׼Ǹ����� ����
		MyActionListener ml = new MyActionListener();
		
		
		//�гο� ��ư �ؽ�Ʈ�ʵ�, �� �߰�
		panel.add(id);
		panel.add(typeId);
		panel.add(password);
		panel.add(typePassword);
		panel.add(name);
		panel.add(typeName);
		panel.add(pn);
		panel.add(typePn);
		panel.add(nickname);
		panel.add(typeNickname);
		panel.add(ok);
		ok.addActionListener(ml);
		panel.add(cancel);
		cancel.addActionListener(ml);
		panel.add(label);
		
		setContentPane(panel);  //������ ���� �гη� ����
		
		setResizable(false);		//ũ������ �Ұ��ϰ� ����
		setSize(450,270);
		
		//ȭ�� �߾ӿ� ��ġ
		Dimension frameSize = this.getSize();   //������ ����� ��������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	    
	    setVisible(true);
	
	}
	//�׼Ǹ�����
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			if(b.getText().equals("ȸ������")) {//ȸ������ ��ư
				//�Է¹��� �������� ������ ����
				String pw = "";
				for(int i = 0 ; i <typePassword.getPassword().length;i++) {
					pw = pw + typePassword.getPassword()[i];
				}
				String id = typeId.getText();
				String name = typeName.getText();
				String pn = typePn.getText();
				String nickname = typeNickname.getText();
				if(mc.sendsignup(id,pw,name,pn,nickname)) {	//����� true�� �ȳ�â�� ���� ȭ�� ����
					er = new ErrorFrame("ȸ������ �Ǿ����ϴ�.");
					dispose();
				}else {										//false�ΰ�� �ȳ�â�� ���� �ؽ�Ʈ�ʵ���� �ʱ�ȭ
					er = new ErrorFrame("�ߺ��� ȸ���� �ֽ��ϴ�.");
					typeId.setText("");
					typePassword.setText("");
					typeName.setText("");
					typePn.setText("");
					typeNickname.setText("");
				}
				
				
			}else {//���ư��� ��ư
				dispose();
			}
		}
	}
}