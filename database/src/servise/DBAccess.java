package servise;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public interface DBAccess { //ユーザー(今回はサーブレット)からデータベースへのアクセスを簡単にできるようにinterface作る
	public void execute(HttpServletRequest request) throws SQLException;
}
