<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Trang Chủ</title>
    <link
        href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
        rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/styles.css"
        rel="stylesheet" type="text/css" />
    <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" />

    <style>
        #carouselExampleSlidesOnly > div{
            height: 500px;
        }
    </style>

</head>

<body>
    <%@include file="layout/header.jsp"%>

    <main>
        <div class="container">
            <div class="row">
                <div class="col-12 text-center my-4">
                    <div id="carouselExampleSlidesOnly" class="carousel slide" data-bs-ride="carousel">
                      <div class="carousel-inner">
                        <div class="carousel-item active">
                          <img src="img/CRS.jpg" class="d-block w-100" alt="...">
                        </div>
                        <div class="carousel-item">
                          <img src="img/CRS2.png" class="d-block w-100" alt="...">
                        </div>
                        <div class="carousel-item">
                          <img src="img/CRS.jpg" class="d-block w-100" alt="...">
                        </div>
                      </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <div class="card mb-4">
                        <img src="img/Romantic.jpg" class="card-img-top" alt="Valorant" />
                        <div class="card-body text-center">
                            <h5 class="card-title">Lãng mạn</h5>
                            <a href="/products?gameName=Valorant" class="btn btn-dark mt-3">More</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card mb-4">
                        <img
                            src="img/Action.jpg"
                            class="card-img-top" alt="League of Legends" />
                        <div class="card-body text-center">
                            <h5 class="card-title">Hành động</h5>
                            <a href="/products?gameName=LOL" class="btn btn-dark mt-3">More</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card mb-4">
                        <img src="img/Horrified.jpg" class="card-img-top"
                            alt="Team Fight Tactics" />
                        <div class="card-body text-center">
                            <h5 class="card-title">Kinh dị</h5>
                            <a href="/products?gameName=TFT" class="btn btn-dark mt-3">More</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="divider"> <h2>TRANG CHỦ</h2> </div>
            
            <!-- Đổ dữ liệu Movies vào đây -->
            <div class="row">
            	<c:if test="${empty movies}">
				    <p>No movies available.</p>
				</c:if>
                <c:forEach var="movie" items="${movies}">
                    <div class="col-md-3">
                        <div class="card mb-4" style="min-height: 560px;">
                            <img src="${pageContext.request.contextPath}/img/${movie.posterUrl}" class="card-img-top" alt="${movie.title}" />
                            <div class="card-body text-center">
                                <h5 class="card-title">${movie.title}</h5>
                                <p class="card-text">⭐ 
                                    <c:if test="${movie.rating > 0}">
                                        ${movie.rating}/10
                                    </c:if>
                                    <c:if test="${movie.rating <= 0}">
                                        No rating
                                    </c:if>
                                </p>
                                <p class="card-text">Genres: 
                                    <c:forEach var="genre" items="${movie.genres}" varStatus="status">
                                        ${genre}<c:if test="${!status.last}">, </c:if>
                                    </c:forEach>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </main>

    <%@include file="layout/footer.jsp"%>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>

</html>
