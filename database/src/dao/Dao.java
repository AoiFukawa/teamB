package dao;//databaseとのアクセスを担当するジャバ

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dto.MessageDto;

public class Dao {
	private Connection con;
	private String sql;
	/**
	 * DB接続コンストラクタ<br>
	 * インスタンス化時にDB接続が行われる
	 * @throws SQLException
	 */
	public Dao() throws SQLException{//Daoクラスのコンストラクタ/データーベースに接続するためのコンストラクタ
		// ここに処理を記入してください
		String url ="jdbc:mysql://192.168.10.14:3306/javaweb?serverTimezone=UTC";//dataベースがある場所
		String user = "admin";//ユーザー名
		String pass = "P@ssw0rd";//パスワード
		con = DriverManager.getConnection(url, user, pass);//3つの仮引数の情報を使ってデーターベースへアクセスする
		System.out.println("Connection success!");//接続成功するとコンソールに現れる
	}
	/**
	 * 
	 */
	public void close() { 
		try {
			if(con != null) con.close();//DB接続を切るためのメソッド 
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	/**
	 * <br>
	 * DBから取得後、件数分のdtoに1レコードずつ情報を持たせてしてArrayListに格納<br>
	 * @return ID列で降順にソートしたArrayList
	 * @throws SQLException
	 */
	public ArrayList<MessageDto> getListAll() throws SQLException{//DBに保存されているデータを全件取得するメソッド/メッセージdto.javaが一行分のデータを取得する
		// ここに処理を記入してください
		sql = " select * from tweet left join user on user.userid = tweet.userid;";//sql文を文字列として配置
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<MessageDto> list = null;
		try {
			ps = con.prepareStatement(sql);//sql文の実行準備
			rs = ps.executeQuery();//
			list = new ArrayList<>();//ArrayListをインスタンス化
			MessageDto dto;
			while(rs.next()) {//rs.nextによってカーソルが移動する
				dto = new MessageDto();//dtoにインスタンス化したものを与え、メッセージｄｔoのインスタンス化をしている
				dto.setId(rs.getInt("id"));//id列の値を取得
				dto.setMention(rs.getInt("userid"));
				dto.setusername(rs.getString("username"));
				dto.setContent(rs.getString("content"));//content列の文をストリング型として受け取る
				dto.setDate(rs.getString("date"));//date列を取得してString型として受け取る
				list.add(dto);
			}
			rs.close();//SQL自体必要がなくなったためリソースを開放する
		}finally {//どの
			ps.close();//SQL自体必要がなくなったためリソースを開放する
		}
		Comparator<MessageDto> comparator = Comparator.comparing(MessageDto::getDate).reversed();
		
		return (ArrayList<MessageDto>) list.stream().sorted(comparator).collect(Collectors.toList());	
	}
	/**
	 * データ登録メソッド<br>
	 * SQL文とパラメータをexecuteUpdateメソッドに渡す
	 * @param input (受け取った入力値)
	 * @return 成功件数
	 * @throws SQLException
	 */
		public int insertData(String input,int input2) throws SQLException{//取得したデータの登録するためのメソッド/String inputはユーザーが打ち込んだ内容/int型として戻ってくる
			PreparedStatement ps = null;//psSQLをどのデータベースにどのようなクエリを送るのか定義
			int n = 0;//トライブロックの中にいると戻り値として認識されない
			try {
				String sql = "INSERT INTO tweet (content,userid)VALUES (?,?)";//?はユーザーが打ち込んだ値
				ps = con.prepareStatement(sql);
				ps.setString(1,  input);//
				ps.setInt(2,  input2);//
				n = ps.executeUpdate();//sqlの実行文
		}finally {
			ps.close();
		}
		// ここに処理を記入してください
		return n;//コード認証が成功した数を返す戻り式
		}
	/**
	 * データ削除メソッド<br>
	 * SQL文とパラメータをexecuteUpdateメソッドに渡す
	 * @param id (削除するデータのid)
	 * @return 成功件数
	 * @throws SQLException
	 */
	public int deleteData(String id) throws SQLException {
		String sql = "delete from tweet where id = ?";
		return executeUpdate(sql, id);
		// ここに処理を記入してください
	}
	/**
	 * 登録、削除処理を担当するメソッド<br>
	 * 使用するメソッドは共通のため汎用化
	 * @param sql (SQL文)
	 * @param param (INパラメータ)
	 * @return 成功件数
	 * @throws SQLException
	 */
	private int executeUpdate(String sql, String param) throws SQLException {
		// ここに処理を記入してください
		PreparedStatement ps = null;
		int n = 0;
		try {
			ps = con.prepareStatement(sql);
			if(isNumber(param)) ps.setInt(1,  Integer.parseInt(param));
			else ps.setString(1,  param);
			n = ps.executeUpdate();
		}finally {
			ps.close();
		}
		return n;
	}
	/**
	 * 数値判定メソッド<br>
	 * 引数に受け取った値が数値に変換できなければ例外発生
	 * @param num (パラメータ)
	 * @return 数値...true, 文字列...false 
	 */
	private boolean isNumber(String num) {
	    try {
	        Integer.parseInt(num);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
public int getLoginInfo(String name, String pass, HttpServletRequest request) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;

		int row = 0;
		sql = "SELECT * from user where username = ? and password = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, name);
		ps.setString(2, pass);
		
		try {
			rs = ps.executeQuery();
			rs.last();
			row = rs.getRow();

			if(row > 0) {
				HttpSession session = request.getSession(true);
				session.setAttribute("userid", rs.getInt("userid"));
				session.setAttribute("username", rs.getString("username"));
			}
		}finally {
			ps.close();
		}
		return row;
	}
}
