<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Danh mục</title>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/styles.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" />
<style>
.center-buttons {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 10vh;
	width: 100%;
}

.btn-group {
	display: flex;
	gap: 10px;
}
</style>
</head>

<body>
	<%@ include file="layout/header.jsp"%>

	<main>
		<div class="container">
			<div class="row">
				<div class="col-12 text-center my-4">
					<img src="img/tft.jpg" style="height: 40vh" class="card-img-top"
						alt="League of Legends" />
				</div>
			</div>
			<div class="row" id="accountList">
				<c:forEach var="account" items="${gameAccounts}">
					<div class="col-md-4">
						<div class="card mb-4">
							<c:if test="${!empty account.images}">
								<a
									href="${pageContext.request.contextPath}/productDetail?accountId=${account.accountId}">
									<img
									src="${pageContext.request.contextPath}/img/${account.images[0].imageUrl}"
									class="card-img-top" alt="Game Image" />
								</a>
							</c:if>
							<div class="card-body text-center">
								<h5 class="card-title">${account.accountId}</h5>
								<p class="card-text">${account.description}</p>
								<p class="card-text">Price: $${account.price}</p>
								<form style="display: inline-block;" action="${pageContext.request.contextPath}/addToCart"
									method="post">
									<input type="hidden" name="gameAccountId"
										value="${account.accountId}"> <input type="hidden"
										name="returnPage" value="productDetail">
									<button type="submit" class="btn btn-dark">Add to cart</button>
								</form>
								<a href="#" class="btn btn-dark">BUY NOW</a>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
			<div class="pagination">
			    <ul class="pagination">
			        <c:if test="${currentPage > 0}">
			            <li class="page-item"><a class="page-link" href="?page=${currentPage - 1}&gameName=${param.gameName}">Previous</a></li>
			        </c:if>
			        <c:forEach var="i" begin="0" end="${totalPages - 1}">
			            <li class="page-item <c:if test='${i == currentPage}'>active</c:if>">
			                <a class="page-link" href="?page=${i}&gameName=${param.gameName}">${i + 1}</a>
			            </li>
			        </c:forEach>
			        <c:if test="${currentPage < totalPages - 1}">
			            <li class="page-item"><a class="page-link" href="?page=${currentPage + 1}&gameName=${param.gameName}">Next</a></li>
			        </c:if>
			    </ul>
			</div>
		</div>
	</main>

	<!-- Toast container -->
	<div aria-live="polite" aria-atomic="true"
		style="position: relative; z-index: 1050;">
		<div id="loginToast" class="toast" role="alert" aria-live="assertive"
			aria-atomic="true" data-delay="5000">
			<div class="toast-header">
				<strong class="mr-auto">Thông báo</strong> <small class="text-muted">vừa
					xong</small>
				<button type="button" class="ml-2 mb-1 close" data-dismiss="toast"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="toast-body">
				<%=request.getAttribute("msg")%>
			</div>
		</div>
	</div>


	<%@ include file="layout/footer.jsp"%>
	
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

	<%
	String msg = (String) session.getAttribute("msg");
	if (msg != null) {
		request.setAttribute("msg", msg);
		session.removeAttribute("msg");
	}
	%>

	<script>
	    if (typeof jQuery === 'undefined') {
	        console.error('jQuery is not loaded');
	    } else {
	        console.log('jQuery is loaded');
	        $(document).ready(function() {
	            var msg = '<%= request.getAttribute("msg") != null ? request.getAttribute("msg").toString().replaceAll("'", "\\\\'") : "" %>';
	            console.log("Message: " + msg);
	            if (msg !== '') {
	                var toast = $('#loginToast');
	                toast.find('.toast-body').text(msg);
	                toast.toast({ delay: 5000 });
	                toast.toast('show');
	            }
	        });
	    }
	</script>

</body>
</html>
