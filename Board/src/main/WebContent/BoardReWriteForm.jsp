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
				<h2>답변글 입력하기</h2>
				<form action="BoardReWriteProcCon.do" method="post">
					<table class="board-row">
						<colgroup>
							<col style="width:15%" />
							<col style="width:35%" />
						</colgroup>
						<tr>
							<th scope="row">작성자</th>
							<td><input type ="text" name="writer" /></td>
						</tr>
						<tr>
							<th scope="row">제목</th>
							<td><input type ="text" name="subject" value ="[답변] " /></td>
						</tr>
						<tr>
							<th scope="row">이메일</th>
							<td><input type ="email" name="email" /></td>
						</tr>
						<tr>
							<th scope="row">비밀번호</th>
							<td><input type ="password" name="password" /></td>
						</tr>
						<tr>
							<th scope="row">내용</th>
							<td><textarea name="content"></textarea></td>
						</tr>
					</table>
					<div class="btn-area">
						<input type="hidden" name ="ref" value="${ref}" />
						<input type="hidden" name ="re_step" value="${re_step}" />
						<input type="hidden" name ="re_level" value="${re_level}" />
						<button type="button" class="btn-list" onclick="location.href='BoardListCon.do'">목록</button>
						<input type="reset" class="btn-re" value="다시작성" />
						<input type="submit" class="btn-ok" value="등록" />
					</div>
				</form>
			</div>
		</div>
	</body>
</html>