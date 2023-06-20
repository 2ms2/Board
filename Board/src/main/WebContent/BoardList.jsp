<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" type="text/css" href="resource/css/common.css">
	</head>
	
	<body>
		<c:if test="${msg == '1'}">
			<script type="text/javascript">
				alert("수정시 비밀번호가 틀렸습니다!");
			</script>
		</c:if>
		<c:if test="${msg=='2'}">
			<script type="text/javascript">
				alert("삭제시 비밀번호가 틀렸습니다!");
			</script>
		</c:if>
		
		<div class="wrap">
			<div class="conts">
				<div class="btn-area right">
					<button type="button" class="btn-write" onclick="location.href='BoardWriteForm.jsp' ">글쓰기</button>
				</div>
				<table class="board-col">
					<colgroup>
						<col style="width:10%" />
						<col style="width:50%" />
						<col style="width:15%" />
						<col style="width:15%" />
						<col style="width:10%" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">번호</th>
							<th scope="col">제목</th>
							<th scope="col">작성자</th>
							<th scope="col">작성일</th>
							<th scope="col">조회수</th>
						</tr>
					</thead>				
					<tbody>
						<%-- <td>${number}</td> 이렇게 하면 계속 넘버값만 출력되기 때문에 넘버값을 1씩 감소시켜줘야된다. --%>
						<c:set var="number" value="${number}" />
						<c:forEach var="bean" items="${v}"> <!-- v를 받아서 bean을 빼겠다 -->
							<tr>
								<td>${number}</td>
								<td>
									<c:if test="${bean.re_step > 1}">
										<c:forEach var="j" begin="1" end="${(bean.re_step-1)*5}">
											&nbsp;
										</c:forEach>
									</c:if>
									<a href="BoardInfoControl.do?num=${bean.num}">${bean.subject}</a>
								</td>
								<td>${bean.writer}</td>
								<td>${bean.reg_date}</td>
								<td>${bean.readcount}</td>
							</tr>
						<c:set var="number" value="${number - 1}" />	
						</c:forEach>
					</tbody>
				</table>
		
				<!-- 페이지 카운터링 소스 작성 -->
				<c:if test="${count > 0}">
					<c:set var="pageCount" value="${count / pageSize + (count%pageSize == 0 ? 0 : 1) }" />
					<c:set var="startPage" value="${1}" />
					<c:if test="${currentPage % 10 != 0}">
						<!-- 결과를 정수형으로 리턴받아야 하기에 fmt태그 사용 -->
						<fmt:parseNumber var="result" value="${currentPage/10}" integerOnly="true" />
						<c:set var="startPage" value="${result*10+1}" />
					</c:if>
					<c:if test="${currentPage % 10 == 0}">
						<c:set var="startPage" value="${(result-1)*10+1}" />
					</c:if>
					
					<!-- 화면에 보여질 페이지 처리 숫자를 표현  -->
					<c:set var="pageBlock" value="${10}" />
					<c:set var="endPage" value="${startPage+pageBlock-1}" />
					
					<c:if test="${endPage > pageCount}">
						<c:set var="endPage" value="${pageCount}" />
					</c:if>
					
					<!-- 페이징 처리 -->
					<div class="pagination">
						<!-- 이전 버튼 -->
						<c:if test="${startPage > 10}">
							<a href="BoardListCon.do?pageNum=${startPage-10}" class="btn-pre">이전</a>
						</c:if>
						<c:forEach var="i" begin="${startPage}" end="${endPage}">
							<a href="BoardListCon.do?pageNum=${i}">${i}</a>
						</c:forEach>
						<!-- 다음 버튼 -->
						<c:if test="${endPage<pageCount}">
							<a href="BoardListCon.do?pageNum=${startPage+10}" class="btn-next">다음</a>
						</c:if>
					</div>
				</c:if>
			</div>
		</div>
	</body>
</html>