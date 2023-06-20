package controller;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.BoardBean;
import model.BoardDAO;

@WebServlet("/BoardListCon.do")
public class BoardListCon extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	
	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 화면에 보여질 게시글의 개수를 지정 
		int pageSize = 10;
		
		// 현재 보여지고 있는 페이지의 넘버값을 읽어드림(ex 10페이지중 3페이지 클릭한 상태) 
		String pageNum = request.getParameter("pageNum");
		
		// 만약 처음  BoardList.jsp를 클릭하거나 수정 삭제 등 다른 게시글에서 이 페이지로 넘어오면 pageNum값이 없기에 null값 처리 
		if (pageNum == null) {
			pageNum = "1";
			
		}
		
		// 전체 글의 갯수를 저장하는 변수
		int count = 0;
		
		// jsp페이지 내에서 보여질 넘버링 숫자값을 저장하는 변수
		int number = 0;
		
		// 현재 보여지고 있는 페이지 문자를 숫자로 변환
		int currentPage = Integer.parseInt(pageNum);
		
		// 전체 게시글의 개수를 jsp쪽으로 가져와야 하기에 데이터베이스 객체 생성
		BoardDAO bdao = new BoardDAO();
		
		// 전체 게시글의 개수를 가져오는 메소드 호출 
		count = bdao.getAllCount();
		
		// 현재 보여질 페이지 시작 번호를 설정(데이터 베이스에서 불러올 시작 번호)
		int startRow = (currentPage-1)*pageSize + 1;
		int endRow = currentPage * pageSize;
		
		// 최신글 10개를 기준으로  게시글을 리턴 받아주는 메소드 호출
		Vector<BoardBean> v = bdao.getAllBoard(startRow , endRow);
		
		// 테이블에 표시할 번호 지정
		number = count  - (currentPage-1)*pageSize;
		
		// 수정, 삭제 시 오류 문구 출력
		String msg = (String) request.getAttribute("msg");
		
		// BoardList.jsp쪽으로 request객체에 담아서 넘긴다
		request.setAttribute("v", v); // 최신글 10개..를 v에 담는다
		request.setAttribute("number", number);
		request.setAttribute("pageSize", pageSize); // 화면에 몇개를 보여줄건지
		request.setAttribute("count", count); // 카운트 값이 있어야지 계산할 수 있다.
		request.setAttribute("currentPage", currentPage); // 현재 보고있는 페이지
		
		request.setAttribute("msg", msg);
		
		RequestDispatcher dis = request.getRequestDispatcher("BoardList.jsp");
		dis.forward(request, response);
		
	}

}
