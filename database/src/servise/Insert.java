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
public class Insert implements DBAccess {

	public void execute(HttpServletRequest request) throws SQLException {
		
		Dao dao=null; //Dao型のdao
		String input=request.getParameter("text"); //ユーザーの入力をinputという変数で受け取る
		
		try {
			dao=new Dao();
			if(dao.insertData(input)>0) { //daoクラスのinsertDataメソッドを実行，入力された文字が0文字より多かったら
				request.setAttribute("message","投稿完了！");
				System.out.println("Insert seccess!"); //コンソールに表示
			}else {
				request.setAttribute("message","投稿失敗...");
				System.out.println("Insert failed..."); //コンソールに表示
			}
		}finally {
			if(dao !=null)dao.close();
		}
		
	}
}
