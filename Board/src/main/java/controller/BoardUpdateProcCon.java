package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardDAO;

@WebServlet("/BoardUpdateProcCon.do")
public class BoardUpdateProcCon extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// <form>에서 넘어온 데이터를 받아
		int num = Integer.parseInt(request.getParameter("num"));
		String password = request.getParameter("password"); // 사용자로부터 입력받은 패스워드값
		String pass = request.getParameter("pass"); // 데이터 베이스에 저장되어 있는 패스워드값
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		// password값과 pass값을 비교
		if (password.equals(pass)) {
			// 패스워드 일치 시 데이터 수정
			BoardDAO bdao = new BoardDAO();
			bdao.updateBoard(num, subject, content);
			
			// 수정이 완료되면 목록으로 이동
			request.setAttribute("msg", "수정이 완료되었습니다.");
			RequestDispatcher dis = request.getRequestDispatcher("BoardListCon.do");
			dis.forward(request, response);
			
		} else {
			// 불일치 시 알림창 노출
			request.setAttribute("msg", "1");
			RequestDispatcher dis = request.getRequestDispatcher("BoardListCon.do");
			dis.forward(request, response);
			
		}
		
	}

}
