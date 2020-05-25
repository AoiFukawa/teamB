package servise;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import dao.Dao;

/**
 * DBAccessインターフェースを実装する削除クラス<br>
 * DBからパラメータに受取ったIDのデータを削除する
 * @author user
 *
 */
public class Delete implements DBAccess {

	@Override
	public void execute(HttpServletRequest request) throws SQLException {

		// ここに処理を記入してください
		Dao dao = null;
		int n = 0;
		
		String code = request.getParameter("id");
		
		try {
			dao = new Dao();
			n = dao.deleteData(code);
			
			if(n > 0) {
				request.setAttribute("message", "商品の削除が完了しました");
			}else {	
				request.setAttribute("message", "商品の削除に失敗しました");
			}
		}finally {
			if(dao != null) dao.close();
		}
	}

}