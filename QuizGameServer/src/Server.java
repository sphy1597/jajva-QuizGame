import java.io.*;
import java.net.*;
import java.util.*;


public class Server {   // 메인클래스
	ServerSocket ss = null;				// 서버소켓 필드 생성
	
	//접속자를 관리학 위한 리스트
	public static ArrayList<ConnectedClient> clients = new ArrayList<ConnectedClient>();		// 연결된 클라이언트들의 관리하기 위한 리스트	
	//로비에 로그인한 유저를 관리하는 리스트
	public static ArrayList<Player> LobbyClients = new ArrayList<Player>();
	//1~5번방에 참여중인 유저를 관리하기 위한 리스트
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
		Server server = new Server();								// 서버 객체 생성
		// 디비를 사용하기 위한 객체생성
		IDB = new InsertDB();
		SDB = new SelectDB(server);
		DDB = new DeletDB();

		try {
			server.ss = new ServerSocket(60000);								// 서버 소캣을 60000으로 설정
			System.out.println("Server > Server Socket is Created...");			//		
			while(true) {	
				Socket socket = server.ss.accept();							//클라이언트가 접속하면 accept 해줌 
				c = new ConnectedClient(socket, server);			// 클라이언트와의 1ㄷ1 연결을 위한 스레드 객체 c 생성
				// 접속한 클라이언트를 1ㄷ1로 관리 (스레드)
				server.clients.add(c);										// clients에 c 추가
				c.start();													// 스레드 시작
							
			}
			
		}catch(SocketException e) {
			System.out.println("Server > 소켓 관련 예외 발생, 서버종료");
		}catch(IOException e) {
			System.out.println("Server >> 입출력 예외 발생");
		}

	}

}