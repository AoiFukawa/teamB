package servise;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.Dao;

public class Favorite implements DBAccess { //DBAccess

	public void execute(HttpServletRequest request) throws SQLException {//executeメソッド
		
		// ここに処理を記入してください
		Dao dao = null;
		HttpSession session = request.getSession(false);
		String input = request.getParameter("id");
		boolean input2 = !Boolean.parseBoolean((String)request.getParameter("fav_param"));
		try {
			dao = new Dao();//Daoクラスのコンストラクタでdbとつなげる
			if(dao.favoriteData(input,input2) > 0) {//inputとはユーザーがテキストをポストした回数
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