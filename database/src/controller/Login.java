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

import dao.Dao;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
   /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		request.setCharacterEncoding("utf-8"); 
		// セッションの取得(なければnullが返ってくる)
		HttpSession session = request.getSession(false); 

		// ここに処理を記入してください
		// セッションの破棄
		if(session != null) session.invalidate(); 
		// ここに処理を記入してください

		// ログイン失敗時、ログアウト時、不正操作時以外の場合
		if(request.getAttribute("message") == null) request.setAttribute("message", "Enter your Name and Password"); //messageがnullの場合

		response.setContentType("text/html; charset=UTF-8");
		ServletContext context = getServletContext();
		RequestDispatcher dis = context.getRequestDispatcher("/top.jsp");
		dis.forward(request, response);
	}

	/**
	 * @param spring 
	 * @param summer 
	 * @param nomal 
	 * @param autumn 
	 * @param winter
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Dao dao;
		int n = 0;
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession(true);

		try {
			dao = new Dao();
			n = dao.getLoginInfo(request.getParameter("name"), request.getParameter("pass"), request);
			
			if(request.getParameter("img").equals("spring")) {
				session.setAttribute("image", "spring");
			}else if(request.getParameter("img").equals("summer")) {
				session.setAttribute("image", "summer");
			}else if(request.getParameter("img").equals("autumn")) {
				session.setAttribute("image", "autumn");
			}else if (request.getParameter("img").equals("winter")){
				session.setAttribute("image", "winter");
			}else {
				session.setAttribute("image", "nomal");
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}	

		if(n > 0) {
			response.sendRedirect("http://localhost:8080/database/DBServlet");
		}else {
			request.setAttribute("message", "I failed to loguin"); 
			doGet(request, response);
		}
	}
}


