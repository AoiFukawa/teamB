package servise;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import dao.Dao;
import dto.MessageDto;

/**
 * DBAccessインターフェースを実装する検索クラス<br>
 * 全てのデータをArrayListとして取得する
 * @author user
 *
 */
public class Select implements DBAccess {
	
	@Override
	public void execute(HttpServletRequest request) throws SQLException { //サーブレットでも使っているリクエスト情報を引数で受ける
		
		Dao dao=null; //Dao型のdao変数を宣言
		try {
			dao=new Dao(); //ｄaoクラスのインスタンス化
			ArrayList<MessageDto>list=dao.getListAll(); //ｄaoクラスのgetListAllと同じ，データベースの各行の配列がここで返ってくる
			
			if(list !=null) { //list(ツイート一覧)がnullじゃなかったら
				request.setAttribute("list",list); //requestスコープに"list"という名前でlist変数を設定
			}else { //list(ツイート一覧)が何もなかったら
				request.setAttribute("message","まだデータがありません");
			}
		}finally {
			if(dao !=null)dao.close(); //daoがnullじゃなかったらデータベースとの通信を切断
		}
		
	}
}
