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
				<h2> 게시글 삭제</h2>
				<form action="BoardDeleteProcCon.do" method ="post">
					<table class="board-row">
						<colgroup>
							<col style="width:15%" />
							<col style="width:35%" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">패스워드</th>
								<td><input type="password" name="password" /></td>
							</tr>
						</tbody>
					</table>
					<div class="btn-area">
						<input type="hidden" name="num" value="${bean.num}" />
						<input type="hidden" name="pass" value="${bean.password}" /> <!-- 위 password랑 pass랑 같을때만 수정되게끔 -->
						<button type="button" class="btn-list" onclick="location.href='BoardListCon.do'">목록</button>
						<input type="submit" class="btn-re" value="삭제">
					</div>
				</form>
			</div>
		</div>
	</body>
</html>