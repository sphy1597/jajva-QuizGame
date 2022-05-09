
public class Quiz {
	//DB에서 읽어온 퀴즈를 저장
	String Quiznum;	//퀴즈넘버
	String Question;	//문제
	String Hint;		//힌트
	String Answer;		//정답
	
	Quiz(String _qnum, String _ques, String _hint, String _ans){
		Quiznum = _qnum;
		Question = _ques;
		Hint = _hint;
		Answer = _ans;
	}

}
