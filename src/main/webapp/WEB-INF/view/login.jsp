<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Đăng nhập</title>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="css/styles.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" />
<style type="text/css">

</style>
</head>
<body>
	<%@include file="layout/header.jsp"%>
	<div class="modal-content-login">
		<div class="modal-body p-0">
			<div class="row no-gutters">
				<div class="col-md-6 p-5">
					<div
						class="justify-content-between align-items-center modal-tab-button mb-3">
						<button type="button" class="btn btn-link" id="loginModalLabel"
							style="font-weight: bold;">Đăng Nhập</button>
						<button type="button" class="btn btn-link" id="switchToRegister"
							style="opacity: 0.5;">Đăng Ký</button>
					</div>
					<div class="mb-4">
						<p>Đăng nhập để theo dõi đơn hàng, lưu danh sách sản phẩm yêu
							thích và nhận nhiều ưu đãi hấp dẫn</p>
					</div>
					<form action="/loginForm" method="post" id="loginForm">
						<div class="form-group">
							<label for="username">Tài Khoản</label> <input type="text"
								class="form-control" id="username" name="username"
								placeholder="Nhập tên đăng nhập" required>
						</div>
						<div class="form-group">
							<label for="password">Mật khẩu</label> <input type="password"
								class="form-control" id="password" name="password"
								placeholder="Nhập mật khẩu" required>
						</div>
						<a href="#" class="text-primary">Bạn quên mật khẩu?</a>
						<button type="submit" class="btn btn-primary btn-block mt-3">Đăng
							Nhập</button>
					</form>

					<!-- Đăng ký form - ẩn ban đầu -->
					<form action="/register" method="post" id="registerForm"
						style="display: none;">
						<div class="form-group">
							<label for="username">Tài Khoản</label> <input type="text"
								class="form-control" id="regUsername" name="regUsername"
								placeholder="Nhập tên đăng nhập" required>
						</div>
						<div class="form-group">
							<label for="email">Email</label> <input type="email"
								class="form-control" id="email" placeholder="Nhập email"
								name="email" required>
						</div>
						<div class="form-group">
							<label for="regPassword">Mật khẩu</label> <input type="password"
								class="form-control" id="regPassword" name="regPassword"
								placeholder="Nhập mật khẩu" required>
						</div>
						<div class="form-group">
							<label for="confirmPassword">Xác nhận mật khẩu</label> <input
								type="password" class="form-control" id="confirmPassword"
								name="regConfirmPassword" placeholder="Xác nhận mật khẩu"
								required>
						</div>
						<button type="submit" class="btn btn-primary btn-block mt-3">Đăng
							Ký</button>
					</form>
					<div class="divider">Hoặc</div>
					<div class="text-center mt-3">
						<p>Đăng nhập bằng</p>
						<button type="button" class="btn btn-outline-primary">
							<i class="fab fa-google"></i> Google
						</button>
						<button type="button" class="btn btn-outline-primary">
							<i class="fab fa-facebook-f"></i> Facebook
						</button>
					</div>
				</div>
				<div
					class="col-md-6 d-flex align-items-center justify-content-center">
					<img src="img/img-dangnhap.jfif" alt="Illustration"
						class="img-fluid" style="border-radius: 0 30px 30px 0;">
				</div>
			</div>
		</div>
	</div>

	<!-- Toast container -->
	<div aria-live="polite" aria-atomic="true">
		<div id="loginToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-autohide="false">
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

	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

	<%
	Boolean showRegisterForm = (Boolean) request.getAttribute("showRegisterForm");
	%>

	<script>
	$(document).ready(function() {
        if (${showRegisterForm} === true) {
            document.getElementById('loginForm').style.display = 'none';
            document.getElementById('registerForm').style.display = 'block';

            document.getElementById('switchToRegister').style.fontWeight = 'bold';
            document.getElementById('switchToRegister').style.opacity = 'unset';

            document.getElementById('loginModalLabel').style.fontWeight = '';
            document.getElementById('loginModalLabel').style.opacity = '0.5';
        }
    });
	</script>

	<script>
		$(document).ready(function() {
	<%if (request.getAttribute("msg") != null) {%>
		var toast = $('#loginToast');
			toast.addClass('show');
			setTimeout(function() {
				toast.removeClass('show');
				toast.addClass('hide');
			}, 5000);
	<%}%>
		});

		document
				.getElementById('switchToRegister')
				.addEventListener(
						'click',
						function() {
							document.getElementById('loginForm').style.display = 'none';
							document.getElementById('registerForm').style.display = 'block';

							document.getElementById('switchToRegister').style.fontWeight = 'bold';
							document.getElementById('switchToRegister').style.opacity = 'unset';

							document.getElementById('loginModalLabel').style.fontWeight = '';
							document.getElementById('loginModalLabel').style.opacity = '0.5';
						});

		document
				.getElementById('loginModalLabel')
				.addEventListener(
						'click',
						function() {
							document.getElementById('registerForm').style.display = 'none';
							document.getElementById('loginForm').style.display = 'block';

							document.getElementById('switchToRegister').style.fontWeight = '';
							document.getElementById('switchToRegister').style.opacity = '0.5';

							document.getElementById('loginModalLabel').style.fontWeight = 'bold';
							document.getElementById('loginModalLabel').style.opacity = 'unset';
						});

		document
				.getElementById('NavDangNhap')
				.addEventListener(
						'click',
						function() {
							document.getElementById('registerForm').style.display = 'none';
							document.getElementById('loginForm').style.display = 'block';

							document.getElementById('switchToRegister').style.fontWeight = '';
							document.getElementById('switchToRegister').style.opacity = '0.5';

							document.getElementById('loginModalLabel').style.fontWeight = 'bold';
							document.getElementById('loginModalLabel').style.opacity = 'unset';
						});
	</script>
</body>
</html>