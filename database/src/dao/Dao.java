package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import dto.MessageDto;

public class Dao {

	private Connection con;
	private String sql;
	
	/**
	 * DB接続コンストラクタ<br>
	 * インスタンス化時にDB接続が行われる
	 * @throws SQLException
	 */
	public Dao() throws SQLException{ //Daoクラスのコンストラクタが記述
		String url="jdbc:mysql://localhost:3306/javaweb?serverTimezone=UTC"; //とこのデータベースか
		String user="root"; //ユーザー名
		String pass="root"; //パスワード
		con=DriverManager.getConnection(url,user,pass); //3の情報を使ってデータベースに接続，conに接続情報が入る(接続に失敗すると例外発生)
		System.out.println("Connection success!"); //コンソールに表示
		
	}
	
	/**
	 * DB接続を切るためのメソッド
	 */
	public void close() {
		try {
			if(con != null) con.close(); //con(接続情報を持っている)がnullじゃなかったら(=conに接続情報が格納されている)からconを閉じる(接続を切断)
		}catch(SQLException e){
			e.printStackTrace(); //エラー発生時点までの処理の呼び出し履歴
		}
	}
	
	/**
	 * DBに保存されているデータを全件取得するメソッド<br>
	 * DBから取得後、件数分のdtoに1レコードずつ情報を持たせてしてArrayListに格納<br>
	 * @return ID列で降順にソートしたArrayList
	 * @throws SQLException
	 */
	public ArrayList<MessageDto> getListAll() throws SQLException{ //取得した行をArrayListに追加，MessageDtoが一行分のデータを持っている
		//データベースで受け取った値をALとして受け取る
		sql="select * from tweet";
		PreparedStatement ps=null; //SQL文の準備クラス
		ResultSet rs=null; //取得した行などの情報がResultSetインターフェースのオブジェクトに格納されている
		ArrayList<MessageDto>list=null; //ArrayListはデータをまとめて保持するもの
		
		try {  //while文は条件がtrueの間ループする，rsは一行分の値を持っている
			ps=con.prepareStatement(sql); //SQL文実行の準備，psにはどのSQL文にどのデータベースを発行するのかの情報を持っている
			rs=ps.executeQuery(); //SQL文を実行，rsが実行結果を持っている
			list=new ArrayList<>(); //結果を受けとるALをインスタンス化，<>の中は省略可
			MessageDto dto; //MessageDto型の変数dtoを宣言
			while(rs.next()) { //カーソルが動いて次の表があるかないかをtrueかfalseで返す
				dto=new MessageDto(); //dto変数にMessageDtoクラスのインスタンスを代入(インスタンス化)
				dto.setId(rs.getInt("id")); //，idのゲッターを使って"実行結果の"id列を取得したものを格納している
				dto.setContent(rs.getString("content"));
				dto.setDate(rs.getString("date"));
				list.add(dto); //ALのlist変数にdtoを追加格納
			}
			rs.close(); //実行結果はいらなくなるのでリソースの解放
		}finally {
			ps.close(); //SQL情報はいらなくなるのでリソースの解放
		}
		//ALに格納されている順番の変更(これをやらないと新しいものが一番下に来てしまう)
		Comparator<MessageDto> comparator = Comparator.comparing(MessageDto::getId).reversed();
		
		return (ArrayList<MessageDto>) list.stream().sorted(comparator).collect(Collectors.toList());	
	}
	
	/**
	 * データ登録メソッド<br>
	 * SQL文とパラメータをexecuteUpdateメソッドに渡す
	 * @param input (受け取った入力値)
	 * @return 成功件数
	 * @throws SQLException
	 */
	public int insertData(String input) throws SQLException{ //inputはユーザーが打った内容
		//tryブロックの中の変数はtryブロックの中でしか使えない
		PreparedStatement ps=null; //SQL文の準備クラス
		int n = 0;
		
		try {
			String sql="INSERT INTO tweet (content) VALUES (?)"; //content列に?を追加(idとdateはデータベース側で勝手に入れてくれる)
			ps=con.prepareStatement(sql); //SQLをどのデータベースにどんなクリエで送るかを準備する
			ps.setString(1, input); //sql変数の?の値を設定する，1個目の?にinput変数の値を設定する
			n=ps.executeUpdate(); //SQLの実行，int型で実行した件数が返ってくる
		}finally {
			ps.close(); //SQL文の情報を解放
		}	
		return n; //何件成功したか(登録したか)を返す
	}
	
	/**
	 * データ削除メソッド<br>
	 * SQL文とパラメータをexecuteUpdateメソッドに渡す
	 * @param id (削除するデータのid)
	 * @return 成功件数
	 * @throws SQLException
	 */
	public int deleteData(String id) throws SQLException {
		
		// ここに処理を記入してください
		
		return 0;
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
		
		return 0;
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
}
