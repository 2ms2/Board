package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardBean;
import model.BoardDAO;

@WebServlet("/BoardInfoControl.do")
public class BoardInfoControl extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 글 번호를 받고
		int num = Integer.parseInt(request.getParameter("num"));
		
		// 하나의 게시글에 대한 정보를 리턴
		BoardDAO bdao = new BoardDAO();
		BoardBean bean = bdao.getOneBoard(num);
		
		request.setAttribute("bean", bean);
		
		// 화면(jsp)에 데이터를 넘겨준다
		RequestDispatcher dis = request.getRequestDispatcher("BoardInfo.jsp");
		dis.forward(request, response);
		
	}

}
