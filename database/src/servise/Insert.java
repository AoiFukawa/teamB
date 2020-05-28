package servise;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.Dao;

/**
 * DBAccessインターフェースを実装する登録クラス<br>
 * パラメータに受取った入力値をDBに登録する
 * @author user
 *
 */
public class Insert implements DBAccess { //DBAccess

	public void execute(HttpServletRequest request) throws SQLException {//executeメソッド
		
		// ここに処理を記入してください
		Dao dao = null;
		HttpSession session = request.getSession(false);
		String input = request.getParameter("text");//ユーザーからの入力を受け取っている
		int input2 = (int)session.getAttribute("userid");
		
		try {
			dao = new Dao();//Daoクラスのコンストラクタでdbとつなげる
				if(dao.insertData(input,input2) > 0) {//inputとはユーザーがテキストをポストした回数
					request.setAttribute("message", "Post completed!");
					System.out.println("Insert success!");
				}else {//なんらかの理由によりポストが出来なかった場合
					request.setAttribute("message", "Post failure...");
					System.out.println("Insert failed...");
				}	
			
		}finally {
			if(dao != null) dao.close();
		}
	}
}
