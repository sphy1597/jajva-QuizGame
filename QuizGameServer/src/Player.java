import java.io.*;
import java.net.*;

public class Player {
	//���ӿ� ������ �������� �г��Ӱ� ���� ���ھ ����
	String nick;
	Socket socket;
	int score = 0;
	
	Player(String _nick, Socket _socket){
		nick = _nick;
		socket = _socket;
	}
}
