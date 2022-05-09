import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class IdfindFrame extends JFrame{
	
	JPanel panel = new JPanel(new FlowLayout()); // ���̾ƿ� ���� 
	JButton find = new JButton("ã��"); // Button find ���� 
	JButton exit = new JButton("���ư���"); // Button ���ư��� ����
	JTextField typename = new JTextField(); // ����ó ������  ����
	JTextField typepn = new JTextField(); // ����ó ������  ����
	JLabel name = new JLabel("n a m e"); // �̸� ��
	JLabel pn = new JLabel("����ó"); // ����ó ��
	Operator op;
	ErrorFrame er;
	IdfindFrame(Operator _op){
	
		op = _op;
		
		
		setTitle("FIND ID");
	//	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//�������� ����Ǹ� ���α׷��� ����
		
		MyActionListener ml = new MyActionListener();
		
		// ������Ʈ���� ũ�� ���� 
		name.setPreferredSize(new Dimension(70, 30));			
		typename.setPreferredSize(new Dimension(280, 30));	
		pn.setPreferredSize(new Dimension(70, 30));	
		typepn.setPreferredSize(new Dimension(280, 30)); 
		find.setPreferredSize(new Dimension(123, 30));		
		exit.setPreferredSize(new Dimension(123, 30));		
		//������Ʈ�� �гο� �߰�
		panel.add(name); //  �̸�	��
		panel.add(typename); // �̸� �Է¹޴� textfield �߰� 
		panel.add(pn); // PASSWORD �߰� 
		panel.add(typepn); // �Էµ� PASSWORD �߰� 
		panel.add(find);	//enter��ư �߰�
		panel.add(exit);	// cancel �Է� ��ư �߰�
		
		setContentPane(panel); // �гη� �������� ����
		
		//�ƼǸ����� �߰�
		find.addActionListener(ml);
		exit.addActionListener(ml);
		
		//��Ʈ����
		Color pcolor = new Color(255,217,250);
		//���� ����
		panel.setBackground(pcolor);
		
		
		setResizable(false);	//ũ������ �Ұ�
		setSize(400,150);		//ũ������
		// ȭ���߾ӿ� ��ġ
		Dimension frameSize = this.getSize();   //������ ����� ��������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		setVisible(true);
		
	}
	//�׼Ǹ�����
	class MyActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e ) {
			JButton b = (JButton)e.getSource();
			
			//ã�� ��ư�� ������ ȸ�������� ����
			if(b.getText().equals("ã��")) {
				String result = op.myconnector.sendIdfind(typename.getText(), typepn.getText());
				if(result.equals("ERROR!")) {					//��ġ�ϴ� ������ ������ �ȳ����� ������
					er = new ErrorFrame("��ġ�ϴ� ȸ�������� �����ϴ�.");
					typename.setText("");
					typepn.setText("");
					
				}else {			// ��ġ�ϴ� ������ ������ ���̵� ������
					er = new ErrorFrame("I D : "+result);
					dispose();
				}
				
			}else {	//���ư��� ��ư�� ������ �������̽� ����
				dispose();
			}
	
		}
	}
}
