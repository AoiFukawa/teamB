package servise;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

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
		String input = request.getParameter("text");//ユーザーからの入力を受け取っている
		
		try {
			dao = new Dao();//Daoクラスのコンストラクタでdbとつなげる
			if(dao.insertData(input) > 0) {//inputとはユーザーがテキストをポストした回数
				request.setAttribute("message", "投稿完了!");
				System.out.println("Insert success!");
			}else {//なんらかの理由によりポストが出来なかった場合
				request.setAttribute("message", "投稿失敗...");
				System.out.println("Insert failed...");
			}
		}finally {
			if(dao != null) dao.close();
		}
	}
}
