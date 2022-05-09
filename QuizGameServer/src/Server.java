import java.io.*;
import java.net.*;
import java.util.*;


public class Server {   // ����Ŭ����
	ServerSocket ss = null;				// �������� �ʵ� ����
	
	//�����ڸ� ������ ���� ����Ʈ
	public static ArrayList<ConnectedClient> clients = new ArrayList<ConnectedClient>();		// ����� Ŭ���̾�Ʈ���� �����ϱ� ���� ����Ʈ	
	//�κ� �α����� ������ �����ϴ� ����Ʈ
	public static ArrayList<Player> LobbyClients = new ArrayList<Player>();
	//1~5���濡 �������� ������ �����ϱ� ���� ����Ʈ
	public static ArrayList<Player> Mode1 = new ArrayList<Player>();
	public static ArrayList<Player> Mode2 = new ArrayList<Player>();
	public static ArrayList<Player> Mode3 = new ArrayList<Player>();
	public static ArrayList<Player> Mode4 = new ArrayList<Player>();
	public static ArrayList<Player> Mode5 = new ArrayList<Player>();
	
	public static ConnectedClient c;
	public static SelectDB SDB;
	public static InsertDB IDB;
	public static DeletDB DDB;
	public static QuizMake QM1;
	public static QuizMake QM2;
	public static QuizMake QM3;
	public static QuizMake QM4;
	public static QuizMake QM5;
	
	public static void main(String[] args) {
		Server server = new Server();								// ���� ��ü ����
		// ��� ����ϱ� ���� ��ü����
		IDB = new InsertDB();
		SDB = new SelectDB(server);
		DDB = new DeletDB();

		try {
			server.ss = new ServerSocket(60000);								// ���� ��Ĺ�� 60000���� ����
			System.out.println("Server > Server Socket is Created...");			//		
			while(true) {	
				Socket socket = server.ss.accept();							//Ŭ���̾�Ʈ�� �����ϸ� accept ���� 
				c = new ConnectedClient(socket, server);			// Ŭ���̾�Ʈ���� 1��1 ������ ���� ������ ��ü c ����
				// ������ Ŭ���̾�Ʈ�� 1��1�� ���� (������)
				server.clients.add(c);										// clients�� c �߰�
				c.start();													// ������ ����
							
			}
			
		}catch(SocketException e) {
			System.out.println("Server > ���� ���� ���� �߻�, ��������");
		}catch(IOException e) {
			System.out.println("Server >> ����� ���� �߻�");
		}

	}

}