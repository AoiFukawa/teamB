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
import javax.servlet.http.HttpSession;

import servise.DBAccess;
import servise.Delete;
import servise.Favorite;
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
		// 全データ抽出処理
		dbAccess = new Select();//インターフェイスの型として定義
		try {
			dbAccess.execute(request);//全データの情報を持ったexecuteクラスを呼び出している
	}catch(SQLException e) {
		e.printStackTrace();
	}	
		// ここに処理を記入してください
		
	ServletContext context = getServletContext();
	RequestDispatcher dis = context.getRequestDispatcher("/db.jsp");
	dis.forward(request, response);
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request.setCharacterEncoding("utf-8");...filterを用意したので必要なし
	
	String btn = request.getParameter("button");
	System.out.println(btn);
	try {	
				// ここに処理を記入してください
				if(btn.equals("POST")) {//ｂｔｎが押されたものがPostだった場合
					HttpSession session = request.getSession(false);
					String input = request.getParameter("text");
					
					if(input.equals("テスト") || input.equals("Hello") || input.equals("マスク")) {
						request.setAttribute("message", "This article cannot be posted...");
						doGet(request, response);
						return;
					}
					if(input.length() >= 100 || input.equals("") || input == null) {
						request.setAttribute("message", "\r\n" + "Is not entered or exceeds 100 characters");
						doGet(request, response);
						return;
					}
					dbAccess = new Insert();
					
				}else if(btn.equals("update")) {
					response.sendRedirect("http://localhost:8080/database/DBServlet");
				}else if(btn.equals("favorite")) {
					dbAccess = new Favorite();
				}else {
						dbAccess = new Delete();
					}
					dbAccess.execute(request);
			// 全データ抽出処理
			doGet(request, response);
		}catch(Exception e) {
			System.out.println("Exception occured...");
			System.out.println(e);
		}
	}
}