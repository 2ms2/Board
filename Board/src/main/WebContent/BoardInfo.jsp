<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" type="text/css" href="resource/css/common.css">
	</head>
	
	<body>
		<div class="wrap">
			<div class="conts">
				<h2>게시글 보기</h2>
				<table class="board-row">
					<colgroup>
						<col style="width:15%" />
						<col style="width:35%" />
						<col style="width:15%" />
						<col style="width:35%" />
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">글번호</th>
							<td>${bean.num}</td>
							<th scope="row">조회수</th>
							<td>${bean.readcount}</td>
						</tr>
						<tr>
							<th scope="row">작성자</th>
							<td>${bean.writer}</td>
							<th scope="row">작성일</th>
							<td>${bean.reg_date}</td>
						</tr>
						<tr>
							<th scope="row">이메일</th>
							<td colspan="3">${bean.email}</td>
						</tr>		
						<tr>
							<th scope="row">제목</th>
							<td colspan="3">${bean.subject}</td>
						</tr>
						<tr>
							<th scope="row">내용</th>
							<td colspan="3">${bean.content}</td>
						</tr>
					</tbody>
				</table>
				<div class="btn-area">
					<button type="button" class="btn-list" onclick="location.href='BoardListCon.do'">목록</button>
					<button type="button" class="btn-re" onclick="location.href='BoardDeleteCon.do?num=${bean.num}'">삭제</button>
					<button type="button" class="btn-ok" onclick="location.href='BoardReWriteCon.do?num=${bean.num}&ref=${bean.ref}&re_step=${bean.re_step}&re_level=${bean.re_level}'">답변</button>
					<button type="button" class="btn-ok" onclick="location.href='BoardUpdateCon.do?num=${bean.num}'">수정</button>
				</div>
			</div>
		</div>
	</body>
</html>