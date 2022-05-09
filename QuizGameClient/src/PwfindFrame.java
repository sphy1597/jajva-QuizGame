import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class PwfindFrame extends JFrame{
	
	//������Ʈ�� ����
	JPanel panel = new JPanel(new FlowLayout()); 
	JButton find = new JButton("ã��"); 
	JButton exit = new JButton("���ư���"); 
	JTextField typeid = new JTextField(); 
	JTextField typepn = new JTextField(); 
	JLabel id = new JLabel("I     D"); 
	JLabel pn = new JLabel("����ó"); 
	Operator op;
	ErrorFrame er;
	
	PwfindFrame(Operator _op){
		
		setTitle("FIND Password");
		//�׼Ǹ����� ����
		MyActionListener ml = new MyActionListener();
		op = _op;
		
		//������Ʈ�� ũ�� ����
		id.setPreferredSize(new Dimension(70, 30));			
		typeid.setPreferredSize(new Dimension(280, 30));	
		pn.setPreferredSize(new Dimension(70, 30));
		typepn.setPreferredSize(new Dimension(280, 30)); 
		find.setPreferredSize(new Dimension(123, 30));			
		exit.setPreferredSize(new Dimension(123, 30));	
		//�гο� ������Ʈ �߰� �� �������� ����
		panel.add(id); 				
		panel.add(typeid); 
		panel.add(pn); 
		panel.add(typepn);
		panel.add(find);	
		panel.add(exit);	
		setContentPane(panel); 
		
		//�׼Ǹ������߰�
		find.addActionListener(ml);
		exit.addActionListener(ml);
		
		//���� ����
		Color pcolor = new Color(255,217,250);
		panel.setBackground(pcolor);
		
		
		//ȭ�� ũ�� �����Ұ�, ũ�� ����, �߾ӿ� ��ġ
		setResizable(false);
		setSize(400,150);
		Dimension frameSize = this.getSize();   //������ ����� ��������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		setVisible(true);
		
	}
	//�׼Ǹ�����
	class MyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			if(b.getText().equals("ã��")) {	//ã�� ��ư�� ������ �Էµ� ������ ������ ����
				String result = op.myconnector.sendPwfind(typeid.getText(),typepn.getText());
				if(result.equals("ERROR!")) {	//�����ΰ�� �ȳ� �������̽� ������
					er = new ErrorFrame("��ġ�ϴ� ȸ�������� �����ϴ�.");
					typeid.setText("");
					typepn.setText("");
				}else {	//�ƴѰ�� ���� ������ ���
					er = new ErrorFrame("PW : " + result);
					dispose();
				}
				
			}else {	//�����ư 
				dispose();
			}
		}
	}
	

}