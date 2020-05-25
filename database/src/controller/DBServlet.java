package controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servise.DBAccess;
import servise.Delete;
import servise.Insert;
import servise.Select;

/**
 * DB学習用サーブレット
 */
@WebServlet("/DBServlet")
public class DBServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private DBAccess dbAccess;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		dbAccess=new Select(); //Selectクラスのインスタンスを代入
		try {
		// 全データ抽出処理
			dbAccess.execute(request); //Selectクラスのexecuteメソッドを実行、これだけでテーブルを全件取得してrequestスコープに結果のリストを設定するまでをやってくれる
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		ServletContext context=getServletContext();
		RequestDispatcher dis=context.getRequestDispatcher("/db.jsp"); //db.jspに処理が飛ぶ
		dis.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request.setCharacterEncoding("utf-8");...filterを用意したので必要なし
		String btn = request.getParameter("button"); //String型のbtnにdb.jspで押されたbuttonが入っている
		System.out.println(btn); //POSTボタンが押されたことをコンソールに表示
		try {
			//DB挿入処理
			String input=request.getParameter("text"); //textに文字がinputされる
			//100文字以上ならdoGet
			if(input.equals("")) { //空文字が入力されたら
				request.setAttribute("message","何も入力されていません"); //messageに"何も入力されていません"が入る
			}else { //入力された値が空文字じゃなかったら
				dbAccess=new Insert();
				dbAccess.execute(request);  //最後はdoGetメソッドをまた実行している
			}
			// 全データ抽出処理
			doGet(request, response);
		}catch(Exception e) {
			System.out.println("Exception occured..."); //コンソールに表示される
			System.out.println(e);
		}
	}
}
