	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>LIÊN HỆ</title>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/styles.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" />
</head>

<body>
	<%@include file="layout/header.jsp"%>

	<main>
		      <div class="container contact-container">
        <div class="row mt-5">
            <div class="col-md-6 contact-info">
            	<img src="img/valorant.jpg" class="card-img-top" alt="Valorant"/>
                <h2 class="mt-2">LIÊN HỆ</h2>
                <hr>
                <h1>XIN CHÀO CÁC GAME THỦ ĐÃ ĐẾN VỚI SHOP ACC VIP!</h1>
                <p>Đây là shop acc uy tín nhất mọi thời đại</p>
                <p><strong>Address:</strong> Quận 12, Thành Phố Hồ Chí Minh</p>
                <p><strong>Phone:</strong> +123 456 7890</p>
                <p><strong>Email:</strong> shopaccvippro@contact.com</p>
            </div>
            <div class="col-md-6">
                <div id="map">
                    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3918.462322180692!2d106.6245299748192!3d10.852397889301026!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752b6c59ba4c97%3A0x535e784068f1558b!2zVHLGsOG7nW5nIENhbyDEkeG6s25nIEZQVCBQb2x5dGVjaG5pYw!5e0!3m2!1svi!2s!4v1721287625012!5m2!1svi!2s" width="540" height="300" style="border:0;" allowfullscreen="" loading="lazy" referrerpol></iframe>
                  </div>
                <form class="contact-form">
                    <div class="form-group">
                        <input type="text" id="name" name="name" class="form-control" placeholder="Tên của bạn..." required>
                    </div>
                    <div class="form-group">
                        <input type="email" id="email" name="email" class="form-control" placeholder="E-mail của bạn..." required>
                    </div>
                    <div class="form-group">
                        <input type="text" id="subject" name="subject" class="form-control" placeholder="Chủ đề..." required>
                    </div>
                    <div class="form-group">
                        <textarea id="message" name="message" class="form-control" rows="5" placeholder="Tin nhắn của bạn..." required></textarea>
                    </div>
                    <button  class="btn btn-danger">GỬI NGAY</button>
                </form>
            </div>
        </div>
    </div> 	
	</main>

	<%@include file="layout/footer.jsp"%>



	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>

</html>
