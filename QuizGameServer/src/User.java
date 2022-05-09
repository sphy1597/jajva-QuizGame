
public class User {
	//유저 객체를 위한 유저정보
	String Id;	//아이디
	String Password;	//비밀번호
	String Name;		//이름
	String Pn;			//연락처
	String Nickname;	//닉네임
	
	
	User(String _id, String _password, String _name, String _pn, String _nickname){
		Id = _id;
		Password = _password;
		Name = _name;
		Pn = _pn;
		Nickname = _nickname;
	}

}
