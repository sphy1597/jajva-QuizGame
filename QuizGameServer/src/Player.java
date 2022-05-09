import java.io.*;
import java.net.*;

public class Player {
	//게임에 참여한 유저들의 닉네임과 소켓 스코어를 저장
	String nick;
	Socket socket;
	int score = 0;
	
	Player(String _nick, Socket _socket){
		nick = _nick;
		socket = _socket;
	}
}
