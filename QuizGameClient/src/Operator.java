
public class Operator {

	LoginFrame loginf = null;
	IdfindFrame idf = null;
	PwfindFrame pwf = null;
	LobbyFrame lobbyf = null;
	GameroomFrame gf = null;
	ErrorFrame er =null;
	SignupFrame sf = null;
	MsgListener msgl = null;
	
	MyConnector myconnector = null;
	
	//메인 클래스
	public static void main(String[] args) {
		
		Operator op = new Operator();
		op.myconnector = new MyConnector(op);
		op.gf = new GameroomFrame(op);
		op.lobbyf = new LobbyFrame(op);
		op.loginf = new LoginFrame(op);
	
	}

}
