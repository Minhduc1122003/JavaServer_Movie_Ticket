<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Product Detail</title>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/styles.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" />
<link rel="stylesheet" href="css/productDetails.css">

</head>

<body>
	<%@include file="layout/header.jsp"%>

	<main>
		<div class="container">
			<div class="container game-account-detail-container">
				<div class="row">
					<div class="col-md-6">
						<div class="image-gallery">
							<c:if test="${!empty gameAccount.images}">
								<img
									src="${pageContext.request.contextPath}/img/${gameAccount.images[0].imageUrl}"
									alt="Game Account Image" class="img-fluid main-image">
							</c:if>
							<div class="thumbnail-images">
								<c:forEach var="image" items="${gameAccount.images}">
									<img
										src="${pageContext.request.contextPath}/img/${image.imageUrl}"
										alt="Thumbnail Image" class="img-thumbnail">
								</c:forEach>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<h2>Mã số: #${gameAccount.accountId}</h2>
						<p class="price">Giá: ${gameAccount.price} VND</p>
						<h4>Tên game: ${gameAccount.gameName}</h4>
						<h4>Mô tả:</h4>
						<p>${gameAccount.description}</p>
						<form style="margin-bottom: 10px" action="${pageContext.request.contextPath}/addToCart"
							method="post">
							<input type="hidden" name="gameAccountId"
								value="${gameAccount.accountId}"> <input type="hidden"
								name="returnPage" value="productDetail">
							<button style="min-width: 120px;" type="submit"
								class="btn btn-dark">Add to cart</button>
						</form>

						<button style="min-width: 120px;" class="btn btn-dark">Mua
							ngay</button>
					</div>
				</div>
			</div>
		</div>
	</main>

	<!-- Toast container -->
	<div aria-live="polite" aria-atomic="true">
		<div id="loginToast" class="toast" role="alert" aria-live="assertive"
			aria-atomic="true">
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

	<%@include file="layout/footer.jsp"%>

	<script>
        document.querySelectorAll('.thumbnail-images img').forEach(img => {
            img.addEventListener('click', function () {
                document.querySelector('.main-image').src = this.src;
            });
        });

        const lightbox = document.getElementById('lightbox');
        const lightboxImg = document.getElementById('lightbox-img');
        const mainImage = document.querySelector('.main-image');
        const close = document.querySelector('.close');

        mainImage.addEventListener('click', function () {
            lightbox.style.display = 'block';
            lightboxImg.src = this.src;
        });

        close.addEventListener('click', function () {
            lightbox.style.display = 'none';
        });

        lightbox.addEventListener('click', function (event) {
            if (event.target !== lightboxImg) {
                lightbox.style.display = 'none';
            }
        });
    </script>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>
